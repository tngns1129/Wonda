package com.semo.wonda.data.response;

import com.semo.wonda.entity.GoalType;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String userName;
    private String userPassword;
    public UserResponseDTO(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }
    public UserResponseDTO(){}
}
