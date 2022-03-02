package studentweb.compus.securitymvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ListUserDetailsService listuserDetailsService;

	      
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		  auth.authenticationProvider(authProvider());
	  }
	  
	  @Bean
	  public PasswordEncoder encoder() {
		  return new BCryptPasswordEncoder();
	  }
	  
	  @Bean
	  public DaoAuthenticationProvider authProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	      authProvider.setUserDetailsService(listuserDetailsService);
	      authProvider.setPasswordEncoder(encoder());     
	      return authProvider;
	    }
	  
		@Bean
		public CustomSuccessHandler customSuccessHandler() {
		     return	new CustomSuccessHandler();
		}
	      @Override
		  protected void configure(HttpSecurity http) throws Exception {
			  http
			    .csrf().disable()
			    .authorizeRequests()
			    .antMatchers("/static/**","/js/**","/css/**","/images/**").permitAll()
			    .antMatchers("/","/register").permitAll()
			    .antMatchers("/Student").hasRole("STUDENT")
			    .antMatchers("/Teacher").hasRole("TEACHER")
			    .antMatchers("/Secretary").hasRole("SECRETARY")
			    .and()
			    .formLogin()
			    .loginPage("/login").permitAll()
			    .successHandler(customSuccessHandler())
			    .and()
			    .exceptionHandling().accessDeniedPage("/accessDenied");
			 }


}


