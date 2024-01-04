package com.adultery_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 12)
  private String phone;

  @NotBlank
  @Size(max = 120)
  private String password;

  private int point;
  @Lob
  private String image;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String username, String phone,int point, String password,String image) {
    this.username = username;
    this.phone = phone;
    this.password = password;
    this.image = image;
    this.point = point;
  }

  public User(String username, String phone, String password, int point, Set<Role> roles ) {
    this.username = username;
    this.phone = phone;
    this.password = password;
    this.point = point;
    this.roles = roles;
  }

  public User(String username, String phone, String password, int point, String image, Set<Role> roles) {
    this.username = username;
    this.phone = phone;
    this.password = password;
    this.point = point;
    this.image = image;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPhone() {
    return phone;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    if (point < 0) {
      this.point = 0;
    } else {
      this.point = point;
    }
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}