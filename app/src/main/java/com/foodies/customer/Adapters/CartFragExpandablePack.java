package com.foodies.customer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.foodies.customer.Models.CartFragChildModel;
import com.foodies.customer.Models.CartFragParentModel;
import com.foodies.customer.R;

import java.util.ArrayList;

public class CartFragExpandablePack  extends BaseExpandableListAdapter {

    Context context;
    ArrayList<CartFragParentModel> ListTerbaru;
    ArrayList<ArrayList<CartFragChildModel>> ListChildTerbaru;

    public CartFragExpandablePack(Context context, ArrayList<CartFragParentModel> listTerbaru, ArrayList<ArrayList<CartFragChildModel>> listChildTerbaru) {
        this.context = context;
        this.ListTerbaru = listTerbaru;
        ListChildTerbaru = listChildTerbaru;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public int getGroupCount() {
        return ListTerbaru.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(ListChildTerbaru.get(groupPosition).size() != 0){
            return ListChildTerbaru.get(groupPosition).size();
        }
        return 0;
    }

    @Override
    public CartFragParentModel getGroup(int groupPosition) {
        return ListTerbaru.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ListChildTerbaru.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CartFragParentModel terbaruModel = (CartFragParentModel) getGroup(groupPosition);
        CartFragExpandable.ViewHolder holder= null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_items_productos, null);

         /*   ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);*/

            holder=new CartFragExpandable.ViewHolder();
            holder.name_tv=(TextView)convertView.findViewById(R.id.nombreP);
            holder.price_tv = (TextView)convertView.findViewById(R.id.priceP);
            holder.item_detail_tv = (TextView)convertView.findViewById(R.id.descriptionP);
            convertView.setTag(holder);

        }

        else{
            holder=(CartFragExpandable.ViewHolder)convertView.getTag();
        }
        String quantity = terbaruModel.getItem_quantity();
        String name = terbaruModel.getItem_name();
        String price = terbaruModel.getItem_price();
        String symbol = terbaruModel.getItem_symbol();
        String desc = terbaruModel.getItem_description();
        holder.name_tv.setText(name);
        holder.price_tv.setText(symbol+price);

        holder.item_detail_tv.setText(desc);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CartFragChildModel childTerbaru = (CartFragChildModel) getChild(groupPosition, childPosition);

        CartFragExpandable.ViewHolder holder= null;
        notifyDataSetChanged();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_item_cart_child, null);

            holder=new CartFragExpandable.ViewHolder();
            holder.item_detail_tv = (TextView)convertView.findViewById(R.id.item_detail_tv);

            convertView.setTag(holder);
        }
        else{
            holder=(CartFragExpandable.ViewHolder)convertView.getTag();
        }

        String quantity = childTerbaru.getQuantity();
        String name = childTerbaru.getName();
        String price = childTerbaru.getPrice();
        String symbol = childTerbaru.getSymbol();
        holder.item_detail_tv.setText(quantity+"x "+name+" + "+symbol+price);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class ViewHolder{
        TextView name_tv,price_tv,item_detail_tv;
    }

}
