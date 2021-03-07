package Projekt_GUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static Projekt_GUI.Obliczenia.policzObjetosc;
import static Projekt_GUI.Obliczenia.policzPole;

public abstract class Pomieszczenie implements IObslugaWynajmu {
    private static int licznikPomieszczen;
    private final int ID_pomieszczenia;
    private final double objetosc;
    private final double standardowaWysokoscPomieszczenia = 2.5;
    private final double powierzchnia;
    private double pozostalaObjetosc;
    private boolean pismoWyslane = false;
    private LocalDate dataRozpWynajmu;
    private LocalDate dataKoncaWynajmu;
    private LocalDate dataEksmisji;
    private Osoba najemca = null;
    private EStatusPomieszczenia status = EStatusPomieszczenia.NIEWYNAJTE;
    private static ArrayList<Pomieszczenie> wszystkiePomieszczenia = new ArrayList<>();
    private ArrayList<Przedmiot> przedmioty_w_pomieszczeniu = new ArrayList<>();
    boolean czyMiejsceParkingowePosiadaPojazd = false;
    private Blok blok = null;

    Pomieszczenie(double powierzchnia) {
        this.powierzchnia=powierzchnia;
        objetosc=powierzchnia*standardowaWysokoscPomieszczenia;
        pozostalaObjetosc = objetosc;
        ID_pomieszczenia = ++licznikPomieszczen;
        wszystkiePomieszczenia.add(this);
    }

    Pomieszczenie(double a, double b) {
        ID_pomieszczenia = ++licznikPomieszczen;
        objetosc = policzObjetosc.metoda(a, b, standardowaWysokoscPomieszczenia);
        powierzchnia = policzPole.metoda(a, b, standardowaWysokoscPomieszczenia);
        pozostalaObjetosc = objetosc;
        wszystkiePomieszczenia.add(this);
    }

    public boolean czyUmiejscowione_w_bloku() {
        if (blok != null) return true;
        else return false;
    }

    public void ustawDatyWynajmu(EDlugoscWynajmu okresWynajmu) {
        if (dataRozpWynajmu == null) {
            dataRozpWynajmu = Data.currentDate;
            dataKoncaWynajmu = dataRozpWynajmu.plusMonths(okresWynajmu.dlugosc_w_miesiacach);
        } else if (dataRozpWynajmu != null) {
            dataKoncaWynajmu = dataKoncaWynajmu.plusMonths(okresWynajmu.dlugosc_w_miesiacach);
        }
        dataEksmisji = dataKoncaWynajmu.plusDays(30);
    }

    public String nazwaKlasy() {
        return getClass().getSimpleName();
    }

    public String daneWynajmu() {
        return "Data rozpoczęcia najmu: " + dataRozpWynajmu + " Data końca najmu: " + dataKoncaWynajmu + " Data eksmiji: " + dataEksmisji;
    }

    public String toString() {
        if (czyNieWynajete())
            return "ID: " + ID_pomieszczenia + " " + nazwaKlasy() + " || " + "powierzchnia użytkowa: " + powierzchnia + "m^2" + " objetosc: " + objetosc + "m^3" + " Osiedle: " + blok.getOsiedle();
        else
            return "ID: " + ID_pomieszczenia + " " + nazwaKlasy() + " powierzchnia: " + powierzchnia + "m^2" + " pozostala objetosc: " + pozostalaObjetosc + "m^3" + " Osiedle: " + blok.getOsiedle() + "\n" + daneWynajmu() + "\n";
    }

    @Override
    public void sprawdzCzyKoniecWynajmu(LocalDate currentDate) throws IOException {
        if (currentDate.isAfter(dataKoncaWynajmu) && pismoWyslane == false) {
            wyslijPowiadomienie_o_koncuWynajmu(najemca);
            pismoWyslane = true;
        }
    }

