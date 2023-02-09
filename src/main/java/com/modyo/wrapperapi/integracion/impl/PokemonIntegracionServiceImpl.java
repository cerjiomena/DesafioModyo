package com.modyo.wrapperapi.integracion.impl;

import java.time.Duration;

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
import com.modyo.wrapperapi.modelo.Pokemons;
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
			log.debug("Entrando a obtenerDetallePokemon");
		
		 
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
	
	

}
