package com.modyo.wrapperapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.modyo.wrapperapi.DesafioModyoApplication;
import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.service.PokemonService;

import lombok.extern.slf4j.Slf4j;	

@SpringBootTest(classes= {DesafioModyoApplication.class})
@Slf4j
public class PokemonServiceImplTest {
	
	@Autowired
	private PokemonService pokemonService;
	
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
}
