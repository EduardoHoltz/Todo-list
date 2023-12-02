package com.carloseduardo.todo_list.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloseduardo.todo_list.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}