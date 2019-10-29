package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNumberOne;
    EditText etNumberTwo;
    Button bAdd;
    Button bSub;
    Button bMultiply;
    Button bDivide;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumberOne = findViewById(R.id.etNumberOne);
        etNumberTwo = findViewById(R.id.etNumberTwo);
        bAdd = findViewById(R.id.bAdd);
        bSub = findViewById(R.id.bSub);
        bMultiply = findViewById(R.id.bMultiply);
        bDivide = findViewById(R.id.bDivide);
        tvResult = findViewById(R.id.tvResult);

        bAdd.setOnClickListener(this);
        bSub.setOnClickListener(this);
        bMultiply.setOnClickListener(this);
        bDivide.setOnClickListener(this);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String results = preferences.getString("results", "");

        tvResult.setText(results);
    }

    @Override
    public void onClick(View v) {
        String value1 = etNumberOne.getText().toString();
        String value2 = etNumberTwo.getText().toString();

        if(value1.isEmpty() || value2.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_value), Toast.LENGTH_SHORT).show();
        } else {
            int number1 = Integer.valueOf(value1);
            int number2 = Integer.valueOf(value2);

            switch (v.getId()) {
                case R.id.bAdd:
                    add(number1, number2);
                    break;
                case R.id.bSub:
                    subs(number1, number2);
                    break;
                case R.id.bMultiply:
                    multiply(number1, number2);
                    break;
                case R.id.bDivide:
                    divide(number1, number2);
                    break;
            }
        }
    }

    private void add(int number1, int number2) {
        int result = number1 + number2;
        tvResult.setText(String.valueOf(result));

        save(number1, number2, result, getString(R.string.plus));
    }

    private void subs(int number1, int number2) {
        int result = number1 - number2;
        tvResult.setText(String.valueOf(result));

        save(number1, number2, result, "-");
    }

    private void multiply(int number1, int number2) {
        int result = number1 * number2;
        tvResult.setText(String.valueOf(result));

        save(number1, number2, result, "*");
    }

    private void divide(int number1, int number2) {
        if(number2 == 0) {
            Toast.makeText(this, "Nie dziel przez 0!", Toast.LENGTH_SHORT).show();
        } else {
            int result = number1 / number2;
            tvResult.setText(String.valueOf(result));

            save(number1, number2, result, "/");
        }
    }

    private void save(int number1, int number2, int result, String sign) {
        String saved = number1 + sign + number2 + " = " + result;

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("results", saved);
        editor.apply();
    }
}
