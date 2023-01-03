package com.template.authentication.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(initialValue=1, name="users_seq", sequenceName="users_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="users_seq")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Setter
    @Column(name = "user_is_active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "roles")
    private Set<RoleEntity> roles = new HashSet<>();

    @Column(name = "user_time_created")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private LocalDateTime timeCreated;

    @Column(name = "user_time_updated")
    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    private LocalDateTime timeUpdated;

    public UserEntity() {}

    public UserEntity(String email, String password, Boolean isActive, Set<RoleEntity> roles, LocalDateTime timeCreated,
                      LocalDateTime timeUpdated) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public UserEntity(Long userId, String email, String password, Boolean isActive, Set<RoleEntity> roles) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
    }

    public UserEntity(Long userId, String email, String password, Boolean isActive, Set<RoleEntity> roles,
                      LocalDateTime timeCreated, LocalDateTime timeUpdated) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.roles = roles;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
}
