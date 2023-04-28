package com.example.martin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.martin.domain.enums.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
@Entity(name = "tbl_users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fullName;
  private String email;

  private String username;
  private String password;

  private String msisdn;

  // Generic default field for every model object
  @Column(nullable = false)
  private LocalDateTime createdAt;
  @Column(nullable = false)
  private String createdBy;
  private LocalDateTime lastUpdatedAt;
  private String lastUpdatedBy;
  private Boolean locked = false;
  private Boolean enabled = false;
  @Enumerated(EnumType.STRING) //

  private UserRole userRole;

  public User(String fullName, String email, String msisdn, String password, UserRole appuserRole,
              String createdBy) {
    this.fullName = fullName;
    this.email = email;
    this.msisdn = msisdn;
    this.password = password;
    this.username = email;
    this.userRole = appuserRole;
    this.createdBy = createdBy;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());

    return Collections.singletonList(grantedAuthority);
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
