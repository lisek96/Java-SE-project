package Projekt_GUI;

import java.util.TreeSet;


public class Mieszkanie extends Pomieszczenie {

    private TreeSet<Osoba> mieszkancy = new TreeSet<>(new ComparatorOsoba());

    Mieszkanie(double a, double b) {
        super(a, b);
    }

    Mieszkanie(double powierzchnia) {
        super(powierzchnia);
    }

    public void dodajNajemceDoMieszkancow() {
        mieszkancy.add(getNajemca());
    }

    public TreeSet<Osoba> getMieszkancy() {
        return mieszkancy;
    }

    public String toString() {

        if (!czyNieWynajete())
            return super.toString() + "Liczba mieszkańców: " + mieszkancy.size() + "\n";
        else
            return super.toString() + "\n";
    }

    public static Mieszkanie stworzMieszkanie(double powierzchnia){
        return new Mieszkanie(powierzchnia);
    }
}
