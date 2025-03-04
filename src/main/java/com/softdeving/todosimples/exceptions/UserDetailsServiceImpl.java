package com.softdeving.todosimples.exceptions;

import com.softdeving.todosimples.models.User;
import com.softdeving.todosimples.repositories.UserRepository;
import com.softdeving.todosimples.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;
import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("Username nao encontrado: " + username);
        return new UserSpringSecurity(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getProfiles());
    }
}
