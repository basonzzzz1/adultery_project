package com.adultery_project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "phone")
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
  private boolean isOnline;

  @NotBlank
  @Size(max = 120)
  private String password;

  private int point;
  private int spin;
  private int countSpin;

  private LocalDateTime lastSpinReset;
  private boolean isBanned;
  @Lob
  private String image;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String username, String phone,int point,int spin,int countSpin,LocalDateTime lastSpinReset,boolean isBanned, String password,String image) {
    this.username = username;
    this.phone = phone;
    this.password = password;
    this.image = image;
    this.point = point;
    this.spin = spin;
    this.lastSpinReset = lastSpinReset;
    this.countSpin = countSpin;
    this.isBanned = isBanned;
  }

  public User(String username, String phone, String password, int point,int spin,int countSpin,LocalDateTime lastSpinReset,boolean isBanned, Set<Role> roles ) {
    this.username = username;
    this.phone = phone;
    this.password = password;
    this.point = point;
    this.spin = spin;
    this.countSpin = countSpin;
    this.lastSpinReset = lastSpinReset;
    this.isBanned = isBanned;
    this.roles = roles;
  }

  public User(String username, String phone,boolean isOnline, String password, int point,int spin,int countSpin,LocalDateTime lastSpinReset,boolean isBanned, String image, Set<Role> roles) {
    this.username = username;
    this.phone = phone;
    this.isOnline = isOnline;
    this.password = password;
    this.point = point;
    this.spin = spin;
    this.countSpin = countSpin;
    this.lastSpinReset = lastSpinReset;
    this.isBanned = isBanned;
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

  public int getSpin() {
    return spin;
  }

  public void setSpin(int spin) {
    this.spin = spin;
  }

  public int getCountSpin() {
    return countSpin;
  }

  public void setCountSpin(int countSpin) {
    this.countSpin = countSpin;
  }

  public LocalDateTime getLastSpinReset() {
    return lastSpinReset;
  }

  public void setLastSpinReset(LocalDateTime lastSpinReset) {
    this.lastSpinReset = lastSpinReset;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setBanned(boolean banned) {
    isBanned = banned;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public boolean isOnline() {
    return isOnline;
  }

  public void setOnline(boolean online) {
    isOnline = online;
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