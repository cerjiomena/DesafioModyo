package com.modyo.wrapperapi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.modyo.wrapperapi.dto.DetallePokemonDTO;
import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.integracion.PokemonIntegracionService;
import com.modyo.wrapperapi.modelo.Ability;
import com.modyo.wrapperapi.modelo.DetallePokemon;
import com.modyo.wrapperapi.modelo.FlavorText;
import com.modyo.wrapperapi.modelo.Pokemon;
import com.modyo.wrapperapi.modelo.Pokemons;
import com.modyo.wrapperapi.modelo.Species;
import com.modyo.wrapperapi.modelo.Type;
import com.modyo.wrapperapi.service.PokemonService;
import com.modyo.wrapperapi.util.Constantes;
import com.modyo.wrapperapi.util.MensajeError;
import com.modyo.wrapperapi.util.Util;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase de servicio para tratar la informacion de los pokemons
 * 
 * @author Sergio Mena
 *
 */
@Service
@Slf4j
public class PokemonServiceImpl implements PokemonService {

	@Autowired
	private PokemonIntegracionService pokemonIntegracionService;

	private List<PokemonDTO> listado;
	
	private List<String> listEvolutions;

	private boolean isSpeciesNode = false;

	/**
	 * {@link PokemonService#obtenerListadoPokemonsPaginado(Pageable)}
	 */
	@Cacheable("listar-pokemons")
	public Page<PokemonDTO> obtenerListadoPokemonsPaginado(Pageable pageable) throws AplicacionExcepcion {

		Page<PokemonDTO> pages = null;
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<PokemonDTO> list;
		Pokemons pokemons = pokemonIntegracionService.obtenerListadoPaginadoPokemons();
		
		if (pokemons != null) {

			listado = new ArrayList<PokemonDTO>();
			
			if(pokemons.getResults() != null) {
				
				for (Pokemon pokemon : pokemons.getResults()) {

					PokemonDTO pokemonDTO = new PokemonDTO();
					pokemonDTO.setId(Util.obtenerId(pokemon.getUrl()));
					DetallePokemon detallePokemon = pokemonIntegracionService.obtenerDetallePokemon(pokemonDTO.getId());
					pokemonDTO.setNombre(detallePokemon.getName());
					pokemonDTO.setPeso(detallePokemon.getWeight());
					List<String> tipos = new ArrayList<String>();
					for (Type type : detallePokemon.getTypes()) {
						tipos.add(type.getType().getName());
					}
					pokemonDTO.setTipo(tipos.toString());
					List<String> habilidades = new ArrayList<String>();
					for (Ability ability : detallePokemon.getAbilities()) {
						habilidades.add(ability.getAbility().getName());
					}
					pokemonDTO.setFoto(detallePokemon.getSprites().getFront_default());
					pokemonDTO.setHabilidades(habilidades.toString());
					listado.add(pokemonDTO);
				}

				if (listado.size() < startItem) {
					list = Collections.emptyList();
				} else {
					int toIndex = Math.min(startItem + pageSize, listado.size());
					list = listado.subList(startItem, toIndex);
				}

				pages = new PageImpl<PokemonDTO>(list, PageRequest.of(currentPage, pageSize),
						Long.valueOf(listado.size()).longValue());	
			}

		} 

		return pages;
	}

	/**
	 * {@link PokemonService#obtenerDetallePokemon(String)}
	 */
	@Cacheable("detalle-pokemon")
	public DetallePokemonDTO obtenerDetallePokemon(String id) throws AplicacionExcepcion {

		this.isSpeciesNode = false;
		List<String> descripciones = null;
		Species species = null;

		DetallePokemon detallePokemon = pokemonIntegracionService.obtenerDetallePokemon(id);
		
		if(detallePokemon != null) {
			species = pokemonIntegracionService.obtenerDescripcion(id);
				
			if(species != null) {
				
				List<FlavorText> listado = species.getFlavor_text_entries();
				descripciones = new ArrayList<String>();
				for (FlavorText flavorText : listado) {
					descripciones.add(flavorText.getFlavor_text());
				}

				String evolucion = pokemonIntegracionService.obtenerEvoluciones(Util.obtenerId(species.getEvolution_chain().getUrl()));
				
				if(evolucion != null) {

					this.listEvolutions = new ArrayList<String>();
					JSONObject jsonObj = new JSONObject(evolucion);
					this.handleValue(jsonObj);
				
				}


			}
		} else {
			throw new AplicacionExcepcion(MensajeError.ERROR_NO_INFO_DETALLE);
		}

		return new DetallePokemonDTO(descripciones, this.listEvolutions);
	}

	/**
	 * Metodo para manejar el valor del objeto json recorriendo de manera recursiva
	 * nodo json species de la cadena de evolucion
	 * 
	 * @param value
	 * @return
	 */
	private void handleValue(Object value) {

		if (value instanceof JSONObject) {
			handleJSONObject((JSONObject) value);
		} else if (value instanceof JSONArray) {
			handleJSONArray((JSONArray) value);
		} else {
			if (this.isSpeciesNode) {
				this.listEvolutions.add(String.valueOf(value));
				this.isSpeciesNode = false;
			}
		}

	}

	/**
	 * En caso de que sea ub objeto JSON
	 * 
	 * @param jsonObject
	 */
	private void handleJSONObject(JSONObject jsonObject) {
		jsonObject.keys().forEachRemaining(key -> {
			Object value = jsonObject.get(key);
			if (key.equals(Constantes.NODE_SPECIES)) {
				this.isSpeciesNode = true;
			}
			handleValue(value);
		});
	}

	/**
	 * En caso de que sea un arreglo JSON
	 * 
	 * @param jsonArray
	 */
	private void handleJSONArray(JSONArray jsonArray) {
		jsonArray.iterator().forEachRemaining(element -> {
			handleValue(element);
		});
	}

}
