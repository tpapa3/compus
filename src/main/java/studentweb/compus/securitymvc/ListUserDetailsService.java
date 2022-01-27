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
import studentweb.compus.repository.SecretaryRepository;
import studentweb.compus.repository.StudentRepository;
import studentweb.compus.repository.TeacherRepository;

@Service
public class ListUserDetailsService implements UserDetailsService {
    
	@Autowired
    private StudentRepository studentrepo;
	@Autowired 
	private TeacherRepository teacherepo;
	@Autowired 
	private SecretaryRepository secretaryrepo;
	
	private Student student=null;
	private Teacher teacher=null;
	private Secretary secretary=null;
	private Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		grantedAuthorities.clear();
		student = studentrepo.findByUsername(username) ;
		
		if(student==null) {
			 teacher = teacherepo.findByUsername(username);
		
		} else{
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
			 secretary = secretaryrepo.findByUsername(username);
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
			throw new UsernameNotFoundException("Invalid User");
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
