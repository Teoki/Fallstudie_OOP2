package Memory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Memory extends JFrame implements ActionListener {

    private Container hauptContentPane;
    private ImageIcon[] alleKartenIcons;//0-7 = Vorderseite, 8 = Rückseite

    public Memory() {
        this.setTitle("Memory");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 800);//größe des Spielfelds
        this.setResizable(false);
        this.hauptContentPane = this.getContentPane();
        this.hauptContentPane.setLayout(new BoxLayout(this.hauptContentPane, BoxLayout.PAGE_AXIS));
        this.setLocationRelativeTo(null); //damit das fenster zentriert erscheint

        //Menü-Zeile
        JMenuBar dropDownMenue = new JMenuBar();
        this.setJMenuBar(dropDownMenue);

        //Spiel Menü-Punkt
        JMenu dropDownElement = new JMenu("Optionen");
        dropDownMenue.add(dropDownElement);

        //Methoden im Submenü
        setNeuesElementImMenue("Neues Spiel", dropDownElement, this);
        setNeuesElementImMenue("Beenden", dropDownElement, this);

        this.alleKartenIcons = getAlleKartenIcons();    //Karten laden
        this.starteNeuesSpiel();
    }

    private ImageIcon[] getAlleKartenIcons() {
        ImageIcon[] icon = new ImageIcon[9]; // 9 Bilder
        for (int i = 0; i < 9; i++) {//Bilder einlesen
            String fileName = "icons/memoryIcons/" + i + ".png";  //Pfad für Bild bezeichnung zusammensetzen
            icon[i] = new ImageIcon(fileName);
        }
        return icon;
    }

    private JPanel erzeugeKarten() {
        //Spielbereich wird erzeugt
        JPanel spielbereich = new JPanel(new GridLayout(4, 4)); //der JFrame bekommt ein JPanel um da alle Karten zu positionieren (das ist der Spielbereich)
        //TODO panel.setBorder(new LineBorder(Color.RED)); //diese Zeile dann beim erklären in der Präsentation aktivieren um den Rahmen des Breichs einzufärben

        ImageIcon rueckseiteIcon = this.alleKartenIcons[8]; //alle Karten bekommen die selbe Rückseite (das achte Icon in dem Array ist das Bild der Rückseite)
        KartenSteuerung steuerung = new KartenSteuerung(this); //steuerung wird erzeugt

        int[] nummernFuerKarten = new int[16];  //da 4x4 grid Layout werden 16 Karten benötigt
        for (int i = 0; i < 8; i++) {
            nummernFuerKarten[2 * i] = i;
            nummernFuerKarten[2 * i + 1] = i;
        }

        mischeAlleNummern(nummernFuerKarten);   //Mischen / randomizer
        for (int kartenNummer : nummernFuerKarten) {    //kartenObjekte
            Karte neueKarte = new Karte(steuerung, this.alleKartenIcons[kartenNummer], rueckseiteIcon, kartenNummer); //neue Karten werden erzeugt
            spielbereich.add(neueKarte);
        }
        return spielbereich;
    }

    private void mischeAlleNummern(int[] nummernFuerKarten) {   //hier wird getauscht (wie in der Vorlesung)
        Random random = new Random();
        for (int i = 0; i < nummernFuerKarten.length; i++) {
            int randomNummer = random.nextInt(nummernFuerKarten.length);

            int ersteNummer = nummernFuerKarten[randomNummer];
            nummernFuerKarten[randomNummer] = nummernFuerKarten[i];
            nummernFuerKarten[i] = ersteNummer;
        }

    }

    private void setNeuesElementImMenue(String string, JMenu menu, ActionListener listener) {
        JMenuItem newItem = new JMenuItem(string);
        newItem.setActionCommand(string);
        newItem.addActionListener(listener);
        menu.add(newItem);
    }

    public void starteNeuesSpiel() {
        this.hauptContentPane.removeAll();
        //neue Karten sichtbar
        this.hauptContentPane.add(erzeugeKarten());


        this.setVisible(true);  //Hauptfenster wird angezeigt
    }

    //Aktionen für das Drop-Down-Menü
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Neues Spiel")) {
            starteNeuesSpiel(); // neues Spiel starten
        }
        if (e.getActionCommand().equals("Beenden")) {
            this.dispose();
        }
    }
}