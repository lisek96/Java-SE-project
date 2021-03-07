package Projekt_GUI;

import java.io.IOException;
import java.time.LocalDate;

public interface IObslugaWynajmu{
    void sprawdzCzyKoniecWynajmu(LocalDate currentDate) throws IOException;
    void sprawdzCzyNadszedłTerminEksmisji(LocalDate currentDate) throws IOException;
    void wyslijPowiadomienie_o_koncuWynajmu(Osoba o) throws IOException;
    void sprzedajPojazd();
    void wyslijPowiadomienie_o_Eksmisji(Osoba o) throws IOException;
}
