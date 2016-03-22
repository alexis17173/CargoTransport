package com.example.android.cargotranport.Adapter;

/**
 * Created by millesca on 15/11/2015.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cargotranport.Entity.TableItem;
import com.example.android.cargotranport.R;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private ArrayList<TableItem> tablas;

    public TableAdapter(ArrayList<TableItem> tablas) {
        this.tablas = tablas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTableTextView;
        CardView tableCard;
        ImageView img_check;

        public ViewHolder(View itemView) {
            super(itemView);
            tableCard = (CardView) itemView.findViewById(R.id.table_card);
            nameTableTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            img_check = (ImageView) itemView.findViewById(R.id.img_check);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.nameTableTextView.setText(tablas.get(position).getName());
        int icon;
        switch (tablas.get(position).getState()) {
            case Integer.MIN_VALUE:
                icon = R.drawable.load;
                break;
            case -1:
                icon = R.drawable.ic_error;
                break;
            case 0:
                icon = R.drawable.ic_warning;
                break;
            default:
                icon = R.drawable.check;
                break;
        }
        holder.img_check.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        return tablas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
