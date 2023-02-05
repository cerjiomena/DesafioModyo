package com.modyo.wrapperapi.integracion.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.modyo.wrapperapi.DesafioModyoApplication;
import com.modyo.wrapperapi.integracion.PokemonService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes= {DesafioModyoApplication.class})
@Slf4j
public class PokemonServiceImplTest {
	
	@Autowired
	private PokemonService pokemonService;
	
	@Test
	public void debeObtenerListadoPokemons() {
		log.debug("Entrando a debeObtenerListadoPokemons");
		pokemonService.obtenerListadoPaginadoPokemons();
		
	}

}
