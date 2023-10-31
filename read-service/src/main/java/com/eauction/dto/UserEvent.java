package com.eauction.dto;

import com.eauction.readservice.entity.UserDetails;
import lombok.Data;

@Data
public class UserEvent {

    private String type;
    private UserDetails userDetails;

    public UserEvent(){}
    public UserEvent(String type, UserDetails userDetails) {
        this.type = type;
        this.userDetails = userDetails;
    }
}
