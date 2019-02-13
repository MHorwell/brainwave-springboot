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
import com.qa.springboot.database.brainwavespringboot.repository.BeachRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {BrainWaveApplication.class})
@DataJpaTest
public class BeachRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private BeachRepository testRepository;

	
	@Test
	public void findByID() {
		Beach testBeachOne = new Beach(101L, "testBeachOne", 12.34, 56.78);
		entityManager.persist(testBeachOne);
		entityManager.flush();
		assertTrue(testRepository.findById(testBeachOne.getId()).isPresent());
	}
	
	@Test
	public void findByNameContaining() {
		Beach testBeachOne = new Beach(101L, "testBeachOne", 12.34, 56.78);
		entityManager.persist(testBeachOne);
		entityManager.flush();
		assertTrue(testRepository.findByNameContaining("tes").stream().findFirst().isPresent());
	}
	
}
