package studentweb.compus.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
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
import studentweb.compus.repository.StudentRepository;
import studentweb.compus.repository.TeacherRepository;
import studentweb.compus.securitymvc.CustomUserDetails;


@Controller
public class LoginController {
	
	@Autowired
	private StudentRepository studentrepo;
	@Autowired
	private TeacherRepository teacherepo;
	@Autowired
	private SecretaryRepository secretaryrepo;
	@Autowired
	private CourseRepository courserepo;
	
	private  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	 public String welcome() {
	    return "welcome";
	  } 
	@RequestMapping(value="/",method=RequestMethod.POST)
		 public String welcomeAgain(@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
				 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("role") String role ) {
		        if(role.equalsIgnoreCase("Student")) {
		        	Student student = new Student(email,firstname,passwordEncoder().encode(password),role.toUpperCase(),surname,username);
		        	studentrepo.save(student);
		        }
		        else if(role.equalsIgnoreCase("Secretary")) {
		        	Secretary secretary = new Secretary(email,firstname,passwordEncoder().encode(password),role.toUpperCase(),surname,username);
		        	secretaryrepo.save(secretary);
		        }
		        else if(role.equalsIgnoreCase("Teacher")) {
		        	Teacher teacher = new Teacher(email,firstname,passwordEncoder().encode(password),role.toUpperCase(),surname,username);
		        	teacherepo.save(teacher);
		        }
		        
			    return "welcome";
			  } 

	
	@RequestMapping(value="/Student", method=RequestMethod.GET)
	 public String Studentpage(Model model, Authentication authentication) {
		CustomUserDetails userdetails=(CustomUserDetails) authentication.getPrincipal();
		String username = userdetails.getUsername();
		Student student = studentrepo.findByUsername(username);
	    Set<Course> courses = student.getCourses();
	    model.addAttribute("courses", courses);
	    model.addAttribute("student",student);
	    return "Studenthome";
		
	  }
	
	@RequestMapping(value="/Secretary", method=RequestMethod.GET)
	 public String Secretarypage(Model model,Authentication authentication) {
		List<Student> students=studentrepo.findAll();
		List<Teacher> teachers=teacherepo.findAll();
		List<Course> courses=courserepo.findAll();
		CustomUserDetails userdetails=(CustomUserDetails) authentication.getPrincipal();
		String username = userdetails.getUsername();
		Secretary secretary=secretaryrepo.findByUsername(username);
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
		Teacher teacher = teacherepo.findByUsername(username);
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
