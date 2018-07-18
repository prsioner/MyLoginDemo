package userlogindemo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {

	@RequestMapping(value = "hello")
	public String say(){
		return "Hello Spring Boot!";
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("DemoApplication  start!!!");



	}
}
