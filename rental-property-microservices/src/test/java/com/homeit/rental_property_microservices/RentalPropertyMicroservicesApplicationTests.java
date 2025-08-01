package com.homeit.rental_property_microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeit.rental_property_microservices.dto.RentalPropertyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class RentalPropertyMicroservicesApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	private RentalPropertyDTO createdProperty;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context).build();
		createdProperty = createProperty();
	}

	private RentalPropertyDTO createProperty() throws Exception {
		RentalPropertyDTO propertyDTO = new RentalPropertyDTO(
				null, UUID.randomUUID(),
				"Test Property",
				"123, abc street",
				"Test City",
				"Test Country",
				"12345",
				1200.0
		);

		String responseBody = mockMvc.perform(
						post("/api/v1/rental-properties")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(propertyDTO)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();

		return objectMapper.readValue(responseBody, RentalPropertyDTO.class);
	}



	@Test
	void contextLoads() {
	}

	@Test
	void testGetPropertyById() throws Exception {
		mockMvc.perform(
						get("/api/v1/rental-properties/{id}",
								createdProperty.id())
								.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name")
						.value("Test Property"));
	}


	@Test
	void testCreateProperty() throws Exception {
		RentalPropertyDTO newProperty = new RentalPropertyDTO(
				UUID.randomUUID(), UUID.randomUUID(),
				"New Property", "456 New St",
				"New City", "New Country",
				"67890",1500.0);

		mockMvc.perform(
						post("/api/v1/rental-properties")
								.contentType("application/json")
								.content(objectMapper.writeValueAsString(newProperty)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name")
						.value("New Property"));
	}


//	@Test
//	void testCreatePropertyErrorNullAddress() throws Exception {
//		RentalPropertyDTO newProperty = new RentalPropertyDTO(
//				UUID.randomUUID(), UUID.randomUUID(),
//				"New Property", null,
//				"New City", "New Country",
//				"67890",1500.0);
//
//		mockMvc.perform(
//						post("/api/v1/rental-properties")
//								.contentType("application/json")
//								.content(objectMapper.writeValueAsString(newProperty)))
//				.andExpect(status().isBadRequest());
//	}

	@Test
	void testUpdateProperty() throws Exception {
		RentalPropertyDTO updatedProperty = new RentalPropertyDTO(
				createdProperty.id(), UUID.randomUUID(),
				"Updated Property","123 Updated St",
				"Updated City", "Updated Country",
				"54321", 1800.0
		);
		mockMvc.perform(
						put("/api/v1/rental-properties/{id}", createdProperty.id())
								.contentType("application/json")
								.content(objectMapper.writeValueAsString(updatedProperty)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Property"));
	}

	@Test
	void testGetAllProperties() throws Exception {
		mockMvc.perform(
						get("/api/v1/rental-properties")
								.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").exists());
	}


	@Test
	void testPatchProperty() throws Exception{
		RentalPropertyDTO partialUpdate = new RentalPropertyDTO(
				createdProperty.id(), UUID.randomUUID(),
				"Patch Property",null,
				"Updated City", null,
				"54321", 1800.0
		);
		mockMvc.perform(
						patch("/api/v1/rental-properties/{id}", createdProperty.id())
								.contentType("application/json")
								.content(objectMapper.writeValueAsString(partialUpdate)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Patch Property"));
	}


	@Test
	void testDeleteProperty() throws Exception{
		RentalPropertyDTO deleteProperty = createProperty();

		mockMvc.perform(
				delete("/api/v1/rental-properties/{id}", deleteProperty.id())
						.content("application/json"))
				.andExpect(status().isNoContent());
	}


	@Test
	void testSearchProperty() throws Exception{
		String searchKey = "Test City";

		mockMvc.perform(
				get("/api/v1/rental-properties/search")
						.content("application/json")
						.param("city", searchKey))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("Test City"));
	}

	@Test
	void testGetHeaderInfo() throws Exception {
		String userAgent = "Test User-Agent";

		mockMvc.perform(
						get("/api/v1/rental-properties/headers")
								.header("User-Agent", userAgent)
								.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string("User-Agent: " + userAgent));
	}




}
