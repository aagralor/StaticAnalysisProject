package com.example.app.resources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class IntegrationTest extends AbstractBaseIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should_return_pong_when_get_request_is_made_to_ping() throws Exception {
		mockMvc.perform(get("/api/ping")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.ping").value(equalTo("pong")));
	}

}
