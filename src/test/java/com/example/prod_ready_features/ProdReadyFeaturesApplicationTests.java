package com.example.prod_ready_features;

import com.example.prod_ready_features.clients.EmployeeClient;
import com.example.prod_ready_features.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	void getAllEmployees(){
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}

	@Test
	void getEmployeeByIdTest(){
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(2L);
		System.out.println(employeeDTO);
	}

	@Test
	void createNewEmployeeTest(){
		EmployeeDTO employeeDTO = new EmployeeDTO(
				null, "Sonam", "sonam@gmail.com", 26, "USER", 29999.00, LocalDate.of(2026, 07, 05), true);
		EmployeeDTO savedEmployeeDTO = employeeClient.createNewEmployee(employeeDTO);

		System.out.println(employeeDTO);
	}
}
