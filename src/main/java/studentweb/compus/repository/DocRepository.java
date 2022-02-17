package studentweb.compus.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import studentweb.compus.entity.Doc;

public interface DocRepository extends JpaRepository<Doc,Integer>{
   
   @Query("select d from Doc d where d.student.id=:student_id")
   Set<Doc>  FindByStudentId(@Param("student_id")Integer student_id);
   
   @Query("select d from Doc d where d.course.id=:course_id")
   Set<Doc>  FindByCourseId(@Param("course_id")Integer course_id);
}
