package Projekt_GUI;


import Projekt_GUI.Exceptions.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;


abstract public class Aplikacja {
    private static Scanner scanner = new Scanner(System.in);
    private static Mieszkanie mieszkanie;
    private static MiejsceParkingowe miejsceParkingowe;
    private static Pomieszczenie pomieszczenie;
    private static Osoba osoba;
    private static Osoba lokator;
    private static Przedmiot przedmiot;
    private static Pojazd pojazd;
    public static int WYBOR = 1;
    private static boolean START_PROGRAMU = true;
    private static ETypSilnika typSilnika = null;
    private static ETypPojazdu typPojazdu = null;
    private static Blok blok = null;

    public static void wybierzKimJestes() throws ExceptionNieWłaściweID {
        if (WYBOR == 1) osoba = null;
        while (osoba == null) {
            try {
                System.out.println("Zaloguj się, aby uzyskać dostęp do aplikacji");
                Osoba.getListaWszystkichOsob().forEach(System.out::println);
                System.out.println("Wybierz ID osoby, którą chcesz \"być\"");
                int wybor = scanner.nextInt();
                Osoba.getListaWszystkichOsob().forEach((x) -> {
                    if (x.getID_osoby() == wybor) osoba = x;
                });
                if (osoba == null)
                    throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID osoby, spróbuj ponownie.");
                if (osoba.getZawiadomienia_o_eksmisjach().size() != 0)
                    System.err.println("Zostałeś już eksmitowany z conamniej jednego mieszkania, " +
                            "gdy zostaniesz eksmitowany z trzech - stracisz możliwość wynajmowania nowych mieszkań");
                else System.out.println("Pracujesz w systmie jako osoba: " + osoba);
                WYBOR = 99;
            } catch (InputMismatchException e) {
                System.err.println("Proszę o wpisanie liczby");
                scanner.next();
            }
        }
    }

