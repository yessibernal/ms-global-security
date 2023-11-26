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
@Table(name = "tb_security_roles")
public class RolEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2122016219214755323L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fi_id", nullable = false)
    private Long id;

    @Column(name = "fc_rol_name" ,nullable = false, length = 100)
    private String rolName;

    @Column(name = "fc_rol_section", nullable = false, length = 50)
    private String rolSection;

    @Column(name = "fc_rol_description" ,nullable = false, length = 100)
    private String rolDescription;

    @Column(name = "fb_status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "rol", cascade= CascadeType.ALL)
    private Set<UserRolEntity> usersRoles;
}