package com.chinasoft.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -7689029078544915129L;

    private String userName;

    private String userPass;
}
