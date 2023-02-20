package com.modyo.wrapperapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.modyo.wrapperapi.dto.DetallePokemonDTO;
import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;

public interface PokemonService {
	
	/**
	 * Metodo utilizado para obtener el listado de pokemons paginado
	 * @param pageable 
	 * @return Pagina de los pokemons
	 * @throws AplicacionExcepcion
	 */
	Page<PokemonDTO> obtenerListadoPokemonsPaginado(Pageable pageable) throws AplicacionExcepcion;
	
	
	/**
	 * Metodo utilizado para obtener el detalle del pokemon
	 * @param id identificador del pokemon
	 * @return Clase de transporte con ambos detalles.
	 * @throws AplicacionExcepcion
	 */
	DetallePokemonDTO obtenerDetallePokemon(String id)  throws AplicacionExcepcion;

}
