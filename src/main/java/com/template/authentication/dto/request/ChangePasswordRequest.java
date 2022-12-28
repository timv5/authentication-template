package com.template.authentication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;

}
