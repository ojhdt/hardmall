package com.easymall.common.dao;

import com.easymall.common.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String userName);
}
