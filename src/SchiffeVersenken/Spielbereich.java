package SchiffeVersenken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Das Spiel funktioniert nur auf Mac, wegen setOpaque(); auf Windows muss setOpaque(true) garnicht aufgerufen werden um den Hintergrund zu sehen
public class Spielbereich extends JPanel implements ActionListener {

    private List<JButton> buttonList = new ArrayList<>();
    private String name;
    private JLabel label = new JLabel();
    private int anzahlSchiffe = 3; //könnte eine lokale Variable sein, aber steht hier, falls man den User entscheiden lassen will kann man den Wert beim Erzeugen eines Spielbereichs übergeben (im Konstruktor)
    private int platzierteSchiffe; //Zählervariable im ActionListener
    private int anzahlTreffer; //Zählervariable im ActionListener
    private JFrame spielFenster; //um nach dem Sieg das Spielfenster (also SchiffeVersenken) zu schließen
    private Spielbereich gegnerischerSpielbereich;

    public Spielbereich(String name, JFrame spielFenster) {
        this.spielFenster = spielFenster;
        this.name = name;

        initialisiereButtons();

        JPanel textPanel = new JPanel();
        label.setText(name + ", platziere drei Schiffe...");
        textPanel.add(label);
        //TODO textPanel.setBorder(new LineBorder(Color.BLUE));  //Rahmen des Bereichs einfärben

        JPanel feldPanel = new JPanel();
        feldPanel.setLayout(new GridLayout(5, 5));
        for (JButton b : buttonList) {
            feldPanel.add(b);
        }
        //TODO feldPanel.setBorder(new LineBorder(Color.MAGENTA)); //Rahmen des Bereichs einfärben

        this.add(textPanel);
        this.add(feldPanel);

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); //Y_AXIS = oben nach unten
        boxLayout.addLayoutComponent("textPanel", textPanel);
        boxLayout.addLayoutComponent("feldPanel", feldPanel);
        this.setLayout(boxLayout);
        //TODO setBorder(new LineBorder(Color.GREEN));   //Rahmen des Bereichs einfärben
        this.setVisible(true);
    }

    void setGegnerischerSpielbereich(Spielbereich gegnerischerSpielbereich) { //ohne modifizierer: package-private
        this.gegnerischerSpielbereich = gegnerischerSpielbereich;
    }

    private void initialisiereButtons() {
        //alle Buttons erzeugen und der buttonList hinzufügen
        for (int i = 0; i < 25; i++) {
            JButton button = new JButton(); //Vorteil die Buttons hier zu initialisieren ist dass man nicht zwei mal Buttons erstellen muss und man einfach die Instanz benutzen kann, außerdem kann der Algorithmus zentral für beide Felder verändert werden
            buttonList.add(button);
        }
        //alle Buttons initialisieren
        for (JButton button : buttonList) {
            button.addActionListener(this); //Parameter: Referenz auf das ActionListener-Objekt (in diesem Fall this) jedem Button einen ActionListener zuweisen
            button.setFocusable(false);
            button.setPreferredSize(new Dimension(100, 100));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();   //Der Button wird durch das ActionEvent e gefunden (wo es ausgelöst wurde) und als "Object" returned, dann in einen Button gecastet. Erspart einem das "if(e.getSource() == a1){...}"
        button.setOpaque(true); //Hintergrund vom Button wird sichtbar gemacht, um Anfangs den grünen Hintergrund zu sehen

        if (platzierteSchiffe < anzahlSchiffe) { //solange die platziertenSchiffe kleiner als die Anzahl der insgesamt zu platzierenden Schiffe sind
            if (isFeldFrei(button)) { //falls das Feld frei ist, also nicht schon ein Schiff gesetzt wurde, platziere da ein Schiff
                platziereSchiff(button);
                button.setIcon(new ImageIcon("icons/schiffeversenkenIcons/schiff.png"));
                platzierteSchiffe++;
            }
            if (platzierteSchiffe == anzahlSchiffe) {
                System.out.println("Alle Schiffe wurden platziert");
                this.label.setText(gegnerischerSpielbereich.getName() + ", schieß!");
                tauscheSpielbereiche();
                versteckePlatzierteSchiffe(); //alle grünen Buttons werden unsichtbar gemacht
            }
        } else {  //ab drei platzierten Schiffen wird RED oder GRAY gesetzt (wird geschossen)
            if (isFeldBelegt(button)) { //wenn ein Schiff auf dem Feld ist, Hintergrund rot, ansonsten grau färben
                platziereSchuss(button, true);
                button.setIcon(new ImageIcon("icons/schiffeversenkenIcons/schiff.png"));
                JOptionPane.showMessageDialog(this, "Treffer! Der Schuss von " + gegnerischerSpielbereich.getName() + " hat getroffen.", "Treffer!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/schiffeversenkenIcons/sinkendesSchiff.png"));
                tauscheSpielbereiche();
                anzahlTreffer++; //für Gewinnscreen anzeigen
            } else {
                platziereSchuss(button, false);
                JOptionPane.showMessageDialog(this, "Daneben... Der Schuss von " + gegnerischerSpielbereich.getName() + " hat nicht getroffen.", "Kein Treffer!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/schiffeversenkenIcons/keinTrefferWelle.png"));
                tauscheSpielbereiche();
            }
            button.setEnabled(false); //Button nicht mehr anklickbar machen
        }
        if (anzahlTreffer == anzahlSchiffe) {   //wenn alle Schiffe getroffen wurden, kommt das Pop-up Fenster zum Schluss:
            int dialogResult = JOptionPane.showConfirmDialog(this, gegnerischerSpielbereich.getName() + " hat gewonnen! Nochmal spielen?", "Sieg!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("icons/schiffeversenkenIcons/siegerKapitaen.png"));
            if (dialogResult == 0) { //0=ja  (falls ja geklickt wurde)
                spielFenster.dispose();
                new SchiffeVersenken();
            } else {                   //falls nein geklickt wird
                spielFenster.dispose();
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    private boolean isFeldBelegt(JButton button) {
        return button.getBackground() == Color.GREEN;
    }

    private boolean isFeldFrei(JButton button) {
        return button.getBackground() != Color.GREEN;
    }

    private void platziereSchiff(JButton button) {
        button.setBackground(Color.GREEN);
    }

    private void platziereSchuss(JButton button, boolean treffer) {
        button.setBackground(treffer ? Color.RED : Color.GRAY); //Ternärer Operator if(treffer == true){RED}else{GRAY} (erspart Zeilen)
        button.setOpaque(true);
    }

    private void tauscheSpielbereiche() {
        gegnerischerSpielbereich.setVisible(true);
        this.setVisible(false);
    }

    private void versteckePlatzierteSchiffe() {
        for (JButton b : buttonList) {
            if (b.getBackground() == Color.GREEN) {
                b.setOpaque(false);
                b.setIcon(null);
            }
        }
    }
}