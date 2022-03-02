package studentweb.compus.securitymvc;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import studentweb.compus.entity.Secretary;
import studentweb.compus.entity.Student;
import studentweb.compus.entity.Teacher;
import studentweb.compus.service.SecretaryService;
import studentweb.compus.service.StudentService;
import studentweb.compus.service.TeacherService;

@Service
public class ListUserDetailsService implements UserDetailsService {
    
	@Autowired
    private StudentService studentserv;
	@Autowired 
	private TeacherService teacherserv;
	@Autowired 
	private SecretaryService secretaryserv;
	
	private Student student=null;
	private Teacher teacher=null;
	private Secretary secretary=null;
	private Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		grantedAuthorities.clear();
		student = studentserv.findByusername(username);
	
		
		if(student==null) {
			teacher  = teacherserv.findByusername(username);
		}else {
			if(student.getRole().contains(",")) {
			  int count=StringUtils.countOccurrencesOf(student.getRole(),",");
			  String[] role= student.getRole().split(",",count);
			  for (String studentrole: role) {
				  grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+studentrole));
			  }
			}else {
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+student.getRole()));
			}
			return new CustomUserDetails(student.getName(),student.getSurname(),student.getEmail(),student.getUsername(),student.getPassword(),grantedAuthorities);
			}	
		if(teacher==null) {
			secretary =secretaryserv.findByusername(username);
		}else {
			if(teacher.getRole().contains(",")) {
				  int count=StringUtils.countOccurrencesOf(teacher.getRole(),",");
				  String[] role= teacher.getRole().split(",",count);
				  for (String teacherrole: role) {
					  grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+teacherrole));
				  }
				}else {
					grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+teacher.getRole()));
				}
			return new CustomUserDetails(teacher.getName(),teacher.getSurname(),teacher.getEmail(),teacher.getUsername(),teacher.getPassword(),grantedAuthorities);
		}
	
		if(secretary ==null) {
			try {
				throw new UsernameNotFoundException("not found");
			}catch(RuntimeException ex ) {
				throw new UsernameNotFoundException("invalid user");
			}
		}else {
			if(secretary.getRole().contains(",")) {
				  int count=StringUtils.countOccurrencesOf(secretary.getRole(),",");
				  String[] role= secretary.getRole().split(",",count);
				  for (String secretaryrole: role) {
					  grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+secretaryrole));
				  }
				}else {
					grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+secretary.getRole()));
				}
			return new CustomUserDetails(secretary.getName(),secretary.getSurname(),secretary.getEmail(),secretary.getUsername(),secretary.getPassword(),grantedAuthorities);
		}				

	}
}
