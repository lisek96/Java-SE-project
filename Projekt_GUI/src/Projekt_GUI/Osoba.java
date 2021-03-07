package Projekt_GUI;

import Projekt_GUI.Exceptions.ExceptionMiejsceParkingoweJuzZajetePrzezPojazd;
import Projekt_GUI.Exceptions.ExceptionTooManyThings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


import static Projekt_GUI.Obliczenia.sprawdzCzyWejdzie;

public class Osoba implements INajemca {

    private String imie;
    private String nazwisko;
    private String pesel;
    private String adres;
    private int ID_osoby;
    private LocalDate dataUrodzenia;
    private boolean czyNajemcaMieszkania = false;
    private final int LIMIT_MIESZKAN = 5;
    private ArrayList<Pomieszczenie> wynajetePomieszczenia = new ArrayList<>();
    private ArrayList<File> zawiadomienia_o_zadluzeniu = new ArrayList<>();
    private static int licznikOsob;
    private File zawiadomienie_o_zadluzeniu_do_usuniecia = null;
    private static ArrayList<Osoba> listaWszystkichOsob = new ArrayList<>();
    private ArrayList<File> zawiadomienia_o_eksmisjach = new ArrayList<>();
    private ArrayList<Pojazd> pojazdyOsoby = new ArrayList<>();

    Osoba(String imie, String nazwisko, String pesel, String adres, LocalDate dataUrodzenia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.adres = adres;
        this.dataUrodzenia = dataUrodzenia;
        listaWszystkichOsob.add(this);
        ID_osoby = ++licznikOsob;
    }
    public String toString() {
        return "ID: " + ID_osoby + " | " + imie + " " + nazwisko;
    }

    public void wynajmijPomieszczenie(Pomieszczenie p, EDlugoscWynajmu okresWynajmu) {
        wynajetePomieszczenia.add(p);
        p.setNajemca(this);
        p.setStatus(EStatusPomieszczenia.WYNAJETE);
        p.ustawDatyWynajmu(okresWynajmu);
        czyNajemcaMieszkania = true;
        if (p.czyMieszkanie()) {
            Mieszkanie m = (Mieszkanie) p;
            m.dodajNajemceDoMieszkancow();
        }
    }

    public void wypiszPojazdyOsoby(){
        if(pojazdyOsoby.size()==0) System.err.println("Nie posiadasz żadynch zaparkowanych pojazdów");
        else pojazdyOsoby.forEach(System.out::println);
    }

    @Override
    public void zaparkujPojazd(Pojazd p, MiejsceParkingowe mp) throws ExceptionTooManyThings, ExceptionMiejsceParkingoweJuzZajetePrzezPojazd {
        if(mp.czyJestPojazd()==true) throw new ExceptionMiejsceParkingoweJuzZajetePrzezPojazd("To miejsce jest już zajęte przez inny pojazd");
        if (sprawdzCzyWejdzie.metoda(p.getObjetosc(), mp.getPozostalaObjetosc())) {
            p.setWlasciciel(this);
            p.setMiejsce_postoju(mp);
            mp.setPojazd(p);
            mp.setPozostalaObjetosc(mp.getPozostalaObjetosc() - p.getObjetosc());
            mp.setJestPojazd(true);
            p.setCzyZaparkowany(true);
            pojazdyOsoby.add(p);
        } else throw new ExceptionTooManyThings("Za mało miejsca, żeby zaparkować pojazd, najpierw coś wypakuj.");
    }

    @Override
    public void wyparkujPojazd(MiejsceParkingowe mp) {
        mp.getPojazd().setMiejsce_postoju(null);
        mp.getPojazd().setWlasciciel(null);
        pojazdyOsoby.remove(mp.getPojazd());
        mp.getPojazd().setCzyZaparkowany(false);
        mp.setPozostalaObjetosc(mp.getPozostalaObjetosc() + mp.getPojazd().getObjetosc());
        mp.setJestPojazd(false);
        mp.setPojazd(null);
    }

    @Override
    public void wyjmijPrzedmiot(Przedmiot przedmiot, Pomieszczenie pomieszczenie) {
        pomieszczenie.getPrzedmioty_w_pomieszczeniu().remove(przedmiot);
        pomieszczenie.setPozostalaObjetosc(pomieszczenie.getPozostalaObjetosc() + przedmiot.getObjetosc());
    }

