package com.adultery_project.service.serviceImpl;
import com.adultery_project.models.User;
import com.adultery_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
    public Optional<User> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Kiểm tra xem đối tượng có được xác thực hay không
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy tên đăng nhập của đối tượng đang đăng nhập
             String username = authentication.getName();
             return userRepository.findByUsername(username);
        }
        // Trường hợp không có ai đăng nhập
        throw new RuntimeException("Error Exception Principal !");
    }
}