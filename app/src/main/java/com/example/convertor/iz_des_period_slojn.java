package com.example.convertor;

import java.util.HashSet;

public class iz_des_period_slojn extends Proverka_dannix {

    public String period_iz_des_slojn(String itog, int syst_v) {
        long ostatok_do_skobki = 0;
        long ostatok_v_skobkax = 0;
        int i = 0;
        long d_v_skobkax = 0;
        long d_do_skobok = 0;
        while (i < itog.length()) {
            if (itog.charAt(i) == '(') {
                d_do_skobok = (long) Math.pow(10, itog.substring(0, i).length());
                d_v_skobkax = (long) Math.pow(10, itog.substring(i + 1, itog.length() - 1).length());
                ostatok_do_skobki = Long.valueOf(itog.substring(0, i));
                ostatok_v_skobkax = Long.valueOf(itog.substring(i + 1, itog.length() - 1));
                i = itog.length();
            }
            i++;
        }
        String result = "";
        long ostatok = ostatok_do_skobki * d_v_skobkax + ostatok_v_skobkax;// в виде десятичного чилсла, 0,1(10) = 110
        int sch = 0;
        HashSet<Long> mn = new HashSet<>();
        long[] mas = new long[500];
        int length = String.valueOf(ostatok_v_skobkax).length();
        mas[0] = ostatok;
        mn.add(ostatok);
        while (ostatok != 0 && result.length() < 30) {
            ostatok_do_skobki *= syst_v;
            ostatok_v_skobkax *= syst_v;
            long sum = 0;
            while (String.valueOf(ostatok_v_skobkax).length() > length) {
                long div = ostatok_v_skobkax / d_v_skobkax; // доделать перевод из десятичной осталось вствавить  одну скобку!!!
                long mod = ostatok_v_skobkax % d_v_skobkax;
                sum += div;
                ostatok_v_skobkax = div + mod;
            }
            sch++;
            ostatok_do_skobki += sum;
            result = result + (ostatok_do_skobki / d_do_skobok);
            ostatok_do_skobki = ostatok_do_skobki % d_do_skobok;
            ostatok = ostatok_do_skobki * d_v_skobkax + ostatok_v_skobkax;
            mas[sch] = ostatok;
            if (mn.contains(ostatok)) {
                i = 0;
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

