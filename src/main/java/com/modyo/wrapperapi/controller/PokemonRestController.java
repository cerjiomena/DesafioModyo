package com.modyo.wrapperapi.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modyo.wrapperapi.dto.DetallePokemonDTO;
import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.service.PokemonService;
import com.modyo.wrapperapi.util.Constantes;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/challenge")
@Slf4j
public class PokemonRestController {
	
	@Autowired
	private PokemonService pokemonService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping(value = "listar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Servicio web para obtener el listado de pokemons")
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = -200, message = "Error of communication")
	})
	public ResponseEntity<Map<String, Object>> obtenerListadoPokemonsPaginado(HttpServletRequest request,
			@ApiParam(value = "page", required = true, example = "1") @RequestParam("page") Optional<Integer> page,
			@ApiParam(value = "size", required = true, example = "3") @RequestParam("size") Optional<Integer> size) {
		
		if(log.isDebugEnabled())
			log.debug(">> Entrando a PokemonRestController.obtenerListadoPokemonsPaginado << ");
		
	
		
		Locale local = request.getLocale();
		final int currentPage = page.orElse(1);
		final int pageSize = size.orElse(10);
		Page<PokemonDTO> pagina = null;
		List<PokemonDTO> pokemons = null;
		String mensaje = null;
		Map<String, Object> response = new HashMap<>();
		
		response.put(Constantes.TIMESTAMP, new Timestamp(System.currentTimeMillis()));
		
		
		try {
			pagina = pokemonService.obtenerListadoPokemonsPaginado(PageRequest.of(currentPage - 1, pageSize));
			pokemons = pagina.getContent();
			if(pokemons.isEmpty()) {
				pagina = Page.empty();
			}
			response.put(Constantes.STATUS, HttpStatus.OK.value());
	        response.put(Constantes.POKEMONS, pokemons);
	        response.put(Constantes.CURRENT_PAGE, pagina.getNumber());
	        response.put(Constantes.TOTAL_ELEMENTS, pagina.getTotalElements());
	        response.put(Constantes.TOTAL_PAGES, pagina.getTotalPages());
	        response.put(Constantes.NUMBER_OF_ELEMENTS, pagina.getNumberOfElements());
	        
		} catch (AplicacionExcepcion e) {
			
			if(e.getCodigoError() != null) {
				mensaje = messageSource.getMessage(e.getCodigoError().getLLaveMensaje(), null, local);
			} else {
				mensaje = e.getMessage();
			}
			
			response.put(Constantes.MESSAGE, mensaje);
			response.put(Constantes.STATUS, Constantes.ERROR_VALIDATION);
			
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "obtenerDetalle", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Servicio web para obtener el detalle del pokemon")
	public ResponseEntity<Map<String, Object>> obtenerDetallePokemon(HttpServletRequest request,
			@ApiParam(value = "url", required = true, example = "https://pokeapi.co/api/v2/pokemon/2/") @RequestParam("url") Optional<String> url){
		
		if(log.isDebugEnabled())
			log.debug(">> Entrando a PokemonRestController.obtenerDetallePokemon << ");
		
		Locale local = request.getLocale();;
		String mensaje = null;
		Map<String, Object> response = new HashMap<>();
		
		response.put(Constantes.TIMESTAMP, new Timestamp(System.currentTimeMillis()));
		
		
		try {
			
			DetallePokemonDTO detallePokemonDTO = pokemonService.obtenerDetallePokemon(url.get());
			response.put(Constantes.STATUS, HttpStatus.OK.value());
	        response.put(Constantes.DETAIL_POKEMON, detallePokemonDTO);
	       
	        
		} catch (AplicacionExcepcion e) {
			
			if(e.getCodigoError() != null) {
				mensaje = messageSource.getMessage(e.getCodigoError().getLLaveMensaje(), null, local);
			} else {
				mensaje = e.getMessage();
			}
			
			response.put(Constantes.MESSAGE, mensaje);
			response.put(Constantes.STATUS, Constantes.ERROR_VALIDATION);
			
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
}
