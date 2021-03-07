package Projekt_GUI;

import java.io.IOException;

public class UpływCzasu extends Thread {

    int milisekundy;
    int iloscDniDoPrzodu = 1;

    UpływCzasu(int milisekundy){
        this.milisekundy=milisekundy;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Thread.sleep(milisekundy);
                Data.currentDate = Data.currentDate.plusDays(iloscDniDoPrzodu);
                Pomieszczenie.getWszystkiePomieszczenia().forEach((x) -> {
                    try {
                        if (x.czyWynajete()) {
                            x.sprawdzCzyKoniecWynajmu(Data.currentDate);
                            x.sprawdzCzyNadszedłTerminEksmisji(Data.currentDate);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public void zatrzymajUplywCzasu(){
        iloscDniDoPrzodu=0;
    }

    public void wznowUplywCzasu(){
        iloscDniDoPrzodu=1;
    }
}
