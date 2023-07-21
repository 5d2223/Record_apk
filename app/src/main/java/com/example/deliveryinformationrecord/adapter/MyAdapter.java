package com.example.deliveryinformationrecord.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryinformationrecord.R;
import com.example.deliveryinformationrecord.bean.message;
import com.example.deliveryinformationrecord.view.Fragment_show;

import org.litepal.tablemanager.Connector;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

//    上下文
    private Context context;

//    数据
    public List<message> list;

//    构造方法将所在的上下文和数据传入
    public MyAdapter(List<message> list,Context text) {
        this.list = list;
        this.context = text;
    }

//    数据更新方法
    public void setData(List<message> data){
        list = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

//    保存cookie到sharepreference
    private void saveCookieToSharedPreferences(String url,String cookie) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookies", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(url, cookie);
        editor.apply();
    }

    // 从 SharedPreferences 加载 Cookie
    private String loadCookieFromSharedPreferences(String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookies", Context.MODE_PRIVATE);
        return sharedPreferences.getString(url,null);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(list.get(position).getName());
        holder.location.setText(list.get(position).getLocation());


        String storedCookie = loadCookieFromSharedPreferences(list.get(position).getWeb());
        if (storedCookie != null) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setCookie(list.get(position).getWeb(), storedCookie);
        }

        String url = list.get(position).getWeb();
        holder.web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                CookieManager cookieManager = CookieManager.getInstance();
                String cookie = cookieManager.getCookie(url);
                // 将 cookie 存储起来，例如通过 SharedPreferences 进行持久化存储
                saveCookieToSharedPreferences(list.get(position).getWeb(),cookie);
            }
        });

        // webview必备设置项
        holder.web.getSettings().setAllowFileAccess(true);
        holder.web.getSettings().setJavaScriptEnabled(true);
        holder.web.getSettings().setAllowFileAccess(true);
        holder.web.getSettings().setDomStorageEnabled(true);
        holder.web.getSettings().setDatabaseEnabled(true);
        holder.web.loadUrl(url);

        holder.itemView.setTag(position);
//        item的长按监听
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null != mOnLongClickListener) {
                    mOnLongClickListener.onLongClick(view, holder.getAdapterPosition());
                }
                return true;
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    设置的长按监听接口，长按后实现其中的方法
    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }
    private OnLongClickListener mOnLongClickListener = null;

//    长按监听接口的设置方法
    public void setOnLongClickListener(OnLongClickListener listener) {
        mOnLongClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,location;
        WebView web;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv1);
            location=itemView.findViewById(R.id.tv2);
            web=itemView.findViewById(R.id.web);
        }
    }
}
