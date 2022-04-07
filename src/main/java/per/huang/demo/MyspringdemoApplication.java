package per.huang.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("per.huang.demo.mystock")
public class MyspringdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringdemoApplication.class, args);
	}

}
