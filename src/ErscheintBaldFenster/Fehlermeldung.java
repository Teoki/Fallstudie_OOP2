package ErscheintBaldFenster;

import javax.swing.*;

public class Fehlermeldung extends JFrame {

    public Fehlermeldung(String spielName) {
        this.setTitle(spielName);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ImageIcon icon = new ImageIcon("icons/fehlermeldungIcons/welpen.png");
        JOptionPane.showConfirmDialog(this, spielName + " befindet sich derzeit in der Entwicklung...", "Erscheint demnächst", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

        this.dispose();
    }
}
