package com.kalolytic.development.Controller;

import com.kalolytic.development.Repository.DepartmentRepo;
import com.kalolytic.development.entity.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    @Autowired DepartmentRepo departmentRepo;

    @PostMapping("create")
    public Department create(@RequestBody Department department) {
        return departmentRepo.save(department);
    }

    @GetMapping("getAll")
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable Long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable Long id, @RequestBody Department department) {
        Department existing = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        existing.setName(department.getName());
        return departmentRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        departmentRepo.deleteById(id);
        return "Department deleted";
    }
}
