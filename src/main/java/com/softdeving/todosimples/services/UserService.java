package com.softdeving.todosimples.services;

import com.softdeving.todosimples.models.User;
import com.softdeving.todosimples.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! ID: " + id));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        User existingUser = findById(user.getId());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir este usuário, pois ele pode estar relacionado a outras entidades.");
        }
    }
}
