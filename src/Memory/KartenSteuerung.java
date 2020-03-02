package Memory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.*;

public class KartenSteuerung implements ActionListener {

    private Vector<Karte> aufgedeckteKarten; //<Karte>, damit dem Vector nur Elemente des Datentyps Karte hinzugefügt werden kann
    private Timer zudeckTimer;
    private int aufdeckCounter;
    private JFrame fenster;

    public KartenSteuerung(JFrame fenster) {
        this.fenster = fenster;
        this.aufgedeckteKarten = new Vector<>(2);
        this.zudeckTimer = new Timer(2000, this); //2000 ms (2 sek.) Verzögerung
        this.zudeckTimer.setRepeats(false);
    }

    public boolean aufdecken(Karte karte) {
        if (this.aufgedeckteKarten.size() < 2) {
            return deckeKarteAuf(karte); //prüft ob die Karte wirklich aufgedeckt werden soll oder nicht (löscht im inneren Vector-Inhalt oder startet den Timer)
        }
        return false;
    }

    private boolean deckeKarteAuf(Karte karte) {
        this.aufgedeckteKarten.add(karte);
        if (this.aufgedeckteKarten.size() == 2) { //wenn zwei Karten aufgedeckt wurden
            Karte andereKarte = this.aufgedeckteKarten.get(0);
            if (andereKarte.getKartenNummer() == karte.getKartenNummer()) { //Prüfen ob die kartenNummern (der Karten im Vector) gleich sind
                aufdeckCounter++;
                if (aufdeckCounter == 8) {
                    JOptionPane.showMessageDialog(fenster, "Du hast gewonnen! In den Optionen kannst Du Memory neustarten oder beenden.");
                }
                this.aufgedeckteKarten.clear(); //die Karten aus dem Vector löschen, damit neue Karten in den Vector können
            } else {
                this.zudeckTimer.start(); //timer starten
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Karte karte : this.aufgedeckteKarten) {
            karte.deckeZu();
        }
        this.aufgedeckteKarten.clear(); //Vector wird geleert
    }
}