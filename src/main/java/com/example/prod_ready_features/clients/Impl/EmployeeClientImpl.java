package com.example.prod_ready_features.clients.Impl;

import com.example.prod_ready_features.advice.ApiResponse;
import com.example.prod_ready_features.clients.EmployeeClient;
import com.example.prod_ready_features.dto.EmployeeDTO;
import com.example.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("Trying to retrieve all employees in getAllEmployess");

        try {
            log.info("Attempting to call the restClient method in getAllEmployees");
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        System.out.println("Error Occurred "+new String(res.getBody().readAllBytes()));
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .body(new ParameterizedTypeReference<>(){});

            log.debug("Successfully retrieved the employees in getAllEmployees");
            log.trace("Retrieved employees list in getAllEmployees : {}", employeeDTOList.getData());

            return employeeDTOList.getData();
        }
        catch (Exception e){
            log.error("Exception occurred in getAllEmployees", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.trace("Trying to get Employee by id in getAllEmployeeById with id : {}", employeeId);
        try{
            ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                    .uri("employees/{employeeId}", employeeId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not get the employee with id : "+ employeeId);
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeDTO.getData();
        }
        catch (Exception e){
            log.error("Exception occurred in getAllEmployeeById", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.trace("Trying to create Employee with information {}", employeeDTO);
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        System.out.println("Error Occurred "+new String(res.getBody().readAllBytes()));
                        log.debug("4xxClient error occured during createNewEmployee");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });

            log.trace("successfully created a new employee :{}", employeeDTOApiResponse.getBody());
            return employeeDTOApiResponse.getBody().getData();
        }
        catch (Exception e){
            log.error("Exception occurred in createNewEmployee", e);
            throw new RuntimeException(e);
        }
    }
}
