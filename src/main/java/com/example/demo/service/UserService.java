package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomUser;
import com.example.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    public void create(String username, String password) {
        CustomUser user = new CustomUser();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    public CustomUser read(long id) {
        CustomUser user = userRepository.findById(id).orElseThrow();
        return user;
    }

    public CustomUser read(String username) {
        CustomUser user = userRepository.findByUsername(username);
        return user;
    }

    public CustomUser update(long id, String username, String password) {
        CustomUser user = userRepository.findById(id).orElseThrow();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

        user.setUsername(username);

        boolean passwordHasChanged = !password.equals("");
        boolean usernameHasChanged = !username.equals(user.getUsername());

        if (passwordHasChanged) {
            user.setPassword(encoder.encode(password));
        }

        if (usernameHasChanged) {
            user.setUsername(username);
        }

        if (passwordHasChanged || usernameHasChanged) {
            userRepository.save(user);
        }
        
        return user;
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailImp(user);
    }
    
}
