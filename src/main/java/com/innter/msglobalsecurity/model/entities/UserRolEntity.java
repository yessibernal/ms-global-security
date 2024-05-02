package com.innter.msglobalsecurity.model.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tb_security_users_roles")
public class UserRolEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -8168721456223187562L;

    @EmbeddedId
    private UserRolKey id;

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("idUser")
    @JoinColumn(name = "fi_id_user")
    private UserEntity user;

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "fi_id_rol")
    private RolEntity rol;

}