package studentweb.compus.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import studentweb.compus.entity.Course;
import studentweb.compus.entity.Secretary;
import studentweb.compus.entity.Student;
import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.CourseRepository;
import studentweb.compus.repository.SecretaryRepository;
import studentweb.compus.repository.TeacherRepository;
import studentweb.compus.securitymvc.CustomUserDetails;
import studentweb.compus.service.SecretaryService;
import studentweb.compus.service.StudentService;
import studentweb.compus.service.TeacherService;


@Controller
public class LoginController {
	
	@Autowired
	private StudentService studentserv;
	@Autowired
	private TeacherService teacherserv;
	@Autowired
	private SecretaryService secretaryserv;
	@Autowired
	private CourseRepository courserepo;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	 public String welcome() {
	    return "welcome";
	  } 
	@RequestMapping(value="/",method=RequestMethod.POST)
		 public String welcomeAgain(@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
				 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("role") String role ) {
		        if(role.equalsIgnoreCase("Student")) {
		        	studentserv.save(firstname, surname, email, username, password, role);
		        }
		        else if(role.equalsIgnoreCase("Secretary")) {
		        	secretaryserv.save(firstname, surname, email, username, password, role);
		        }
		        else if(role.equalsIgnoreCase("Teacher")) {
		        	teacherserv.save(firstname, surname, email, username, password, role);
		        }
		        
			    return "welcome";
			  } 

	
	@RequestMapping(value="/Student", method=RequestMethod.GET)
	 public String Studentpage(Model model, Authentication authentication) {
		CustomUserDetails userdetails=(CustomUserDetails) authentication.getPrincipal();
		String username = userdetails.getUsername();
		Student student = studentserv.findByusername(username);
		System.out.println(student);
	    Set<Course> courses = student.getCourses();
	    model.addAttribute("courses", courses);
	    model.addAttribute("student",student);
	    return "Studenthome";
		
	  }
	
	@RequestMapping(value="/Secretary", method=RequestMethod.GET)
	 public String Secretarypage(Model model,Authentication authentication) {
		List<Student> students=studentserv.findAllStudent();
		List<Teacher> teachers=teacherserv.findAllTeacher();
		List<Course> courses=courserepo.findAll();
		CustomUserDetails userdetails=(CustomUserDetails) authentication.getPrincipal();
		String username = userdetails.getUsername();
		Secretary secretary=secretaryserv.findByusername(username);
		model.addAttribute("secretary", secretary);
		model.addAttribute("students",students);
		model.addAttribute("teachers", teachers);
		model.addAttribute("courses", courses);
	    return "Secretaryhome";
		
	  }
	
	@RequestMapping(value="/Teacher", method=RequestMethod.GET)
	 public String Teacherpage(Model model,Authentication authentication) {
		CustomUserDetails userdetails=(CustomUserDetails) authentication.getPrincipal();
		String username = userdetails.getUsername();
		Teacher teacher = teacherserv.findByusername(username);
	    Set<Course> courses = teacher.getCourses();
	    model.addAttribute("courses", courses);
	    model.addAttribute("teacher",teacher);
	    return "Teacherhome";
	  }
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	 public String login() {
	    return "login";
	  }
	
	@RequestMapping(value="/logoutPage",method=RequestMethod.GET)
	public String logout() {
		return "logout";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	 public String register() {
	    return "register";
	  }
	
}
