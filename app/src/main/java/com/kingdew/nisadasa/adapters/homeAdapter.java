package com.kingdew.nisadasa.adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kingdew.nisadasa.R;
import com.kingdew.nisadasa.models.Posts;

import java.util.ArrayList;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.ViewHolder> {

    Context context;
    ArrayList<Posts> posts;

    public homeAdapter(Context context, ArrayList<Posts> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public homeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull homeAdapter.ViewHolder holder, int position) {

        Posts item=posts.get(position);
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.desc.setText(item.getDesc());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.desc.setText(Html.fromHtml(item.getDesc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.desc.setText(Html.fromHtml(item.getDesc()));
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,date,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            date=itemView.findViewById(R.id.txt_date);
            desc=itemView.findViewById(R.id.txt_desc);
        }
    }
}
