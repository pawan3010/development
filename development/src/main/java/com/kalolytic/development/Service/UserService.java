package com.kalolytic.development.Service;

import com.kalolytic.development.Repository.UserRepository;
import com.kalolytic.development.entity.Users;
import com.kalolytic.development.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtService jwtToken;

    

    public Users createUser(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    
    public String verify(Users user){
        Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        
        if(authentication.isAuthenticated()){
            return jwtToken.generateToken(user.getUsername());
        }
        return "false";

    }



    public Users deleteUser(int id){
        try {
            Optional<Users> user = userRepo.findById(id);

            if (user.isEmpty()) {
                throw new ResourceNotFoundException("no user found with Id" + id);
            } else {

                userRepo.deleteById(id);
                return user.get();
            }
        }
        catch (Exception e){
            System.out.println(("something wring happend"+e));
        }
        return null;
    }
    public void deleteAllUser(){
        userRepo.deleteAll();
    }
    @Cacheable("users")
    public List<Users> getAllUser() {
        return userRepo.findAll();
    }

    @Cacheable("users")
    public Users getUserById(int id){
        Optional<Users> user=userRepo.findById(id);
        return user.orElse(null);
    }
}
