package com.example.first_project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.example.first_project.MQTT.client;

public class Home extends Fragment {
    View v;
    private long mLastClickTime = 0;
    public String message_transfer = "1";
    TextInputLayout textInputLayout_url;
    TextInputEditText editText_url;
    Drawable status_green, status_red, status_black, roundbuttonwhite, roundbuttonred, roundbuttongreen;
    String url, Recievedmessage, temppreviousfishweight = "", temppreviousfoodweight = "";
    int temppreviousremainingweight = 12;
    MQTT mqtt;
    TextView connected, disconnected, subscribed, fishweight, foodweight, remainingweight;
    Button Disconnect, Connect, Subscribe;
    String topic = "kishor";
    SendMessage SM;
    ProgressBar progress;
    int flagdisconnected = 0;


    public Home() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home, container, false);
        textInputLayout_url = (TextInputLayout) v.findViewById(R.id.textlayout_url);
        editText_url = (TextInputEditText) v.findViewById(R.id.edittext_url);
        roundbuttonwhite = getContext().getResources().getDrawable(R.drawable.roundbuttonwhite);
        roundbuttongreen = getContext().getResources().getDrawable(R.drawable.roundbuttongreen);
        roundbuttonred = getContext().getResources().getDrawable(R.drawable.roundbuttonred);
//        connected= v.findViewById(R.id.textview_connect);
//        disconnected= v.findViewById(R.id.textview_disconnect);
//        subscribed = v.findViewById(R.id.textview_subscribe);
        Disconnect = (Button) v.findViewById(R.id.button_disconnect);
        Connect = (Button) v.findViewById(R.id.button_connect);
        Subscribe = (Button) v.findViewById(R.id.button_subscribe);
        fishweight = v.findViewById(R.id.text_fishweight);
        foodweight = v.findViewById(R.id.text_foodweight);
        remainingweight = v.findViewById(R.id.text_remainingweight);

        status_green = getContext().getResources().getDrawable(R.drawable.green);
        status_red = getContext().getResources().getDrawable(R.drawable.red);
        status_black = getContext().getResources().getDrawable(R.drawable.black);
        progress = v.findViewById(R.id.disconnect_progress);

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = textInputLayout_url.getEditText().getText().toString();
                mqtt = new MQTT(getContext(), url, topic);
                int flag = mqtt.ClientConnect();
                client.setCallback(new MqttCallbackExtended() {
                    @Override
                    public void connectComplete(boolean reconnect, String serverURI) {

                    }

                    @Override
                    public void connectionLost(Throwable cause) {

                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        Recievedmessage = new String(message.getPayload());
                        Log.d("message recived", Recievedmessage);
                        String spiltedarray[] = Recievedmessage.split(" ");
                        String tempfish = spiltedarray[0];
                        String tempfood = spiltedarray[1];
                        int tempremaining = (Integer.parseInt(tempfish) + Integer.parseInt(tempfood));
                        Log.d("recievedmessage", "messageArrived: " + tempremaining + "--" + temppreviousremainingweight);
                        if (!tempfish.equals(temppreviousfishweight)) {
                            fishweight.setText(tempfish);
                            temppreviousfishweight = tempfish;

                        }

                        if (!tempfood.equals(temppreviousfoodweight)) {
                            foodweight.setText(tempfood);
                            temppreviousfoodweight = tempfood;


                        }
                        if (!(tempremaining == temppreviousremainingweight)) {
                            remainingweight.setText(Integer.toString(tempremaining));
                            temppreviousremainingweight = tempremaining;

                        }

                        if ((tempfish.equals("0")) && (tempfood.equals("0"))) {
                            message_transfer = "1";
                            SM.sendData(message_transfer);

                        } else if (!tempfish.equals("0")) {
                            message_transfer = "3";
                            SM.sendData(message_transfer);
                        } else {
                            message_transfer = "2";
                            SM.sendData(message_transfer);

                        }
                        Log.d("recievedmessage", "messageArrived: " + tempfish + "--" + tempfood + "  " + message_transfer);
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });

                switch (flag) {
                    /*0xFFFA3333 //red
                        0xFF03DAC6 // Blue/green
                        0xFFFA3333 // red
                        0xFFFCFAFA // White*/
                    case 0:
                        editText_url.setCompoundDrawablesWithIntrinsicBounds(status_red, null, null, null);

                        Connect.setBackgroundDrawable(roundbuttonred);


                        break;
                    case 1:
                        editText_url.setCompoundDrawablesWithIntrinsicBounds(status_green, null, null, null);

                        Connect.setBackgroundDrawable(roundbuttongreen);

                        Disconnect.setBackgroundDrawable(roundbuttonwhite);
                        break;
                    case 2:
                        editText_url.setCompoundDrawablesWithIntrinsicBounds(status_black, null, null, null);

                        Connect.setBackgroundDrawable(roundbuttonred);
                        break;


                }
            }
        });

        Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flagsubscribed = mqtt.Subscribe();
                switch (flagsubscribed) {
                    case 0:
                        /*0xFFFA3333 //red
                        0xFF03DAC6 // Blue/green
                        0xFFFA3333 // red
                        0xFFFCFAFA // White*/

//                        Subscribe.setBackgroundDrawable(roundbuttonwhite);
                        break;
                    case 1:

                        Subscribe.setBackgroundDrawable(roundbuttongreen);
                        break;
                    case 2:

                        Subscribe.setBackgroundDrawable(roundbuttonred);

                        break;


                }
            }
        });

        Disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagdisconnected = mqtt.Disconnected();
                switch (flagdisconnected) {
                    case 0:
                        /*0xFFFA3333 //red
                        0xFF03DAC6 // Blue/green
                        0xFFFA3333 // red
                        0xFFFCFAFA // White*/
//                        Disconnect.setBackgroundColor(0xFFFCFAFA);
//                        Disconnect.setBackgroundDrawable(roundbutton);
                        break;
                    case 1:

                        Disconnect.setBackgroundDrawable(roundbuttongreen);

                        Subscribe.setBackgroundDrawable(roundbuttonwhite);

                        Connect.setBackgroundDrawable(roundbuttonwhite);
                        break;
                    case 2:
                        Disconnect.setBackgroundColor(0xFF0C0C0C);
                        Disconnect.setBackgroundDrawable(roundbuttonred);

                        break;


                }
            }
        });


        // mis-clicking prevention, using threshold of 1000 ms
