package studentweb.compus.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.JoinColumn;

@Entity
public class Course {
	
    private int id;
    private String name;
    private String content;
	private Set<Student> students = new HashSet<Student>();
    private Set<Teacher> teachers = new HashSet<Teacher>();
    
    public Course() {
    	
    }
    
	public Course(String name,String content) {
		this.name = name;
		this.content=content;
	}
    @Column(length=5000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    

	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="Student_Course",
        joinColumns=
            @JoinColumn(name="Course_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="Student_id", referencedColumnName="id")
        )
	@JsonBackReference
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
     
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="Teacher_Course",
        joinColumns=
            @JoinColumn(name="Course_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="Teacher_id", referencedColumnName="id")
        )
	@JsonBackReference
	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
    
	
    
    
}
