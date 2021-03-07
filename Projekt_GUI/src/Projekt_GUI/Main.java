package Projekt_GUI;

import Projekt_GUI.Exceptions.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExceptionTooManyThings, ExceptionMiejsceParkingoweJuzZajetePrzezPojazd, ExceptionPrzypisaneOsiedle,
            ExceptionPomieszczenieJużMaSwojBlok, ExceptionLimitMiejscParkingowych, ExceptionLimitMieszkan_w_Bloku {

        final int EXIT = 0;
        Scanner scanner = new Scanner(System.in);

        UpływCzasu upływCzasu = new UpływCzasu(200);
        upływCzasu.start();
        Inicjalizacja_wstepnych_obiektow.start();

        while (Aplikacja.WYBOR != EXIT) {
            try {
                Aplikacja.wybierzKimJestes();
                Thread.sleep(25);
                if (Aplikacja.WYBOR != 24)
                    System.out.println("Wybierz 24, aby wyświetlić menu");
                Aplikacja.WYBOR = scanner.nextInt();
                switch (Aplikacja.WYBOR) {
                    case 1:
                        Aplikacja.wybierzKimJestes();
                        break;
                    case 2:
                        Aplikacja.wyswietlWszystkieWolnePomieszczenia();
                        break;
                    case 3:
                        Aplikacja.wynajmijMieszkanie();
                        break;
                    case 4:
                        Aplikacja.wynajmijMiejsceParkingowe();
                        break;
                    case 5:
                        Aplikacja.wypiszSwojeDane_i_swojePomieszczenia();
                        break;
                    case 6:
                        Aplikacja.anulujWynajemPomieszczenia();
                        break;
                    case 7:
                        Aplikacja.przedluzWynajem();
                        break;
                    case 8:
                        Aplikacja.dodajLokatora();
                        break;
                    case 9:
                        Aplikacja.usunLokatora();
                        break;
                    case 10:
                        Aplikacja.wyswietlLokatorow();
                        break;
                    case 11:
                        Aplikacja.dodajPrzedmiot();
                        break;
                    case 12:
                        Aplikacja.usunPrzedmiot();
                        break;
                    case 13:
                        Aplikacja.wyswietlZawartoscPomieszczenia();
                        break;
                    case 14:
                        Aplikacja.zaparkujPojazd();
                        break;
                    case 15:
                        Aplikacja.wyparkujPojazd();
                        break;
                    case 16:
                        Osoba.WypiszDane_o_OsobachDoPliku();
                        break;
                    case 17:
                        Aplikacja.wyswietlSwojePowiadomieniaO_zadluzeniach();
                        break;
                    case 18:
                        Aplikacja.wyswietlSwojePowiadomienia_o_eksmisjach();
                        break;
                    case 19:
                        System.out.println(Data.currentDate);
                        break;
                    case 20:
                        Aplikacja.wyswietlSwojePojazdy();
                        break;
                    case 21:
                        Pojazd.wyswietlWszystkiePojazdy();
                        break;
                    case 22:
                        Aplikacja.stworzNowyObiekt();
                        break;
                    case 23:
                        Aplikacja.wyswietl_najemce_pomieszczenia_o_podanym_ID();
                        break;
                    case 24:
                        Aplikacja.menu();
                        break;
                    //// metody ułatwiające testy:
                    case 100:
                        upływCzasu.zatrzymajUplywCzasu();
                        System.out.println("Czas zatrzymał się.");
                        break;
                    case 101:
                        upływCzasu.wznowUplywCzasu();
                        System.out.println("Czas ruszył.");
                        break;
                }
            } catch (ExceptionUsuwanieNajemcy | ExceptionNieWłaściweID | ExceptionNiewłaściwyOkresWynajmu | ExceptionLimitWynajetychPomieszczenOsiagniety | ExceptionOsobaNieJestNajemca |
                    ExceptionTooManyThings | IOException | ExceptionProblematycznyNajemca | ExceptionNieMaŻadnychWolnychMieszkań | ExceptionNiePosiadaszWynajetychPomieszczeń |
                    ExceptionNieMaPojazdu | ExceptionNieMaZadnychWolnychMiejscParkingowych | ExceptionNiePosiadaszWynajetychMieszkan | ExceptionNiePosiadaszWynajetychMiejscParkingowych |
                    ExceptionBrakPrzedmiotów_w_Pomieszczeniu | ExceptionBrakPojazdowDoZaparkowania | ExceptionMiejsceParkingoweJuzZajetePrzezPojazd | InterruptedException |
                    ExceptionNiewlaściwyWybór | ExceptionBrakNajemcy e) {
                System.err.println(e.getMessage());
            }catch (InputMismatchException e){
                System.err.println("Proszę o wybranie liczby");
                scanner.next();
            }
        }
        upływCzasu.interrupt();
        System.out.println("\nKONIEC");
    }
}


