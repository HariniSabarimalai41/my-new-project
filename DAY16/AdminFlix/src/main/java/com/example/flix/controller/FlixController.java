package com.example.flix.controller;

import com.example.flix.exception.DataNotFoundException;
import com.example.flix.model.Flix;
import com.example.flix.repository.FlixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
public class FlixController {

    @Autowired
    private FlixRepository flixRepository;

    @PostMapping("/post")
    Flix newUser(@RequestBody Flix newUser) {
        return flixRepository.save(newUser);
    }

    @GetMapping("/get")
    List<Flix> getAllUsers() {
        return flixRepository.findAll();
    }

    @GetMapping("/get/{id}")
    Flix getUserById(@PathVariable Long id) {
        return flixRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(id));
    }

    @PutMapping("/put/{id}")
    Flix updateUser(@RequestBody Flix newUser, @PathVariable Long id) {
        return flixRepository.findById(id)
                .map(user -> {
                	user.setId(newUser.getId());
                	user.setName(newUser.getName());
                    user.setDate(newUser.getDate());
                    user.setRating(newUser.getRating());
                    user.setTime(newUser.getTime());
                    return flixRepository.save(user);
                }).orElseThrow(() -> new DataNotFoundException(id));
    }

    @DeleteMapping("/delete/{id}")
    String deleteUser(@PathVariable Long id){
        if(!flixRepository.existsById(id)){
            throw new DataNotFoundException(id);
        }
        flixRepository.deleteById(id);
        return  "Movie Data With Id "+id+" Has Been Deleted.";
    }


}