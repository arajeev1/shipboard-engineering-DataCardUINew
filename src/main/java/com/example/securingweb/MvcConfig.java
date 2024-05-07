package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("public/login");
		registry.addViewController("/forgotPwd").setViewName("public/forgotPwd");
		///added to test the restricted page
		registry.addViewController("/restricted").setViewName("restricted");
		registry.addViewController("/pmss").setViewName("pmss");
		registry.addViewController("/calendar").setViewName("calendar");
	}

}
