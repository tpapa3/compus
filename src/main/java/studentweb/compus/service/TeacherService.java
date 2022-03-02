package studentweb.compus.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studentweb.compus.entity.Doc;
import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.DocRepository;
import studentweb.compus.repository.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
    private TeacherRepository teacherepo;
	
	@Autowired
	private DocRepository docrepo;
	
	@PersistenceContext
	EntityManager em;
	
	private  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void save(String firstname, String surname,String email, String username,String password,
			String role) {
		try {
		Teacher teacher = new Teacher(email,firstname,passwordEncoder().encode(password),role.toUpperCase(),surname,username);
    	teacherepo.save(teacher);
		} catch(DataAccessException ex) {
			throw new DataIntegrityViolationException("YOU CANNOT PUT NULL VALUE IN DATABSE");
		}
	}
	
	public Teacher findByusername(String username) {
		return teacherepo.findByUsername(username);		 
	}
	
	public List<Teacher> findAllTeacher(){
		return teacherepo.findAll();
	}
	
	public Teacher findByid(Integer id) {
		return teacherepo.findById(id).get();
	}
	
	public void update(String id, String firstname, String surname, String email,String username,
		     String password,String role) {
				 Teacher teacher =findByid(Integer.parseInt(id));
				 teacher.setEmail(email);
		         teacher.setName(firstname);
		         teacher.setSurname(surname);
		         teacher.setUsername(username);
		         teacher.setPassword(passwordEncoder().encode(password));
		         teacher.setRole(role);
		         em.merge(teacher);
		          
			}
	
	public String fileGrade(String fileId , String filegrade) {
		
	    String result=null;
	    
		if(!filegrade.matches("[0-9]+")) {
		  result="you can put only number in this cell";
		}else {
			
	       int grade = Integer.parseInt(filegrade);
	    
	        if(grade < 0 && grade > 10) {
	    	   result="you can put number between 0-9";
		    }else {
			
			Doc file = docrepo.findById(Integer.parseInt(fileId)).get();
			file.setGrade(grade);
			
			 if(grade >= 5) {
				 result="pass";
			 }else {
				 result="fail";
			 }
			 
		   }
	}
		return result;
 }
}
