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
import org.springframework.web.multipart.MultipartFile;

import studentweb.compus.entity.Course;
import studentweb.compus.entity.Doc;
import studentweb.compus.entity.Student;
import studentweb.compus.repository.CourseRepository;
import studentweb.compus.repository.StudentRepository;
import studentweb.compus.service.DocStorageService;
import studentweb.compus.service.StudentService;

@Controller
@RequestMapping("/Student")
public class StudentController {
	
	@Autowired
	private StudentService studentserv;
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private DocStorageService  docservice;
	

@RequestMapping(value="/update", method=RequestMethod.GET)
public String StudentUpdate(@RequestParam String id,Model model) {
	Student student= studentserv.findByid(Integer.parseInt(id));
	
	model.addAttribute("student",student);
	return "register";
	
}
@RequestMapping(value="/courses", method=RequestMethod.GET)
public String StudentCourse(@RequestParam String id,Model model) {
	Student student = studentserv.findByid(Integer.parseInt(id));
	Set<Course> studentCourses= student.getCourses();
	List<Course> courses=courserepo.findAll();
	model.addAttribute("courses", courses);
	model.addAttribute("stdCourses", studentCourses);
	model.addAttribute("student", student);
	return "Studentcourses";
}

@RequestMapping(value="/courseAdd", method=RequestMethod.GET)
public ResponseEntity<Course> AddCourse(@RequestParam(value="idc",required=false) String idc,@RequestParam(value="ids",required=false) String ids) 
{
	Course course = courserepo.findById(Integer.parseInt(idc)).get();
	Student student = studentserv.findByid(Integer.parseInt(ids));
	Course newCourse =studentserv.addCourse(course, student);
	
     return ResponseEntity.status(HttpStatus.OK).body(newCourse);
}

@RequestMapping(value="/courseDelete", method=RequestMethod.GET)
public ResponseEntity<Student> DeleteCourse(@RequestParam(value="idc",required=false) String idc,@RequestParam(value="ids",required=false) String ids){
	 
	 Course course = courserepo.findById(Integer.parseInt(idc)).get();
	 Student student = studentserv.findByid(Integer.parseInt(ids));
	 
	 Student deleteStudentCourse =studentserv.deleteCourse(course,student);
	
	
	return ResponseEntity.status(HttpStatus.OK).body(deleteStudentCourse);
 }

@RequestMapping(value="/homework", method=RequestMethod.GET)
public String Homework(@RequestParam(value="idc",required=false) String idc,@RequestParam(value="ids",required=false)String ids,Model model) {
	 Course course = courserepo.findById(Integer.parseInt(idc)).get();
	 Student student = studentserv.findByid(Integer.parseInt(ids));
	 List<Course> courses= courserepo.findAll();
	
	 model.addAttribute("course",course);
	 model.addAttribute("student",student);
	 model.addAttribute("courses",courses);
	 
	 return "Studenthomework";
}

@RequestMapping(value="/homework", method=RequestMethod.POST)
public ResponseEntity<Doc> UploadFile(@RequestParam(value="idc",required=false) String idc,@RequestParam(value="ids",required=false)String ids,@RequestParam("file") MultipartFile file){
	Doc dbfile= docservice.storeFile(idc, ids, file);
	
	return  ResponseEntity.status(HttpStatus.OK).body(dbfile);
}

@RequestMapping(value="/changeData",method=RequestMethod.POST)
public void StudentUpdate(@RequestParam String id,@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
		 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,
		 @RequestParam("role") String role,HttpServletResponse response) throws IOException {
	
	     studentserv.update(id,firstname,surname,email,username,password, role);
    
	   response.sendRedirect("/login");
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
