package Projekt_GUI;

public class Obliczenia {
    public static IPolicz_objetosc_powierzchnia policzObjetosc = (a, b, c) -> a*b*c;
    public static IPolicz_objetosc_powierzchnia policzPole = (a, b, c) -> a*b;
    public static ISprawdzCzyWejdzie sprawdzCzyWejdzie = (a, b) -> a<=b;



}
