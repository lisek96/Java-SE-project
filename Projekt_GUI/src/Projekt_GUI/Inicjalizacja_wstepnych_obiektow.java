package Projekt_GUI;

import Projekt_GUI.Exceptions.*;

import java.time.LocalDate;

public class Inicjalizacja_wstepnych_obiektow {
    public static void start() throws ExceptionTooManyThings, ExceptionLimitMiejscParkingowych, ExceptionLimitMieszkan_w_Bloku, ExceptionPomieszczenieJużMaSwojBlok, ExceptionPrzypisaneOsiedle, ExceptionMiejsceParkingoweJuzZajetePrzezPojazd {

        Developer osoba1 = new Developer("Rafał", "Wójcik", "96072804551", "Nowogrodzka 17/8", LocalDate.of(1996, 7, 28));
        Osoba osoba2 = new Osoba("Oliwia", "Cichy", "98041809113", "Nowogrodzka 17/8", LocalDate.of(1998, 7, 18));
        Osoba osoba3 = new Osoba("Angelika", "Wójcik", "90070118996", "Hoża 21/23/35", LocalDate.of(1990, 7, 01));
        Osoba osoba4 = new Osoba("Anna", "Ogrodnik", "01122808946", "Złoty Łan 15", LocalDate.of(1974, 10, 11));
        Osoba osoba5 = new Osoba("Adam", "Wojcik", "68022308453", "Złota 15", LocalDate.of(1998, 2, 23));
        Osoba osoba6 = new Osoba("Ewa", "Wójcik", "64032104351", "Pomorska 44/35", LocalDate.of(1964, 3, 21));
        Osoba osoba7 = new Osoba("Robert", "Lewandowski", "82111809113", "Mokotowska 8", LocalDate.of(1982, 11, 18));
        Osoba osoba8 = new Osoba("Olaf", "Cichy", "15040301000", "Nowogrodzka 17/8", LocalDate.of(2015, 03, 01));
        Osoba osoba9 = new Osoba("Wojciech", "Ogrodnik", "01122808946", "Złoty Łan 15", LocalDate.of(1974, 10, 11));
        Osoba osoba10 = new Osoba("Janusz", "Randomski", "50022308453", "Jałowcowa 21/6", LocalDate.of(1950, 2, 23));


        Osiedle osiedle = new Osiedle("Śródmieście");

        osoba1.dodajOsiedle(osiedle);
        Blok blok = new Blok();
        osiedle.dodajBlok(blok);

        Mieszkanie m1 = new Mieszkanie(200);
        Mieszkanie m2 = new Mieszkanie(400);
        Mieszkanie m3 = new Mieszkanie(100);
        Mieszkanie m4 = new Mieszkanie(600);
        Mieszkanie m5 = new Mieszkanie(800);

        MiejsceParkingowe mp1 = new MiejsceParkingowe(50);
        MiejsceParkingowe mp2 = new MiejsceParkingowe(30);
        MiejsceParkingowe mp3 = new MiejsceParkingowe(40);
        MiejsceParkingowe mp4 = new MiejsceParkingowe(60);
        MiejsceParkingowe mp5 = new MiejsceParkingowe(500);

        blok.dodajPomieszczenieDoBloku(m1);
        blok.dodajPomieszczenieDoBloku(m2);
        blok.dodajPomieszczenieDoBloku(m3);
        blok.dodajPomieszczenieDoBloku(m4);
        blok.dodajPomieszczenieDoBloku(m5);
        blok.dodajPomieszczenieDoBloku(mp1);
        blok.dodajPomieszczenieDoBloku(mp2);
        blok.dodajPomieszczenieDoBloku(mp3);
        blok.dodajPomieszczenieDoBloku(mp4);
        blok.dodajPomieszczenieDoBloku(mp5);

        Wózek_inwalidzki wózek_inwalidzki = new Wózek_inwalidzki("Polonez", 2, 1, Projekt_GUI.ETypPojazdu.Lądowy, ETypSilnika.Elektryczny, "Skóra węża");
        Samochod samochod = new Samochod("AUDI A3", 25, 2000, Projekt_GUI.ETypPojazdu.Lądowy, ETypSilnika.Spalinowy, "Czerwony");
        Motorowka motorowka = new Motorowka("Yamaha", 15, 4000, Projekt_GUI.ETypPojazdu.Wodny, ETypSilnika.Spalinowy, 2);
        Motocykl motocykl = new Motocykl("Guawa Indyjska", 17, 5000, Projekt_GUI.ETypPojazdu.Lądowy, ETypSilnika.Hybrydowy, true);
        Jacht jacht = new Jacht("Diamond", 300, 100000, Projekt_GUI.ETypPojazdu.Wodny, ETypSilnika.Wodorowy, 50000000);

        Przedmiot p1 = new Przedmiot("Szafa", 10);
        Przedmiot p2 = new Przedmiot("Lodówka", 5);
        Przedmiot p3 = new Przedmiot("Stół", 7);
        Przedmiot p4 = new Przedmiot("Dinozaur", 100000);
        Przedmiot p5 = new Przedmiot("Biżuteria", 0.05);
        Przedmiot p6 = new Przedmiot("Komputer", 0.5);
        Przedmiot p7 = new Przedmiot("Kanapa", 20);
        Przedmiot p8 = new Przedmiot("Narzędzia", 5);
        Przedmiot p9 = new Przedmiot("Lampka", 3);
        Przedmiot p10 = new Przedmiot("Fotel", 7);

        osoba1.wynajmijPomieszczenie(m1, EDlugoscWynajmu.KWARTAL);
        osoba2.wynajmijPomieszczenie(m2, EDlugoscWynajmu.POL_ROKU);
        osoba3.wynajmijPomieszczenie(m3, EDlugoscWynajmu.ROK);

        osoba1.wynajmijPomieszczenie(mp1, EDlugoscWynajmu.KWARTAL);
        osoba2.wynajmijPomieszczenie(mp3, EDlugoscWynajmu.ROK);
        osoba3.wynajmijPomieszczenie(mp2, EDlugoscWynajmu.POL_ROKU);

        osoba1.zaparkujPojazd(wózek_inwalidzki, mp1);
        osoba2.zaparkujPojazd(samochod, mp3);
        osoba3.zaparkujPojazd(motocykl, mp2);

        osoba1.dodajMieszkanca(m1, osoba5);
        osoba2.dodajMieszkanca(m2, osoba8);

        osoba1.wlozPrzedmiot(p1, m1);
        osoba1.wlozPrzedmiot(p2, mp1);

        osoba3.wlozPrzedmiot(p5, m3);
        osoba3.wlozPrzedmiot(p6, mp2);

    }
}
