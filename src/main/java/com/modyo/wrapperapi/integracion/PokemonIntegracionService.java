package com.modyo.wrapperapi.integracion;

import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.modelo.DetallePokemon;
import com.modyo.wrapperapi.modelo.Pokemons;

public interface PokemonIntegracionService {
	
	/**
	 * Servicio que obtiene el listado de los pokemons consumiendo la Poke API
	 * @return Listado de pokemons y otras propiedades
	 * @throws AplicacionExcepcion
	 */
	Pokemons obtenerListadoPaginadoPokemons() throws AplicacionExcepcion;
	
	/**
	 * Servicio encargado en obtener el detalle del pokemon
	 * @param url del servicio
	 * @return Objeto detalle Pokemon
	 * @throws AplicacionExcepcion
	 */
	DetallePokemon obtenerDetallePokemon(String url) throws AplicacionExcepcion;

}
