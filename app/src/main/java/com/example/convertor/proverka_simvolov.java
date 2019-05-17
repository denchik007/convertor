package com.example.convertor;

public class proverka_simvolov{

    public boolean Proverka(String str, int j,int syst_iz) {
        int l = 0;
        boolean znak = false;
        try {
            l = Integer.valueOf(String.valueOf(str.charAt(j)));
        } catch (Exception e) {
            znak = true;
        }
        if (l >= syst_iz) {
            znak = true;
        }
        return znak;
    }
}
