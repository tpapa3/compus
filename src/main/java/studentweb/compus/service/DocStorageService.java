package studentweb.compus.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import studentweb.compus.entity.Course;
import studentweb.compus.entity.Doc;
import studentweb.compus.entity.Student;
import studentweb.compus.exception.FileStorageException;
import studentweb.compus.exception.NoDataFoundException;
import studentweb.compus.repository.CourseRepository;
import studentweb.compus.repository.DocRepository;
import studentweb.compus.repository.StudentRepository;

@Service
public class DocStorageService {

	@Autowired
	private DocRepository docRepository;
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private StudentRepository studentrepo;
	@PersistenceContext
	EntityManager em;
	
	  public Doc storeFile(String courseId,String studentId,MultipartFile file) {
           
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	        	
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            Doc docfile = new Doc(file.getBytes(),fileName,file.getContentType());
	            Course course = courserepo.findById(Integer.parseInt(courseId)).get();
	            Student student = studentrepo.findById(Integer.parseInt(studentId)).get();         
	            docfile.setCourse(course); 
	            docfile.setStudent(student);
	            Doc dbfile = docRepository.save(docfile);
	            
	            Set<Doc> Cfiles= docRepository.FindByCourseId(course.getId());
	            Set<Doc> Sfiles = docRepository.FindByStudentId(student.getId());
	            student.setFiles(Sfiles);
	            course.setFiles(Cfiles);
	            
	            return dbfile;
	            
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
	        }
	    }
        
	 
	    public Doc getFile(String fileId) throws FileNotFoundException {
	    	
         Doc dbfile=docRepository.findById(Integer.parseInt(fileId)).get();
	          
	     if(dbfile==null) {
	    	 throw new FileNotFoundException("File not found with id"+fileId);
	     }
	   
	     return dbfile;
	    }
	    
	    public Set<Doc> getFiles(String courseId){
	    	Course course = courserepo.findById(Integer.parseInt(courseId)).get();
	    	Set<Doc> files = course.getFiles();
	    	
	    	return files;
	    } 	
}
