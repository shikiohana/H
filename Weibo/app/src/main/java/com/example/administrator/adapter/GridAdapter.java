package com.example.administrator.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.quickweibo.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/7/20.
 * 显示图片
 */
public class GridAdapter extends RecyclerView.Adapter {
    private String[] strs;
    private ImageLoader imageLoader;
    public GridAdapter(String[] strs){
        this.strs=strs;
    }
    @Override
    public int getItemCount() {
        if(strs.length>0){
            return strs.length;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImgHolder imgHolder=(ImgHolder)holder;
        if(imageLoader==null) {
           imageLoader =ImageLoader.getInstance();
        }
        imageLoader.displayImage(strs[position],imgHolder.img,loadingListener);//展示图片
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image,null);
            return  new ImgHolder(view);
    }

    class ImgHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public ImgHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.single_img);//初始化ImageView
        }
    }

    SimpleImageLoadingListener loadingListener=new SimpleImageLoadingListener(){
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            ImageView imageView=(ImageView)view;
            imageView.setImageBitmap(loadedImage);
            super.onLoadingComplete(imageUri, view, loadedImage);
        }
    };
}
