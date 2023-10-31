package com.eauction.readservice.repository;

import com.eauction.readservice.entity.BidDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidDetailsRepository extends JpaRepository<BidDetails,Integer> {

    @Query(value = "select * from bid_details where product_id = :productId and user_email = :userEmail",
    nativeQuery = true)
    public BidDetails getBidFromUserOnProduct(Integer productId, String userEmail);

    @Query(value="select * from bid_details where product_id = :productId order by bid_amount desc", nativeQuery = true)
    public List<BidDetails> getAllBidsOnProduct(Integer productId);
}
