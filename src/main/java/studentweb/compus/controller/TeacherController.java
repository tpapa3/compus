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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import studentweb.compus.entity.Teacher;
import studentweb.compus.repository.TeacherRepository;

@Controller
@RequestMapping("/Teacher")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherrepo;
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
@Transactional
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

