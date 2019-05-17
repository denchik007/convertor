package com.example.convertor;

import java.util.HashSet;

public class period_iz_des extends Proverka_dannix {

    public String period_iz_des(String itog, int syst_v) {

        int sch = 0;
        HashSet<Long> mn = new HashSet<>();
        String result = "";
        long[] mas = new long[500];
        long ostatok = Long.valueOf(itog);
        long d = (long) Math.pow(10, itog.length());
        mas[0] = ostatok;
        mn.add(ostatok);
        while (ostatok != 0 && result.length() < 30) {
            ostatok *= syst_v;
            int sum = 0;
            while (String.valueOf(ostatok).length() > itog.length()) {
                long div = ostatok / d; // доделать перевод из десятичной осталось вствавить  одну скобку!!!
                long mod = ostatok % d;
                sum += div;
                ostatok = div + mod;
            }
            sch++;
            mas[sch] = ostatok;
            result = result + sum;
            if (mn.contains(ostatok)) {
                int i = 0;
                while (i <= sch) {
                    if (mas[i] == mas[sch]) {
                        result = result.substring(0, i) + "(" + result.substring(i);
                        i = sch;
                    }
                    i++;
                }
                result += ')';
                break;
            }
            mn.add(ostatok);
        }
        if (result.length() > 28) super.long_period = true;
        return result;
    }
}
