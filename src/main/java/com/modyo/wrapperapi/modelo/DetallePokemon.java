package com.modyo.wrapperapi.modelo;

import java.util.List;

import lombok.Data;

@Data
public class DetallePokemon {
	
	private String name;
	private String foto;
	private List<Ability> abilities;
	private String weight;
	private List<Type> types;
	private Sprite sprites;
	private CommonProperties species;

}