    @Override
    public void wlozPrzedmiot(Przedmiot przedmiot, Pomieszczenie pomieszczenie) throws ExceptionTooManyThings {
        if (sprawdzCzyWejdzie.metoda(przedmiot.getObjetosc(), pomieszczenie.getPozostalaObjetosc())) {
            pomieszczenie.getPrzedmioty_w_pomieszczeniu().add(przedmiot);
            pomieszczenie.setPozostalaObjetosc(pomieszczenie.getPozostalaObjetosc() - przedmiot.getObjetosc());
        } else throw new ExceptionTooManyThings("Za mało miejsca na ten przedmiot - najpierw coś wyjmij.");
    }

    @Override
    public void dodajMieszkanca(Mieszkanie m, Osoba o) {
        m.getMieszkancy().add(o);
    }

    @Override
    public void usunMieszkanca(Mieszkanie m, Osoba o) {
        m.getMieszkancy().remove(o);
    }

    @Override
    public void przedluzNajem(Pomieszczenie p, EDlugoscWynajmu okresWynajmu) {
        p.ustawDatyWynajmu(okresWynajmu);
        usuńZawiadomienieOzadłużeniu(p);
    }

    @Override
    public void anulujNajemPomieszczenia(Pomieszczenie p) {
        czyNajemcaMieszkania = false;
        wynajetePomieszczenia.forEach((x) -> {
            if (x.czyMieszkanie()) czyNajemcaMieszkania = true;
        });
        wynajetePomieszczenia.remove(p);
        p.getPrzedmioty_w_pomieszczeniu().clear();
        p.setStatus(EStatusPomieszczenia.NIEWYNAJTE);
        p.setPozostalaObjetosc(p.getObjetosc());
        p.setDataKoncaWynajmu(null);
        p.setDataRozpWynajmu(null);
        p.setPismoWyslane(false);
        p.setNajemca(null);
        usuńZawiadomienieOzadłużeniu(p);
        if (p.czyMieszkanie()) {
            var m = (Mieszkanie) p;
            m.getMieszkancy().clear();
        }
        if (p.czyMiejsceParkingowe()) {
            var mp = (MiejsceParkingowe) p;
            mp.setPojazd(null);
        }
    }

    public void usuńZawiadomienieOzadłużeniu(Pomieszczenie p) {
        zawiadomienie_o_zadluzeniu_do_usuniecia = null;
        zawiadomienia_o_zadluzeniu.forEach((x) -> {
            if (x.getName().startsWith(Integer.toString(p.getID_pomieszczenia()))) {
                zawiadomienie_o_zadluzeniu_do_usuniecia = x;
            }
        });
        if (zawiadomienie_o_zadluzeniu_do_usuniecia != null) {
            zawiadomienia_o_zadluzeniu.remove(zawiadomienie_o_zadluzeniu_do_usuniecia);
            zawiadomienie_o_zadluzeniu_do_usuniecia.delete();
            System.out.println("Usunięto wpis o zadłużeniu dotyczący pomieszczenia " + "nr: "+ p.getID_pomieszczenia() + ".");
        } else System.out.println("Pomieszczenie nie było zadłużone");
    }

    public static void WypiszDane_o_OsobachDoPliku() throws IOException {
        StringBuilder tekstPliku = new StringBuilder();
        String name = "Mieszkańcy.txt";
        File file = new File(name);
        file.createNewFile();
        for (Osoba o : listaWszystkichOsob) {
            tekstPliku.append(o.toString() + "\n");
            tekstPliku.append("\nWynajęte pomieszczenia:\n");
            Collections.sort(o.getWynajetePomieszczenia(), new ComparatorPomieszczenia());
            for (Pomieszczenie p : o.getWynajetePomieszczenia()) {
                if (p.czyMieszkanie()) {
                    Mieszkanie m = (Mieszkanie) p;
                    tekstPliku.append("\n" + m.toString() + "\n");
                    tekstPliku.append("Ludzie zamieszkujący pomieszczenie:\n");
                    for (Osoba o1 : m.getMieszkancy()) tekstPliku.append(o1.toString() + "\n");
                } else if (p.czyMiejsceParkingowe()) {
                    MiejsceParkingowe mp = (MiejsceParkingowe) p;
                    tekstPliku.append(mp.toString());
                    if (mp.czyJestPojazd()) tekstPliku.append("Pojazd: " + mp.getPojazd().toString() + "\n");
                }
                tekstPliku.append("\nPrzedmioty w pomieszczeniu:\n\n");
                Collections.sort(p.getPrzedmioty_w_pomieszczeniu(), new ComparatorPrzedmioty());
                for (Przedmiot p1 : p.getPrzedmioty_w_pomieszczeniu()) {
                    tekstPliku.append(p1.toString() + "\n");
                }
            }
            tekstPliku.append("\n\n");
        }
        var writer = new FileWriter(file);
        writer.write(tekstPliku.toString());
        writer.close();
    }

