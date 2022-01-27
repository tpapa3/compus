package studentweb.compus.securitymvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"studentweb.compus.controller"})
public class WebConfig implements WebMvcConfigurer{
     
	
	 @Override
	  public void configureViewResolvers(ViewResolverRegistry registry) {
	    registry.jsp().prefix("/WEB-INF/views/").suffix(".jsp");
	  }
	 
	 @Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		 registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		 registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
	  
	 }
}
	 
	 
