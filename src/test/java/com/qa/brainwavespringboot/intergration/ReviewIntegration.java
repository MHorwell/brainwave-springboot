package com.qa.brainwavespringboot.intergration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.qa.springboot.database.brainwavespringboot.BrainWaveApplication;
import com.qa.springboot.database.brainwavespringboot.model.Beach;
import com.qa.springboot.database.brainwavespringboot.model.Review;
import com.qa.springboot.database.brainwavespringboot.repository.BeachRepository;
import com.qa.springboot.database.brainwavespringboot.repository.ReviewRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BrainWaveApplication.class })
@AutoConfigureMockMvc
public class ReviewIntegration {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private BeachRepository beachRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	private Beach testBeachOne = new Beach(101L, "testBeachOne", 12.34, 56.78);
	private long beachId = testBeachOne.getId();

	private Review review = new Review(testBeachOne, 2, 3, 4, "This is certainly a beach review.");

	@Before
	public void emptyDB() {
		beachRepository.deleteAll();
		beachRepository.save(testBeachOne);
		reviewRepository.deleteAll();
	}

	@Test
	public void getAllReviewsForBeachTest() throws Exception {
		reviewRepository.save(review);
		mvc.perform(get("/api/beach/" + beachId + "/reviews").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].rockpoolRating", is(4)));
	}

	@Test
	public void postReviewBeachTest() throws Exception {
		reviewRepository.save(review);
		mvc.perform(post("/api/beach/" + beachId + "/reviews").contentType(MediaType.APPLICATION_JSON).content(
				"{\"facilitiesRating\": 4, \"surfRating\": 5, \"rockpoolRating\": 0, \"comment\": \"Test beach review\"}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rockpoolRating", is(0)));
	}

	@Test
	public void changeBeachReviewTest() throws Exception {
		reviewRepository.save(review);
		mvc.perform(put("/api/beach/" + beachId + "/reviews/" + review.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(
						"{\"facilitiesRating\": 4, \"surfRating\": 5, \"rockpoolRating\": 0, \"comment\": \"Test beach review\"}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.rockpoolRating", is(0))).andExpect(jsonPath("$.facilitiesRating", is(4)))
				.andExpect(jsonPath("$.surfRating", is(5))).andExpect(jsonPath("$.comment", is("Test beach review")));
	}
	
	@Test
	public void deleteBeachReview() throws Exception {
		reviewRepository.save(review);
		mvc.perform(MockMvcRequestBuilders.delete("/api/beach/" + beachId + "/reviews/" + review.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/beach/" + beachId + "/reviews/" + review.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
