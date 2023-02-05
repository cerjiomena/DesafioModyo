package com.modyo.wrapperapi.integracion.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.modyo.wrapperapi.integracion.PokemonService;
import com.modyo.wrapperapi.modelo.Pokemon;
import com.modyo.wrapperapi.modelo.Pokemons;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase encargada de consumir e integrar el API de Pokedex
 * @author Sergio Mena
 *
 */
@Service
@PropertySource("classpath:restclient.properties")
@Slf4j
public class PokemonServiceImpl implements PokemonService {
	
	
	@Value("${rest.pokemon.operation.get}")
	private String uriPokemon;
	
	private WebClient webClient;
	
    @Autowired
	public PokemonServiceImpl(@Value("${rest.base.url}") String baseUrl) {
		this.webClient = WebClient.builder().baseUrl(baseUrl).build();
	}
    
    
	/**
	 * @see com.modyo.wrapperapi.integracion.PokemonService#obtenerListadoPaginadoPokemons()
	 */
	public void obtenerListadoPaginadoPokemons() {
		
		if(log.isDebugEnabled())
			log.debug("Entrando a obtenerListadoPaginadoPokemons");
		
		
		Pokemons pokemons = this.webClient.get().uri(uriPokemon).retrieve().bodyToMono(Pokemons.class).block();
		
	
		for (Pokemon pokemon : pokemons.getResults()) {
		
			log.debug(pokemon.getUrl());
		}


	}
	
	

}
