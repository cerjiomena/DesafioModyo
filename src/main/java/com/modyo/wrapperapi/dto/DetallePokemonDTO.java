package com.modyo.wrapperapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class DetallePokemonDTO {

	List<String> descripciones;
	List<String> evoluciones;
	
	public DetallePokemonDTO(List<String> descripciones, List<String> evoluciones) {
		this.descripciones = descripciones;
		this.evoluciones = evoluciones;
	}
}
