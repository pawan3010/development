package com.kalolytic.development.Service;

import com.kalolytic.development.entity.Department;
import com.kalolytic.development.Repository.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    @Autowired DepartmentRepo departmentRepo;

    public Department createDepartment(Department d) {
        return departmentRepo.save(d);
    }

    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
    }
}
