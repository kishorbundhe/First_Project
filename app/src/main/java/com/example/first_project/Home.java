package com.example.first_project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Fragment {
    View v;
    TextInputLayout textInputLayout_url;
    TextInputEditText editText_url;
    Drawable status_green,status_red;
    String url;
    MQTT mqtt;
    TextView connected, disconnected, subscribed;
    Button Disconnect, Connect , Subscribe;
    public Home() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      v=inflater.inflate(R.layout.home,container,false);
        textInputLayout_url = (TextInputLayout)v.findViewById(R.id.textlayout_url);
        editText_url=(TextInputEditText)v.findViewById(R.id.edittext_url);

//        connected= v.findViewById(R.id.textview_connect);
//        disconnected= v.findViewById(R.id.textview_disconnect);
//        subscribed = v.findViewById(R.id.textview_subscribe);
        Disconnect=(Button)v.findViewById(R.id.button_disconnect);
        Connect=(Button)v.findViewById(R.id.button_connect);
        Subscribe=(Button)v.findViewById(R.id.button_subscribe);

       status_green = getContext().getResources().getDrawable( R.drawable.green);
       status_red=getContext().getResources().getDrawable( R.drawable.red);
        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url=textInputLayout_url.getEditText().getText().toString();
                mqtt= new MQTT(getContext(),url);
                int flag = mqtt.ClientConnect();
                switch (flag){
                    case 0 :
                            break;
                    case  1 : editText_url.setCompoundDrawablesWithIntrinsicBounds(status_green,null,null,null);
                           Connect.setBackgroundColor();
                            break;
                    case 2 :editText_url.setCompoundDrawablesWithIntrinsicBounds(status_red,null,null,null);
                        break;



                }
            }
        });

        Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int flagsubscribed= mqtt.Subscribe("foo/bar");
                Log.d("flag connected or not ", "Connected "+flagsubscribed);
                subscribed.setText(""+flagsubscribed);
            }
        });

        Disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flagdisconnected= mqtt.Subscribe("foo/bar");
                Log.d("flag connected or not ", "Connected "+flagdisconnected);
                disconnected.setText(""+flagdisconnected);
            }
        });


        return v;
    }
}
