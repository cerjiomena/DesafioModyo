package com.modyo.wrapperapi.integracion.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/**
	 * Prueba para obtener el listado de pokemons
	 * @throws AplicacionExcepcion
	 */
	@Test
	public void debeObtenerListadoPokemons() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerListadoPokemons");
		Pokemons pokemons =  pokemonService.obtenerListadoPaginadoPokemons();
		assertNotNull(pokemons);
		assertTrue(pokemons.getResults().size() > 0);
		
	}
	
	/**
	 * Prueba para obtener el detalle pokemon
	 * @throws AplicacionExcepcion
	 */
	@Test
	public void debeObtenerDetallePokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerDetallePokemon");
		
		DetallePokemon detallePokemon = pokemonService.obtenerDetallePokemon("3");
		assertNotNull(detallePokemon);
		log.debug(detallePokemon.toString());
		
	}
	
	/**
	 * Prueba para obtener descripciones pokemon
	 * @throws AplicacionExcepcion
	 */
	@Test
	public void debeObtenerDescripcionesPokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerDescripcionesPokemon");
		
		Species species = pokemonService.obtenerDescripcion("25");
		assertNotNull(species);
		log.debug(species.toString());
		
	}
	
	/**
	 * Prueba para obtener evoluciones
	 * @throws AplicacionExcepcion
	 */
	@Test
	public void debeObtenerEvolucionesPokemon() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerEvolucionesPokemon");
		
		String evoluciones = pokemonService.obtenerEvoluciones("3");
		log.debug("*****************************");
		assertNotNull(evoluciones);
		log.debug(evoluciones.toString());
	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle del pokemon
	 * @throws AplicacionExcepcion 
	 */
	@Test
	public void debeObtenerExcepcionEnDetalle() throws AplicacionExcepcion {
		
		log.debug("Entrando a debeObtenerExcepcionEnDetalle");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerDetallePokemon("10000000"), "Detalle pokemon no encontrado");
		

	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle del pokemon
	 * @throws AplicacionExcepcion 
	 */
	@Test
	public void debeObtenerExcepcionEnDescripciones() {
		
		log.debug("Entrando a debeObtenerExcepcionEnDetalle");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerDescripcion("10000000"), "Detalle pokemon no encontrado");
		

	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle de evolucion
	 * @throws AplicacionExcepcion 
	 */
	@Test
	public void debeObtenerExcepcionEnEvoluciones() {
		
		log.debug("Entrando a debeObtenerExcepcionEnEvoluciones");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerEvoluciones("10000000"), "Evoluciones de pokemon no encontrados");
		

	}
	

}
