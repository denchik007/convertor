package com.example.convertor;


class Perevod_do_tochki extends Proverka_dannix {

    public String Perevod(String chislo, String result, int syst_v, int syst_iz) {
        if (syst_iz > 10 || syst_v > 10 || syst_v < 2 || syst_iz < 2) super.proverka_oshibok = true;
        String res_dz = "";
        proverka_simvolov obj_proverka_simvolov = new proverka_simvolov();
        if (!super.proverka_oshibok) {
            long itog = 0;
            for (int i = 0; i < chislo.length(); i++) {
                super.proverka_oshibok = obj_proverka_simvolov.Proverka(chislo, i, syst_iz);
                if (super.proverka_oshibok) break;
                else {
                    int l = Integer.parseInt(String.valueOf(chislo.charAt(i)));
                    itog += (int) Math.pow(syst_iz, chislo.length() - i - 1) * l;
                }
            }
            if (!super.proverka_oshibok) {
                while (itog != 0) {
                    long l = itog % syst_v;
                    res_dz = l + res_dz;  // целая часть из 10-ной системы счиление в нужную систему счисления
                    itog = itog / syst_v;
                }
                if (res_dz.equals("")) res_dz += "0";
                if (!result.equals("")) res_dz += "." + result;
            }
        }
        if (!super.proverka_oshibok) return res_dz;
        else {
            res_dz = "";
            return res_dz;
        }
    }
}
