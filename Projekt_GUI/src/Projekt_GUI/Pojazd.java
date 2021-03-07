package Projekt_GUI;

import java.util.ArrayList;
import java.util.Collections;

import static Projekt_GUI.Obliczenia.policzObjetosc;

public abstract class Pojazd implements IPojazd {
    private String name;
    private double objetosc;
    private double pojemnoscSilnika;
    private ETypSilnika typSilnika;
    private ETypPojazdu typPojazdu;
    private Osoba wlasciciel = null;
    private boolean czyZaparkowany = false;
    private int ID_pojazdu;
    private static ArrayList<Pojazd> listaPojazdow = new ArrayList<>();
    private static int licznikPojazdów = 0;
    private MiejsceParkingowe miejsce_postoju = null;

    Pojazd(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika) {
        this.name = name;
        this.objetosc = objetosc;
        this.pojemnoscSilnika = pojemnoscSilnika;
        this.typPojazdu = typPojazdu;
        this.typSilnika = typSilnika;
        listaPojazdow.add(this);
        ID_pojazdu = ++licznikPojazdów;
    }

    Pojazd(String name, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double dlugosc, double szerokosc, double wysokosc) {
        this.name = name;
        this.pojemnoscSilnika = pojemnoscSilnika;
        this.typPojazdu = typPojazdu;
        this.typSilnika = typSilnika;
        objetosc = policzObjetosc.metoda(dlugosc, szerokosc, wysokosc);
        listaPojazdow.add(this);
        ID_pojazdu = ++licznikPojazdów;
    }

    public static void wyswietlWszystkiePojazdy() {
        Collections.sort(listaPojazdow, new ComparatorPojazdy());
        listaPojazdow.forEach(System.out::println);
    }




    public double getObjetosc() {
        return objetosc;
    }

    public boolean isCzyZaparkowany() {
        return czyZaparkowany;
    }

    public void setCzyZaparkowany(boolean czyZaparkowany) {
        this.czyZaparkowany = czyZaparkowany;
    }

    public static ArrayList<Pojazd> getListaPojazdow() {
        return listaPojazdow;
    }

    public int getID_pojazdu() {
        return ID_pojazdu;
    }

    private String klasaPojazduToString() {
        return getClass().getSimpleName();
    }

    public String toString() {
        String wynik = "ID: " + ID_pojazdu + "\t" + klasaPojazduToString() + " NAZWA: " + name + "\tPOJEMNOSC SILNIKA: " + pojemnoscSilnika +
                "\tOBJETOSC: " + objetosc + "\tTYP SILNIKA: " + typSilnika + "\tTYP POJAZDU: " + typPojazdu + "\t ZAPARKOWANY: " + czyZaparkowany + "\tMIEJSCE POSTOJU: ";
        if (miejsce_postoju != null) wynik += miejsce_postoju.getID_pomieszczenia();
        else wynik += "BRAK";

        return wynik;
    }

    @Override
    public void wystartujSilnik() {
        System.out.println("Silnik ON");
    }

    @Override
    public void wylaczSilnik() {
        System.out.println("Silnik OFF");
    }

    @Override
    public void zatankujPaliwo() {
        System.out.println("Tankuje paliwo...");
    }

    @Override
    public void wlaczRadio() {
        System.out.println("Lalalala");
    }

    public Osoba getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(Osoba wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    public MiejsceParkingowe getMiejsce_postoju() {
        return miejsce_postoju;
    }

    public void setMiejsce_postoju(MiejsceParkingowe miejsce_postoju) {
        this.miejsce_postoju = miejsce_postoju;
    }
}
