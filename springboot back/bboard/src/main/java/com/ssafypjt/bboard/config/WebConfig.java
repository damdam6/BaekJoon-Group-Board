package com.ssafypjt.bboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// static에 자료가 없다면 해당 코드를 추가하지 않아도 된다
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//  인터셉터 사용 안함
		// 내가 원하는 customInterceptor를 만들어서 등록하면 됨
//		registry.addInterceptor(customInterceptor).addPathPatterns("/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("https://solved.ac/api/v3", "http://localhost:5173")
				.allowedMethods("GET", "POST").allowCredentials(true); // 쿠키 인증 요청 허용
	}
	
	
	
	

}