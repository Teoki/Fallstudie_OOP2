package Memory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;

public class Karte extends JLabel implements MouseListener {

    private Icon vorderseiteIcon; //icon für die Vorderseite der Karte
    private Icon rueckseiteIcon; //icon für die Rückseite der Karte
    private boolean aufgedeckt = false; //ob die Karte aufgedeckt ist oder nicht (start wert = sie ist nicht aufgedeckt)
    private int kartenNummer;
    private int iconBreiteHaelfte, iconHoeheHaelfte;
    private boolean mausHatMichGetroffen = false;
    private KartenSteuerung steuerung;

    public Karte(KartenSteuerung steuerung, Icon vorderseite, Icon rueckseite, int kartenNummer) {
        super(rueckseite); //übergibt der Oberklasse die rueckseite (also dem JLabel --> alle Karten sind JLabel)
        this.vorderseiteIcon = vorderseite;
        this.rueckseiteIcon = rueckseite;
        this.kartenNummer = kartenNummer;

        this.addMouseListener(this); //"hör auf alle maus klicks"
        this.iconHoeheHaelfte = rueckseite.getIconHeight() / 2;
        this.iconBreiteHaelfte = vorderseite.getIconWidth() / 2;
        this.steuerung = steuerung;
    }

    public int getKartenNummer() {
        return kartenNummer;
    }

    private boolean ueberIcon(int x, int y) {

        int distX = Math.abs(x - this.getWidth() / 2);
        int distY = Math.abs(y - this.getHeight() / 2);

        if (distX > this.iconHoeheHaelfte || distY > this.iconBreiteHaelfte) {
            return false;   //außerhalb der Karte geklickt
        }
        return true;    //innerhalb der Karte geklickt
    }

    //Aufdecken
    private void deckeAuf() {
        //Prüfen ob die Karte bereits aufgedeckt ist
        if (this.aufgedeckt) {
            return; //falls die Karte bereits aufgedeckt ist soll nichts in der gesamten Methode gemacht werden
        }

        //falls die Karte noch nicht aufgedeckt war wird sie ab hier aufgedeckt
        this.aufgedeckt = true;
        this.aufgedeckt = steuerung.aufdecken(this); //true/false wird aufgedeckt zugewiesen

        if (this.aufgedeckt) {
            this.setIcon(this.vorderseiteIcon); //das Label bekommt den icon der vorderseite
        }
    }

    //Zudecken
    public void deckeZu() {
        if (!this.aufgedeckt) {
            return;
        }
        this.setIcon(this.rueckseiteIcon);
        this.aufgedeckt = false;
    }

    //MouseEvents; was passiert wenn Aktion mit Maus
    @Override
    public void mouseClicked(MouseEvent geklickteStelle) {
        if (ueberIcon(geklickteStelle.getX(), geklickteStelle.getY())) { //wenn die geklickte stelle über einem Icon liegt
            this.deckeAuf();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (ueberIcon(e.getX(), e.getY())) {
            this.mausHatMichGetroffen = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.mausHatMichGetroffen) {
            this.mausHatMichGetroffen = false;
            this.mouseClicked(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }


    @Override
    public void mouseExited(MouseEvent e) {
        this.mausHatMichGetroffen = false;
    }
}