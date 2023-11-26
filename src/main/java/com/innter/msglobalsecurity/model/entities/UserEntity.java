package com.innter.msglobalsecurity.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_security_users")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2614479385669138123L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fi_id", nullable = false)
    private Long id;

    @Column(name = "fc_username" ,nullable = false, length = 100)
    private String userName;

    @Column(name = "fc_password", nullable = false)
    private String password;

    @Column(name = "fb_status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    private Set<UserRolEntity> usersRoles;

}