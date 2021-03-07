package Projekt_GUI;

public class Jacht extends Pojazd{
    private double cena;

    public Jacht(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double cena) {
        super(name, objetosc, pojemnoscSilnika, typPojazdu, typSilnika);
        this.cena = cena;
    }

    public Jacht(String name, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double dlugosc, double szerokosc, double wysokosc, double cena) {
        super(name, pojemnoscSilnika, typPojazdu, typSilnika, dlugosc, szerokosc, wysokosc);
        this.cena = cena;
    }

    public String toString(){
        return super.toString() + "\tCENA: " + cena + "\n";
    }

    public static Jacht stworzJacht(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double cena){
        return new Jacht(name, objetosc,pojemnoscSilnika,typPojazdu,typSilnika,cena);
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}
