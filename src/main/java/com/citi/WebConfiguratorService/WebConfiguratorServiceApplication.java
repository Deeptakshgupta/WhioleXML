package com.citi.WebConfiguratorService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class WebConfiguratorServiceApplication extends SpringBootServletInitializer  {

	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
	    return builder.sources(WebConfiguratorServiceApplication.class);
	  }
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
	    super.onStartup(servletContext);

	     }
	public static void main(String[] args) {
		SpringApplication.run(WebConfiguratorServiceApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				 registry.addMapping("/**")
		            .allowedOrigins("http://localhost:4200")
		            .allowedMethods("GET")
		            .allowedHeaders("content-type", "X-Requested-With", "accept", "origin", "access-control-request-method",
	                        "access-control-request-headers")
	                .exposedHeaders("access-control-allow-origin", "access-control-allow-credentials")
	                .allowCredentials(true).maxAge(3600);
			}
		};
	}
	@Bean
    public Docket apiSwag() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()
          .paths(PathSelectors.any())
          .apis(RequestHandlerSelectors.basePackage("com.citi.WebConfiguratorService"))              
          .build();                                           
    }
	
	
}

