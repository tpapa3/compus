package studentweb.compus.repository;


import studentweb.compus.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface SecretaryRepository extends JpaRepository<Secretary,Integer>{
    Secretary findByUsername(String username);
}
