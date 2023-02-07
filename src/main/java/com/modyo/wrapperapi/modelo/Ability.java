package com.modyo.wrapperapi.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "ability")
public class Ability {

	private CommonProperties ability;
	
}
