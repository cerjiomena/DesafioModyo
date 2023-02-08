package com.modyo.wrapperapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.modyo.wrapperapi.DesafioModyoApplication;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest(classes = {DesafioModyoApplication.class})
@AutoConfigureMockMvc
@Slf4j
public class PokemonRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void debeObtenerListadoPaginado() throws Exception {
		log.debug("Entrando a debeObtenerListadoPaginado");
		
		 MvcResult result =  mockMvc.perform(get("/api/v1/challenge/listar?page=1&size=3&esNuevaPeticion=true"))
		.andExpect(status().isOk()).andReturn();
		
		 MockHttpServletResponse response = result.getResponse();
		 
		 JSONObject json = new JSONObject(response.getContentAsString());
		 
		 log.debug(response.getContentAsString());
			
	    assertEquals(3,json.getInt("numberOfElements"));
		
	}

}
