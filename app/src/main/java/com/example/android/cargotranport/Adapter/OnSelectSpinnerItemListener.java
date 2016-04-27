package com.example.android.cargotranport.Adapter;

import android.view.View;
import android.widget.Adapter;

/**
 * 
 * @author J. García
 * 
 */
public interface OnSelectSpinnerItemListener {

	/**
	 * Se ejecuta cuando se selecciona un elemento de lista
	 * 
	 * @param adapter
	 *            adapter del spinner
	 * @param item
	 *            elemento seleccionado
	 * @param position
	 *            posicion actual
	 */
	public void onSelectItem(View v, Adapter adapter, Selectable item, int position);

}
