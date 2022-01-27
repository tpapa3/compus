package studentweb.compus.repository;

import studentweb.compus.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer>{
   Course findByName(String name);
}
