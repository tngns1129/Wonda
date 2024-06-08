package com.semo.wonda.data.response;

import com.semo.wonda.entity.GoalType;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String userName;
    private String userPassward;
    public UserResponseDTO(String userName, String userPassward){
        this.userName = userName;
        this.userPassward = userPassward;
    }
    public UserResponseDTO(){}
}
