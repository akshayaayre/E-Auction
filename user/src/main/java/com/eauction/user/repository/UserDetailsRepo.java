package com.eauction.user.repository;

import com.eauction.user.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails,String> {

    @Query(value = "select * from User_Details where email = :email and password = :password",
            nativeQuery = true)
    public UserDetails getUser(@Param("email") String email, @Param("password") String password);

    UserDetails findByEmail(String email);
}
