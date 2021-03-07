package Projekt_GUI;

public enum EDlugoscWynajmu {
    KWARTAL(3), POL_ROKU(6), ROK(12);

    int dlugosc_w_miesiacach;
    EDlugoscWynajmu(int dlugosc_w_miesiacach){
        this.dlugosc_w_miesiacach = dlugosc_w_miesiacach;
    }
}
