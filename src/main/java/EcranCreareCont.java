import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranCreareCont {
    static JFrame fereastra;
    static GridBagLayout gridBag;
    static GridBagConstraints gbcons;

    static void adauga(Component comp, int x, int y, int w, int h) {
        gbcons.gridx = x;
        gbcons.gridy = y;
        gbcons.gridwidth = w;
        gbcons.gridheight = h;
        gridBag.setConstraints(comp, gbcons);
        fereastra.add(comp);
    }

    public static void main(String args[]) {
        fereastra = new JFrame("Inregistrare");
        gridBag = new GridBagLayout();
        gbcons = new GridBagConstraints();
        gbcons.weightx = 1.0;
        gbcons.weighty = 1.0;

        gbcons.insets = new Insets(10, 10, 10, 10);
        fereastra.setLayout(gridBag);
        JLabel lblLogin = new JLabel("Creare cont", JLabel.CENTER);
        lblLogin.setFont(new Font(" Arial ", Font.BOLD, 24));
        gbcons.fill = GridBagConstraints.BOTH;
        adauga(lblLogin, 0, 0, 4, 2);

        JLabel lblNume = new JLabel("Nume:");
        gbcons.fill = GridBagConstraints.NONE;
        gbcons.anchor = GridBagConstraints.EAST;
        adauga(lblNume, 0, 2, 1, 1);

        JLabel lblPrenume = new JLabel("Prenume:");
        adauga(lblPrenume, 0, 3, 1, 1);

        JLabel lblEmail = new JLabel("Email:");
        adauga(lblEmail, 0, 4, 1, 1);

        JLabel lblParola = new JLabel("Parola:");
        adauga(lblParola, 0, 5, 1, 1);


        JTextField txtUtilizator = new JTextField("", 30);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        gbcons.anchor = GridBagConstraints.CENTER;
        adauga(txtUtilizator, 1, 2, 2, 1);

        JTextField txtPrenume = new JTextField("", 30);
        adauga(txtPrenume, 1, 3, 2, 1);

        JTextField txtEmail = new JTextField("", 30);
        adauga(txtEmail, 1, 4, 2, 1);

        JTextField txtParola = new JPasswordField("", 30);
        adauga(txtParola, 1, 5, 2, 1);

        JButton btnSalvare = new JButton(" Inregistrare ");
        btnSalvare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(Utilizator.inregistrare(txtUtilizator.getText(),txtPrenume.getText(),txtEmail.getText(),txtParola.getText()));
                System.out.println("Inregistrare reusita");
            }
        });
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        adauga(btnSalvare, 1, 6, 1, 1);

        JButton btnIesire = new JButton(" Iesire ");
        btnIesire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fereastra.dispose();
            }
        });
        adauga(btnIesire, 2, 6, 1, 1);

        fereastra.setSize(new Dimension(300, 300));
        fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);
    }
}
