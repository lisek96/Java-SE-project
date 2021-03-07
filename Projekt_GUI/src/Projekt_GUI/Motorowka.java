package Projekt_GUI;

public class Motorowka extends Pojazd {
    private int ilosc_dozwolonych_osob_na_pokladzie;

    public Motorowka(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, int ilosc_dozwolonych_osob_na_pokladzie) {
        super(name, objetosc, pojemnoscSilnika, typPojazdu, typSilnika);
        this.ilosc_dozwolonych_osob_na_pokladzie = ilosc_dozwolonych_osob_na_pokladzie;
    }

    public Motorowka(String name, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double dlugosc, double szerokosc, double wysokosc, int ilosc_dozwolonych_osob_na_pokladzie) {
        super(name, pojemnoscSilnika, typPojazdu, typSilnika, dlugosc, szerokosc, wysokosc);
        this.ilosc_dozwolonych_osob_na_pokladzie = ilosc_dozwolonych_osob_na_pokladzie;
    }

    public String toString(){
        return super.toString() + "\tILUOSOBOWY: " + ilosc_dozwolonych_osob_na_pokladzie + "\n";
    }
    public static Motorowka stworzMotorowke(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, int ilosc_dozwolonych_osob_na_pokladzie){
        return new Motorowka(name, objetosc,pojemnoscSilnika,typPojazdu,typSilnika,ilosc_dozwolonych_osob_na_pokladzie);
    }

    public int getIlosc_dozwolonych_osob_na_pokladzie() {
        return ilosc_dozwolonych_osob_na_pokladzie;
    }

    public void setIlosc_dozwolonych_osob_na_pokladzie(int ilosc_dozwolonych_osob_na_pokladzie) {
        this.ilosc_dozwolonych_osob_na_pokladzie = ilosc_dozwolonych_osob_na_pokladzie;
    }
}
