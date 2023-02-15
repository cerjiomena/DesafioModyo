package com.modyo.wrapperapi.util;

public enum MensajeError {
	
	ERROR_TIEMPO_ESPERA_OPERACION("error.timeout"),
	ERROR_NO_INFO_DETALLE("error.data.detail.pokemon"),
	ERROR_NO_INFO_DESCRIPCIONES("error.data.descriptions.pokemon"),
	ERROR_NO_INFO_EVOLUCIONES("error.data.evolutions.pokemon"),
	ERROR_NO_INFO_LISTADO_POKEMONS("error.data.list.pokemon");
	
	private String llaveMensaje;
	
	private MensajeError(String llaveMensaje) {
		this.llaveMensaje= llaveMensaje;
	}
	
	public String getLLaveMensaje() {
		return llaveMensaje;
	}

}
