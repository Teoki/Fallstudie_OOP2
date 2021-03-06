package SchiffeVersenken;

import javax.swing.*;

public class SchiffeVersenken extends JFrame { //extends ActionListener geht auch hier, wird jedoch bislang nicht benötigt

    public SchiffeVersenken() {
        this.setTitle("Schiffe versenken");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);    //damit das Fenster zentriert erscheint
        this.setVisible(true);

        JPanel hauptbereich = new JPanel(); //ein Haupt-JPanel, der später alles beinhalten wird (ist im JFrame von SchiffeVersenken) beinhaltet zwei Spielbereich-Instanzen

        ImageIcon icon = new ImageIcon("icons/schiffeVersenkenIcons/benutzerIcon.png");
        String nameSpielerEins = (String) JOptionPane.showInputDialog(this, "Gebe bitte den Namen von Spieler 1 ein:", "Optionale Eingabe", JOptionPane.INFORMATION_MESSAGE, icon, null, ""); //gibt den Typen "Object" zurück
        String nameSpielerZwei = (String) JOptionPane.showInputDialog(this, "Gebe bitte den Namen von Spieler 2 ein:", "Optionale Eingabe", JOptionPane.INFORMATION_MESSAGE, icon, null, ""); //gibt den Typen "Object" zurück

        if (nameSpielerEins == null || nameSpielerEins.isEmpty()) {//null == true (ABBRECHEN), isEmpty()==true (OK)
            nameSpielerEins = "Spieler 1";
        }
        if (nameSpielerZwei == null || nameSpielerZwei.isEmpty()) {
            nameSpielerZwei = "Spieler 2";
        }

        Spielbereich spielbereichEins = new Spielbereich(nameSpielerEins, this); //vor dem Übergeben von String name soll Name überprüft werden ob "" ansonsten "Spieler 1" bzw. "Spieler 2" als Name übergeben
        Spielbereich spielbereichZwei = new Spielbereich(nameSpielerZwei, this);
        spielbereichZwei.setVisible(false);

        hauptbereich.add(spielbereichEins);
        hauptbereich.add(spielbereichZwei);

        spielbereichEins.setGegnerischerSpielbereich(spielbereichZwei); //so lernt die ein Instanz, die andere kennen
        spielbereichZwei.setGegnerischerSpielbereich(spielbereichEins);

        //TODO hauptbereich.setBorder(new LineBorder(Color.RED));    //Rahmen des Bereichs einfärben
        this.add(hauptbereich);
        this.pack(); //Größe des JFrames wird an den Inhalt angepasst
    }
}
