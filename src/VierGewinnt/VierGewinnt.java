package VierGewinnt;

import javax.swing.*;

public class VierGewinnt extends JFrame {

    public VierGewinnt() {
        this.setTitle("Vier gewinnt");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ImageIcon icon = new ImageIcon("icons/vierGewinntIcons/QR-Code.png");
        JOptionPane.showConfirmDialog(this, "Scannen Sie den QR-Code, um Vier gewinnt auf Ihren mobilen Geräten zu spielen!", "Probieren Sie es aus!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);

        this.dispose();
    }
}
