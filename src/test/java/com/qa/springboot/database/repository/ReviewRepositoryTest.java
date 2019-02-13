package com.qa.springboot.database.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.springboot.database.brainwavespringboot.BrainWaveApplication;
import com.qa.springboot.database.brainwavespringboot.model.Beach;
import com.qa.springboot.database.brainwavespringboot.model.Review;
import com.qa.springboot.database.brainwavespringboot.repository.ReviewRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BrainWaveApplication.class })
@DataJpaTest
public class ReviewRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ReviewRepository testOrderRepository;

	@Test
	public void findByOrderID() {
		Beach testBeachOne = new Beach(101L, "testBeachOne", 12.34, 56.78);
		Review testReview = new Review(testBeachOne, 2, 3, 4, "This is certainly a beach review.");
		entityManager.persist(testBeachOne);
		entityManager.persist(testReview);
		entityManager.flush();
		assertTrue(testOrderRepository.findById(testReview.getId()).isPresent());
	}

}
