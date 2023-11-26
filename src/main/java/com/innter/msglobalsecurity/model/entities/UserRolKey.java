package com.innter.msglobalsecurity.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserRolKey {

    @Column(name = "fi_id_user", nullable = false)
    private Long idUser;

    @Column(name = "fi_id_rol", nullable = false)
    private Long idRol;
}