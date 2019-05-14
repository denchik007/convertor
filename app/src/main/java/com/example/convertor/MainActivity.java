package com.example.convertor;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.*;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private EditText v;
    private EditText chislo;
    private EditText iz;
    private TextView res;
    private Button btn;
    private Button clear;
    protected FloatingActionButton floatbtn;
    protected int syst_v = 0, syst_iz = 0;
    protected boolean q = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnBtnClick();
        ClearAll();
    }

    public void OnBtnClick() {
        this.v = findViewById(R.id.v);
        this.iz = findViewById(R.id.iz);
        this.chislo = findViewById(R.id.chislo);
        this.res = findViewById(R.id.res);
        this.btn = findViewById(R.id.btn);
        this.floatbtn = findViewById(R.id.floatbtn);
        floatbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.convertor.LoginActivity");
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = v.getText().toString();
                String s2 = iz.getText().toString();
                String ch = chislo.getText().toString();
                String dz = "", pz = "";
                String result = "";
                boolean pz_ = false;
                q = false;
                HashSet<String> mn = new HashSet<>();

                for (int i = 0; i < ch.length(); i++)
                    if (ch.charAt(i) == '.') {
                        dz = ch.substring(0, i);//то что стоит до запятной
                        pz = ch.substring(i + 1);//то что стоит после запятой
                        pz_ = true;
                    }

                if (dz.equals("")) dz = ch;
                try {
                    syst_iz = Integer.valueOf(s2);//из этой системы осуществляется перевод
                    syst_v = Integer.valueOf(s1);//в эту систему осущствляется перевод
                } catch (Exception e) {
                    q = true;
                }
                if(!q){
                    for(int i = 0;i < syst_iz;i++)
                        mn.add(String.valueOf(i));
                    for(int i = 0;i < dz.length();i++)
                        if(!mn.contains(String.valueOf(dz.charAt(i)))) {
                            q = true;
                            break;
                        }
                }

                String d_sk = "null";//до скобок
                String v_sk = "null";//в скобках
                boolean f = false;
                int o = 0, z = 0;
                for (int i = 0; i < pz.length(); i++) {
                    if (pz.charAt(i) == '(') o++;
                    if (pz.charAt(i) == ')') z++;
                }

                if ((pz.equals("") && pz_) || o > 1 || z > 1) q = true;
                if (!q) {
                    for (int i = 0; i < pz.length(); i++)
                        if (pz.charAt(i) == '(') //если скобки присутствуют тогда f = true
                            if (pz.charAt(pz.length() - 1) != ')') {
                                q = true;
                            } else {
                                f = true;
                                v_sk = pz.substring(i + 1, pz.length() - 1);
                                d_sk = pz.substring(0, i);
                                break;
                            }
                }

                if (v_sk.equals("")) q = true;
                BigDecimal dr = new BigDecimal(0);
                MathContext okr = new MathContext(500);
                BigDecimal itog;

                if (!f && !pz.equals("") && !q) {
                    itog = drob(pz, okr);
                    if (!q) {
                        result = String.valueOf(itog).substring(2);
                        if (result.length() == 500) result = period(result);
//                        else {
//                            int sv = Integer.valueOf(result);
//                            String r = "";
//                            StringBuffer r1 = new StringBuffer();
//                            HashSet<Integer> mn = new HashSet<>();
//                            boolean x = false;
//                            while (!x && sv != 0){
//                                sv *= syst_v;
//                                int l = sv / 10; // доделать перевод из десятичной осталось встваиь  одну скобку!!!
//                                int k = sv % 10;
//                                if(mn.contains(l)){
//                                    x = true;
//                                    r = r + ')';
//                                    break;
//                                }
//                                r = r + k;
//                                sv = l;
//                                mn.add(l);
//                            }
//                            int i = 0;
//                            while (i < r.length()){
//                                if(r.charAt(i) == r.charAt(r.length() - 1))
//                                     i = i + 1;
//                          }
//                        }
                    }

                } else if (!v_sk.equals("null") && !v_sk.equals("") && !q) {
                    long length = (long) Math.pow(syst_iz, v_sk.length()) - 1;
                    int itog1 = 0;
                    for (int i = 0; i < v_sk.length(); i++) {
                        Proverka(v_sk, i);
                        if (!q) {
                            int l = Integer.valueOf(String.valueOf(v_sk.charAt(i)));
                            itog1 += Math.pow(syst_iz, v_sk.length() - i - 1) * l;
                        } else break;
                    }
                    itog = BigDecimal.valueOf(itog1);
                    dr = itog.divide(BigDecimal.valueOf(length), okr);
                    if (!q) {
                        String n = String.valueOf(dr).substring(2);
                        if (n.length() == 500) result = period(n);
                    }
                }


                if (!d_sk.equals("null") && !d_sk.equals("")) {
                    BigDecimal it = new BigDecimal(0);
                    for (int i = 0; i < d_sk.length(); i++)
                        Proverka(d_sk, i);
                    if (!q)
                        it = drob(d_sk, okr);// часть перед скобками которая плюсуется в конце
                    int length = d_sk.length();
                    BigDecimal x = new BigDecimal(syst_iz);
                    x = x.pow(-length, okr);// на это умножается dr
                    x = x.multiply(dr, okr);
                    it = it.add(x, okr);
                    String n = String.valueOf(it).substring(2);
                    result = period(n);
                }
                Perevod(dz, result);
            }
        });
    }

    public void ClearAll() {
        this.clear = findViewById(R.id.clear);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                res.setText("");
                // chislo.setText("");
                //  v.setText("");
                //  iz.setText("");
            }
        });
    }

    public void Perevod(String x2, String result) {
        if (syst_iz > 10 || syst_v > 10 || syst_v < 2 || syst_iz < 2) q = true;
        if (!q) {
            long itog = 0;
            for (int i = 0; i < x2.length(); i++) {
                Proverka(x2, i);
                if (q) break;
                else {
                    int l = Integer.parseInt(String.valueOf(x2.charAt(i)));
                    itog += (int) Math.pow(syst_iz, x2.length() - i - 1) * l;//всеее
                }
            }
            if (!q) {
                String st = "";
                while (itog != 0) {
                    long l = itog % syst_v;
                    st = l + st;  // целая часть из 10-ной системы счиление в нужную систему счисления
                    itog = itog / syst_v;
                }
                if (st.equals("")) st += "0";
                if (!result.equals("")) st += "." + result;
                res.setText(st);
            }
        }
        if (q) {
            res.setText("");
            Toast.makeText(MainActivity.this, "Введено неверное значение", Toast.LENGTH_SHORT).show();
        }
    }

    public BigDecimal drob(String st, MathContext okr) {
        BigDecimal itog = new BigDecimal(0);
        double x = 1d;
        for (int i = 0; i < st.length(); i++) {
            Proverka(st, i);

            if (!q) {
                x *= syst_iz;
                String c = String.valueOf(st.charAt(i));
                BigDecimal w = new BigDecimal(Integer.valueOf(c));
                BigDecimal res = new BigDecimal(x, okr);
                res = w.divide(res, okr);
                itog = itog.add(res, okr);
            }
        }
        return itog;
    }


    public void Proverka(String str, int j) {
        int l = 0;
        try {
            l = Integer.parseInt(String.valueOf(str.charAt(j)));
        } catch (Exception e) {
            q = true;
        }
        if (l >= syst_iz)
            q = true;
    }

    public String period(String n) {
        int i = -1;
        String result = "";
        while (i < 20) {
            i++;
            for (int j = i + 1; j < 50; j++) {
                String str = n.substring(i, j);
                int h = j - i;
                boolean f = false;
                int l = 0;
                for (int z = i; l < 10; z = z + h) {
                    l++;
                    if (!n.substring(z, z + h).equals(str)) {
                        f = true;
                        break;
                    }
                }
                if (!f){
                    result = n.substring(0, i) + "(" + str + ")";
                    i = i + 20;
                    break;
                }
            }
        }
        if (result.equals("")) {
            result = n.substring(0, 11);
            Toast.makeText(MainActivity.this, "Слишком большой период!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
