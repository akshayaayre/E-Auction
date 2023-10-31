package com.eauction.buyer.repository;

import com.eauction.buyer.entity.BidDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BidDetailsRepo extends JpaRepository<BidDetails,Integer> {

    @Query(value = "select * from BID_DETAILS where PRODUCT_ID = :productId and" +
            " USER_EMAIL = :userEmail", nativeQuery = true)
    public BidDetails findByProductIdAndUserEmail(Integer productId,String userEmail);
}
