package com.modyo.wrapperapi.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "sprites")
public class Sprite {

	private String front_default;
}
