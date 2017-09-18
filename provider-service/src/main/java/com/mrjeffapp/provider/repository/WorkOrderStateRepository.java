package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.WorkOrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WorkOrderStateRepository extends PagingAndSortingRepository<WorkOrderState, String> {

    Page<WorkOrderState> findByActiveTrue(Pageable pageable);

    WorkOrderState findByActiveTrueAndCode(String code);

}
