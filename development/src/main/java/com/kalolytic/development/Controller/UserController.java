package com.kalolytic.development.Controller;

import com.kalolytic.development.Service.UserService;
import com.kalolytic.development.entity.Users;
import com.kalolytic.development.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Users> createEmployee(@Valid @RequestBody Users user){
        return ResponseEntity.ok(userService.createUser(user));

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Users>> getAll(){
        List<Users> user=userService.getAllUser();
        if(user.isEmpty()){
            throw new ResourceNotFoundException("No data available");
        }
        else {
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteById(@PathVariable int id){


        return ResponseEntity.ok(userService.deleteUser(id));
    }
    @DeleteMapping("/deleteAll")
    public void deleteAllUser(){
        userService.deleteAllUser();
    }

}
