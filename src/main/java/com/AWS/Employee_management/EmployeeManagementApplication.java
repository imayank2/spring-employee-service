package com.AWS.Employee_management;


//import com.AWS.Employee_management.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class EmployeeManagementApplication implements CommandLineRunner {

//	private final EmployeeService employeeService;
//	private final DataService dataService;

	@Value(value = "${my.variable}")
	private String myVariable;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("my variable: "+myVariable);
//		System.out.println("The data is: " + dataService.getData());
	}

}
