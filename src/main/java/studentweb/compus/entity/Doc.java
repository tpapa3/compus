package studentweb.compus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="doc")
public class Doc {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",unique=true,nullable=false)
	private int id;
	
	@Column(name="docName",nullable=false)
	private String docName;
	@Column(name="docType",nullable=false)
	private String docType;
	
	@Lob
	@Column(name="data",nullable=false)
	private byte[] data;
	
	@OneToOne
	@JoinColumn(name="student_id", referencedColumnName="id")
	@JsonBackReference
	private Student student;
	
	@OneToOne
	@JoinColumn(name="course_id",referencedColumnName="id")
	@JsonBackReference
	private Course course;
	
    private int grade;	

	public Doc() {
		
	}
	
	public Doc(byte[] data,String docName,String docType,int grade) {
		this.data = data;
		this.docName = docName;
		this.docType = docType;
		this.grade=grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

}
