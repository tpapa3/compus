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
import studentweb.compus.service.SecretaryService;
import studentweb.compus.service.StudentService;
import studentweb.compus.service.TeacherService;

@Controller
@RequestMapping("/Secretary")
public class SecretaryController {
    
	@Autowired
	private TeacherService teacherserv;
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private StudentService studentserv;
	@Autowired
	private SecretaryService secretaryserv;
	@PersistenceContext
	EntityManager em;
	
	
	@RequestMapping(value="/TeacherPage",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> TeacherCourse(@RequestParam(name="idt",required=false) String idt,@RequestParam(name="idc",required=false) String idc){
		
	     Teacher teacher = teacherserv.findByid(Integer.parseInt(idt));	 
		 Course course=courserepo.findById(Integer.parseInt(idc)).get();
		 
		 secretaryserv.teacherCourse(teacher,course);
		 
		return ResponseEntity.status(HttpStatus.OK).body("the data saved in database");
	}
	@RequestMapping(value="/StudentPage",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> StudentCourse(@RequestParam(name="ids",required=false) String ids, @RequestParam(name="idc",required=false) String idc){
		
	     Student student = studentserv.findByid(Integer.parseInt(ids));
		 Course course=courserepo.findById(Integer.parseInt(idc)).get();
		 
		 secretaryserv.studentCourse(student,course);
		 
		return ResponseEntity.status(HttpStatus.OK).body("the data saved in database");
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String SecretaryUpdate(@RequestParam String id,Model model) {
		Secretary secretary= secretaryserv.findByid(Integer.parseInt(id));
		
		model.addAttribute("secretary",secretary);
		return "register";
		
	}
	@RequestMapping(value="/changeData",method=RequestMethod.POST)
	public void SecretaryUpload(@RequestParam String id,@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
			 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,
			 @RequestParam("role") String role,HttpServletResponse response) throws IOException {
		       
	    
		   secretaryserv.update(id,firstname,surname,email,username,password, role);
                 
		   response.sendRedirect("/login");
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