//                    if (SystemClock.elapsedRealtime()-mLastClickTime < 500){
//                        flagdisconnected= mqtt.Disconnected();
//                        progress.setVisibility(View.VISIBLE);
//
//                            }
//                    if(SystemClock.elapsedRealtime()-mLastClickTime < 30000)
//                        {
//                            return ;
//
//                        }
//                        if(SystemClock.elapsedRealtime()==40000)
//                        {
//                            progress.setVisibility(View.GONE);
//                        }
//                    mLastClickTime=SystemClock.elapsedRealtime();
//                 Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        flagdisconnected= mqtt.Disconnected();
//                        progress.setVisibility(View.VISIBLE);
//                    }
//                },10);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//                        progress.setVisibility(View.GONE);
//
//                        switch (flagdisconnected){
//                            case 0 :
//                                Disconnect.setBackgroundColor(0xFFFCFAFA);
//
//                                break;
//                            case  1 :/*0xFFFA3333 //red
//                        0xFF03DAC6 // Blue/green
//                        0xFFFA3333 // red
//                        0xFFFCFAFA // White*/
//
//                                Disconnect.setBackgroundColor(0xFF03DAC6);
//                                Subscribe.setBackgroundColor(0xFFFCFAFA);
//                                Connect.setBackgroundColor(0xFFFCFAFA);
//                                editText_url.setCompoundDrawablesWithIntrinsicBounds(status_red,null,null,null);
//
//                                break;
//                            case 2 :
//                                Disconnect.setBackgroundColor(0xFF0C0C0C);
//
//                                break;
//
//
//
//                        }
//
//                    }
//                }, 30000);
//
//
//
//
//


//          }


//        });


        return v;
    }


//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        Button btnPassData = (Button) view.findViewById(R.id.btnPassData);
//        final EditText inData = (EditText) view.findViewById(R.id.inMessage);
//        btnPassData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SM.sendData(inData.getText().toString().trim());
//            }
//        });
//
//    }

    interface SendMessage {
        void sendData(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}






