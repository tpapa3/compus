package studentweb.compus.securitymvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
     
	  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
	   
	  
	    @Override
	    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
	            throws IOException {
	        String targetUrl = determineTargetUrl(authentication);

	        if (response.isCommitted()) {
	            System.out.println("Can't redirect");
	            return;
	        }

	        redirectStrategy.sendRedirect(request, response, targetUrl);
	    }

	  
	  protected String determineTargetUrl(Authentication authentication) {
		    System.out.println(authentication);
		    String url="";
	        List<String> roles = new ArrayList<String>();           
            Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
           
            for(GrantedAuthority a:authorities) {
	            roles.add(a.getAuthority());
            }
                     
	        if (isStudent(roles)) {
	            url = "/Student";
	        } else if (isTeacher(roles)) {
	            url = "/Teacher";
	        } else if (isSecretary(roles)) {
	            url = "/Secretary";
	        } else {
	            url = "/accessDenied";
	        }

	        return url;     
	    }

	    private boolean isStudent(List<String> roles) {
	        if ( roles.contains("ROLE_STUDENT")) {
	            return true;
	        }
	        return false;
	    }

	    private boolean isTeacher(List<String> roles) {  	
	        if (roles.contains("ROLE_TEACHER")) {
	            return true;
	        }
	        return false;
	    }
	    private boolean isSecretary(List<String> roles) {
	        if (roles.contains("ROLE_SECRETARY")) {
	            return true;
	        }
	        return false;
	    }
	    
   }
	  
	  
