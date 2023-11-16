package com.example.hitungluas;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText length = (EditText) findViewById(R.id.edtext1);
        EditText width = (EditText) findViewById(R.id.edtext2);
        TextView result = (TextView) findViewById(R.id.edtext3);
        Button calculate = (Button) findViewById(R.id.button1);

        calculate.setOnClickListener(this);
    }

    public void onClick(View v){

        EditText length = (EditText) findViewById(R.id.edtext1);
        EditText width = (EditText) findViewById(R.id.edtext2);
        calculateArea(length.getText().toString(), width.getText().toString());
    }

    private void calculateArea(String clength, String cwidth){

        TextView result = (TextView) findViewById(R.id.edtext3);

        int area = Integer.parseInt(clength)*Integer.parseInt(cwidth);

        result.setText(Double.toString(area));
    }}