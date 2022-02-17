package studentweb.compus.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import studentweb.compus.entity.Course;
import studentweb.compus.entity.Doc;
import studentweb.compus.entity.Student;
import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.CourseRepository;
import studentweb.compus.repository.TeacherRepository;
import studentweb.compus.service.DocStorageService;

@Controller
@RequestMapping("/Teacher")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherrepo;
	@Autowired
	private DocStorageService docservice; 
	@Autowired
	private CourseRepository courserepo;
	@PersistenceContext
	EntityManager em;

@RequestMapping(value="/update", method=RequestMethod.GET)
public String TeacherUpdate(@RequestParam String id,Model model) {
	Optional<Teacher> teacher= teacherrepo.findById(Integer.parseInt(id));
	Teacher tch = teacher.get();
	model.addAttribute("teacher",tch);
	return "register";
	
}
@RequestMapping(value="/changeData",method=RequestMethod.POST)
public void TeacherUpload(@RequestParam String id,@RequestParam("firstname") String firstname, @RequestParam("lastname") String surname,
		 @RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password,
		 @RequestParam("role") String role,HttpServletResponse response) throws IOException {
	     Teacher teacher= teacherrepo.findById(Integer.parseInt(id)).get();
         teacher.setEmail(email);
         teacher.setName(firstname);
         teacher.setSurname(surname);
         teacher.setUsername(username);
         teacher.setPassword(passwordEncoder().encode(password));
         teacher.setRole(role);
         em.merge(teacher);
          
	   response.sendRedirect("/login");
}

@RequestMapping(value="/homework", method=RequestMethod.GET)
public String Homework(@RequestParam(value="idc",required=false) String idc,@RequestParam(value="idt",required=false)String idt,Model model) {
	 
	 Teacher teacher = teacherrepo.findById(Integer.parseInt(idt)).get();
	 Course course = courserepo.findById(Integer.parseInt(idc)).get();
	 List<Course> courses = courserepo.findAll();
	 Set<Doc> files = docservice.getFiles(idc);
	 
	 model.addAttribute("files",files);
	 model.addAttribute("teacher",teacher);
	 model.addAttribute("course",course);
	 model.addAttribute("courses", courses);
	 
	 return "Teacherhomework";
}

@RequestMapping(value="/downloadfile/{id}",method=RequestMethod.GET)
public ResponseEntity<ByteArrayResource> getFile(@PathVariable String id)  {
  Doc fileDB = null;
try {
	fileDB = docservice.getFile(id);
} catch (FileNotFoundException e) {
	e.printStackTrace();
}
  return ResponseEntity.ok()
	  .contentType(MediaType.parseMediaType(fileDB.getDocType()))
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getDocName() + "\"")
      .body(new ByteArrayResource(fileDB.getData()));
}

private BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

@RequestMapping(value="/logout",method=RequestMethod.GET)
public void TeacherLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	HttpSession session= request.getSession(false);
    SecurityContextHolder.getContext().setAuthentication(null);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
            cookie.setValue("");
            response.addCookie(cookie);
        }
    response.sendRedirect("/logoutPage");
}
}