    public static Osoba stworzOsobe(String imie, String nazwisko, String pesel, String adres, LocalDate dataUrodzenia){
        return new Osoba(imie, nazwisko, pesel, adres, dataUrodzenia);
    }

    public String komunikat_o_problematycznym_najemcy() {
        StringBuilder komunikat = new StringBuilder();
        komunikat.append("Nie udało się wynająć pomieszczenia. Osoba " + this.toString() + " posiadała juz najem pomieszczen:\n\n");
        int ID_1_pomieszczenia = Character.getNumericValue(zawiadomienia_o_eksmisjach.get(0).getName().charAt(0));
        int ID_2_pomieszczenia = Character.getNumericValue(zawiadomienia_o_eksmisjach.get(1).getName().charAt(0));
        int ID_3_pomieszczenia = Character.getNumericValue(zawiadomienia_o_eksmisjach.get(2).getName().charAt(0));
        Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
            if (x.getID_pomieszczenia() == ID_1_pomieszczenia || x.getID_pomieszczenia() == ID_2_pomieszczenia || x.getID_pomieszczenia() == ID_3_pomieszczenia)
                komunikat.append(x + "\n");
        });
        return komunikat.toString();
    }

    public boolean czyProblematycznyNajemca() {
        return zawiadomienia_o_eksmisjach.size() >= 3;
    }

    public boolean czyOsobaMaWiecejPomieszczenNizLimit() {
        return wynajetePomieszczenia.size() >= LIMIT_MIESZKAN;
    }

    public ArrayList<Pomieszczenie> getWynajetePomieszczenia() {
        return wynajetePomieszczenia;
    }

    public static ArrayList<Osoba> getListaWszystkichOsob() {
        return listaWszystkichOsob;
    }

    public int getID_osoby() {
        return ID_osoby;
    }

    public String getPesel() {
        return pesel;
    }

    public ArrayList<File> getZawiadomienia_o_zadluzeniu() {
        return zawiadomienia_o_zadluzeniu;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public boolean isCzyNajemcaMieszkania() {
        return czyNajemcaMieszkania;
    }

    public void setCzyNajemcaMieszkania(boolean czyNajemcaMieszkania) {
        this.czyNajemcaMieszkania = czyNajemcaMieszkania;
    }

    public ArrayList<File> getZawiadomienia_o_eksmisjach() {
        return zawiadomienia_o_eksmisjach;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Osoba osoba = (Osoba) o;
        return ID_osoby == osoba.ID_osoby &&
                czyNajemcaMieszkania == osoba.czyNajemcaMieszkania &&
                LIMIT_MIESZKAN == osoba.LIMIT_MIESZKAN &&
                Objects.equals(imie, osoba.imie) &&
                Objects.equals(nazwisko, osoba.nazwisko) &&
                Objects.equals(pesel, osoba.pesel) &&
                Objects.equals(adres, osoba.adres) &&
                Objects.equals(dataUrodzenia, osoba.dataUrodzenia) &&
                Objects.equals(wynajetePomieszczenia, osoba.wynajetePomieszczenia) &&
                Objects.equals(zawiadomienia_o_zadluzeniu, osoba.zawiadomienia_o_zadluzeniu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imie, nazwisko, pesel, adres, ID_osoby, dataUrodzenia, czyNajemcaMieszkania, LIMIT_MIESZKAN, wynajetePomieszczenia, zawiadomienia_o_zadluzeniu);
    }


    public ArrayList<Pojazd> getPojazdyOsoby() {
        return pojazdyOsoby;
    }
}


