package com.kingdew.nisadasa.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kingdew.nisadasa.Helpers.ShareAny;
import com.kingdew.nisadasa.R;
import com.kingdew.nisadasa.models.Posts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.ViewHolder> {

    Context context;
    ArrayList<Posts> posts;

    AsyncTask myTask;

    public homeAdapter(Context context, ArrayList<Posts> posts) {
        this.context = context;
        this.posts = posts;
    }

    public void insertItems(ArrayList<Posts> posts){
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
        holder.desc.setText(Html.fromHtml( item.getDesc()));

        Animation fade_out = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_out);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.desc.setText(Html.fromHtml(item.getDesc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.desc.setText(Html.fromHtml(item.getDesc()));
        }
        if (!item.getImage().equals("no"))
        {
            Glide.with(context).load(item.getImage()).placeholder(R.drawable.progress_animation).into(holder.img);
        }else{
            holder.img.setVisibility(View.GONE);
        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ShareAny shareAny=new ShareAny(context,item.getDesc());
                    myTask=shareAny.execute(new URL(item.getImage()));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void search(String toString) {

        for (Posts post:posts){
            //if (post.get)
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,date,desc;
        ImageView img,share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            date=itemView.findViewById(R.id.txt_date);
            desc=itemView.findViewById(R.id.txt_desc);
            img=itemView.findViewById(R.id.img);
            share=itemView.findViewById(R.id.share);
        }
    }
}
