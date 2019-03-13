package com.example.first_project;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Graphic extends Fragment {
    View v;
    static String message_recieved="1";
    AnimationDrawable anim;
    ImageView imageView;
    public Graphic() {
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//         message_transfer = getArguments().getString("key");
        v=inflater.inflate(R.layout.graphic,container,false);











        return v;
    }
    protected void displayReceivedData(String message) {
        message_recieved = message;
        Log.d("**********8", message);
        onResume();

    }

    @Override
    public void onResume() {
        super.onResume();
        imageView = (ImageView) v.findViewById(R.id.fish_status);
        if (imageView == null) throw new AssertionError();
        if (message_recieved.equals("2")) {
            imageView.setBackgroundResource(R.drawable.animation_bait);
            anim = (AnimationDrawable) imageView.getBackground();
            anim.start();

        }
        if (message_recieved.equals("3")) {
            imageView.setBackgroundResource(R.drawable.animation_caught);
            anim = (AnimationDrawable) imageView.getBackground();
            anim.start();
        }if (message_recieved.equals("1")) {
            imageView.setBackgroundResource(R.drawable.ic_one);
        }

    }

}
