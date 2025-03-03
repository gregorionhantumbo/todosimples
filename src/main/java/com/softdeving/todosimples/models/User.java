package com.softdeving.todosimples.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softdeving.todosimples.models.enums.ProfileEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user") // Evita conflitos com palavras reservadas do banco de dados
public class User {
    public interface CreateUser {}
    public interface UpdateUser {}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    @NotBlank(groups = CreateUser.class)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false) // A senha pode precisar de mais espaço devido ao hash
    @NotBlank(groups = {CreateUser.class, UpdateUser.class})
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles() {
        return this.profiles.stream()
                .map(ProfileEnum::toEnum)
                .collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(tasks, user.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, tasks);
    }
}
