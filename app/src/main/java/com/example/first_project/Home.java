package com.example.first_project;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Fragment {
    View v;
    TextInputLayout textInputLayout_url;
    TextInputEditText editText_url;
    Drawable status_green,status_red,status_black;
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
       status_black=getContext().getResources().getDrawable(R.drawable.black);

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url=textInputLayout_url.getEditText().getText().toString();
                mqtt= new MQTT(getContext(),url);
                int flag = mqtt.ClientConnect();
                switch (flag){
                    case 0 :editText_url.setCompoundDrawablesWithIntrinsicBounds(status_red,null,null,null);
                        Connect.setBackgroundColor(0xFFFA3333);
                        break;
                    case  1 : editText_url.setCompoundDrawablesWithIntrinsicBounds(status_green,null,null,null);
                           Connect.setBackgroundColor(0xFF03DAC6);
                                if(Disconnect.getBackground().equals(status_green)){
                                    Disconnect.setBackgroundColor(0xFFFCFAFA);
                                }
                            break;
                    case 2 :
                        editText_url.setCompoundDrawablesWithIntrinsicBounds(status_black,null,null,null);
                        Connect.setBackgroundColor(0xFFFA3333);
                        break;



                }
            }
        });

        Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int flagsubscribed= mqtt.Subscribe("foo/bar");
                switch (flagsubscribed){
                    case 0 :
                        Subscribe.setBackgroundColor(0xFFFCFAFA);
                        break;
                    case  1 :
                        Subscribe.setBackgroundColor(0xFF03DAC6);
                        break;
                    case 2 :
                        Subscribe.setBackgroundColor(0xFF0C0C0C);

                        break;



                }
            }
        });

        Disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flagdisconnected= mqtt.Subscribe("foo/bar");
                switch (flagdisconnected){
                    case 0 :
                        Disconnect.setBackgroundColor(0xFFFCFAFA);

                        break;
                    case  1 :
                        Disconnect.setBackgroundColor(0xFF03DAC6);
                        Subscribe.setBackgroundColor(0xFFFCFAFA);
                        Connect.setBackgroundColor(0xFFFCFAFA);
                        editText_url.setCompoundDrawablesWithIntrinsicBounds(status_red,null,null,null);

                        break;
                    case 2 :
                        Disconnect.setBackgroundColor(0xFF0C0C0C);

                        break;



                }
            }
        });


        return v;
    }
}
