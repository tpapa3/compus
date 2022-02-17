package studentweb.compus.controller;

import java.io.IOException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import  studentweb.compus.entity.Course;
import studentweb.compus.entity.Secretary;
import studentweb.compus.entity.Student;
import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.CourseRepository;
import studentweb.compus.repository.SecretaryRepository;
import studentweb.compus.repository.StudentRepository;
import studentweb.compus.repository.TeacherRepository;

@Controller
@RequestMapping("/Secretary")
public class SecretaryController {
    
	@Autowired
	private TeacherRepository teacherrepo;
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private StudentRepository studentrepo;
	@Autowired
	private SecretaryRepository secretaryrepo;
	@PersistenceContext
	EntityManager em;
	
	
	@RequestMapping(value="/TeacherPage",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> TeacherCourse(@RequestParam(name="idt",required=false) String idt,@RequestParam(name="idc",required=false) String idc){
		
	    System.out.println(idt+" "+idc);	
		 int idteacher = Integer.parseInt(idt);
		 int idcourse=Integer.parseInt(idc);
	     Optional<Teacher> teacher = teacherrepo.findById(idteacher);
		 Teacher tutor=teacher.get();
		 Optional<Course> course=courserepo.findById(idcourse);
		 Course elearning = course.get();
		if(tutor!=null && elearning!=null) {
		elearning.getTeachers().add(tutor);
		tutor.getCourses().add(elearning);
		
	     if(tutor.getId()>=0 && elearning.getId()>=0) {
	      em.createNativeQuery("insert into teacher_course(course_id,teacher_id)"+
	      "values(:a,:b)").setParameter("a",elearning.getId())
	                      .setParameter("b", tutor.getId());
	     }else {
	    	 System.out.println("Something went wrong with data of those tables");
	     }
	   }else {
		   System.out.println("not found in database");
	   }
		return ResponseEntity.status(HttpStatus.OK).body("the data saved in database");
	}
	@RequestMapping(value="/StudentPage",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> StudentCourse(@RequestParam(name="ids",required=false) String ids, @RequestParam(name="idc",required=false) String idc){
		
		
	    System.out.println(ids+" "+idc);	
		 int idtudent = Integer.parseInt(ids);
		 int idcourse=Integer.parseInt(idc);
	     Optional<Student> student = studentrepo.findById(idtudent);
		 Student stud= student.get();
		 Optional<Course> course=courserepo.findById(idcourse);
		 Course elearning = course.get();
		if(stud!=null && elearning!=null) {
		elearning.getStudents().add(stud);
		stud.getCourses().add(elearning);
		
	     if(stud.getId()>=0 && elearning.getId()>=0) {
	      em.createNativeQuery("insert into student_course(course_id,student_id)"+
	      "values(:a,:b)").setParameter("a",elearning.getId())
	                      .setParameter("b", stud.getId());
	     }else {
	    	 System.out.println("Something went wrong with data of those tables");
	     }
	   }else {
		   System.out.println("not found in database");
	   }

		return ResponseEntity.status(HttpStatus.OK).body("the data saved in database");
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String SecretaryUpdate(@RequestParam String id,Model model) {
		Optional<Secretary> secretary= secretaryrepo.findById(Integer.parseInt(id));
		Secretary employee = secretary.get();
		model.addAttribute("secretary",employee);
		return "register";
		
	}
	@RequestMapping(value="/changeData",method=RequestMethod.POST)
	public void SecretaryUpload(@RequestParam String id,@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
			 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,
			 @RequestParam("role") String role,HttpServletResponse response) throws IOException {
		        Secretary secretary= secretaryrepo.findById(Integer.parseInt(id)).get();
                secretary.setEmail(email);
                secretary.setName(firstname);
                secretary.setSurname(surname);
                secretary.setUsername(username);
                secretary.setPassword(passwordEncoder().encode(password));
                secretary.setRole(role);
                em.merge(secretary);
                 
		   response.sendRedirect("/login");
	}
	private BCryptPasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public void SecretaryLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        
        response.sendRedirect("/logoutPage");
	}
}
