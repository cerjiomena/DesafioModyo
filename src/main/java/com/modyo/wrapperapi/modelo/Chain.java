package com.modyo.wrapperapi.modelo;

import java.util.List;

import lombok.Data;

@Data
public class Chain {
	
	private CommonProperties species; 
	
	private List<EvolvesTo> evolves_to;
}
