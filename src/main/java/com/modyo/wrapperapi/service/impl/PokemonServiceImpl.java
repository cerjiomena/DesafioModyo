package com.modyo.wrapperapi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.integracion.PokemonIntegracionService;
import com.modyo.wrapperapi.modelo.Ability;
import com.modyo.wrapperapi.modelo.DetallePokemon;
import com.modyo.wrapperapi.modelo.Pokemon;
import com.modyo.wrapperapi.modelo.Pokemons;
import com.modyo.wrapperapi.modelo.Type;
import com.modyo.wrapperapi.service.PokemonService;

/**
 * Clase de servicio para tratar la informacion de los pokemons
 * @author Sergio Mena
 *
 */
@Service
public class PokemonServiceImpl implements PokemonService {

	@Autowired
	private PokemonIntegracionService pokemonIntegracionService;

	List<PokemonDTO> listado;

	/**
	 * {@link PokemonService#obtenerListadoPokemonsPaginado(Pageable)}
	 */
	public Page<PokemonDTO> obtenerListadoPokemonsPaginado(Pageable pageable) throws AplicacionExcepcion {

		Page<PokemonDTO> pages = null;
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<PokemonDTO> list;
		Pokemons pokemons = pokemonIntegracionService.obtenerListadoPaginadoPokemons();

		if (pokemons != null) {

			listado = new ArrayList<PokemonDTO>();

			for (Pokemon pokemon : pokemons.getResults()) {

				DetallePokemon detallePokemon = pokemonIntegracionService.obtenerDetallePokemon(pokemon.getUrl());
				PokemonDTO pokemonDTO = new PokemonDTO();
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

		return pages;
	}

}
