package com.example.martin.domain;

import com.example.martin.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import com.example.martin.domain.enums.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode
@Data
@NoArgsConstructor
@Entity(name = "tbl_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable {

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

  @NotNull
  @Column(nullable = false)
  private boolean activated = false;


  @JsonIgnore
  @ManyToMany
  @JoinTable(
          name = "tbl_user_authority",
          joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
          inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
  )
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @BatchSize(size = 20)
  private Set<Authority> authorities = new HashSet<>();



  @Pattern(regexp = Constants.LOGIN_REGEX)
  @Size(min = 1, max = 50)
  @Column(length = 50, unique = true, nullable = false)
  private String login;


  @Size(max = 20)
  @Column(name = "activation_key", length = 20)
  @JsonIgnore
  private String activationKey;

  @Size(max = 20)
  @Column(name = "reset_key", length = 20)
  @JsonIgnore
  private String resetKey;



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

//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());
//
//    return Collections.singletonList(grantedAuthority);
//  }
//
//  @Override
//  public String getPassword() {
//    return null;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return false;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return false;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return false;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return false;
//  }
}
