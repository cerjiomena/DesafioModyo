package com.modyo.wrapperapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.modyo.wrapperapi.DesafioModyoApplication;
import com.modyo.wrapperapi.dto.DetallePokemonDTO;
import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.service.PokemonService;

import lombok.extern.slf4j.Slf4j;	

@SpringBootTest(classes= {DesafioModyoApplication.class})
@Slf4j
public class PokemonServiceImplTest {
	
	@Autowired
	private PokemonService pokemonService;
	
	/**
	 * Prueba para obtener los pokemons paginados
	 * @throws AplicacionExcepcion
	 */
	@Test
	public void debeObtenerPokemonesPaginado() throws AplicacionExcepcion {
		log.debug("Entrando a debeObtenerPokemonesPaginado");
		
		Pageable pageable = PageRequest.of(0, 2);
		
		Page<PokemonDTO> resultado = pokemonService.obtenerListadoPokemonsPaginado(pageable);
		
		assertThat(resultado).hasSize(2);
		assertThat(pageable.getPageNumber()).isEqualTo(0);
		
		Pageable nextPageable = pageable.next();
		
		resultado = pokemonService.obtenerListadoPokemonsPaginado(nextPageable);
			
		assertThat(resultado).hasSize(2);
		assertThat(nextPageable.getPageNumber()).isEqualTo(1);
		
		
		List<PokemonDTO>  listadoPokemones = resultado.getContent();
		
		for (PokemonDTO pokemonDTO : listadoPokemones) {
			log.debug(pokemonDTO.toString());
		}

	}
	
	/**
	 * Prueba para obtener el detalle del pokemon
	 * @throws AplicacionExcepcion 
	 */
	@Test
	public void debeObtenerDetallePokemon() throws AplicacionExcepcion {
		
		log.debug("Entrando a debeObtenerDetallePokemon");
		
		DetallePokemonDTO pokemonDTO =  pokemonService.obtenerDetallePokemon("3");
		
		assertNotNull(pokemonDTO);
		
		log.debug(pokemonDTO.toString());
		
		
		
	}
	
	/**
	 * Prueba para lanzar un excepcion al no encontrar el detalle del pokemon
	 * @throws AplicacionExcepcion 
	 */
	@Test
	public void debeObtenerExcepcion() throws AplicacionExcepcion {
		
		log.debug("Entrando a debeObtenerExcepcion");
		
		assertThrows(AplicacionExcepcion.class, () -> pokemonService.obtenerDetallePokemon("10000000"), "Detalle pokemon no encontrado");
		

	}
		
		
		
}
