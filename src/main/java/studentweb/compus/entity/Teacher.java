package studentweb.compus.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;





@Entity
@Table(name="teacher")
public class Teacher {
    

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstname;
	private String surname;
	private String email;
	private String username;
	private String password;
	private String role;
	
	
	private Set<Course> courses = new HashSet<Course>();
	
	@ManyToMany(mappedBy="teachers")
	@JsonManagedReference
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	public Teacher() {
		
	}
	public Teacher(String email, String firstname,String password,String role,String surname,String username) {
		this.email=email;
		this.firstname=firstname;
		this.password=password;
		this.role=role;
		this.surname=surname;
		this.username=username;
	}
	

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false,unique =true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="firstname",nullable=false)
	public String getName() {
		return firstname;
	}
	public void setName(String name) {
		this.firstname = name;
	}
	@Column(name="surname",nullable=false)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@Column(name="email",nullable=false, unique=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="username",nullable=false,unique=true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="password",nullable=false,unique=true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="role",nullable=false,unique=false)
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
