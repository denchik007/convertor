package com.example.convertor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;

public class Proverka_dannix extends MainActivity {

    public boolean proverka_oshibok;
    public boolean long_period;
    public int syst_iz, syst_v;

    public String Click(String iz, String v, String chislo) {
        proverka_oshibok = false;
        String dz = "", pz = "", result_pz = "";
        boolean pz_ = false;
        HashSet<String> mn = new HashSet<>();

        try {
            syst_iz = Integer.valueOf(iz);//из этой системы осуществляется перевод
            syst_v = Integer.valueOf(v);//в эту систему осущствляется перевод
        } catch (Exception e) {
            proverka_oshibok = true;
        }


        for (int i = 0; i < chislo.length(); i++)
            if (chislo.charAt(i) == '.') {
                dz = chislo.substring(0, i);//то что стоит до запятной
                pz = chislo.substring(i + 1);//то что стоит после запятой
                pz_ = true;  // наличие дробной части
            }


        if (dz.equals("")) dz = chislo;
        if (!proverka_oshibok) {
            for (int i = 0; i < syst_iz; i++)
                mn.add(String.valueOf(i));
            for (int i = 0; i < dz.length(); i++)
                if (!mn.contains(String.valueOf(dz.charAt(i)))) {
                    proverka_oshibok = true;
                    break;
                }
        }

        int o = 0, z = 0;
        for (int i = 0; i < pz.length(); i++) {
            if (pz.charAt(i) == '(') o++;
            if (pz.charAt(i) == ')') z++;
        }
        if ((pz.equals("") && pz_) || o > 1 || z > 1) proverka_oshibok = true;
        boolean skobki = false;
        String d_sk = "null";//до скобок
        String v_sk = "null";//в скобках
        if (!proverka_oshibok) {
            for (int i = 0; i < pz.length(); i++)
                if (pz.charAt(i) == '(') //если скобки присутствуют тогда posle_zap = true
                    if (pz.charAt(pz.length() - 1) != ')') {
                        proverka_oshibok = true;
                    } else {
                        skobki = true;
                        v_sk = pz.substring(i + 1, pz.length() - 1);
                        d_sk = pz.substring(0, i);
                        break;
                    }
        }
        if (v_sk.equals("") && skobki) proverka_oshibok = true;


        proverka_simvolov obj_proverkaSimvolov = new proverka_simvolov();

        poisk_period obj_poisk_period = new poisk_period();

        Drob_bez_skobok_v_des obj_Drob_bez_skobok_v_des = new Drob_bez_skobok_v_des();

        iz_des_bes_period obj_is_des_bes_period = new iz_des_bes_period();

        period_iz_des obj_period_iz_des = new period_iz_des();

        iz_des_period_slojn obj_iz_des_period_slojn = new iz_des_period_slojn();
        BigDecimal drob;
        MathContext okr = new MathContext(500);
        BigDecimal itog = new BigDecimal(0);


        if (!skobki && !pz.equals("") && !proverka_oshibok) {//если нет скобок и присутствует дробная часть
            for (int i = 0; i < pz.length(); i++) {         //проверка правильности знаков
                proverka_oshibok = obj_proverkaSimvolov.Proverka(pz, i, syst_iz);
                if (proverka_oshibok) break;
            }

            if (!proverka_oshibok) {
                if (pz.length() < 20)
                    result_pz = String.valueOf(obj_Drob_bez_skobok_v_des.drob(pz, syst_iz, okr));
                else long_period = true;
                result_pz = result_pz.substring(2);
                if (result_pz.length() >= 450) {
                    result_pz = obj_poisk_period.period(result_pz);
                    if (result_pz.length() < 22) {
                        if (result_pz.charAt(0) == '(')
                            result_pz = obj_period_iz_des.period_iz_des(result_pz.substring(1, result_pz.length() - 1), syst_v);
                        else
                            result_pz = obj_iz_des_period_slojn.period_iz_des_slojn(result_pz, syst_v);
                    } else long_period = true;
                    //добавить перевод из десятичной с учетом того что перед выражение вида 0,10(1)
                }  else
                    if(result_pz.length() < 20) result_pz = obj_is_des_bes_period.iz_des_drob(result_pz, syst_v); else long_period = true;
            }
        }
        if (!v_sk.equals("null") && !v_sk.equals("") && !proverka_oshibok) {    //если присутствует период
            long length = (long) Math.pow(syst_iz, v_sk.length()) - 1;
            for (int i = 0; i < v_sk.length(); i++) {                  //проверка правильности знаков
                proverka_oshibok = obj_proverkaSimvolov.Proverka(v_sk, i, syst_iz);
                if (proverka_oshibok) break;
                else {                                   // перевод как обычное число
                    int l = Integer.valueOf(String.valueOf(v_sk.charAt(i)));
                    itog = itog.add(BigDecimal.valueOf(Math.pow(syst_iz, v_sk.length() - i - 1) * l), okr);
                }
            }

            if (!proverka_oshibok) {
                drob = itog.divide(BigDecimal.valueOf(length), okr);
                if (!d_sk.equals("null") && !d_sk.equals("")) {  // если период имеет вид 0,1(10)
                    BigDecimal BD_result;
                    for (int i = 0; i < d_sk.length(); i++) {         //проверка правильности знаков
                        proverka_oshibok = obj_proverkaSimvolov.Proverka(d_sk, i, syst_iz);
                        if (proverka_oshibok) break;
                    }
                    if (!proverka_oshibok) {
                        BD_result = obj_Drob_bez_skobok_v_des.drob(d_sk, syst_iz, okr);// часть перед скобками которая плюсуется в конце 0,1
                        BigDecimal s_iz = new BigDecimal(syst_iz);
                        s_iz = s_iz.pow(-d_sk.length(), okr);
                        drob = s_iz.multiply(drob, okr);
                        drob = drob.add(BD_result, okr);
                    }
                }
                if (String.valueOf(drob).length() >= 450) {

                    result_pz = String.valueOf(drob).substring(2);
                    result_pz = obj_poisk_period.period(result_pz);//перевод дроби в 10-ую систему1
                    if (result_pz.length() < 22) {
                        if (result_pz.charAt(0) == '(') // поработать над типами данных      !!!!!!!!!!!! и пробелами
                            result_pz = obj_period_iz_des.period_iz_des(result_pz.substring(1, result_pz.length() - 1), syst_v);
                        else
                            result_pz = obj_iz_des_period_slojn.period_iz_des_slojn(result_pz, syst_v);
                    } else long_period = true;


                } else result_pz = obj_is_des_bes_period.iz_des_drob(result_pz, syst_v);
            }
        }
        Perevod_do_tochki obj = new Perevod_do_tochki();
        if (!proverka_oshibok) {
            iz = obj.Perevod(dz, result_pz, syst_v, syst_iz);
            return iz;
        } else return null;
    }
}
