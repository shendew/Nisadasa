package com.kingdew.nisadasa.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.kingdew.nisadasa.Helpers.ShareAny;
import com.kingdew.nisadasa.R;
import com.kingdew.nisadasa.models.Posts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class homeNAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    Context context;
    Activity context;
    ArrayList<Posts> posts;
    private static final int ITEM_VIEW = 0;
    private static final int AD_VIEW = 1;
    private static final int ITEM_FEED_COUNT = 6;

    AsyncTask myTask;

    public homeNAdapter(Activity context, ArrayList<Posts> posts) {
        this.context = context;
        this.posts = posts;
    }

    public void insertItems(ArrayList<Posts> posts){
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
        LayoutInflater layoutInflater=LayoutInflater.from(context);

        if (viewType==ITEM_VIEW){
            View view=layoutInflater.inflate(R.layout.item,parent,false);
            return new MainViewHolder(view);
        } else if (viewType==AD_VIEW) {
            View view=layoutInflater.inflate(R.layout.layout_ad,parent,false);
            return new AdViewHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==ITEM_VIEW) {
            int pos = position- Math.round(position/ITEM_FEED_COUNT);
            ((MainViewHolder)holder).bindData(posts.get(pos));
        }else if (holder.getItemViewType()==AD_VIEW){
            ((AdViewHolder)holder).bindAdData();
        }
    }


//    @Override
//    public void onBindViewHolder(@NonNull homeNAdapter.ViewHolder holder, int position) {
//
//        Posts item=posts.get(position);
//        holder.name.setText(item.getName());
//        holder.date.setText(item.getDate());
//        holder.desc.setText(Html.fromHtml( item.getDesc()));
//
//        Animation fade_out = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_out);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.desc.setText(Html.fromHtml(item.getDesc(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            holder.desc.setText(Html.fromHtml(item.getDesc()));
//        }
//        if (!item.getImage().equals("no"))
//        {
//            Glide.with(context).load(item.getImage()).placeholder(R.drawable.progress_animation).into(holder.img);
//        }else{
//            holder.img.setVisibility(View.GONE);
//        }
//
//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    ShareAny shareAny=new ShareAny(context,item.getDesc());
//                    myTask=shareAny.execute(new URL(item.getImage()));
//                } catch (MalformedURLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
//        return posts.size();
        if (posts.size()>0){
            return posts.size()+Math.round(posts.size()/ITEM_FEED_COUNT);
        }
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % ITEM_FEED_COUNT == 0) {
            return AD_VIEW;
        }
        return ITEM_VIEW;
    }


    public void search(String toString) {

        for (Posts post:posts){
            //if (post.get)
        }
    }

    private void populateNativeADView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView(adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView name,date,desc;
//        ImageView img,share;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name=itemView.findViewById(R.id.txt_name);
//            date=itemView.findViewById(R.id.txt_date);
//            desc=itemView.findViewById(R.id.txt_desc);
//            img=itemView.findViewById(R.id.img);
//            share=itemView.findViewById(R.id.share);
//        }
//    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        TextView name,date,desc;
        ImageView img,share;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            date=itemView.findViewById(R.id.txt_date);
            desc=itemView.findViewById(R.id.txt_desc);
            img=itemView.findViewById(R.id.img);
            share=itemView.findViewById(R.id.share);
        }
        private void bindData(Posts item){

            name.setText(item.getName());
            date.setText(item.getDate());
            desc.setText(Html.fromHtml( item.getDesc()));

            Animation fade_out = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.fade_out);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               desc.setText(Html.fromHtml(item.getDesc(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                desc.setText(Html.fromHtml(item.getDesc()));
            }
            if (!item.getImage().equals("no"))
            {
                Glide.with(context).load(item.getImage()).placeholder(R.drawable.progress_animation).into(img);
            }else{
                img.setVisibility(View.GONE);
            }

            share.setOnClickListener(new View.OnClickListener() {
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
    }


    public class AdViewHolder extends RecyclerView.ViewHolder {

        ProgressDialog progress = new ProgressDialog(context);
        FrameLayout adLayout;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            adLayout=itemView.findViewById(R.id.adLayout);
        }

        private void bindAdData() {
            progress.setTitle("Loading");
            progress.setMessage("Wait while loading...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

            AdLoader.Builder builder = new AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                            NativeAdView nativeAdView = (NativeAdView) context.getLayoutInflater().inflate(R.layout.layout_native_ad, null);
                            populateNativeADView(nativeAd, nativeAdView);
                            adLayout.removeAllViews();
                            adLayout.addView(nativeAdView);
                        }
                    });

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Toast.makeText(context, loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    progress.dismiss();
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());


        }
    }

}

