package com.modyo.wrapperapi.dto;

import lombok.Data;

@Data
public class PokemonDTO {
	
	private String nombre;
	private String foto;
	private String tipo;
	private String peso;
	private String habilidades;
	private String urlDetalle;

}
