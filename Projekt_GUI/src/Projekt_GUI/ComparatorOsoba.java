package Projekt_GUI;

import java.util.Comparator;

public class ComparatorOsoba implements Comparator<Osoba> {
    @Override
    public int compare(Osoba osoba, Osoba osoba1) {
        return osoba.getID_osoby() - osoba1.getID_osoby();
    }
}
