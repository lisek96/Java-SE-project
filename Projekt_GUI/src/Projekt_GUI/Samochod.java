package Projekt_GUI;

public class Samochod extends Pojazd{
    private String kolor;

    public Samochod(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, String kolor) {
        super(name, objetosc, pojemnoscSilnika, typPojazdu, typSilnika);
        this.kolor =kolor;
    }

    Samochod(String name, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double dlugosc, double szerokosc, double wysokosc) {
        super(name, pojemnoscSilnika, typPojazdu, typSilnika, dlugosc, szerokosc, wysokosc);
        this.kolor=kolor;
    }

    public String toString(){
        return super.toString() + "\tKOLOR" + kolor + "\n";
    }

    public static Samochod stworzSamochod(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, String kolor){
        return new Samochod(name, objetosc,pojemnoscSilnika,typPojazdu,typSilnika,kolor);
    }

    public String getKolor() {
        return kolor;
    }

    public void setKolor(String kolor) {
        this.kolor = kolor;
    }
}
