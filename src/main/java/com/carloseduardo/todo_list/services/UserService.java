package com.carloseduardo.todo_list.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carloseduardo.todo_list.models.User;
import com.carloseduardo.todo_list.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {

        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException(
                "User not found, id: " + id + " | type: " + User.class.getName()));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);

        user = this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User update(User user) {
        User newUser = findById(user.getId());

        newUser.setPassword(user.getPassword());

        return this.userRepository.save(newUser);
    }

    public void delete(Long id) {
        findById(id);

        try {
            this.userRepository.deleteById(id);
        }

        catch (Exception exception) {
            throw new RuntimeException(
                    "You cannot delete a user related a task");
        }

    }
}