package studentweb.compus.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import studentweb.compus.entity.Course;
import studentweb.compus.entity.Student;
import studentweb.compus.repository.CourseRepository;
import studentweb.compus.repository.StudentRepository;

@Controller
@RequestMapping("/Student")
public class StudentController {
	
	@Autowired
	private StudentRepository studentrepo;
	@Autowired
	private CourseRepository courserepo;
	@PersistenceContext
	EntityManager em;

@RequestMapping(value="/update", method=RequestMethod.GET)
public String StudentUpdate(@RequestParam String id,Model model) {
	Optional<Student> student= studentrepo.findById(Integer.parseInt(id));
	Student std = student.get();
	model.addAttribute("student",std);
	return "register";
	
}
@RequestMapping(value="/courses", method=RequestMethod.GET)
public String StudentCourse(@RequestParam String id,Model model) {
	Student student = studentrepo.findById(Integer.parseInt(id)).get();
	Set<Course> studentCourses= student.getCourses();
	List<Course> courses=courserepo.findAll();
	model.addAttribute("courses", courses);
	model.addAttribute("stdCourses", studentCourses);
	model.addAttribute("student", student);
	return "Studentcourses";
}

@RequestMapping(value="/courseAdd", method=RequestMethod.GET)
@Transactional
public ResponseEntity<Course> AddCourse(@RequestParam(value="idc",required=false) Integer idc,@RequestParam(value="ids",required=false) Integer ids) 
{
	Course course = courserepo.findById(idc).get();
	Student student = studentrepo.findById(ids).get();
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
     return ResponseEntity.status(HttpStatus.OK).body(course);
}

@RequestMapping(value="/courseDelete", method=RequestMethod.GET)
@Transactional
public ResponseEntity<Student> DeleteCourse(@RequestParam(value="idc",required=false) Integer idc,@RequestParam(value="ids",required=false) Integer ids){
	 
	 Course course = courserepo.findById(idc).get();
	 Student student = studentrepo.findById(ids).get();
	 Set<Course> studentCourses = student.getCourses();
	 
	Query q =em.createNativeQuery("DELETE FROM student_course sc where sc.course_id=? and sc.student_id=?");
	q.setParameter(1, course.getId());
	q.setParameter(2, student.getId());

	
	studentCourses.remove(course);
	course.getStudents().remove(student);
	
	return ResponseEntity.status(HttpStatus.OK).body(student);
 }
 
@RequestMapping(value="/changeData",method=RequestMethod.POST)
@Transactional
public void StudentUpload(@RequestParam String id,@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
		 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,
		 @RequestParam("role") String role,HttpServletResponse response) throws IOException {
	     Student student= studentrepo.findById(Integer.parseInt(id)).get();
         student.setEmail(email);
         student.setName(firstname);
         student.setSurname(surname);
         student.setUsername(username);
         student.setPassword(passwordEncoder().encode(password));
         student.setRole(role);
         em.merge(student);
          
	   response.sendRedirect("/login");
}
private BCryptPasswordEncoder passwordEncoder() {
	// TODO Auto-generated method stub
	return new BCryptPasswordEncoder();
}

@RequestMapping(value="/logout",method=RequestMethod.GET)
public void StudentLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	HttpSession session= request.getSession(false);
    SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null) {
        	System.out.println(auth);
        }else {
        	System.out.println("tha auth is deleted");
        }
    
    response.sendRedirect("/logoutPage");
}

}
