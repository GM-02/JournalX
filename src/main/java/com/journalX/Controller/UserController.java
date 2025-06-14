package com.journalX.Controller;

import com.journalX.Entry.User;
import com.journalX.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }
    @PostMapping
    public void createUser(@RequestBody User user) {
        userServices.saveUser(user);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName) {
        User byUserName = userServices.findByUserName(userName);
        if (byUserName!=null){
            byUserName.setUserName(user.getUserName());
            byUserName.setPassword(user.getPassword());
            userServices.saveUser(byUserName);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
