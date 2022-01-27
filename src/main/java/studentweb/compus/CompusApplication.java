package studentweb.compus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"studentweb.compus.*"})
public class CompusApplication  {
	
	
	public static void main(String[] args) {
		SpringApplication.run(CompusApplication.class, args);
	}

}