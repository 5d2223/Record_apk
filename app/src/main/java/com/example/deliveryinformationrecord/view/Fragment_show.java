package com.example.deliveryinformationrecord.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Fragment;
import com.example.deliveryinformationrecord.R;
import com.example.deliveryinformationrecord.adapter.MyAdapter;
import com.example.deliveryinformationrecord.bean.message;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

public class Fragment_show extends Fragment {

    public List<message> data = new ArrayList<>();

    public RecyclerView recyclerView;
    public MyAdapter adapter;


//    recycleview边界大小
    public int mSpace=15;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycleview,container,false);

//        从数据库中更新数据
        data = LitePal.findAll(message.class);

        recyclerView=view.findViewById(R.id.RV);
        adapter=new MyAdapter(data,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
//        设置recycleview的边界大小
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = mSpace;
                outRect.right = mSpace;
                outRect.bottom = mSpace;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = mSpace;
                }

            }
        });

//        设置recycleview的item的长按监听
        adapter.setOnLongClickListener(new MyAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                showDialog(view.getContext(),position);
            }
        });

        return view;
    }

//    长按弹出dialog删除对应item和数据库中对应数据
    public void showDialog(Context context, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示")
                .setMessage("是否删除对应投递信息？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        data.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(0,data.size());
                    }
                })
                .setNegativeButton("否", null)
                .show();

        LitePal.deleteAll(message.class, "name = ?", data.get(position).getName());
    }
}
