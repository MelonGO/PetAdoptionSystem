package com.pet.beans;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class ConstantsInitializer {
	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    CharacterEncodingFilter filter = new CharacterEncodingFilter();
	    filter.setEncoding("UTF-8");
	    filter.setForceEncoding(true);
	    registrationBean.setFilter(filter);
	    return registrationBean;
	}
}
