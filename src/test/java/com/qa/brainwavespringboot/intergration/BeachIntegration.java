package com.qa.brainwavespringboot.intergration;

	import static org.hamcrest.CoreMatchers.is;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

	import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springboot.database.brainwavespringboot.BrainWaveApplication;
import com.qa.springboot.database.brainwavespringboot.model.Beach;
import com.qa.springboot.database.brainwavespringboot.repository.BeachRepository;

	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = {BrainWaveApplication.class})
	@AutoConfigureMockMvc
	public class BeachIntegration {
		
		@Autowired
		private MockMvc mvc;
		
		@Autowired
		private BeachRepository testrepository;
		
		@Before
		public void emptyDB() {
			testrepository.deleteAll();
		}
		
		@Test
		public void findAndRetrieveBeachFromDatabase()
		throws Exception{
			testrepository.save(new Beach(1L, "testName", 12.34, 56.78));
			mvc.perform(get("/api/beach")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
					.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("testName")));		
		}
		
		@Test
		public void addBeachToDatabaseTest() 
				throws Exception {
			mvc.perform(MockMvcRequestBuilders.post("/api/beach")
					.contentType(MediaType.APPLICATION_JSON)
					.content(" {\"id\": 166," + 
							"\"name\": \"Perranporth\"," + 
							"\"latitude\": 50.34838," + 
							"\"longtitude\": -5.154902}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.name", is("Perranporth")));
		}
		
		@Test
		public void putToBeach()
				throws Exception {
			ObjectMapper beachToJson = new ObjectMapper();
			Beach testBeach = new Beach(1253L,"testName", 87.65, 43.21);
			testrepository.save(testBeach);
			
			Beach changedBeach = new Beach("Porthleven", 50.080572, -5.313125);
			String jsonString = beachToJson.writeValueAsString(changedBeach);
			
			mvc.perform(MockMvcRequestBuilders.put("/api/beach/" + testBeach.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is((int) testBeach.getId())))
			.andExpect(jsonPath("$.name", is(changedBeach.getName())))
			.andExpect(jsonPath("$.latitude", is(changedBeach.getLatitude())))
			.andExpect(jsonPath("$.longtitude", is(changedBeach.getLongtitude())));
		}
		
		@Test
		public void addToBeach() throws Exception {
			ObjectMapper beachToJson = new ObjectMapper();
			Beach testBeach = new Beach(1L, "testName", 12.34, 56.78);
			testrepository.save(testBeach);
			
			Beach emptyBeach = new Beach();
			String jsonString = beachToJson.writeValueAsString(emptyBeach);
			
			mvc.perform(MockMvcRequestBuilders.put("/api/beach/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is((int) testBeach.getId())))
			.andExpect(jsonPath("$.name", is(testBeach.getName())))
			.andExpect(jsonPath("$.latitude", is(testBeach.getLatitude())))
			.andExpect(jsonPath("$.longtitude", is(testBeach.getLongtitude())));
		}
		 
		@Test
		public void deleteBeach()
		throws Exception {
			Beach testBeach = new Beach(1253L,"testName", 87.65, 43.21);
			testrepository.save(testBeach);
			mvc.perform(MockMvcRequestBuilders.delete("/api/beach/" + testBeach.getId())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			
			mvc.perform(MockMvcRequestBuilders.delete("/api/beach/" + testBeach.getId())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		}

		

	}


