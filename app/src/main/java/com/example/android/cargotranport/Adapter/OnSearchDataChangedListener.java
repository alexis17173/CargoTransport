package com.example.android.cargotranport.Adapter;

import android.view.View;
import android.widget.AbsListView;

/**
 * 
 * @author J. García
 * 
 */
public interface OnSearchDataChangedListener {

	/**
	 * Se ejecuta cuando cambia el valor del campo de texto
	 * 
	 * @param v
	 *            listview
	 * @param text
	 *            text ingresado
	 */
	public void onDataChanged(View v, AbsListView listView, String text);

}
