package com.example.convertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected EditText v_text;
    protected EditText chislo_text;
    protected EditText iz_text;
    protected TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClearAll();
    }

    public void OnBtnClick(View t) {
        v_text = findViewById(R.id.v);
        String v = v_text.getText().toString();
        chislo_text = findViewById(R.id.chislo);
        String chislo = chislo_text.getText().toString();
        iz_text = findViewById(R.id.iz);
        String iz = iz_text.getText().toString();
        res = findViewById(R.id.res);
        Proverka_dannix obj_vvod_dannix_proverka = new Proverka_dannix();
        String vivod = obj_vvod_dannix_proverka.Click(iz, v, chislo);
        if (!obj_vvod_dannix_proverka.proverka_oshibok) {
            res.setText(vivod);
            if (obj_vvod_dannix_proverka.long_period)
                Toast.makeText(MainActivity.this, "Слишком большой период!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(MainActivity.this, "Введено неверное значение", Toast.LENGTH_SHORT).show();
    }

    public void ClearAll() {
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                res.setText("");
                chislo_text.setText("");
                v_text.setText("");
                iz_text.setText("");
            }
        });
    }
}
