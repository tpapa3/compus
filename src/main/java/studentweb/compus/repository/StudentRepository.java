package studentweb.compus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studentweb.compus.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{
	Student findByUsername(String username);

}
