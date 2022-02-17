package studentweb.compus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import studentweb.compus.repository.DocRepository;

@SpringBootApplication(scanBasePackages= {"studentweb.compus.*"})
public class CompusApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(CompusApplication.class, args);
	}

}