package com.modyo.wrapperapi.integracion.impl;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.integracion.PokemonIntegracionService;
import com.modyo.wrapperapi.modelo.DetallePokemon;
import com.modyo.wrapperapi.modelo.FlavorText;
import com.modyo.wrapperapi.modelo.Pokemons;
import com.modyo.wrapperapi.modelo.Species;
import com.modyo.wrapperapi.util.Constantes;
import com.modyo.wrapperapi.util.MensajeError;

import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase encargada de consumir e integrar el API de Pokedex
 * @author Sergio Mena
 *
 */
@Service
@PropertySource("classpath:restclient.properties")
@Slf4j
public class PokemonIntegracionServiceImpl implements PokemonIntegracionService {
	
	
	@Value("${rest.pokemon.operation.get}")
	private String uriPokemon;
	
	private WebClient webClient;
	
    @Autowired
	public PokemonIntegracionServiceImpl(@Value("${rest.base.url}") String baseUrl) {
		this.webClient = WebClient.builder().baseUrl(baseUrl).codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(500*1024)).build();
	}
    
	@Autowired
	private MessageSource messageSource;
    
	/**
	 * {@link PokemonIntegracionService#obtenerListadoPaginadoPokemons()}
	 */
	public Pokemons obtenerListadoPaginadoPokemons() throws AplicacionExcepcion {
		
		if(log.isDebugEnabled())
			log.debug("Entrando a PokemonIntegracionServiceImpl.obtenerListadoPaginadoPokemons");
		
		
		Pokemons pokemons = this.webClient.get()
				.uri(uriPokemon)
				.retrieve()
				.bodyToMono(Pokemons.class)
				.timeout(Duration.ofSeconds(5))
				.onErrorMap(ReadTimeoutException.class, ex -> new AplicacionExcepcion(MensajeError.ERROR_TIEMPO_ESPERA_OPERACION))
				.doOnError(WriteTimeoutException.class, ex -> log.error(messageSource.getMessage(
						MensajeError.ERROR_TIEMPO_ESPERA_OPERACION.getLLaveMensaje(), 
						null, LocaleContextHolder.getLocale())))
				.block();
		
	
		return pokemons;


	}

	/**
	 * {@link PokemonIntegracionService#obtenerDetallePokemon(String)}
	 */
	public DetallePokemon obtenerDetallePokemon(String url) throws AplicacionExcepcion {
		
		if(log.isDebugEnabled())
			log.debug("Entrando a PokemonIntegracionServiceImpl.obtenerDetallePokemon");
		
		 
		 DetallePokemon detallePokemon = this.webClient.get()
							.uri(url)
							.retrieve()
							.bodyToMono(DetallePokemon.class)
							.timeout(Duration.ofSeconds(5))
							.onErrorMap(ReadTimeoutException.class, ex -> new AplicacionExcepcion(MensajeError.ERROR_TIEMPO_ESPERA_OPERACION))
							.doOnError(WriteTimeoutException.class, ex -> log.error(messageSource.getMessage(
									MensajeError.ERROR_TIEMPO_ESPERA_OPERACION.getLLaveMensaje(), 
									null, LocaleContextHolder.getLocale())))
							.block();
		
		return detallePokemon;
	}

	/**
	 * {@link PokemonIntegracionService#obtenerDescripcion(String)}
	 */
	public Species obtenerDescripcion(String url) throws AplicacionExcepcion {
		if(log.isDebugEnabled())
			log.debug("Entrando a PokemonIntegracionServiceImpl.obtenerDescripcion");
		
		Species resultado = this.webClient.get()
							.uri(url)
							.retrieve()
							.bodyToMono(Species.class)
							.map(species -> {
								List<FlavorText> matches = species.getFlavor_text_entries()
										.stream()
										.filter( flavor -> flavor.getLanguage().getName().equals(Constantes.LANGUAGE))
										.collect(Collectors.toList());
								species.setFlavor_text_entries(matches);
								return species;
							})
							.timeout(Duration.ofSeconds(5))
							.onErrorMap(ReadTimeoutException.class, ex -> new AplicacionExcepcion(MensajeError.ERROR_TIEMPO_ESPERA_OPERACION))
							.doOnError(WriteTimeoutException.class, ex -> log.error(messageSource.getMessage(
									MensajeError.ERROR_TIEMPO_ESPERA_OPERACION.getLLaveMensaje(), 
									null, LocaleContextHolder.getLocale())))
							.block();
		
		return resultado;
		
	}


	/**
	 * {@link PokemonIntegracionService#obtenerEvoluciones(String)}
	 */
	public void obtenerEvoluciones(String url) throws AplicacionExcepcion {
		if(log.isDebugEnabled())
			log.debug("Entrando a PokemonIntegracionServiceImpl.obtenerEvoluciones");
		
		String resultado = this.webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(String.class)
				.timeout(Duration.ofSeconds(5))
				.onErrorMap(ReadTimeoutException.class, ex -> new AplicacionExcepcion(MensajeError.ERROR_TIEMPO_ESPERA_OPERACION))
				.doOnError(WriteTimeoutException.class, ex -> log.error(messageSource.getMessage(
						MensajeError.ERROR_TIEMPO_ESPERA_OPERACION.getLLaveMensaje(), 
						null, LocaleContextHolder.getLocale())))
				.block();
		log.debug(resultado);
	}


	
	//https://pokeapi.co/api/v2/pokemon-species/25
	//flavor_text
	//https://pokeapi.co/api/v2/evolution-chain/25
	

}
