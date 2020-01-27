package com.max.binarycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DecimaltoBinary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decimalto_binary);
        Button home, reset, convert;
        final EditText num1;
        final TextView result;
        home = (Button)findViewById(R.id.home);
        reset = (Button)findViewById(R.id.reset);
        convert = (Button)findViewById(R.id.convert);
        num1 = (EditText)findViewById(R.id.num1);
        result = (TextView)findViewById(R.id.result);
        ConstraintLayout dectobin = (ConstraintLayout)findViewById(R.id.dectobin);


        //Hide keyboard when touched on the screen
        dectobin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DecimaltoBinary.this, HomePage.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText("");
                result.setText("RESULT");
                //hideKeyboard(v);
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = num1.getText().toString();
                    Integer temp = Integer.parseInt(str);
                    if (num1.getText().toString().length()==0)
                    {
                        num1.setError("Can't be empty !!");
                    }
                    else
                    {
                        if (temp==0){
                            str="0";
                            num1.setText(str);
                        }
                        str = str.replaceFirst("^0+(?!$)", "");
                        num1.setText(str);
                        result.setText(str+" = "+Integer.toBinaryString(temp));

                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "INPUT VALID NUMBER !!", Toast.LENGTH_SHORT).show();
                    num1.setText("");
                    result.setText("RESULT");
                    return;
                }
            }
        });


    }

    void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
