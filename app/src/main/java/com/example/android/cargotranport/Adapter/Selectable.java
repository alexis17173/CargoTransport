package com.example.android.cargotranport.Adapter;

/**
 * 
 * @author J. García
 * 
 */
public interface Selectable {

	/**
	 * Retorna la key del elemento
	 * 
	 * @return key
	 */
	public String getKeyForItem();

	/**
	 * Retorna el valor del elemento
	 * 
	 * @return valor
	 */
	public String getValueForItem();

	public String getDescription();

}
