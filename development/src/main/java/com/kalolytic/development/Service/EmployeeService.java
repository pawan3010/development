package com.kalolytic.development.Service;

import com.kalolytic.development.Repository.EmployeeRepo;
import com.kalolytic.development.Repository.UserRepository;
import com.kalolytic.development.entity.Employees;
import com.kalolytic.development.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    UserRepository userRepo;


    public Employees createEmployee(Employees employee) {
        return employeeRepo.save(employee);
    }

    @Cacheable("Employees")
    public List<Employees> getAllEmployees() {
        System.out.println("cacheshind is used");
        return employeeRepo.findAll();

    }
    @Cacheable("Employees")
    public Employees getEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employeee not found of id"+id));
    }
    @CachePut("Employees")
    public Employees updateEmployee(Long id, Employees e) {
        Employees existing = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found of id "+id));

        existing.setName(e.getName());
        existing.setAge(e.getAge());
        existing.setSalary(e.getSalary());
        existing.setDepartment(e.getDepartment());

        return employeeRepo.save(existing);
    }
    @CacheEvict("Employees")
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }
    @CacheEvict("Employees")
    public void deleteAll(){
        employeeRepo.deleteAll();
    }

}
