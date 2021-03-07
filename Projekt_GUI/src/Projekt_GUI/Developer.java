package Projekt_GUI;

import java.time.LocalDate;
import java.util.ArrayList;

public class Developer<Arraylist> extends Osoba {
    ArrayList<Osiedle> osiedlaDevelopera = new ArrayList<>();
    int iloscPosiadanychOsiedli;


    Developer(String imie, String nazwisko, String pesel, String adres, LocalDate dataUrodzenia) {
        super(imie, nazwisko, pesel, adres, dataUrodzenia);
    }

    public void dodajOsiedle(Osiedle o){
        osiedlaDevelopera.add(o);
        iloscPosiadanychOsiedli++;
    }


}

