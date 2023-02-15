package com.modyo.wrapperapi.integracion.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	@Disabled
	public void debeObtenerEvolucionesPokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerEvolucionesPokemon");
		
		String evoluciones = pokemonService.obtenerEvoluciones("https://pokeapi.co/api/v2/evolution-chain/25");
		assertNotNull(evoluciones);
	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle del pokemon
	 * @throws AplicacionExcepcion 
	 */
	@Test
	@Disabled
	public void debeObtenerExcepcionEnDetalle() throws AplicacionExcepcion {
		
		log.debug("Entrando a debeObtenerExcepcionEnDetalle");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerDetallePokemon("https://pokeapi.co/api/v2/pokemon/10000000/"), "Detalle pokemon no encontrado");
		

	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle del pokemon
	 * @throws AplicacionExcepcion 
	 */
	@Test
	@Disabled
	public void debeObtenerExcepcionEnDescripciones() {
		
		log.debug("Entrando a debeObtenerExcepcionEnDetalle");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerDescripcion("https://pokeapi.co/api/v2/pokemon-species/10000000/"), "Detalle pokemon no encontrado");
		

	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle de evolucion
	 * @throws AplicacionExcepcion 
	 */
	@Test
	public void debeObtenerExcepcionEnEvoluciones() {
		
		log.debug("Entrando a debeObtenerExcepcionEnEvoluciones");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerEvoluciones("https://pokeapi.co/api/v2/evolution-chain/10000000/"), "Evoluciones de pokemon no encontrados");
		

	}

}
