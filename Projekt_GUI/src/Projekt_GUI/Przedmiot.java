package Projekt_GUI;

import java.util.ArrayList;
import java.util.Objects;

import static Projekt_GUI.Obliczenia.policzObjetosc;

public class Przedmiot {
    private final String name;
    private final double objetosc;
    private final int ID_przedmiotu;
    private static int licznikPrzedmiotow = 0;
    private static ArrayList<Przedmiot> listaWszystkichPrzedmiotow  = new ArrayList<>();


    Przedmiot(String name, double objetosc) {
        this.name = name;
        this.objetosc = objetosc;
        ID_przedmiotu = ++licznikPrzedmiotow;
        listaWszystkichPrzedmiotow.add(this);
    }

    Przedmiot(double dlugosc, double szerokosc, double wysokosc, String name) {
        this.name = name;
        ID_przedmiotu = ++licznikPrzedmiotow;
        objetosc = policzObjetosc.metoda(dlugosc, szerokosc, wysokosc);
        listaWszystkichPrzedmiotow.add(this);
    }

    public static Przedmiot stworzPrzedmiot(double dlugosc, double szerokosc, double wysokosc, String name){
        return new Przedmiot(dlugosc, szerokosc,wysokosc,name);
    }




    public static void wyswietlWszystkiePrzedmioty() {
        listaWszystkichPrzedmiotow.forEach(System.out::println);
    }

    public double getObjetosc() {
        return objetosc;
    }

    public static ArrayList<Przedmiot> getListaWszystkichPrzedmiotow() {
        return listaWszystkichPrzedmiotow;
    }

    public int getID_przedmiotu() {
        return ID_przedmiotu;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getClass().getSimpleName() + " ID: " + ID_przedmiotu + " name: " + name + " objetosc: " + objetosc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Przedmiot przedmiot = (Przedmiot) o;
        return Double.compare(przedmiot.objetosc, objetosc) == 0 &&
                ID_przedmiotu == przedmiot.ID_przedmiotu &&
                Objects.equals(name, przedmiot.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, objetosc, ID_przedmiotu);
    }
}
