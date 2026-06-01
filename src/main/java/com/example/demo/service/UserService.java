package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 用户登录验证
     */
    public Map<String, Object> login(String studentNo, String password) {
        Map<String, Object> result = new HashMap<>();
        
        // 参数验证
        if (studentNo == null || studentNo.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "学号不能为空");
            return result;
        }
        
        if (password == null || password.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "密码不能为空");
            return result;
        }
        
        // 查找用户
        Optional<User> userOpt = userRepository.findByStudentNoAndPassword(studentNo, password);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("data", Map.of(
                "id", user.getId(),
                "studentNo", user.getStudentNo()
            ));
        } else {
            result.put("success", false);
            result.put("message", "学号或密码错误");
        }
        
        return result;
    }
    
    /**
     * 根据学号查找用户
     */
    public Optional<User> findByStudentNo(String studentNo) {
        return userRepository.findByStudentNo(studentNo);
    }
}
