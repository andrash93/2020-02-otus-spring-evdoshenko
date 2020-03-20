package ru.otus.testing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import ru.otus.testing.dao.QuestionDao;
import ru.otus.testing.dao.QuestionDaoCsv;
import ru.otus.testing.service.LocalizationService;
import ru.otus.testing.service.LocalizationServiceImpl;
import ru.otus.testing.service.QuestionService;
import ru.otus.testing.service.QuestionServiceImpl;
import ru.otus.testing.service.SurveyService;
import ru.otus.testing.service.SurveyServiceImpl;

@Configuration
public class SpringConfig {

    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }

    @Bean
    public LocalizationService localizationService(@Autowired LocalizationProperties localizationProperties) {
        return new LocalizationServiceImpl(localizationProperties);
    }

    @Bean
    public QuestionService questionService(@Autowired QuestionDao questionDao) {
        return new QuestionServiceImpl(questionDao);
    }

    @Bean
    public QuestionDao questionDao(@Autowired ResourceLoader resourceLoader, @Autowired LocalizationService localizationService) {
        return new QuestionDaoCsv(resourceLoader, localizationService);
    }

    @Bean
    public SurveyService surveyService(@Autowired QuestionService questionService, @Autowired MessageSource messageSource, @Autowired LocalizationService localizationService, @Autowired SurveyProperties surveyProperties) {
        return new SurveyServiceImpl(questionService, messageSource, localizationService, surveyProperties);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasename("classpath:i18n/bundle/messages");
        ret.setDefaultEncoding("utf-8");
        ret.setFallbackToSystemLocale(false);
        return ret;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
