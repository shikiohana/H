package com.example.administrator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.model.WeiboInfo;
import com.example.administrator.quickweibo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/20.
 */
public class WeiboAdapter extends RecyclerView.Adapter {
    private ArrayList<WeiboInfo> weiboInfos;
    private WeiboInfo weiboInfo;
    private OnItemClickListener onItemClickListener;
    public WeiboAdapter(ArrayList<WeiboInfo> weiboInfos) {
        this.weiboInfos = weiboInfos;
    }


    public interface OnItemClickListener{
        void OnClickListener(View view, int position);
    }

    @Override
    public int getItemCount() {
        if (weiboInfos.size() > 0) {
            return weiboInfos.size();
        }
        return 0;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            WeiboHolder weiboHolder=(WeiboHolder)holder;
            weiboInfo=weiboInfos.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.OnClickListener(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.normal_item, null);//原创
        //view= LayoutInflater.from(parent.getContext()).inflate(R.layout.repost_item,null);//转发
        return new WeiboHolder(view);
    }

    class WeiboHolder extends RecyclerView.ViewHolder {
        TextView userName, content, repostUserName;
        ImageView icon, repostIcon;
        RecyclerView imgs;

        public WeiboHolder(View itemView) {

            super(itemView);

            /*repostUserName=(TextView)itemView.findViewById(R.id.repost_user_name);
            repostIcon=(ImageView)itemView.findViewById(R.id.repost_user_img);*/
            userName = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content_text);
            icon = (ImageView) itemView.findViewById(R.id.user_img);
            imgs = (RecyclerView) itemView.findViewById(R.id.content_img);
        }
    }
}
