package com.selflearning.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.request.async.DeferredResult;

import static java.util.GregorianCalendar.BC;

@SpringBootApplication
public class SelfLearningApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext cac = SpringApplication.run(SelfLearningApplication.class, args);
//		for(String s: cac.getBeanDefinitionNames()){
//			System.out.println(s);
//		}

//		ABC abc = new ABC();
//		System.out.println("class name: " + abc.getClassName());
		BC bc = new BC();
		System.out.println("class name: " + bc.getClassName());

		System.out.println(ABC.getConnection());
		try{
			Thread.sleep(1000);
			System.out.println(ABC.getConnection());
		}catch(Exception e){

		}


	}

}
