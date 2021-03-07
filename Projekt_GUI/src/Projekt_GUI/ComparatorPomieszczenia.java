package Projekt_GUI;

import java.util.Comparator;

public class ComparatorPomieszczenia implements Comparator<Pomieszczenie> {
    @Override
    public int compare(Pomieszczenie p1, Pomieszczenie p2) {
        if (!(p1.getClass().getSimpleName().equals(p2.getClass().getSimpleName())))
            return p1.getClass().getSimpleName().compareTo(p2.getClass().getSimpleName());
        else
            return (int) (p1.getPowierzchnia() - p2.getPowierzchnia());
    }
}