    private void checkCzyMiejsceParkingowePosiadaPojazd() throws IOException {
        if (czyMiejsceParkingowe()) {
            MiejsceParkingowe mp = (MiejsceParkingowe) this;
            if (mp.czyJestPojazd()) czyMiejsceParkingowePosiadaPojazd = true;
            else czyMiejsceParkingowePosiadaPojazd = false;
        } else czyMiejsceParkingowePosiadaPojazd = false;
    }

    @Override
    public void sprawdzCzyNadszedłTerminEksmisji(LocalDate currentDate) throws IOException {

        if (currentDate.isAfter(dataEksmisji)) {
            checkCzyMiejsceParkingowePosiadaPojazd();
            if (czyMiejsceParkingowePosiadaPojazd) {
                MiejsceParkingowe mp = (MiejsceParkingowe) this;
                mp.sprzedajPojazd();
            } else if (czyMiejsceParkingowe() || czyMieszkanie()) {
                najemca.setCzyNajemcaMieszkania(false);
                najemca.getWynajetePomieszczenia().forEach((x) -> {
                    if (x.czyMieszkanie()) najemca.setCzyNajemcaMieszkania(true);
                });
                najemca.getWynajetePomieszczenia().remove(this);
                wyslijPowiadomienie_o_Eksmisji(najemca);
                System.err.print("Panie " + najemca.getImie() + " " + najemca.getNazwisko() + "." +" Zostałeś eksmitowany z pomieszczenia " + "nr: " +getID_pomieszczenia() + " !!!!");
                najemca.usuńZawiadomienieOzadłużeniu(this);
                przedmioty_w_pomieszczeniu.clear();
                status = EStatusPomieszczenia.NIEWYNAJTE;
                pozostalaObjetosc = objetosc;
                dataKoncaWynajmu = null;
                dataRozpWynajmu = null;
                dataEksmisji = null;
                pismoWyslane = false;
                if(najemca.getZawiadomienia_o_eksmisjach().size()>2) System.err.println("Zostałeś eksmitowany z conajmniej trzech pomieszczeń, straciłeś możliwość wynajmowania. Udaj się pod most :)");
                najemca = null;
            }
            if (czyMieszkanie()) {
                var m = (Mieszkanie) this;
                m.getMieszkancy().clear();
            }
        }
    }

    public void wyslijPowiadomienie_o_Eksmisji(Osoba o) throws IOException {
        String nazwaPisma = ID_pomieszczenia + "IDP_Eksmisja" + o.getPesel() + "_" + (o.getZawiadomienia_o_eksmisjach().size() + 1);
        File file = new File(nazwaPisma + ".txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write("Szanowny Panie " + o.toString() + " Zostałeś eksmitowany z pomieszczenia:\n" +
                ID_pomieszczenia + ". Incydent ten został na stałe wpisane do pańskich akt.");
        writer.close();
        o.getZawiadomienia_o_eksmisjach().add(file);
    }

    @Override
    public void wyslijPowiadomienie_o_koncuWynajmu(Osoba o) throws IOException {
        String nazwaPisma = ID_pomieszczenia + "IDP_Pismo" + o.getPesel() + "_" + (o.getZawiadomienia_o_zadluzeniu().size() + 1);
        File file = new File(nazwaPisma + ".txt");
        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        String komunikat = "Szanowny Panie " + o.toString() + ", Pański wynajem zakończył się dnia: " + dataKoncaWynajmu.toString() +
                "\n" + "ID pomieszczenia to: " + ID_pomieszczenia + ". Prosimy o anulowanie najmu lub jego przedłużenie." + "\n" + "Pozdrawiam, " + "\n" + "Wlasciciel";
        writer.write(komunikat);
        writer.close();
        o.getZawiadomienia_o_zadluzeniu().add(file);
        System.out.println("Panie " + o.getImie() + " " + o.getNazwisko() + ", wysłano Panu zawiadomienie o nieuregulowanej należności za wynajem." +
                " Prosmimy o uregulowania należności, w przeciwnym razie zostanie Pan eksmitowany.\n"+
                "Pozdrawiam serdecznie, właściciel.");
    }

