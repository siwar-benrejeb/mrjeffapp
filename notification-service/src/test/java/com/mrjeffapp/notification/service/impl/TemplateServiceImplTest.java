package com.mrjeffapp.notification.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceImplTest {
    private static final String BODY_TEMPLATE = "template/mail/Test/Body";
    private static final String SUBJECT_TEMPLATE = "template/mail/Test/Subject";

    private static final String TEMPLATE_PLACEHOLDER_PARAM_A_KEY = "PLACEHOLDER_A";
    private static final String TEMPLATE_PLACEHOLDER_PARAM_A_VALUE = "1";
    private static final String TEMPLATE_PLACEHOLDER_PARAM_B_KEY = "PLACEHOLDER_B";
    private static final String TEMPLATE_PLACEHOLDER_PARAM_B_VALUE = "2";
    private static final String LANGUAGE_CODE = "DEFAULT";
    private static final String LANGUAGE_CODE_ES = "es";

    private static final String EXPECTED_RENDERED_TEMPLATE_VALUE = "DEFAULT:a=1,b=2";
    private static final String EXPECTED_RENDERED_TEMPLATE_VALUE_ES = "ES:a=1,b=2";


    @InjectMocks
    private TemplateServiceImpl templateServiceImpl;

    @Test
    public void testRenderBodyTemplateES() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_A_KEY, TEMPLATE_PLACEHOLDER_PARAM_A_VALUE);
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_B_KEY, TEMPLATE_PLACEHOLDER_PARAM_B_VALUE);

        String renderedTemplate = templateServiceImpl.renderBodyTemplate(BODY_TEMPLATE,LANGUAGE_CODE_ES, parameters);

        assertThat("Template should be rendered", renderedTemplate, is(EXPECTED_RENDERED_TEMPLATE_VALUE_ES));
    }

    @Test
    public void testRenderBodyTemplateDefault() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_A_KEY, TEMPLATE_PLACEHOLDER_PARAM_A_VALUE);
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_B_KEY, TEMPLATE_PLACEHOLDER_PARAM_B_VALUE);

        String renderedTemplate = templateServiceImpl.renderBodyTemplate(BODY_TEMPLATE,LANGUAGE_CODE, parameters);

        assertThat("Template should be rendered", renderedTemplate, is(EXPECTED_RENDERED_TEMPLATE_VALUE));
    }
    @Test
    public void testRenderSubjectTemplateEs(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_A_KEY, TEMPLATE_PLACEHOLDER_PARAM_A_VALUE);
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_B_KEY, TEMPLATE_PLACEHOLDER_PARAM_B_VALUE);

        String renderedTemplate = templateServiceImpl.renderSubjectTemplate(SUBJECT_TEMPLATE,LANGUAGE_CODE_ES, parameters);

        assertThat("Template should be rendered", renderedTemplate, is(EXPECTED_RENDERED_TEMPLATE_VALUE_ES));

    }
    @Test
    public void testRenderSubjectTemplateDefault(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_A_KEY, TEMPLATE_PLACEHOLDER_PARAM_A_VALUE);
        parameters.put(TEMPLATE_PLACEHOLDER_PARAM_B_KEY, TEMPLATE_PLACEHOLDER_PARAM_B_VALUE);

        String renderedTemplate = templateServiceImpl.renderSubjectTemplate(SUBJECT_TEMPLATE,LANGUAGE_CODE, parameters);

        assertThat("Template should be rendered", renderedTemplate, is(EXPECTED_RENDERED_TEMPLATE_VALUE));


    }

}
