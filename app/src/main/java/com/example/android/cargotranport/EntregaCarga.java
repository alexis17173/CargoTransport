package com.example.android.cargotranport;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.cargotranport.Adapter.EntregaAdapter;
import com.example.android.cargotranport.Adapter.OnCloseDialogFragmentListener;
import com.example.android.cargotranport.Adapter.OnSelectSpinnerItemListener;
import com.example.android.cargotranport.Adapter.Selectable;
import com.example.android.cargotranport.Entity.Client;
import com.example.android.cargotranport.model.OperationModel;
import com.example.android.cargotranport.ui.fragment.CustomSpinnerFragment;

import java.util.List;

public class EntregaCarga extends AppCompatActivity {
    //private final static String[] Clientes = {"" +
         //   "Hiraoka", "Curacao", "Carsa",
          //  "Tottus", "Metro", "Paris"};
    //public Spinner spCliente;
    private CustomSpinnerFragment mSpinnerFragment;
    private EntregaAdapter mEntregaAdapter;
    private TextView mActionBarTitleTextView;
    private RecyclerView mPeopleRecyclerView;
    private RecyclerView.LayoutManager mPeopleLayoutManager;
   // private TextView mActionBarSubtitleTextView;
    private OperationModel mOperationModel;
    private Button btnClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_carga);
        createActionBar();
        mOperationModel = OperationModel.getInstance();
        btnClient=(Button)findViewById(R.id.btnClient);
        mPeopleRecyclerView = (RecyclerView) findViewById(R.id.rcvListaDatos);
        mPeopleRecyclerView.setHasFixedSize(true);
        mPeopleLayoutManager = new LinearLayoutManager(this);
        mPeopleRecyclerView.setLayoutManager(mPeopleLayoutManager);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       // ArrayAdapter adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_spinner_dropdown_item, Clientes);
        //spCliente = (Spinner) findViewById(R.id.cboCliente);
        //spCliente.setAdapter(adapter);

    }
    public  void venta1(View view)
    {
        Intent intent = new Intent(this, ListaMandar.class);
        startActivity(intent);
    }

    public  void venta2(View view)
    {
        Intent intent = new Intent(this, TrazabilidadTransporte.class);
        startActivity(intent);
    }

    private void createActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_main, null);
        mActionBarTitleTextView = (TextView) mCustomView.findViewById(R.id.txt_title);
        mActionBarTitleTextView.setText("Entrega de Carga");

        //mActionBarSubtitleTextView = (TextView) mCustomView.findViewById(R.id.txt_subtitle);
        //mActionBarSubtitleTextView.setText("Entrega de Carga");

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);


    }
    public void onSpinnerClicked(View v) {
        showSpinner(v);
    }
    private void showSpinner(View v) {

        int count = 0;
        List items = null;
        String title = null;
            title = "Seleccione Cliente";
        items = mOperationModel.getListClient();
        count = mOperationModel.getListClient().size();

        mSpinnerFragment = CustomSpinnerFragment.create(items, v, false);
        mSpinnerFragment.setTitle(title);
        mSpinnerFragment.setItemsCount(count);
        mSpinnerFragment.setOnSelectItemListener(mOnSelectItemListener);
        mSpinnerFragment.show(getFragmentManager(), "spinnerFragment");
        mSpinnerFragment.setOnCloseDialogFragmentListener(mOnCloseDialogFragmentListener);
    }

    private OnSelectSpinnerItemListener mOnSelectItemListener = new OnSelectSpinnerItemListener() {
        @Override
        public void onSelectItem(View v, Adapter adapter, Selectable item, int position) {
            Button button = (Button) v;
            button.setText(item.getValueForItem());
            button.setTag(item);

            loadProgrammedCarga();
        }
    };

    private OnCloseDialogFragmentListener mOnCloseDialogFragmentListener = new OnCloseDialogFragmentListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {

        }
    };

    public void loadProgrammedCarga() {
        if (btnClient.getTag() != null) {
            Client client = (Client) btnClient.getTag();
            mEntregaAdapter = new EntregaAdapter(mOperationModel.getProgrammedCargas(client.getClientid()));
            mPeopleRecyclerView.setAdapter(mEntregaAdapter);
        } else {
            if (mEntregaAdapter != null) {
                mEntregaAdapter.clear();
                mEntregaAdapter.notifyDataSetChanged();
            }
        }
    }
}
