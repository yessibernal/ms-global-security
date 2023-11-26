package com.innter.msglobalsecurity.model.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innter.msglobalsecurity.model.dtos.RolDto;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    private String username;
    private String password;
}
