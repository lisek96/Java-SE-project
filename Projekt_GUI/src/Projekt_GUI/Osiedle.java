package Projekt_GUI;

import Projekt_GUI.Exceptions.ExceptionPrzypisaneOsiedle;

import java.util.ArrayList;

public class Osiedle {
    private ArrayList<Blok> bloki;
    private String nazwaOsiedla;
    private Developer wlascicielOSiedla;
    private ArrayList<Blok> listaBlokowNaOsiedlu = new ArrayList<>();

    Osiedle(String nazwaOsiedla) {
        this.nazwaOsiedla=nazwaOsiedla;
        bloki = new ArrayList<>();
    }

    public void dodajBlok(Blok b) throws ExceptionPrzypisaneOsiedle {
        if(!(b.czyPrzypisaneOsiedle())) {
            listaBlokowNaOsiedlu.add(b);
            b.setOsiedle(this);
        }else throw new ExceptionPrzypisaneOsiedle("Blok jest już przypisany do osiedla");
    }

    public void wypiszWlascicielaOsiedla(){
        System.out.println(wlascicielOSiedla.toString());
    }

    public String toString(){
        return nazwaOsiedla;
    }
}
