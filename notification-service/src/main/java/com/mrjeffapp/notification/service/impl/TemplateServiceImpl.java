package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.service.TemplateService;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final static Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);
    private static final String LANGUAGE_CODE_ES = "es";

    @Override
    public String renderBodyTemplate(String templatePath, String languageCode, Map<String, Object> parameters) {
        log.debug("Rendering bodyTemplate: bodyTemplate={}", templatePath);

        JtwigTemplate template = JtwigTemplate.fileTemplate(getBodyFile(templatePath, languageCode));
        JtwigModel model = JtwigModel.newModel();

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            log.debug("adding value: key={} value={}", entry.getKey(), entry.getValue());
            model.with(entry.getKey(), entry.getValue());
        }

        String bodyTemplateRendered = template.render(model);
        log.debug("bodyTemplate rendered: template={}, content=\n{}", templatePath, bodyTemplateRendered);

        return bodyTemplateRendered;
    }

    @Override
    public String renderSubjectTemplate(String templatePath, String languageCode, Map<String, Object> parameters) {
        log.debug("Rendering subjectTemplate: subjectTemplate={}", templatePath);


        JtwigTemplate template = JtwigTemplate.fileTemplate(getSubjectFile(templatePath, languageCode));
        JtwigModel model = JtwigModel.newModel();

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            log.debug("adding value: key={} value={}", entry.getKey(), entry.getValue());
            model.with(entry.getKey(), entry.getValue());
        }

        String subjectTemplateRendered = template.render(model);
        log.debug("subjectTemplate rendered: template={}, content=\n{}", templatePath, subjectTemplateRendered);

        return subjectTemplateRendered;

    }

    public File getBodyFile(String templatePath, String languageCode) {
        String languageCodeLowerCase = languageCode.toLowerCase();
        URL url = getClass().getClassLoader().getResource(templatePath);
        URL defaultUrl = getClass().getClassLoader().getResource(String.format("%s/template.twig", templatePath));
        URL spainishUrl = getClass().getClassLoader().getResource(String.format("%s/template_%s.twig", templatePath, languageCodeLowerCase));

        if (url == null) {
            log.error("Folder does not exist :{}", templatePath);
        } else if ((spainishUrl != null) && ((languageCodeLowerCase.equalsIgnoreCase(LANGUAGE_CODE_ES))))
            return new File(spainishUrl.getFile());
        return new File(defaultUrl.getFile());

    }


    public File getSubjectFile(String templatePath, String languageCode) {
        String languageCodeLowerCase = languageCode.toLowerCase();
        URL url = getClass().getClassLoader().getResource(templatePath);
        URL defaultUrl = getClass().getClassLoader().getResource(String.format("%s/subject.twig", templatePath));
        URL spainishUrl = getClass().getClassLoader().getResource(String.format("%s/subject_%s.twig", templatePath, languageCodeLowerCase));

        if (url == null) {
            log.error("Folder does not exist :{}", templatePath);
        } else if ((spainishUrl != null) && ((languageCodeLowerCase.equalsIgnoreCase(LANGUAGE_CODE_ES))))
            return new File(spainishUrl.getFile());
        return new File(defaultUrl.getFile());


    }
}







