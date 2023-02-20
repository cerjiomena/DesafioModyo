package com.modyo.wrapperapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria
 * @author Sergio Mena
 *
 */
public class Util {

	private static Pattern patron = Pattern.compile(Constantes.PATTERN_ID_URL);
	
	/**
	 * Metodo para obtener el id de una URL del api de pokemon
	 * @return
	 */
	public static String obtenerId(String cadena) {
		
		String resultado = null;
		
		Matcher m = patron.matcher(cadena);
		
		while (m.find()) {
			resultado = m.group().replaceAll(Constantes.SLASH_URL, Constantes.EMPTY_STRING);
		}
		
		return resultado;
	}
}
