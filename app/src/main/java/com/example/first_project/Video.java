package com.example.first_project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Video extends Fragment  {
    TextInputLayout textInputLayout_url1;
    TextInputEditText editText_url1;
    String url;
    Button button_play;
    View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         rootView = inflater.inflate(R.layout.video, container, false);
        editText_url1=rootView.findViewById(R.id.text_input_url);

            //
      //  url=textInputLayout_url.getEditText().getText().toString();

            button_play= rootView.findViewById(R.id.button_play);
            button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url=editText_url1.getText().toString();
                Log.d("heloo url ", "onCreateView: "+url);
                Intent intent = new Intent(getContext(),VideoPlaying.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        return rootView;
    }
}