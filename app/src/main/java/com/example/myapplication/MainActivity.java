package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView output;
    RadioGroup radio;
    public enum Type {
        phoneNumber,
        webPage,
        geopoint
    }

    public Type getType(String value){

        if(value.matches("(?:\\+7|8)\\d{10}")){
            return Type.phoneNumber;
        }
        if(value.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")){
            return Type.geopoint;
        }
        if(value.matches("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})")){
            Log.d("myTag", "works");
            return Type.webPage;
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        radio = findViewById(R.id.radio);

    }

    public void RadioUncheck(View view){
        radio.clearCheck();
    }
    public void onClick(View view){

        String value = input.getText().toString();
        Type type = getType(value);
        int id = radio.getCheckedRadioButtonId();

        output.setText("");

        if(id == -1){
            if(type == Type.phoneNumber) {
                output.setText("Making a phone call");
            } else if(type == Type.geopoint){

                Uri address = Uri.parse("https://www.google.com/maps/place/" + value);
                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

                try {
                    startActivity(openLinkIntent);
                } catch (ActivityNotFoundException e) {
                    output.setText("Unable to execute");
                }

            } else if(type == Type.webPage) {

                Uri address = Uri.parse(value);
                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

                try {
                    startActivity(openLinkIntent);
                } catch (ActivityNotFoundException e) {
                    output.setText("Unable to execute");
                }

            } else {
                output.setText("Wrong input");
            }
        } else if(id == R.id.radio_phoneNumber && type == Type.phoneNumber){
            output.setText("Making a phone call");
        } else if(id == R.id.radio_geopoint && type == Type.geopoint){

            Uri address = Uri.parse("https://www.google.com/maps/place/" + value);
            Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

            try {
                startActivity(openLinkIntent);
            } catch (ActivityNotFoundException e) {
                output.setText("null");
            }

        }  else if(id == R.id.radio_webpage && type == Type.webPage){

            Uri address = Uri.parse(value);
            Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);

            try {
                startActivity(openLinkIntent);
            } catch (ActivityNotFoundException e) {
                output.setText("Unable to execute");
            }

        } else {
            output.setText("Wrong input");
        }

    }

}