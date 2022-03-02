package studentweb.compus.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studentweb.compus.entity.Course;
import studentweb.compus.entity.Student;
import studentweb.compus.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentrepo;
    
	@PersistenceContext
	EntityManager em;
	
	private  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void save(String firstname, String surname,String email, String username,String password,
			String role) {
		try {
		Student student = new Student(email,firstname,passwordEncoder().encode(password),role.toUpperCase(),surname,username);
    	studentrepo.save(student);
		} catch(DataAccessException ex) {
			throw new DataIntegrityViolationException("YOU CANNOT PUT NULL VALUE IN DATABSE");
		}
	}
	
	public Student findByusername(String username) {
		return studentrepo.findByUsername(username);		 
	}
	
	public List<Student> findAllStudent(){
		return studentrepo.findAll();
	}
	
	public Student findByid(Integer id) {
		return studentrepo.findById(id).get();
	}
	
	public Course addCourse(Course course,Student student) {
		Set<Course> studentCourses= student.getCourses();
		for(Course stdCourse: studentCourses) {
			   if(course == stdCourse) {
				System.out.println("Choose other course");
				course=null;
			   }
			}
			
		     if(course!=null) {
				studentCourses.add(course);
				course.getStudents().add(student);
				em.createNativeQuery("insert into student_course(course_id,student_id)"+
					      "values(:a,:b)").setParameter("a",course.getId())
					                      .setParameter("b", student.getId());
			
			}
		    return course; 
	}
	
	public Student deleteCourse(Course course,Student student) {
		 
			Query q =em.createNativeQuery("DELETE FROM student_course sc where sc.course_id=? and sc.student_id=?");
			q.setParameter(1, course.getId());
			q.setParameter(2, student.getId());
			
			student.getCourses().remove(course);
			course.getStudents().remove(student);
			
		   return student;
	}
	
	public void update(String id, String firstname, String surname, String email,String username,
     String password,String role) {
		 Student student =findByid(Integer.parseInt(id));
		 student.setEmail(email);
         student.setName(firstname);
         student.setSurname(surname);
         student.setUsername(username);
         student.setPassword(passwordEncoder().encode(password));
         student.setRole(role);
         em.merge(student);
          
	}
}
