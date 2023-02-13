package com.modyo.wrapperapi.modelo;

import java.util.List;

import lombok.Data;

@Data
public class Species {
	
	private List<FlavorText> flavor_text_entries;
	private CommonProperties evolution_chain;

}
