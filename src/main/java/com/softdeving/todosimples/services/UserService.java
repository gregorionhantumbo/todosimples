package com.softdeving.todosimples.services;

import com.softdeving.todosimples.models.User;
import com.softdeving.todosimples.models.enums.ProfileEnum;
import com.softdeving.todosimples.repositories.TaskRepository;
import com.softdeving.todosimples.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor // 🔹 Injeta automaticamente os atributos final
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! ID: " + id));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 🔹 Senha codificada corretamente
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet())); // 🔹 Correção aqui
        return userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        User existingUser = findById(user.getId());

        // 🔹 Verifica primeiro se a senha foi informada antes de codificá-la
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);
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
