package com.modyo.wrapperapi.integracion;

import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.modelo.DetallePokemon;
import com.modyo.wrapperapi.modelo.Pokemons;
import com.modyo.wrapperapi.modelo.Species;

public interface PokemonIntegracionService {
	
	/**
	 * Metodo que obtiene el listado de los pokemons consumiendo la Poke API
	 * @return Listado de pokemons y otras propiedades
	 * @throws AplicacionExcepcion
	 */
	Pokemons obtenerListadoPaginadoPokemons() throws AplicacionExcepcion;
	
	/**
	 * Metodo  encargado en obtener el detalle del pokemon
	 * @param url del servicio
	 * @return Objeto detalle Pokemon
	 * @throws AplicacionExcepcion
	 */
	DetallePokemon obtenerDetallePokemon(String url) throws AplicacionExcepcion;
	
	
	/**
	 * Metodo utilizado para obtener la descripcion de los pokemons
	 * @param url del servicio
	 * @return Objeto especies con el texto descriptivo
	 * @throws AplicacionExcepcion
	 */
	Species obtenerDescripcion(String url) throws AplicacionExcepcion;
	
	
	/**
	 * Metodo utilizado para obtener la cadena de evoluciones en String posteriormente para ser tratada la informacion
	 * @param url del servicio
	 * @return Cadena de evoluciones en formato String
	 * @throws AplicacionExcepcion
	 */
	String obtenerEvoluciones(String url) throws AplicacionExcepcion;
	
	

}
