package com.example.android.cargotranport.Adapter;

import android.view.View;

/**
 * 
 * @author J. García
 * 
 */
public interface OnSpinnerScrollListener {

	/**
	 * Se ejecuta cuando el spinner esta listo para agregar mas elementos
	 * 
	 * @param text
	 *            texto de busqueda
	 * @param pageIndex
	 *            indice de pagina
	 */
	public void onReadyForLoadingMoreItems(View v, String text, int pageIndex);

}
