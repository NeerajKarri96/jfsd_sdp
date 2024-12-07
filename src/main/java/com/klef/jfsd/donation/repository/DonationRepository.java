package com.klef.jfsd.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.donation.model.Donation;

import jakarta.transaction.Transactional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    // JpaRepository provides built-in methods like save(), findById(), delete(), etc.
	

	    List<Donation> findByDonorId(Long donorId); // Custom query to find donations by donorId
	    
	    @Query("update Donation d set d.status=?1 where d.id=?2")
	    @Modifying  // DML operation
	    @Transactional  // Enables auto-commit
	    public int updatedonationstatus(String status, Long donationId);
}
