package com.modyo.wrapperapi.controller;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modyo.wrapperapi.dto.PokemonDTO;
import com.modyo.wrapperapi.error.AplicacionExcepcion;
import com.modyo.wrapperapi.service.PokemonService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/challenge")
public class PokemonRestController {
	
	@Autowired
	private PokemonService pokemonService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<PokemonDTO> obtenerListadoPokemonsPaginado(HttpServletRequest request,@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("esNuevaPeticion") boolean esNuevaPeticion) {
		
		Locale local = request.getLocale();
		final int currentPage = page.orElse(1);
		final int pageSize = size.orElse(10);
		Page<PokemonDTO> pagina = null;
		
		try {
			pagina = pokemonService.obtenerListadoPokemonsPaginado(PageRequest.of(currentPage - 1, pageSize));
		} catch (AplicacionExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pagina;
	}
}
