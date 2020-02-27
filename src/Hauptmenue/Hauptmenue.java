package Hauptmenue;

import ErscheintBaldFenster.Fehlermeldung;
import Memory.*;
import VierGewinnt.VierGewinnt;
import Schiffeversenken.Schiffeversenken;

import javax.swing.*;
import java.awt.*;

public class Hauptmenue extends JFrame {

    private Hauptmenue() {
        this.setTitle("Hauptmenü");
        this.setPreferredSize(new Dimension(430, 340));
        this.setMinimumSize(new Dimension(430, 340));
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null); //Fenster soll zentriert geöffnet werden
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton memoryBtn = new JButton();
        setButton(memoryBtn, "Memory", "icons/hauptmenueIcons/memory.png");

        JButton schiffeversenkenBtn = new JButton();
        setButton(schiffeversenkenBtn, "Schiffe versenken", "icons/hauptmenueIcons/schiffe_versenken.png");

        JButton vierGewinntBtn = new JButton();
        setButton(vierGewinntBtn, "Vier gewinnt", "icons/hauptmenueIcons/vier_gewinnt.png");

        JButton monopolyBtn = new JButton();
        setButton(monopolyBtn, "Monopoly", "icons/hauptmenueIcons/wuerfel.png");

        JButton ticTacToeBtn = new JButton();
        setButton(ticTacToeBtn, "Tic Tac Toe", "icons/hauptmenueIcons/tic_tac_toe.png");

        JButton muehleBtn = new JButton();
        setButton(muehleBtn, "Mühle", "icons/hauptmenueIcons/muehle.png");

        this.add(memoryBtn);
        this.add(schiffeversenkenBtn);
        this.add(vierGewinntBtn);
        this.add(monopolyBtn);
        this.add(ticTacToeBtn);
        this.add(muehleBtn);

        //Fenster werden erzeugt,-> die Klasse "ActionListener" hat nur eine Methode "actionPerformed()" die bei einem Klick aufgerufen wird
        memoryBtn.addActionListener(e -> new Memory());
        schiffeversenkenBtn.addActionListener(e -> new Schiffeversenken());
        vierGewinntBtn.addActionListener(e -> new VierGewinnt());
        monopolyBtn.addActionListener(e -> new Fehlermeldung("Monopoly"));
        ticTacToeBtn.addActionListener(e -> new Fehlermeldung("Tic Tac Toe"));
        muehleBtn.addActionListener(e -> new Fehlermeldung("Mühle"));

        this.pack(); //passt das Fenster an die Größe des Inhalts (bzw. Layouts) an
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Hauptmenue();
    }

    private void setButton(JButton button, String text, String url) {   //Buttons werden gestaltet
        button.setText(text);
        button.setIcon(new ImageIcon(url));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setFocusable(false);
    }
}
