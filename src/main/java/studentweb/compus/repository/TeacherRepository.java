package studentweb.compus.repository;

import org.springframework.stereotype.Repository;
import studentweb.compus.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
     Teacher findByUsername(String username);
   }
