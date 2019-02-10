package com.qa.springboot.database.brainwavespringboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springboot.database.brainwavespringboot.exception.ResourceNotFoundException;
import com.qa.springboot.database.brainwavespringboot.model.Review;
import com.qa.springboot.database.brainwavespringboot.repository.BeachRepository;
import com.qa.springboot.database.brainwavespringboot.repository.ReviewRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReviewController {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private BeachRepository beachRepository;

	@GetMapping("/beach/{beachId}/reviews")
	public Page<Review> getAllReviewsbyBeach(@PathVariable(value = "beachId") Long beachId,
			Pageable pageable) {
		return reviewRepository.findByBeachId(beachId, pageable);
	}

	@PostMapping("/beach/{beachId}/reviews")
	public Review createReview(@PathVariable(value = "beachId") Long beachId, 
			@Valid @RequestBody Review review) {
		return beachRepository.findById(beachId).map(beach -> {
			review.setBeach(beach);
			return reviewRepository.save(review);
		}).orElseThrow(() -> new ResourceNotFoundException("BeachID", "id", review));
	}

	@PutMapping("/beach/{id}/reviews/{reviewId}")
	public Review updateReview(@PathVariable(value = "id") Long beachId,
			@PathVariable(value = "reviewId") Long reviewId, @Valid @RequestBody Review reviewRequest) {
		if (!beachRepository.existsById(beachId)) {
			throw new ResourceNotFoundException("Beach", "id", reviewRequest);
		}
		return reviewRepository.findById(reviewId).map(review -> {
			review.setComment(reviewRequest.getComment());
			return reviewRepository.save(review);
		}).orElseThrow(() -> new ResourceNotFoundException("ReviewId", "id", reviewRequest));

	}

	@DeleteMapping("/beach/{id}/reviews/{reviewId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Long beachId,
			@PathVariable(value = "reviewId") Long reviewId) {
		if (!beachRepository.existsById(beachId)) {
			throw new ResourceNotFoundException("Beach", "id", beachId);
		}

		return reviewRepository.findById(reviewId).map(review -> {
			reviewRepository.delete(review);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Review Id ", reviewId.toString(), null));
	}

}
