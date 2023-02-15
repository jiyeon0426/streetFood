package com.kumoh19.streetfood3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<ShopItem> mShopList;


    Fragment fg;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);

        ViewHolder viewholder = new ViewHolder(context, view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
//        String text1 = arrayList.get(position);
//        //String text2 = arrayList.get(position);
//        holder.textView1.setText(text1);
//        //holder.textView2.setText(text2);
        holder.onBind(mShopList.get(position));
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    // 데이터를 입력
    public void setShopList(ArrayList<ShopItem> list) {
        this.mShopList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopName;
        TextView shopAddress;
        TextView shopCategory;

        ViewHolder(Context context, View itemView) {
            super(itemView);

            shopName = itemView.findViewById(R.id.shopName);
            shopAddress = itemView.findViewById(R.id.shopAddress);
            shopCategory = itemView.findViewById(R.id.shopCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
        void onBind(ShopItem item) {
            shopName.setText(item.getShopName());
            shopAddress.setText(item.getshopAddress());
            shopCategory.setText(item.getshopCategory());
        }
    }
}
