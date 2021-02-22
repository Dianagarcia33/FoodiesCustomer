package com.foodies.customer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.foodies.customer.Models.CartFragParentModel;
import com.foodies.customer.R;

import java.util.List;

public class AdapterRecyclerProductos extends RecyclerView.Adapter<AdapterRecyclerProductos.ViewHolder> {

    private List<CartFragParentModel> mData;
    private LayoutInflater layoutInflater;
    private Context context;
    final AdapterRecyclerProductos.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(CartFragParentModel item);
    }

    public AdapterRecyclerProductos(List<CartFragParentModel> mData, Context context,AdapterRecyclerProductos.OnItemClickListener listener) {
        this.mData = mData;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public AdapterRecyclerProductos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.row_items_productos,null);
        return new AdapterRecyclerProductos.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final  AdapterRecyclerProductos.ViewHolder holder,final int position){
        holder.bindData(mData.get(position));
    }

    public void setItem(List<CartFragParentModel> item){
        mData = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre,descripTextView;

        ViewHolder(View itemView){
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreP);
            descripTextView = itemView.findViewById(R.id.descriptionP);
        }

        void bindData(final CartFragParentModel item){
            nombre.setText(item.getItem_name());
            descripTextView.setText(item.getItem_description());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
