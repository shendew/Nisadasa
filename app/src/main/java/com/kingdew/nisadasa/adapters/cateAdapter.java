package com.kingdew.nisadasa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kingdew.nisadasa.R;
import com.kingdew.nisadasa.models.Category;
import com.kingdew.nisadasa.models.Posts;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class cateAdapter extends RecyclerView.Adapter<cateAdapter.ViewHolder> {

    Context context;
    ArrayList<Category> cates;


    public cateAdapter(Context context, ArrayList<Category> cates) {
        this.context = context;
        this.cates = cates;
    }

    @NonNull
    @Override
    public cateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cate_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull cateAdapter.ViewHolder holder, int position) {
        holder.cateTitleT.setText(cates.get(position).getCateTitle());
    }

    @Override
    public int getItemCount() {
        return cates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cateTitleT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cateTitleT=itemView.findViewById(R.id.cateTitleT);
        }
    }
}
