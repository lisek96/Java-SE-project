package Projekt_GUI;

public class Motocykl extends Pojazd {
    private boolean czyMaWozekBoczny;

    public Motocykl(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, boolean czyMaWozekBoczny) {
        super(name, objetosc, pojemnoscSilnika, typPojazdu, typSilnika);
        this.czyMaWozekBoczny = czyMaWozekBoczny;
    }

    public Motocykl(String name, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double dlugosc, double szerokosc, double wysokosc, boolean czyMaWozekBoczny) {
        super(name, pojemnoscSilnika, typPojazdu, typSilnika, dlugosc, szerokosc, wysokosc);
        this.czyMaWozekBoczny = czyMaWozekBoczny;
    }

    @Override
    public String toString() {
        return super.toString() + "\tCZY MA WOZEK BOCZNY: " + czyMaWozekBoczny + "\n";
    }
    public static Motocykl stworzMotocykl(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, boolean czyMaWozekBoczny){
        return new Motocykl(name, objetosc,pojemnoscSilnika,typPojazdu,typSilnika,czyMaWozekBoczny);
    }

}
