package com.ssafypjt.bboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


// 안쓸듯
@Configuration
@OpenAPIDefinition(
		info = @Info(title = "User-Service API 명세서",
				description = "사용자 어플 서비스 API 명세서",
				version = "v1"))
public class SwaggerConfig {


}