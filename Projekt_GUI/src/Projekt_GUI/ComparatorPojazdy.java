package Projekt_GUI;

import java.util.Comparator;

public class ComparatorPojazdy implements Comparator<Pojazd> {
    @Override
    public int compare(Pojazd p1, Pojazd p2) {
        if(p1.getID_pojazdu()>p2.getID_pojazdu()) return 1;
        else return -1;
    }
}
