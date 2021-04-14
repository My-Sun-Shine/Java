package com.springbootweb.repository;

import com.springbootweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * @Classname UserRepository
 * @Date 2021/4/14 23:00
 * @Created by FallingStars
 * @Description
 */
public interface UserRepository extends JpaRepository<User, Integer> , QueryByExampleExecutor<User>, CrudRepository<User, Integer> {
}