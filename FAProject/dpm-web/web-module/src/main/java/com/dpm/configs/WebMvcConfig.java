package com.dpm.configs;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	@Description("Thymeleaf template resolver serving HTML 5")
	public ClassLoaderTemplateResolver templateResolver() {

		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");

		return templateResolver;
	}

	@Bean
	@Description("Thymeleaf template engine with Spring integration")
	public SpringTemplateEngine templateEngine() {

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(conditionalCommentDialect());

		return templateEngine;
	}

	@Bean
	@Description("Thymeleaf view resolver")
	public ViewResolver viewResolver() {

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");

		return viewResolver;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	// update by DinhDN 20/01/2020 02:43:00 AM
	/**
	 * Switch language, default language is Vietnamese. If user would like to switch
	 * to other language, use parameter, for example: http://localhost:8080/?lang=en
	 *
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		Locale vietnamLocale = new Locale("vi", "VN");
		sessionLocaleResolver.setDefaultLocale(vietnamLocale);
		// sessionLocaleResolver.setDefaultLocale(Locale.US);
		return sessionLocaleResolver;
	}

	// update by DinhDN 20/01/2020 02:43:00 AM
	/**
	 * switch to a new locale based on the value of the lang parameter appended to a
	 * request
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	// update by DinhDN 20/01/2020 02:43:00 AM
	/**
	 * added to the application's interceptor registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor(localeChangeInterceptor());
	}

	// update by DinhDN 20/01/2020 02:43:00 AM
	/**
	 * get message from file
	 * 
	 * @return
	 */
	@Bean(name = "messageSource")
	public MessageSource getMessageResource() {
		ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
		// Read file i18n/messages_xx.properties
		messageResource.setBasenames("classpath:i18n/messages", "classpath:i18n/pressing-monitor", "classpath:i18n/metal-detector");
		messageResource.setDefaultEncoding("UTF-8");
		
		return messageResource;
	}
	
	// update by LamPQT 29/01/2021 01:19:00 AM
	/**
	 * configue to get value in message file from entity or dto
	 * 
	 * @return
	 */
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator()
	{
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(getMessageResource());
	    return bean;
	}
	// update by LamPQT 29/01/2021 01:19:00 AM
	/**
	 * configue to get value in message file from entity or dto
	 * 
	 * @return
	 */
	@Override
	public Validator getValidator()
	{
	    return validator();
	}
	@Bean
    public IDialect conditionalCommentDialect() {
        return new Java8TimeDialect();
    }
	
}
