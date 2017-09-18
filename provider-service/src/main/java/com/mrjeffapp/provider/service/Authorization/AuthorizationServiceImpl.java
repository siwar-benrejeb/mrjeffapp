package com.mrjeffapp.provider.service.Authorization;

import com.mrjeffapp.provider.api.dto.AuthorizationRequest;
import com.mrjeffapp.provider.client.order.OrderServiceClient;
import com.mrjeffapp.provider.client.order.model.CheckoutOrderProductRequest;
import com.mrjeffapp.provider.client.order.model.OrderProductQuantityRequest;
import com.mrjeffapp.provider.client.product.ProductServiceClient;
import com.mrjeffapp.provider.client.product.model.Product;
import com.mrjeffapp.provider.domain.Authorization;
import com.mrjeffapp.provider.domain.AuthorizationState;
import com.mrjeffapp.provider.domain.event.AuthorizationAuthorizedEvent;
import com.mrjeffapp.provider.domain.event.AuthorizationNoAuthorizedEvent;
import com.mrjeffapp.provider.exception.PaymentException;
import com.mrjeffapp.provider.repository.AuthorizationRepository;
import com.mrjeffapp.provider.repository.AuthorizationStateRepository;
import com.mrjeffapp.provider.service.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;
import static com.mrjeffapp.provider.domain.AuthorizationState.AUTHORIZED;
import static com.mrjeffapp.provider.domain.AuthorizationState.NO_AUTHORIZED;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);
    private static final String ID_DELIMITER = ",";

    private final AuthorizationRepository authorizationRepository;
    private final AuthorizationStateRepository authorizationStateRepository;
    private final ApplicationEventPublisher publisher;
    private final OrderServiceClient orderServiceClient;
    private final ProductServiceClient productServiceClient;

    @Autowired
    public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository,
                                    AuthorizationStateRepository authorizationStateRepository,
                                    ApplicationEventPublisher publisher,
                                    OrderServiceClient orderServiceClient,
                                    ProductServiceClient productServiceClient) {
        this.authorizationRepository = authorizationRepository;
        this.authorizationStateRepository = authorizationStateRepository;
        this.publisher = publisher;
        this.orderServiceClient = orderServiceClient;
        this.productServiceClient = productServiceClient;
    }

    @Transactional
    @Override
    public List<Authorization> authorizationAuthorized(AuthorizationRequest authorizationRequest) {

        LOG.debug("authorizationAuthorized: {}", authorizationRequest);

        AuthorizationState authorizedState = getAuthorizationState(AUTHORIZED);

        List<Authorization> authorizations = findAuthorizations(authorizationRequest.getAuthorizationList());

        List<String> ordersId = getOrdersId(authorizations);

        PagedResources<Product> products = findProducts(authorizations);

        Map<String, List<Authorization>> resultList = findAllAuthByOrderId(authorizations, ordersId);

        List<String> authorizationAuthorized = new ArrayList<>();

        resultList.forEach((order, authsList)-> {

            CheckoutOrderProductRequest checkoutOrderProductRequest = new CheckoutOrderProductRequest();
            checkoutOrderProductRequest.setPaymentMethodCode("STRIPE-CUSTOMER");

            Set<OrderProductQuantityRequest> orderProductQuantityRequests = new HashSet<>();

            authsList.forEach(auth -> {

                OrderProductQuantityRequest request = new OrderProductQuantityRequest();
                request.setQuantity(auth.getProduct().getQuantity());
                Product product = products.getContent().stream()
                        .filter(p->p.getId().equals(auth.getProduct().getProductId())).findFirst().get();

                request.setCode(product.getCode());

                orderProductQuantityRequests.add(request);

                checkoutOrderProductRequest.setProducts(orderProductQuantityRequests);

            });

            try{
                orderServiceClient.checkOutOrder(order, checkoutOrderProductRequest);

                authsList.forEach(auth -> {
                    auth.changeAuthorizationState(authorizedState, authorizationRequest.getUserOperator());
                    authorizationRepository.save(auth);
                    authorizationAuthorized.add(auth.getId());
                    BUSINESS_LOG.info("Product has been authorized: " + auth.getId() +  " by UserId: " + authorizationRequest.getUserOperator());


                });

            }catch (PaymentException exception) {
                LOG.error("Invalid payment received: orderId={}, message={}", order, exception.getMessage());
            }

        });


        publisher.publishEvent(new AuthorizationAuthorizedEvent(
                authorizationAuthorized,
                authorizationRequest.getUserOperator()));

        LOG.debug("Authorization product finish");

        return authorizations;
    }

    private Map<String, List<Authorization>> findAllAuthByOrderId(List<Authorization> authorizations, List<String> ordersId) {
        Map<String, List<Authorization>> resultList = new HashMap<>();
        ordersId.stream().forEach(orderId->{
            List<Authorization> auths = authorizations.stream()
                        .filter(authorization -> (authorization.getProduct().getWorkOrder().getOrderId()).equals(orderId))
                        .collect(Collectors.toList());
                        ;
            resultList.put(orderId, auths);

        });
        return resultList;
    }

    private PagedResources<Product> findProducts(List<Authorization> authorizations) {
        String productsId = authorizations.stream()
                .map(authorization -> authorization.getProduct().getProductId())
                .map(Object::toString)
                .distinct()
                .collect(Collectors.joining(ID_DELIMITER));

        return productServiceClient.findProductsByIdIn(productsId, Integer.MAX_VALUE);
    }

    private List<String> getOrdersId(List<Authorization> authorizations) {
        return authorizations.stream().
                   map(auth -> auth.getProduct().getWorkOrder().getOrderId())
                   .distinct()
                   .collect(Collectors.toList());
    }

    private List<Authorization> findAuthorizations(List<String> authorizationsIds) {
        List<Authorization> authorizations = new ArrayList<Authorization>();
        authorizationsIds.forEach(authId->{
            authorizations.add(authorizationRepository.findOne(authId));

        });
        return authorizations;
    }

    @Transactional
    @Override
    public List<Authorization> authorizationNoAuthorized(AuthorizationRequest authorizationRequest) {

        LOG.debug("authorizationNoAuthorized: {}", authorizationRequest);

        AuthorizationState authorizedState = getAuthorizationState(NO_AUTHORIZED);

        List<Authorization> authorizations = new ArrayList<Authorization>();

        authorizationRequest.getAuthorizationList().forEach(authId->{

            Authorization authorization = changeAuthorizationState(authId, authorizedState, authorizationRequest.getUserOperator());
            authorizationRepository.save(authorization);
            authorizations.add(authorization);

            BUSINESS_LOG.info("Product has been no authorized: " + authId +  " by UserId: " + authorizationRequest.getUserOperator());
        });

        publisher.publishEvent(new AuthorizationNoAuthorizedEvent(
                authorizationRequest.getAuthorizationList(),
                authorizationRequest.getUserOperator()
        ));

        LOG.debug("Authorization product finish");

        return authorizations;
    }

    private AuthorizationState getAuthorizationState(String state) {
        return authorizationStateRepository.findByCode(state);
    }

    private Authorization changeAuthorizationState(String authId, AuthorizationState state, String userOperator) {
        Authorization authorization = authorizationRepository.findOne(authId);
        authorization.changeAuthorizationState(state, userOperator);

        return authorization;
    }
}
