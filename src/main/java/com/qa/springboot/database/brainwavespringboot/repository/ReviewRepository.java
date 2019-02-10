package com.qa.springboot.database.brainwavespringboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springboot.database.brainwavespringboot.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Page<Review> findByBeachId(Long beachId, Pageable pageable);
	
}
	
	


