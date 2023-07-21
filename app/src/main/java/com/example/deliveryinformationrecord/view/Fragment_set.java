package com.example.deliveryinformationrecord.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.example.deliveryinformationrecord.R;
import com.example.deliveryinformationrecord.bean.message;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

public class Fragment_set extends Fragment {

    public EditText name,location,web;

    public Button confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set,container,false);

        name=view.findViewById(R.id.editTextText);
        location=view.findViewById(R.id.editText2);
        web=view.findViewById(R.id.editText3);

        confirm=view.findViewById(R.id.button2);

//        确认按钮将数据传入数据库中并跳转回列表页面
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = name.getText().toString();
                String data2 = location.getText().toString();
                String data3 = web.getText().toString();

                Connector.getDatabase();
                message message = new message();
                message.setName(data1);
                message.setLocation(data2);
                message.setWeb(data3);
                message.save();

                Fragment_show show = new Fragment_show();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout,show).commit();
            }
        });

        return view;
    }
}
