package Projekt_GUI;

public class MiejsceParkingowe extends Pomieszczenie {
    private Pojazd pojazd = null;
    private boolean jestPojazd = false;

    public void sprzedajPojazd() {
        pojazd.setMiejsce_postoju(null);
        pojazd.setCzyZaparkowany(false);
        setPojazd(null);
        setJestPojazd(false);
        setDataKoncaWynajmu(getDataKoncaWynajmu().plusMonths(2));
        setDataEksmisji(getDataKoncaWynajmu().plusDays(30));
        setPismoWyslane(false);
        System.out.print("Sprzedano pojazd osoby: " + getNajemca().toString() + ". ");
        getNajemca().usuńZawiadomienieOzadłużeniu(this);

    }

    public static MiejsceParkingowe stworzMiejsceParkingowe(double powierzchnia){
        return new MiejsceParkingowe(powierzchnia);
    }

    MiejsceParkingowe(double powierzchnia) {
        super(powierzchnia);
    }

    MiejsceParkingowe(double a, double b, double c) {
        super(a, b);
    }

    public boolean czyJestPojazd() {
        return jestPojazd;
    }

    public void setJestPojazd(boolean jestPojazd) {
        this.jestPojazd = jestPojazd;
    }

    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public String jestPojazdtoString() {
        if (jestPojazd) return "tak";
        else return "nie";
    }

    public String toString() {
        return super.toString() + " Pojazd zaparkowany: " + jestPojazdtoString() + "\n";
    }
}
