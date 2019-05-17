package com.example.convertor;

import java.math.BigDecimal;
import java.math.MathContext;

class Drob_bez_skobok_v_des extends Proverka_dannix {
    public BigDecimal drob(String st, int syst_iz, MathContext okr) {
        BigDecimal itog = new BigDecimal(0);
        double x = 1d;
        for (int i = 0; i < st.length(); i++) {
            x *= syst_iz;
            BigDecimal w = new BigDecimal(Integer.valueOf(String.valueOf(st.charAt(i))));
            BigDecimal res;
            res = w.divide(BigDecimal.valueOf(x), okr);
            itog = itog.add(res, okr);
        }
        return itog;
    }
}
