package com.trent.vueblog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {
    @NotNull(message = "昵称不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
