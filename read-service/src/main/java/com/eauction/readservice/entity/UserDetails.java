package com.eauction.readservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Column(name = "EMAIL")
    @Id
    String email;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "FIRST_NAME")
    String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "ADDRESS")
    String address;

    @Column(name = "CITY")
    String city;

    @Column(name = "STATE")
    String state;

    @Column(name = "PIN")
    Integer pincode;

    @Column(name = "PHONE")
    Long phoneNumber;

    @Column(name = "USER_TYPE") // buyer or seller
    String userType;
}

