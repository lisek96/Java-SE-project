package Projekt_GUI;

public class Wózek_inwalidzki extends Pojazd{

    private String material_siedziska;


    Wózek_inwalidzki(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, String material_siedziska) {
        super(name, objetosc, pojemnoscSilnika, typPojazdu, typSilnika);
        this.material_siedziska=material_siedziska;
    }

    public Wózek_inwalidzki(String name, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double dlugosc, double szerokosc, double wysokosc, String material_siedziska) {
        super(name, pojemnoscSilnika, typPojazdu, typSilnika, dlugosc, szerokosc, wysokosc);
        this.material_siedziska = material_siedziska;
    }


    public String toString(){
        return super.toString() + "\tMATERIAL SIEDZISKA: " + material_siedziska + "\n";
    }

    public static Wózek_inwalidzki stworzWózekInwalidzki(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, String material_siedziska){
        return new Wózek_inwalidzki(name, objetosc,pojemnoscSilnika,typPojazdu,typSilnika,material_siedziska);
    }
}