    public static void wyswietlWszystkieWolnePomieszczenia() {
        Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
            if (x.czyNieWynajete()) System.out.println(x);
        });
    }

    public static void wynajmijMieszkanie() throws ExceptionNieWłaściweID, ExceptionNiewłaściwyOkresWynajmu,
            ExceptionLimitWynajetychPomieszczenOsiagniety, ExceptionProblematycznyNajemca, ExceptionNieMaŻadnychWolnychMieszkań {
        try {
            mieszkanie = null;
            StringBuilder mieszkania_do_wynajecia = new StringBuilder();
            if (!osoba.czyProblematycznyNajemca()) {
                if (!osoba.czyOsobaMaWiecejPomieszczenNizLimit()) {
                    Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
                        if (x.czyNieWynajete() && x.czyMieszkanie())
                            mieszkania_do_wynajecia.append(x.toString() + "\n");
                    });
                    if (mieszkania_do_wynajecia.length() == 0)
                        throw new ExceptionNieMaŻadnychWolnychMieszkań("Nie ma żadnych wolnych mieszkań do wynajęcia");
                    else System.out.println(mieszkania_do_wynajecia);
                    System.out.println("Wybierz ID mieszkania. które chcesz wynająć");
                    int ID_mieszkania = scanner.nextInt();
                    Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
                        if (x.czyNieWynajete() && x.czyMieszkanie() && x.getID_pomieszczenia() == ID_mieszkania) {
                            mieszkanie = (Mieszkanie) x;
                        }
                    });
                    if (mieszkanie == null) {
                        throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID mieszkania, spróbuj ponownie.");
                    }
                    EDlugoscWynajmu dlugoscWynajmu = dlugoscWynajmu();
                    osoba.wynajmijPomieszczenie(mieszkanie, dlugoscWynajmu);
                    System.out.println("Sukces");
                } else
                    throw new ExceptionLimitWynajetychPomieszczenOsiagniety("Limit wynajętych pomieszczeń został osiągniety, nie możesz wynająć więcej");
            } else throw new ExceptionProblematycznyNajemca(osoba.komunikat_o_problematycznym_najemcy());
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wynajmijMiejsceParkingowe() throws ExceptionOsobaNieJestNajemca, ExceptionLimitWynajetychPomieszczenOsiagniety,
            ExceptionNieWłaściweID, ExceptionNiewłaściwyOkresWynajmu, ExceptionProblematycznyNajemca, ExceptionNieMaZadnychWolnychMiejscParkingowych {
        try {
            miejsceParkingowe = null;
            StringBuilder miejscaParkingowe_do_wynajecia = new StringBuilder();
            if (!osoba.czyProblematycznyNajemca()) {
                if (osoba.isCzyNajemcaMieszkania()) {
                    if (!osoba.czyOsobaMaWiecejPomieszczenNizLimit()) {
                        Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
                            if (x.czyNieWynajete() && x.czyMiejsceParkingowe()) {
                                miejscaParkingowe_do_wynajecia.append(x.toString() + "\n");
                            }
                        });
                        if (miejscaParkingowe_do_wynajecia.length() == 0)
                            throw new ExceptionNieMaZadnychWolnychMiejscParkingowych("Nie ma żadnych wolnych miejsc parkingowych do wynajęcia.");
                        else System.out.println(miejscaParkingowe_do_wynajecia);
                        System.out.println("Podaj ID miejsca parkingowego, które chcesz wynająć");
                        int ID_miejscaParkingowego = scanner.nextInt();
                        Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
                            if (x.czyNieWynajete() && x.czyMiejsceParkingowe() && x.getID_pomieszczenia() == ID_miejscaParkingowego)
                                miejsceParkingowe = (MiejsceParkingowe) x;
                        });
                        if (miejsceParkingowe == null)
                            throw new ExceptionNieWłaściweID("Podałeś niewłaściwe ID miejsca parkingowego, spróbuj ponownie.");
                        EDlugoscWynajmu dlugoscWynajmu = dlugoscWynajmu();
                        osoba.wynajmijPomieszczenie(miejsceParkingowe, dlugoscWynajmu);
                        System.out.println("Sukces");
                    } else
                        throw new ExceptionLimitWynajetychPomieszczenOsiagniety("Limit wynajętych pomieszczeń został osiągniety, nie możesz wynająć więcej");
                } else
                    throw new ExceptionOsobaNieJestNajemca("Najpierw wynajmij mieszkanie, a potem dopiero miejsce parkingowe.");
            } else throw new ExceptionProblematycznyNajemca(osoba.komunikat_o_problematycznym_najemcy());
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wypiszSwojeDane_i_swojePomieszczenia() {
        System.out.println("Pracujesz w systemie jako osoba: " + osoba + "\n");
        System.out.println("Twoje pomieszczenia to: " + "\n");
        Collections.sort(osoba.getWynajetePomieszczenia(), new ComparatorPomieszczenia());
        osoba.getWynajetePomieszczenia().forEach(System.out::println);
        System.out.println("Ilosc wynajętych pomieszczeń: " + osoba.getWynajetePomieszczenia().size() + "\n");
    }

    public static void anulujWynajemPomieszczenia() throws ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychPomieszczeń {
        try {
            pomieszczenie = null;
            sprawdzCzyOsobaMaWynajetePomieszczenia_i_je_Wyswietl();
            wybierzIDwynajegoPomieszczenia();
            osoba.anulujNajemPomieszczenia(pomieszczenie);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void przedluzWynajem() throws ExceptionNiewłaściwyOkresWynajmu, ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychPomieszczeń {
        try {
            pomieszczenie = null;
            sprawdzCzyOsobaMaWynajetePomieszczenia_i_je_Wyswietl();
            wybierzIDwynajegoPomieszczenia();
            EDlugoscWynajmu dlugoscWynajmu = dlugoscWynajmu();
            osoba.przedluzNajem(pomieszczenie, dlugoscWynajmu);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void dodajLokatora() throws ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychMieszkan {
        try {
            mieszkanie = null;
            lokator = null;
            wybierzLokatora();
            sprawdzCzyOsobaMaWynajeteMieszkania_i_je_Wyswietl();
            wybierzIDwynajetegoMieszkania();
            if (mieszkanie == null)
                throw new ExceptionNieWłaściweID("Wprowadziłeś niewłaściwe ID mieszkania, spróbuj ponownie");
            osoba.dodajMieszkanca(mieszkanie, lokator);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void usunLokatora() throws ExceptionUsuwanieNajemcy, ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychMieszkan {
        try {
            mieszkanie = null;
            lokator = null;
            sprawdzCzyOsobaMaWynajeteMieszkania_i_je_Wyswietl();
            wybierzIDwynajetegoMieszkania();
            System.out.println("Wybierz ID lokatora, którego chcesz usunąć.");
            mieszkanie.getMieszkancy().forEach(System.out::println);
            int ID_lokatora = scanner.nextInt();
            if (ID_lokatora == osoba.getID_osoby())
                throw new ExceptionUsuwanieNajemcy("Nie mozesz usunąć samego siebie z mieszkania.");
            mieszkanie.getMieszkancy().forEach((x) -> {
                if (x.getID_osoby() == ID_lokatora) lokator = x;
            });
            if (lokator == null)
                throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID lokatora, spróbuj ponownie.");
            osoba.usunMieszkanca(mieszkanie, lokator);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wyswietlLokatorow() throws ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychMieszkan {
        mieszkanie = null;
        sprawdzCzyOsobaMaWynajeteMieszkania_i_je_Wyswietl();
        wybierzIDwynajetegoMieszkania();
        mieszkanie.getMieszkancy().forEach(System.out::println);
    }

    public static void dodajPrzedmiot() throws ExceptionNieWłaściweID, ExceptionTooManyThings, ExceptionNiePosiadaszWynajetychPomieszczeń {
        try {
            pomieszczenie = null;
            przedmiot = null;
            sprawdzCzyOsobaMaWynajetePomieszczenia_i_je_Wyswietl();
            wybierzIDwynajegoPomieszczenia();
            Przedmiot.wyswietlWszystkiePrzedmioty();
            System.out.println("Wybierz ID przedmiotu, który chcesz włożyc.");
            int ID_przedmiotu = scanner.nextInt();
            Przedmiot.getListaWszystkichPrzedmiotow().forEach((x) -> {
                if (x.getID_przedmiotu() == ID_przedmiotu && !pomieszczenie.getPrzedmioty_w_pomieszczeniu().contains(x))
                    przedmiot = x;// nie można mieć wielu takich samych przedmiotów w jednym pomieszczeniu

            });
            if (przedmiot == null)
                throw new ExceptionNieWłaściweID("Wybrałes niewłaściwe ID przedmiotu, spróbuj ponownie");
            osoba.wlozPrzedmiot(przedmiot, pomieszczenie);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void usunPrzedmiot() throws ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychPomieszczeń, ExceptionBrakPrzedmiotów_w_Pomieszczeniu {
        try {
            pomieszczenie = null;
            przedmiot = null;
            sprawdzCzyOsobaMaWynajetePomieszczenia_i_je_Wyswietl();
            wybierzIDwynajegoPomieszczenia();
            if (pomieszczenie.getPrzedmioty_w_pomieszczeniu().size() == 0)
                throw new ExceptionBrakPrzedmiotów_w_Pomieszczeniu("Brak przedmiotów w pomieszczeniu");
            else pomieszczenie.getPrzedmioty_w_pomieszczeniu().forEach(System.out::println);
            System.out.println("Wybierz ID przedmiotu, który chcesz wyjąć.");
            int ID_przedmiotu = scanner.nextInt();
            pomieszczenie.getPrzedmioty_w_pomieszczeniu().forEach((x) -> {
                if (x.getID_przedmiotu() == ID_przedmiotu) przedmiot = x;
            });
            if (przedmiot == null)
                throw new ExceptionNieWłaściweID("Wybrałes niewłaściwe ID przedmiotu, spróbuj ponownie");
            osoba.wyjmijPrzedmiot(przedmiot, pomieszczenie);
            System.out.println("Suckes");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wyswietlZawartoscPomieszczenia() throws ExceptionNieWłaściweID, ExceptionNiePosiadaszWynajetychPomieszczeń {
        try {
            pomieszczenie = null;
            sprawdzCzyOsobaMaWynajetePomieszczenia_i_je_Wyswietl();
            wybierzIDwynajegoPomieszczenia();
            Collections.sort(pomieszczenie.getPrzedmioty_w_pomieszczeniu(), new ComparatorPrzedmioty());
            pomieszczenie.getPrzedmioty_w_pomieszczeniu().forEach(System.out::println);
            System.out.println();
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void zaparkujPojazd() throws ExceptionNieWłaściweID, ExceptionTooManyThings, ExceptionNiePosiadaszWynajetychMiejscParkingowych,
            ExceptionBrakPojazdowDoZaparkowania, ExceptionMiejsceParkingoweJuzZajetePrzezPojazd {
        try {
            pojazd = null;
            miejsceParkingowe = null;
            StringBuilder listaPojazdow = new StringBuilder();
            sprawdzCzyOsobaMaWynajeteMiejscaParkingowe_i_je_wyswietl();
            wybierzIDwynajetegoMiejscaParkingowego();
            Pojazd.getListaPojazdow().forEach((x) -> {
                if (x.isCzyZaparkowany() == false) listaPojazdow.append(x.toString() + "\n");
            });
            if (listaPojazdow.length() == 0)
                throw new ExceptionBrakPojazdowDoZaparkowania("Brak pojazdu do zaparkowania");
            else System.out.println(listaPojazdow);
            System.out.println("Wprowadź ID pojazdu, który chcesz zaparkować");
            int ID_pojazdu = scanner.nextInt();
            Pojazd.getListaPojazdow().forEach((x) -> {
                if (x.isCzyZaparkowany() == false && x.getID_pojazdu() == ID_pojazdu) pojazd = x;
            });
            if (pojazd == null)
                throw new ExceptionNieWłaściweID("Wprowadziłeś niewłaściwe ID pojazdu, spróbuj ponownie");
            osoba.zaparkujPojazd(pojazd, miejsceParkingowe);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wyparkujPojazd() throws ExceptionNieWłaściweID, ExceptionNieMaPojazdu, ExceptionNiePosiadaszWynajetychMiejscParkingowych {
        try {
            miejsceParkingowe = null;
            sprawdzCzyOsobaMaWynajeteMiejscaParkingowe_i_je_wyswietl();
            wybierzIDwynajetegoMiejscaParkingowego();
            if (miejsceParkingowe.czyJestPojazd() == false)
                throw new ExceptionNieMaPojazdu("Na tym miejscu Parkingowym nie stoi żaden pojazd");
            osoba.wyparkujPojazd(miejsceParkingowe);
            System.out.println("Sukces");
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wyswietlSwojePojazdy() {
        Collections.sort(osoba.getPojazdyOsoby(), new ComparatorPojazdy());
        osoba.wypiszPojazdyOsoby();
    }

    public static void wyswietlSwojePowiadomieniaO_zadluzeniach() {
        wyswietlPlikiTekstowe(osoba.getZawiadomienia_o_zadluzeniu());
    }

    public static void wyswietlSwojePowiadomienia_o_eksmisjach() {
        wyswietlPlikiTekstowe(osoba.getZawiadomienia_o_eksmisjach());
        System.out.println("Zostales eksmitowany z: " + osoba.getZawiadomienia_o_eksmisjach().size() + ".");
        if (osoba.getZawiadomienia_o_eksmisjach().size() > 2)
            System.err.println("Nie możesz wynajmować pomieszczeń, bo jesteś niewyplacalny");
        else
            System.err.println("Jeśli będziesz mieć więcej niż 2 eksmisje to nie będziesz mógł wynajmować pomieszczeń");
    }

    public static void stworzNowyObiekt() throws ExceptionNiewlaściwyWybór, ExceptionNieWłaściweID, ExceptionLimitMiejscParkingowych,
            ExceptionLimitMieszkan_w_Bloku, ExceptionPomieszczenieJużMaSwojBlok {
        try {
            System.out.println("Co chcesz stworzyć?\n" +
                    "1 - nową osobę\n" +
                    "2 - nowy przedmiot\n" +
                    "3 - nowy pojazd\n" +
                    "4 - nowe pomieszczenie\n");
            int wybor = scanner.nextInt();
            if (wybor != 1 && wybor != 2 && wybor != 3 && wybor != 4)
                throw new ExceptionNiewlaściwyWybór("Wybierz 1, 2, 3 lub 4");
            switch (wybor) {
                case 1:
                    try {
                        System.out.println("Podaj kolejno: imię, nazwisko, pesel, adres oraz rok, miesiąc i dzień urodzenia osoby.");
                        String imie = scanner.next();
                        String nazwisko = scanner.next();
                        String pesel = scanner.next();
                        String adres = scanner.next();
                        int rok = scanner.nextInt();
                        int miesiac = scanner.nextInt();
                        int dzien = scanner.nextInt();
                        Osoba.stworzOsobe(imie, nazwisko, pesel, adres, LocalDate.of(rok, miesiac, dzien));
                        System.out.println("Suckes");
                        break;
                    }catch (DateTimeException e){
                        System.err.println("Proszę uważnie wpisać prawidłową datę.");
                        break;
                    }
                case 2:
                    System.out.println("Podaj kolejno: dlugosc, szerokosc, wysokosc oraz nazwę przedmiotu.");
                    double dlugosc = scanner.nextDouble();
                    double szerokosc = scanner.nextDouble();
                    double wysokosc = scanner.nextDouble();
                    String nazwa = scanner.next();
                    Przedmiot.stworzPrzedmiot(dlugosc, szerokosc, wysokosc, nazwa);
                    System.out.println("Sukces");
                    break;
                case 3:
                    System.out.println("Jakiego typu pojazd chcesz stworzyć?\n" +
                            "1 - wózek inwalidzki\n" +
                            "2 - samochód\n" +
                            "3 - motorówka\n" +
                            "4 - motocykl\n" +
                            "5 - jacht\n");
                    int wyborPojazdu = scanner.nextInt();
                    if (wyborPojazdu != 1 && wyborPojazdu != 2 && wyborPojazdu != 3 && wyborPojazdu != 4 && wyborPojazdu != 5)
                        throw new ExceptionNiewlaściwyWybór("Wybierz 1, 2, 3, 4 lub 5");
                    switch (wyborPojazdu) {
                        //// public Jacht(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, double cena)
                        ////public Motocykl(String name, double objetosc, double pojemnoscSilnika, ETypPojazdu typPojazdu, ETypSilnika typSilnika, boolean czyMaWozekBoczny)
                        case 1:
                            System.out.println("Podaj kolejno: nazwę, objętość, pojemność silnika oraz materiał siedziska wózka inwalidzkiego.");
                            String anazwa = scanner.next();
                            double objetosc = scanner.nextDouble();
                            double pojemnosc_silnika = scanner.nextDouble();
                            String material_siedziska = scanner.next();
                            wybierzTypSilnika();
                            wybierzTypPojazdu();
                            Wózek_inwalidzki.stworzWózekInwalidzki(anazwa, objetosc, pojemnosc_silnika, typPojazdu, typSilnika, material_siedziska);
                            System.out.println("Sukces");
                            break;
                        case 2:
                            System.out.println("Podaj kolejno: nazwę, objętość, pojemność silnika i kolor samochodu.");
                            String bnazwa = scanner.next();
                            double aobjetosc = scanner.nextDouble();
                            double apojemosc_silnika = scanner.nextDouble();
                            String kolor = scanner.next();
                            wybierzTypPojazdu();
                            wybierzTypSilnika();
                            Samochod.stworzSamochod(bnazwa, aobjetosc, apojemosc_silnika, typPojazdu, typSilnika, kolor);
                            System.out.println("Sukces");
                            break;
                        case 3:
                            System.out.println("Podaj kolejno: nazwę, objętość, pojemność silnika, ilość dozwolonych osób na pokładzie motorówki");
                            String cnazwa = scanner.next();
                            double bobjetosc = scanner.nextDouble();
                            double bpojemnosc_silnika = scanner.nextDouble();
                            int liczba_dozwolonych_osob = scanner.nextInt();
                            wybierzTypSilnika();
                            wybierzTypPojazdu();
                            Motorowka.stworzMotorowke(cnazwa, bobjetosc, bpojemnosc_silnika, typPojazdu, typSilnika, liczba_dozwolonych_osob);
                            System.out.println("Sukces");
                            break;
                        case 4:
                            System.out.println("Podaj kolejno: nazwę objętość, pojemnosc silnika oraz informację czy ma wózek boczny (true/false)");
                            String dnazwa = scanner.next();
                            double cobjetosc = scanner.nextDouble();
                            double cpojemnosc_silnika = scanner.nextDouble();
                            boolean czy_ma_wozek = scanner.hasNextBoolean();
                            wybierzTypSilnika();
                            wybierzTypPojazdu();
                            Motocykl.stworzMotocykl(dnazwa, cobjetosc, cpojemnosc_silnika, typPojazdu, typSilnika, czy_ma_wozek);
                            System.out.println("Sukces");
                            break;
                        case 5:
                            System.out.println("Podaj kolejno: nazwę objętość, pojemnosc silnika oraz informację czy ma wózek boczny (true/false)");
                            String enazwa = scanner.next();
                            double dobjetosc = scanner.nextDouble();
                            double dpojemnosc_silnika = scanner.nextDouble();
                            double cena = scanner.nextInt();
                            wybierzTypPojazdu();
                            wybierzTypSilnika();
                            Jacht.stworzJacht(enazwa, dobjetosc, dpojemnosc_silnika, typPojazdu, typSilnika, cena);
                            System.out.println("Suckes");
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Jeśli chcesz dodać nowe mieszaknie, wybierz 1, jeśli miejsce parkingowe, wybierz 2.");
                    int wybor_pomieszczenia = scanner.nextInt();
                    if (wybor_pomieszczenia != 1 && wybor_pomieszczenia != 2)
                        throw new ExceptionNiewlaściwyWybór("Wybierz 1 lub 2");
                    switch (wybor_pomieszczenia) {
                        case 1:
                            System.out.println("Podaj powierzchnia mieszkania.");
                            double pow = scanner.nextDouble();
                            pomieszczenie = Mieszkanie.stworzMieszkanie(pow);
                            break;
                        case 2:
                            System.out.println("Podaj powierzchnie miejsca parkingowego.");
                            double apow = scanner.nextDouble();
                            pomieszczenie = MiejsceParkingowe.stworzMiejsceParkingowe(apow);
                            break;
                    }
                    blok = null;
                    Blok.listaBlokow.forEach(System.out::println);
                    System.out.println("Wybierz ID bloku, do którego chcesz dodać stworzone pomieszczenie");
                    int wybor_bloku = scanner.nextInt();
                    Blok.listaBlokow.forEach((x) -> {
                        if (wybor_bloku == x.getID_bloku()) blok = x;
                    });
                    if (blok == null) throw new ExceptionNieWłaściweID("Wprowadzono niewłaściwe ID bloku");
                    else blok.dodajPomieszczenieDoBloku(pomieszczenie);
                    System.out.println("Sukces");
                    break;
            }
        } catch (InputMismatchException e) {
            System.err.println("Proszę wpisać liczbę.");
            scanner.next();
        }
    }

    public static void wyswietl_najemce_pomieszczenia_o_podanym_ID() throws ExceptionNieWłaściweID, ExceptionBrakNajemcy {
        try {
            pomieszczenie = null;
            Pomieszczenie.getWszystkiePomieszczenia().forEach(System.out::println);
            System.out.println("Wybierz ID pomieszczenia, którego najemce chcesz zobaczyć.");
            int wybor = scanner.nextInt();
            Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
                if (x.getID_pomieszczenia() == wybor) pomieszczenie = x;
            });
            if (pomieszczenie == null) throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID pomieszczenia.");
            else if (pomieszczenie.getNajemca() == null)
                throw new ExceptionBrakNajemcy("To mieszkanie nie jest przez nikogo wynajęte.");
            else System.out.println(pomieszczenie + "\n" + "Najemca: " + pomieszczenie.getNajemca());
        } catch (InputMismatchException e) {
            System.err.println("Proszę wybrać liczbę.");
            scanner.next();
        }

    }

    public static void menu() {
        System.out.println(
                "Wybierz 0, aby opuścić program.\n" +
                        "Wybierz 1, aby wybrać użytkownika, którym chcesz zarządzać.\n" +
                        "Wybierz 2, aby wyświetlić wszystkie wolne pomieszczenia.\n" +
                        "Wybierz 3, aby wynająć mieszkanie.\n" +
                        "Wybierz 4, aby wynająć miejsce parkingowe.\n" +
                        "Wybierz 5, aby wypisać swoje dane oraz swoje wynajmowane pomieszczenia.\n" +
                        "Wybierz 6, aby anulować wynajem jednego ze swoich pomieszczeń.\n" +
                        "Wybierz 7, aby przedłużyć wynajem jednego ze swoich pomieszczeń\n" +
                        "Wybierz 8, aby dodać lokatora do mieszkania, które wynajmujesz.\n" +
                        "Wybierz 9, aby usunąć lokatora z mieszkania, które wynajmujesz.\n" +
                        "Wybierz 10, aby wyświetlić lokatorów mieszkania, które wynajmujesz.\n" +
                        "Wybierz 11, aby dodać przedmiot do pomieszczenia, które wynajmujesz.\n" +
                        "Wybierz 12, aby usunąć przedmiot z pomieszczenia, które wynajmujesz.\n" +
                        "Wybierz 13, aby wyświetlić przedmioty, które znajdują się w pomieszczeniu, które wynajmujesz.\n" +
                        "Wybierz 14, aby zaparkować pojazd.\n" +
                        "Wybierz 15, aby wyparkować pojazd.\n" +
                        "Wybierz 16, aby zgrać dane o mieszkańcach do pliku.\n" +
                        "Wybierz 17, aby wyświetlić swoje zawiadomienia o zadłużeniach.\n" +
                        "Wybierz 18, aby wyświetlić swoje zawiadomienia o eksmisjach.\n" +
                        "Wybierz 19, aby wywietlic date.\n" +
                        "Wybierz 20, aby wyswietlic wszystkie swoje pojazdy.\n" +
                        "Wybierz 21, aby wyswietlic wszystkie pojazdy.\n" +
                        "Wybierz 22, aby dodać do bazy danych nowy obiekt.\n" +
                        "Wybierz 23, aby wyśweitlic najemcę wybranego pomieszczenia.\n" +
                        "Wybierz 24, aby wyświetlic MENU.\n");

    }

    private static EDlugoscWynajmu dlugoscWynajmu() throws ExceptionNiewłaściwyOkresWynajmu {
        System.out.println("\n" + "Podaj czas na jaki chcesz wynająć mieszkanie." + "\n" + "1-kwartał, 2-pół roku, 3-rok" + "\n");
        int okres_wynajmu = scanner.nextInt();
        if (okres_wynajmu != 1 && okres_wynajmu != 2 && okres_wynajmu != 3)
            throw new ExceptionNiewłaściwyOkresWynajmu("Wybrałeś niedostępny okres wynajmu, spróbuj ponownie.");
        EDlugoscWynajmu dlugoscWynajmu = EDlugoscWynajmu.KWARTAL;
        switch (okres_wynajmu) {
            case 1:
                dlugoscWynajmu = EDlugoscWynajmu.KWARTAL;
                break;
            case 2:
                dlugoscWynajmu = EDlugoscWynajmu.POL_ROKU;
                break;
            case 3:
                dlugoscWynajmu = EDlugoscWynajmu.ROK;
                break;
        }
        return dlugoscWynajmu;
    }

    private static void wybierzIDwynajetegoMiejscaParkingowego() throws ExceptionNieWłaściweID {
        System.out.println("Wybierz ID miejsca parkingowego.");
        int ID_miejscaParkingowego = scanner.nextInt();
        osoba.getWynajetePomieszczenia().forEach((x) -> {
            if (x.czyMiejsceParkingowe() && ID_miejscaParkingowego == x.getID_pomieszczenia()) {
                pomieszczenie = x;
                miejsceParkingowe = (MiejsceParkingowe) x;
            }
        });
        if (miejsceParkingowe == null)
            throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID miejsca parkingowego, spróbuj ponownie");
    }

    private static void wybierzIDwynajetegoMieszkania() throws ExceptionNieWłaściweID {
        System.out.println("Wybierz ID mieszkania");
        int ID_mieszkania = scanner.nextInt();
        osoba.getWynajetePomieszczenia().forEach((x) -> {
            if (x.getID_pomieszczenia() == ID_mieszkania && x.czyMieszkanie()) {
                mieszkanie = (Mieszkanie) x;
                pomieszczenie = x;
            }
        });
        if (mieszkanie == null)
            throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID mieszkania, spróbuj ponownie.");
    }

    private static void wybierzIDwynajegoPomieszczenia() throws ExceptionNieWłaściweID {
        System.out.println("Wybierz ID pomieszczenia");
        int ID_pomieszczenia = scanner.nextInt();
        osoba.getWynajetePomieszczenia().forEach((x) -> {
            if (x.getID_pomieszczenia() == ID_pomieszczenia) pomieszczenie = x;
        });
        if (pomieszczenie == null)
            throw new ExceptionNieWłaściweID("Wybrałeś niewłaściwe ID pomieszczenia, spróbuj ponownie.");
    }

    private static void sprawdzCzyOsobaMaWynajetePomieszczenia_i_je_Wyswietl() throws ExceptionNiePosiadaszWynajetychPomieszczeń {
        if (osoba.getWynajetePomieszczenia().size() == 0)
            throw new ExceptionNiePosiadaszWynajetychPomieszczeń("Nie posidadasz wynajętych pomieszczeń.");
        else {
            System.out.println("Twoje wynajete pomieszczczenia: ");
            osoba.getWynajetePomieszczenia().forEach(System.out::println);
        }
    }

    private static void sprawdzCzyOsobaMaWynajeteMieszkania_i_je_Wyswietl() throws ExceptionNiePosiadaszWynajetychMieszkan {
        StringBuilder mieszkania = new StringBuilder();
        osoba.getWynajetePomieszczenia().forEach((x) -> {
            if (x.czyMieszkanie()) mieszkania.append(x.toString() + "\n");
        });
        if (mieszkania.length() == 0)
            throw new ExceptionNiePosiadaszWynajetychMieszkan("Nie posiadasz wynajętych mieszkań");
        else System.out.println(mieszkania);
    }

    private static void sprawdzCzyOsobaMaWynajeteMiejscaParkingowe_i_je_wyswietl() throws ExceptionNiePosiadaszWynajetychMiejscParkingowych {
        StringBuilder miejscaParkingowe = new StringBuilder();
        osoba.getWynajetePomieszczenia().forEach((x) -> {
            if (x.czyMiejsceParkingowe()) miejscaParkingowe.append(x.toString() + "\n");
        });
        if (miejscaParkingowe.length() == 0)
            throw new ExceptionNiePosiadaszWynajetychMiejscParkingowych("Nie posiadasz wynajętych miejsc parkingowych");
        else System.out.println(miejscaParkingowe);
    }

    private static void wybierzLokatora() throws ExceptionNieWłaściweID {
        System.out.println("WYbierz osobę, którą chcesz dodać do jednego ze swoich wynajętych mieszkań");
        Osoba.getListaWszystkichOsob().forEach(System.out::println);
        int ID_lokatora = scanner.nextInt();
        Osoba.getListaWszystkichOsob().forEach((x) -> {
            if (x.getID_osoby() == ID_lokatora) lokator = x;
        });
        if (lokator == null) throw new ExceptionNieWłaściweID("Wprowadziłeś niewłaściwe ID lokatora.");
    }

    public static Osoba getOsoba() {
        return osoba;
    }

    private static void wyswietlPlikiTekstowe(ArrayList<File> lista) {
        if (lista.size() == 0) System.err.println("Brak treści do wyświetlenia.");
        else {
            lista.forEach((x) -> {
                try (
                        var reader = new FileReader(x.getName());
                ) {
                    int i;
                    StringBuilder trescPliku = new StringBuilder();
                    while ((i = reader.read()) != -1) {
                        trescPliku.append((char) i);
                    }
                    System.out.println(trescPliku);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void wybierzTypSilnika() throws ExceptionNiewlaściwyWybór {
        typSilnika = null;
        System.out.println("Wybierz typ silnika jaki posiada twój pojazd:\n" +
                "1 - spalinowy\n" +
                "2 - elektryczny\n" +
                "3 - hybrydowy\n" +
                "4 - wodorowy\n");
        int wybor = scanner.nextInt();
        if (wybor != 1 && wybor != 2 && wybor != 3 && wybor != 4)
            throw new ExceptionNiewlaściwyWybór("Wybierz 1, 2, 3 lub 4");
        switch (wybor) {
            case 1:
                typSilnika = ETypSilnika.Spalinowy;
                break;
            case 2:
                typSilnika = ETypSilnika.Elektryczny;
                break;
            case 3:
                typSilnika = ETypSilnika.Hybrydowy;
                break;
            case 4:
                typSilnika = ETypSilnika.Wodorowy;
                break;
        }
    }

    private static void wybierzTypPojazdu() throws ExceptionNiewlaściwyWybór {
        typPojazdu = null;
        System.out.println("Wybierz typ pojazdo jakim jest twój pojazd:\n" +
                "1 - lądowy\n" +
                "2 - wodny\n" +
                "3 - lądowo-wodny\n");
        int wybor = scanner.nextInt();
        if (wybor != 1 && wybor != 2 && wybor != 3) throw new ExceptionNiewlaściwyWybór("Wybierz 1, 2 lub 3.");
        switch (wybor) {
            case 1:
                typPojazdu = ETypPojazdu.Lądowy;
                break;
            case 2:
                typPojazdu = ETypPojazdu.Wodny;
                break;
            case 3:
                typPojazdu = ETypPojazdu.Lądowo_Wodny;
                break;
        }
    }
}