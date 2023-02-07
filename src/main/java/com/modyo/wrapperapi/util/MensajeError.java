package com.modyo.wrapperapi.util;

public enum MensajeError {
	
	ERROR_TIEMPO_ESPERA_OPERACION("error.timeout");
	
	private String llaveMensaje;
	
	private MensajeError(String llaveMensaje) {
		this.llaveMensaje= llaveMensaje;
	}
	
	public String getLLaveMensaje() {
		return llaveMensaje;
	}

}
