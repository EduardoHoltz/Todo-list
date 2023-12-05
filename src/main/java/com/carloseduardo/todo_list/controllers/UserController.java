package com.carloseduardo.todo_list.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carloseduardo.todo_list.models.User;
import com.carloseduardo.todo_list.models.User.CreateUser;
import com.carloseduardo.todo_list.models.User.UpdateUser;
import com.carloseduardo.todo_list.services.UserService;

/**
 * UserController
 */

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;

    // find a user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok().body(user);
    }

    // create a user
    @PostMapping
    @Validated(CreateUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User user) {
        this.userService.create(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    // update a user
    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id) {
        user.setId(id);

        this.userService.update(user);

        return ResponseEntity.noContent().build();
    }

    // delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}