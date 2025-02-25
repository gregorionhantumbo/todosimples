package com.softdeving.todosimples.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Evita problemas de serialização
    private User user;

    @Column(name = "description", nullable = false, length = 200)
    @NotBlank
    private String description;

    public Task() {}

    public Task(Long id, User user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
