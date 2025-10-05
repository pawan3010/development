package com.kalolytic.development.Controller;

import com.kalolytic.development.Service.EmployeeService;
import com.kalolytic.development.Service.UserService;
import com.kalolytic.development.entity.Department;
import com.kalolytic.development.entity.Employees;
import com.kalolytic.development.Repository.DepartmentRepo;
import com.kalolytic.development.entity.Users;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired EmployeeService employeeService;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }


    @PostMapping("/create")
    public Employees create(@Valid @RequestBody Employees e) {
        // Fetch department from DB
        if (e.getDepartment() != null && e.getDepartment().getId() != 0) {
            Department dept = departmentRepo.findById(e.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            e.setDepartment(dept);
        }
        return employeeService.createEmployee(e);
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<Employees>> getAll() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employees> update(@PathVariable Long id,@Valid @RequestBody Employees e) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
    @DeleteMapping
    public ResponseEntity<String> delete(){
        employeeService.deleteAll();
        return ResponseEntity.ok("delete all records")   ;
    }
    @PutMapping("/update/{id}/{updatedPassword}")
    public ResponseEntity<String> updatePassWord(@PathVariable int id,@PathVariable String updatedPassword){
        Users user=userService.getUserById(id);
        user.setPassword(updatedPassword);
        userService.createUser(user);
        return ResponseEntity.ok(updatedPassword);
    }
}
