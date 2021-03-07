package Projekt_GUI;

import java.util.Comparator;

public class ComparatorPrzedmioty implements Comparator<Przedmiot> {
    @Override
    public int compare(Przedmiot przedmiot, Przedmiot p1) {
        if(przedmiot.getObjetosc()>p1.getObjetosc()) return -1;
        else if(przedmiot.getObjetosc()<p1.getObjetosc()) return 1;
        else return przedmiot.getName().compareTo(p1.getName());
    }
}
