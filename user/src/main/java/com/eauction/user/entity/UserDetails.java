package com.eauction.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

//@Entity
//@Table(name = "SELLER_DETAILS")
//@Data
//public class SellerDetails {
@Entity
@Table(name = "USER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

//    @Id
//    @Column(name = "SELLER_ID")
//    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
//    @SequenceGenerator(name="seq",sequenceName="SEQ_SELLER")
//    Integer sellerId;

//    @Id
//    @Column(name = "USER_ID")
//    String userId;

    @Column(name = "EMAIL")
    @Id
    @NotNull(message = "Email cannot be null")
    @Email(message = "Improper email")
    String email;

    @Column(name = "PASSWORD")
    @NotNull(message = "Password cannot be null")
    String password;

    @Column(name = "FIRST_NAME")
    @NotNull(message = "First Name cannot be null")
    @Size(min = 5, max = 30, message = "First Name must have minimum 5 letters, max 30")
    String firstName;

    @Column(name = "LAST_NAME")
    @NotNull(message = "Last Name cannot be null")
    @Size(min = 3, max = 30, message = "Last Name must have minimum 3 letters, max 30")
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
    @NotNull(message = "Phone number cannot be null")
//    @Size(min = 10, max = 10, message = "Phone number should be 10 digits long")
//    @Length(min = 10, max = 10, message = "Phone number should be 10 digits long")
    Long phoneNumber;

    @Column(name = "USER_TYPE") // buyer or seller
    String userType;

    @Transient
    String token;
}
