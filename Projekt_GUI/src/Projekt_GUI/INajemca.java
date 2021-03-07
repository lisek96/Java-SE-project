package Projekt_GUI;

import Projekt_GUI.Exceptions.ExceptionMiejsceParkingoweJuzZajetePrzezPojazd;
import Projekt_GUI.Exceptions.ExceptionTooManyThings;

public interface INajemca {
    void wyjmijPrzedmiot(Przedmiot przedmiot, Pomieszczenie pomieszczenie);
    void wlozPrzedmiot(Przedmiot przedmiot, Pomieszczenie pomieszczenie) throws ExceptionTooManyThings;
    void dodajMieszkanca(Mieszkanie m, Osoba o);
    void usunMieszkanca(Mieszkanie m, Osoba o);
    void przedluzNajem(Pomieszczenie p, EDlugoscWynajmu dlugoscWynajmu);
    void anulujNajemPomieszczenia(Pomieszczenie p);
    void zaparkujPojazd(Pojazd p, MiejsceParkingowe mp) throws ExceptionTooManyThings, ExceptionMiejsceParkingoweJuzZajetePrzezPojazd;
    void wyparkujPojazd(MiejsceParkingowe mp);
    String komunikat_o_problematycznym_najemcy();
    boolean czyProblematycznyNajemca();
    boolean czyOsobaMaWiecejPomieszczenNizLimit();
    void usuńZawiadomienieOzadłużeniu(Pomieszczenie p);
}
