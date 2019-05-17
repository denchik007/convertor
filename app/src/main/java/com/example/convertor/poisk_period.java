package com.example.convertor;

class poisk_period extends Proverka_dannix {

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
                if (!f) {
                    result = n.substring(0, i) + "(" + str + ")";
                    i = i + 20;
                    break;
                }
            }
        }
        if (result.equals("")) {
            result = n.substring(0, 11);
            super.long_period = true;
        }
        return result;
    }
}
