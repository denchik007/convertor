package com.example.convertor;

import java.util.HashSet;

public class iz_des_bes_period extends Proverka_dannix {
    public String iz_des_drob(String itog, int syst_v) {
        long ostatok = Long.valueOf(itog);
        long d = (long) Math.pow(10, itog.length());
        String result = "";
        long[] mas = new long[500];
        int sch = 0;
        mas[0] = ostatok;
        HashSet<Long> mn = new HashSet<>();
        mn.add(ostatok);
        while (ostatok != 0 && itog.length() < 30) {
            ostatok *= syst_v;
            long div = ostatok / d;//это пишется в резульатат
            long mod = ostatok % d;//обновленное значение
            sch++;
            mas[sch] = mod;
            result = result + div;
            if (mn.contains(mod)) {
                int i = 0;
                while (i <= sch) {
                    if (mas[i] == mas[sch]) {
                        result = result.substring(0, i) + "(" + result.substring(i);
                        i = sch;
                    }
                    i++;
                }
                result = result + ')';
                break;
            }
            ostatok = mod;
            mn.add(mod);
        }
        if (result.length() > 28) super.long_period = true;
        return result;
    }
}
