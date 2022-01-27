package studentweb.compus.securitymvc;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcWebApplicationInitializer extends  
	AbstractAnnotationConfigDispatcherServletInitializer{
	
	 @Override
	  protected Class<?>[] getRootConfigClasses() {
	    return new Class[] { WebSecurityConfig.class,JpaConfig.class };
	  }

	  // Load spring web configuration
	  @Override
	  protected Class<?>[] getServletConfigClasses() {
	    return new Class[] { WebConfig.class };
	  }

	  @Override
	  protected String[] getServletMappings() {
	    return new String[] { "/" };
	  }
}
