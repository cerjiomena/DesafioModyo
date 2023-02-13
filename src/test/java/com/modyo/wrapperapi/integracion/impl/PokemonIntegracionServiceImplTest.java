package com.modyo.wrapperapi.integracion.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.modyo.wrapperapi.DesafioModyoApplication;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.integracion.PokemonIntegracionService;
import com.modyo.wrapperapi.modelo.DetallePokemon;
import com.modyo.wrapperapi.modelo.Pokemons;
import com.modyo.wrapperapi.modelo.Species;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes= {DesafioModyoApplication.class})
@Slf4j
public class PokemonIntegracionServiceImplTest {
	
	@Autowired
	private PokemonIntegracionService pokemonService;
	
	@Test
	@Disabled
	public void debeObtenerListadoPokemons() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerListadoPokemons");
		Pokemons pokemons =  pokemonService.obtenerListadoPaginadoPokemons();
		assertNotNull(pokemons);
		assertTrue(pokemons.getResults().size() > 0);
		
	}
	
	@Test
	@Disabled
	public void debeObtenerDetallePokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerDetallePokemon");
		
		DetallePokemon detallePokemon = pokemonService.obtenerDetallePokemon("https://pokeapi.co/api/v2/pokemon/1/");
		assertNotNull(detallePokemon);
		log.debug(detallePokemon.toString());
		
	}
	
	@Test
	@Disabled
	public void debeObtenerDescripcionesPokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerDescripcionesPokemon");
		
		Species species = pokemonService.obtenerDescripcion("https://pokeapi.co/api/v2/pokemon-species/25");
		assertNotNull(species);
		log.debug(species.toString());
		
	}
	
	@Test
	public void debeObtenerEvolucionesPokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerEvolucionesPokemon");
		
		pokemonService.obtenerEvoluciones("https://pokeapi.co/api/v2/evolution-chain/25");
	}

}
