package Projekt_GUI;

import Projekt_GUI.Exceptions.ExceptionLimitMiejscParkingowych;
import Projekt_GUI.Exceptions.ExceptionLimitMieszkan_w_Bloku;
import Projekt_GUI.Exceptions.ExceptionPomieszczenieJużMaSwojBlok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Blok {
    private final static int liczbaMieszkan = 10;
    private final static int liczbaMiejscParkingowych = 10;
    private static int licznikBlokow=0;
    private Mieszkanie[] mieszkania;
    private MiejsceParkingowe[] miejscaParkingowe;
    private int licznikMieszkan=0;
    private int licznikMiejscParkingowych=0;
    private int ID_bloku=++licznikBlokow;
    public static ArrayList<Blok> listaBlokow = new ArrayList<>();
    private Osiedle osiedle = null;

    Blok(){
        mieszkania = new Mieszkanie[liczbaMieszkan];
        miejscaParkingowe = new MiejsceParkingowe[liczbaMiejscParkingowych];
        listaBlokow.add(this);
    }

    public boolean czyPrzypisaneOsiedle(){
        return osiedle != null;
    }

    public void dodajPomieszczenieDoBloku(Pomieszczenie p) throws ExceptionLimitMieszkan_w_Bloku, ExceptionLimitMiejscParkingowych, ExceptionPomieszczenieJużMaSwojBlok {
        if(!(p.czyUmiejscowione_w_bloku())) {

            if (p.czyMieszkanie()) {
                if (licznikMieszkan < mieszkania.length) {
                    Mieszkanie m = (Mieszkanie) p;
                    mieszkania[licznikMieszkan] = m;
                    m.setBlok(this);
                    licznikMieszkan++;
                } else throw new ExceptionLimitMieszkan_w_Bloku("Nie można dodać więcej mieszkań do tego bloku");
            } else if (p.czyMiejsceParkingowe()) {
                if (licznikMiejscParkingowych < miejscaParkingowe.length) {
                    MiejsceParkingowe mp = (MiejsceParkingowe) p;
                    miejscaParkingowe[licznikMiejscParkingowych] = mp;
                    mp.setBlok(this);
                    licznikMiejscParkingowych++;
                } else throw new ExceptionLimitMiejscParkingowych("Nie można dodać więcej mieszkań do tego bloku");
            }
        }else throw new ExceptionPomieszczenieJużMaSwojBlok("Pomieszczenie już ma przypisany blok");
   }

   public String toString(){
        return "Osiedle: " + osiedle + " ID bloku: " + ID_bloku;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blok blok = (Blok) o;
        return licznikMieszkan == blok.licznikMieszkan &&
                licznikMiejscParkingowych == blok.licznikMiejscParkingowych &&
                ID_bloku == blok.ID_bloku &&
                Arrays.equals(mieszkania, blok.mieszkania) &&
                Arrays.equals(miejscaParkingowe, blok.miejscaParkingowe);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(licznikMieszkan, licznikMiejscParkingowych, ID_bloku);
        result = 31 * result + Arrays.hashCode(mieszkania);
        result = 31 * result + Arrays.hashCode(miejscaParkingowe);
        return result;
    }

    public Osiedle getOsiedle() {
        return osiedle;
    }

    public void setOsiedle(Osiedle osiedle) {
        this.osiedle = osiedle;
    }

    public int getID_bloku() {
        return ID_bloku;
    }

    public void setID_bloku(int ID_bloku) {
        this.ID_bloku = ID_bloku;
    }
}
