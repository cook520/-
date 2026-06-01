package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据学号查找用户
     */
    Optional<User> findByStudentNo(String studentNo);
    
    /**
     * 根据学号和密码查找用户（登录验证）
     */
    Optional<User> findByStudentNoAndPassword(String studentNo, String password);
}
