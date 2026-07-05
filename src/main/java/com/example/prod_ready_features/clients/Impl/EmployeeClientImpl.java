package com.example.prod_ready_features.clients.Impl;

import com.example.prod_ready_features.advice.ApiResponse;
import com.example.prod_ready_features.clients.EmployeeClient;
import com.example.prod_ready_features.dto.EmployeeDTO;
import com.example.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
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

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>(){});

            return employeeDTOList.getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try{
            ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                    .uri("employees/{employeeId}", employeeId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeDTO.getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        System.out.println("Error Occurred "+new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });

            return employeeDTOApiResponse.getBody().getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