    public void sprzedajPojazd() {
    }

    public boolean czyMieszkanie() {
        return getClass().getSimpleName().equals("Mieszkanie") ? true : false;
    }

    public boolean czyMiejsceParkingowe() {
        return getClass().getSimpleName().equals("MiejsceParkingowe") ? true : false;
    }

    public boolean czyNieWynajete() {
        return status.equals(EStatusPomieszczenia.NIEWYNAJTE) ? true : false;
    }

    public boolean czyWynajete() {
        return status.equals(EStatusPomieszczenia.WYNAJETE) ? true : false;
    }

    public double getObjetosc() {
        return objetosc;
    }

    public double getPozostalaObjetosc() {
        return pozostalaObjetosc;
    }

    public void setPozostalaObjetosc(double pozostalaObjetosc) {
        this.pozostalaObjetosc = pozostalaObjetosc;
    }

    public EStatusPomieszczenia getStatus() {
        return status;
    }

    public void setStatus(EStatusPomieszczenia status) {
        this.status = status;
    }

    public double getPowierzchnia() {
        return powierzchnia;
    }

    public int getID_pomieszczenia() {
        return ID_pomieszczenia;
    }

    public void setNajemca(Osoba o) {
        najemca = o;
    }

    public Osoba getNajemca() {
        return najemca;
    }

    public void setDataKoncaWynajmu(LocalDate dataKoncaWynajmu) {
        this.dataKoncaWynajmu = dataKoncaWynajmu;
    }

    public LocalDate getDataKoncaWynajmu() {
        return dataKoncaWynajmu;
    }

    public void setDataRozpWynajmu(LocalDate dataRozpWynajmu) {
        this.dataRozpWynajmu = dataRozpWynajmu;
    }

    public ArrayList<Przedmiot> getPrzedmioty_w_pomieszczeniu() {
        return przedmioty_w_pomieszczeniu;
    }

    public void setDataEksmisji(LocalDate dataEksmisji) {
        this.dataEksmisji = dataEksmisji;
    }

    public static ArrayList<Pomieszczenie> getWszystkiePomieszczenia() {
        return wszystkiePomieszczenia;
    }

    public void setPismoWyslane(boolean pismoWyslane) {
        this.pismoWyslane = pismoWyslane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pomieszczenie that = (Pomieszczenie) o;
        return ID_pomieszczenia == that.ID_pomieszczenia &&
                Double.compare(that.objetosc, objetosc) == 0 &&
                Double.compare(that.standardowaWysokoscPomieszczenia, standardowaWysokoscPomieszczenia) == 0 &&
                Double.compare(that.powierzchnia, powierzchnia) == 0 &&
                Double.compare(that.pozostalaObjetosc, pozostalaObjetosc) == 0 &&
                pismoWyslane == that.pismoWyslane &&
                Objects.equals(dataRozpWynajmu, that.dataRozpWynajmu) &&
                Objects.equals(dataKoncaWynajmu, that.dataKoncaWynajmu) &&
                Objects.equals(dataEksmisji, that.dataEksmisji) &&
                Objects.equals(najemca, that.najemca) &&
                status == that.status &&
                Objects.equals(przedmioty_w_pomieszczeniu, that.przedmioty_w_pomieszczeniu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID_pomieszczenia, objetosc, standardowaWysokoscPomieszczenia, powierzchnia, pozostalaObjetosc, pismoWyslane, dataRozpWynajmu, dataKoncaWynajmu, dataEksmisji, najemca, status, przedmioty_w_pomieszczeniu);
    }

    public Blok getBlok() {
        return blok;
    }

    public void setBlok(Blok blok) {
        this.blok = blok;
    }
}

