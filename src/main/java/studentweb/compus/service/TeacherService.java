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

import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.TeacherRepository;

@Service
@Transactional
public class TeacherService {
	
	@Autowired
    private TeacherRepository teacherepo;
	
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
}
