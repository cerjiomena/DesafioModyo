package com.modyo.wrapperapi.modelo;

import java.util.List;

import lombok.Data;

@Data
public class Pokemons {

	private Integer count;
	
	private String next;
	
	private String previous;
	
	private List<Pokemon> results;
}
