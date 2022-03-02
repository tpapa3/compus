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

import studentweb.compus.entity.Course;
import studentweb.compus.entity.Secretary;
import studentweb.compus.entity.Student;
import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.SecretaryRepository;

@Service
public class SecretaryService {
    
	@Autowired
	private SecretaryRepository secretaryrepo;
	
	@PersistenceContext
	EntityManager em;
	
	private  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void save(String firstname, String surname,String email, String username,String password,
			String role) {
		try {
		Secretary secretary = new Secretary(email,firstname,passwordEncoder().encode(password),role.toUpperCase(),surname,username);
    	secretaryrepo.save(secretary);
		} catch(DataAccessException ex) {
			throw new DataIntegrityViolationException("YOU CANNOT PUT NULL VALUE IN DATABSE");
		}
	}
	
	public Secretary findByusername(String username) {
		return secretaryrepo.findByUsername(username);		 
	}
	
	public List<Secretary> findAllSecretary(){
		return secretaryrepo.findAll();
	}
	
	public Secretary findByid(Integer id) {
		return secretaryrepo.findById(id).get();
	}
	
	public void update(String id, String firstname, String surname, String email,String username,
		     String password,String role) {
				 Secretary secretary =findByid(Integer.parseInt(id));
				 secretary.setEmail(email);
		         secretary.setName(firstname);
		         secretary.setSurname(surname);
		         secretary.setUsername(username);
		         secretary.setPassword(passwordEncoder().encode(password));
		         secretary.setRole(role);
		         em.merge(secretary);
		          
			}
	
	public void teacherCourse(Teacher teacher,Course course) {
		
		
	     em.createNativeQuery("insert into teacher_course(course_id,teacher_id)"+
	      "values(:a,:b)").setParameter("a",course.getId())
	                      .setParameter("b", teacher.getId());
	     
	     course.getTeachers().add(teacher);
		 teacher.getCourses().add(course);
	}
	
	public void studentCourse(Student student,Course course) {
		
		      em.createNativeQuery("insert into student_course(course_id,student_id)"+
		      "values(:a,:b)").setParameter("a",course.getId())
		                      .setParameter("b",student.getId());
		    

             course.getStudents().add(student);
		     student.getCourses().add(course);
	}
}
