package com.max.binarycalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    public final Integer[] temp = new Integer[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //different objects of the main Layout
        ConstraintLayout layout_one = (ConstraintLayout) findViewById(R.id.Layout_one);
        final Spinner operator = (Spinner) findViewById(R.id.operator);
        final EditText num1 = (EditText) findViewById(R.id.num1);
        final EditText num2 = (EditText) findViewById(R.id.num2);
        Button equals = (Button) findViewById(R.id.equals);
        final TextView result = (TextView) findViewById(R.id.result);
        Button home = (Button) findViewById(R.id.home);
        Button reset = (Button) findViewById(R.id.reset);


        //Hide keyboard when touched on the screen
        layout_one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText("");
                num2.setText("");
                result.setText("RESULT");
               // hideKeyboard(v);
            }
        });


        //making textview scrollable
        result.setMovementMethod(new ScrollingMovementMethod());

        //drop down list of operators in the spinner
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.operators));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operator.setAdapter(myadapter);     //without this line, drop down won't be shown in the spinner widget

        //on spinner item selected
        operator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        temp[0] = 0;
                        break;
                    case 1 :
                        temp[0] = 1;
                        break;
                    case 2 :
                        temp[0] = 2;
                        break;
                    case 3 :
                        temp[0] = 3;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "No operation can be performed !!", Toast.LENGTH_SHORT).show();

            }
        });

        //on pressing equal to Button
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                //hide keyboard when equals to is pressed
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                //check if num input field is correct or not (must be in binary format, must be non empty and must be upto 8 bits)
                long temp1=-99;
                long temp2=-99;
                int flag=1,bin1 = 2,bin2=2,dec1=0,dec2 = 0;
                String str1 = null,str2 = null;
                if (num1.getText().toString().length()==0 || num1.getText().toString()==null || num1.getText().toString().equals("") || num1.getText().toString().isEmpty())
                {
                    flag=0;
                    num1.setError("Can't be empty !!");
                }
                else if (num2.getText().toString().length()==0 || num2.getText().toString()==null || num2.getText().toString().equals("") || num2.getText().toString().isEmpty())
                {
                    flag=0;
                    num2.setError("Can't be empty !!");
                }
                else if (num1.getText().toString().length()>8)
                {
                    flag=1;
                    //num1.setError("Maximum 8 bit is allowed !!");
                    str1=num1.getText().toString().substring(0,8);
                    str1 = str1.replaceFirst("^0+(?!$)", "");
                    num1.setText(str1);
                    temp1 = Long.parseLong(str1);
                    dec1=Integer.parseInt(str1,2);
                    if (temp1==0){
                        num1.setText(""+0);
                        temp1=0;
                    }
                    Toast.makeText(MainActivity.this, "Maximum 8 bit is allowed !!", Toast.LENGTH_SHORT).show();
                    bin1=isbinary(temp1);
                    if (bin1==0)
                    {
                        num1.setError("NOT BINARY !!");
                        flag=0;
                    }
                    //num1.setText("");
                }
                else if (num2.getText().toString().length()>8)
                {
                    flag=1;
                    //num2.setError("Maximum 8 bit is allowed !!");
                    str2=num2.getText().toString().substring(0,8);
                    str2 = str2.replaceFirst("^0+(?!$)", "");
                    num2.setText(str2);
                    temp2 = Long.parseLong(str2);
                    dec2=Integer.parseInt(str2,2);
                    if (temp2==0) {
                        num2.setText("" + 0);
                        temp2=0;
                    }
                    Toast.makeText(MainActivity.this, "Maximum 8 bit is allowed !!", Toast.LENGTH_SHORT).show();
                    bin2=isbinary(temp2);
                    if (bin2==0)
                    {
                        num2.setError("NOT BINARY !!");
                        flag=0;
                    }
                    //num1.setText("");
                }

                /*int x = isbinary(temp1);
                int y = isbinary(temp2);
                if (x==0)
                {
                    num1.setError("Enter Number 1 as in binary format !!");
                    Toast.makeText(MainActivity.this, "Enter Number 1 as in binary format !!", Toast.LENGTH_SHORT).show();
                    num1.setText("");
                }
                if (y==0)
                {
                    num2.setError("Enter Number 2 as in binary format !!");
                    Toast.makeText(MainActivity.this, "Enter Number 2 as in binary format !!", Toast.LENGTH_SHORT).show();
                    num2.setText("");
                }
                if (x==1 && y==1)
                {
                    long xx=temp1/10;
                    result.setText(""+xx);
                    //perform operations
                }*/
                if (flag==1)
                {
                    if (num1.getText().toString().length()<=8 && num2.getText().toString().length()<=8)
                    {
                        str1=num1.getText().toString();
                        str1 = str1.replaceFirst("^0+(?!$)", "");
                        num1.setText(str1);
                        temp1 = Long.parseLong(str1);

                        str2=num2.getText().toString();
                        str2 = str2.replaceFirst("^0+(?!$)", "");
                        num2.setText(str2);
                        temp2 = Long.parseLong(str2);

                        if (temp1==0) {
                            num1.setText("" + 0);
                            temp1=0;
                        }
                        if (temp2==0) {
                            num2.setText("" + 0);
                            temp2 = 0;
                        }
                        bin1=isbinary(temp1);
                        bin2=isbinary(temp2);
                    }
                    if (bin1==0){
                        num1.setError("Number must be in binary format");
                        Toast.makeText(getApplicationContext(), "NOT Binary !!", Toast.LENGTH_SHORT).show();
                    }
                    else if (bin2==0)
                    {
                        num2.setError("Number must be in binary format");
                        Toast.makeText(getApplicationContext(), "NOT Binary !!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (flag==1)
                        {
                            dec1 = Integer.parseInt(str1, 2);
                            dec2 = Integer.parseInt(str2, 2);
                        }
                        String res;
                        switch (temp[0])
                        {
                            case 0 :
                                //res=addBinary(temp1, temp1);
                                res = Integer.toBinaryString((dec1+dec2));
                                result.setText("BINARY\n"+Integer.toBinaryString(dec1)+" + "+Integer.toBinaryString(dec2)+" = "+res
                                               +"\n\nDECIMAL\n"+dec1+" + "+dec2+" = "+(dec1+dec2));
                                break;
                            case 1 :
                                //res=subtract(temp1, temp1);
                                res = Integer.toBinaryString((dec1-dec2));
                                result.setText("BINARY\n"+Integer.toBinaryString(dec1)+" - "+Integer.toBinaryString(dec2)+" = "+res
                                        +"\n\nDECIMAL\n"+dec1+" - "+dec2+" = "+(dec1-dec2));
                                break;
                            case 2 :
                                //res=multiply(temp1, temp1);
                                res = Integer.toBinaryString((dec1*dec2));
                                result.setText("BINARY\n"+Integer.toBinaryString(dec1)+" * "+Integer.toBinaryString(dec2)+" = "+res
                                        +"\n\nDECIMAL\n"+dec1+" * "+dec2+" = "+(dec1*dec2));
                                break;
                            case 3 :
                                if (temp2 == 0)
                                    result.setText("Can't Divide With Zero !!!");
                                else
                                {
                                    //res = Long.toBinaryString((temp1/temp2));
                                    //String rem = Long.toBinaryString((temp1%temp2));
                                    res = Integer.toBinaryString((dec1/dec2));
                                    String rem = Integer.toBinaryString((dec1%dec2));
                                    result.setText("BINARY\n"+Integer.toBinaryString(dec1)+" ÷ "+Integer.toBinaryString(dec2)+" = Q : "+res+", R : "+rem
                                            +"\n\nDECIMAL\n"+dec1+" ÷ "+dec2+" = Q : "+(dec1/dec2)+", R : "+(dec1%dec2));
                                }
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "No Operation Can Be Performed !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "INPUT NUMBERS !!", Toast.LENGTH_SHORT).show();
                    num1.setText("");
                    num2.setText("");
                    result.setText("RESULT");
                    return;
                }
            }
        });




    }



    //keyboard hide function
    void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //function to check if the number is binary or not
    int isbinary(long num)
    {
        String str = Long.toString(num);
        char c;
        for(int i=0; i<str.length(); ++i)
        {
            c=str.charAt(i);
            if (c!='0')
                if (c!='1')
                    return 0;
        }

        return 1;
    }

    //addition function
    public static String addBinary(Long a, Long b)
    {
        String result;
        int s,x=binToDec(a),y=binToDec(b);
        s=x+y;
        result= Integer.toBinaryString(s);
        return result;
    }

    //subtract
    public static String subtract(Long a, Long b)
    {
        String result;
        int s,x=binToDec(a),y=binToDec(b);
        s=x-y;
        result=Integer.toBinaryString(s);
        return result;
    }

    //multiplication
    public static String multiply(Long a, Long b)
    {
        String result;
        int s,x=binToDec(a),y=binToDec(b);
        s=x*y;
        result=Integer.toBinaryString(s);
        return result;
    }

    //function to convert binary to decimal
    public static int binToDec(long n)
    {
        long num = n;
        int dec_value = 0;

        // Initializing base value to 1, i.e 2^0
        int base = 1;

        long temp = num;
        while (temp>0) {
            int last_digit = (int) (temp % 10);
            temp = temp / 10;

            dec_value += last_digit * base;

            base = base * 2;
        }

        return dec_value;
    }

    //function to convert decimal to binary
    public static int[] decToBin(int n)
    {
        // array to store binary number
        //int[] binaryNumtemp = new int[1000];
        // counter for binary array
        int i = 0,t=n;
        while (n > 0)
        {
            // storing remainder in binary array
           // binaryNumtemp[i] = n % 2;
            t = t / 2;
            i++;
        }
        int len=i;
        int[] binaryNum = new int[len];
        i=0;
        while (n > 0)
        {
            // storing remainder in binary array
            binaryNum[i] = n % 2;
            n = n / 2;
            i++;
        }
        for (i = 0; i < len / 2; i++) {
            t = binaryNum[i];
            binaryNum[i] = binaryNum[len - i - 1];
            binaryNum[len - i - 1] = t;
        }
        //binaryNum = Collections.reverse(Arrays.asList(binaryNum));
        //String str= Arrays.toString(binaryNum).substring(1,len);
        // printing binary array in reverse order
        //for (int j = 0; j < len; ++j)
          //  System.out.print(binaryNum[j]);
        return binaryNum;
    }


}
