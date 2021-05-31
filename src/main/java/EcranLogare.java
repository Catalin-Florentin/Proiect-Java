import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcranLogare {
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
        fereastra = new JFrame("Logare");
        gridBag = new GridBagLayout();
        gbcons = new GridBagConstraints();
        gbcons.weightx = 1.0;
        gbcons.weighty = 1.0;

        gbcons.insets = new Insets(5, 5, 5, 5);
        fereastra.setLayout(gridBag);
        JLabel lblLogin = new JLabel("LOGIN", JLabel.CENTER);
        lblLogin.setFont(new Font(" Arial ", Font.BOLD, 24));
        gbcons.fill = GridBagConstraints.BOTH;
        adauga(lblLogin, 0, 0, 4, 2);

        JLabel lblNume = new JLabel("Utilizator:");
        gbcons.fill = GridBagConstraints.NONE;
        gbcons.anchor = GridBagConstraints.EAST;
        adauga(lblNume, 0, 2, 1, 1);

        JLabel lblParola = new JLabel("Parola:");
        adauga(lblParola, 0, 3, 1, 1);

        JTextField txtUtilizator = new JTextField("", 30);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        gbcons.anchor = GridBagConstraints.CENTER;
        adauga(txtUtilizator, 1, 2, 2, 1);

        JTextField txtParola = new JPasswordField("", 30);
        adauga(txtParola, 1, 3, 2, 1);

        JButton btnSalvare = new JButton(" Conectare ");
        btnSalvare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilizator utilizator = Utilizator.conectare(txtUtilizator.getText(),txtParola.getText());
                if(utilizator!=null){
                    EcranPosteazaArticol.main(new String[]{String.valueOf(utilizator.getId_utilizator())});
                    System.out.println("Conectare reusita");
                }

            }
        });
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        adauga(btnSalvare, 1, 4, 1, 1);

        JButton btnIesire = new JButton(" Conectare aprobare comentarii ");
        btnIesire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilizator utilizator = Utilizator.conectare(txtUtilizator.getText(),txtParola.getText());
                if(utilizator!=null){
                    EcranArticole.main(new String[]{String.valueOf(utilizator.getId_utilizator())});
                    System.out.println("Conectare reusita");
                }
            }
        });
        adauga(btnIesire, 2, 4, 1, 1);



        fereastra.setSize(new Dimension(300, 300));
        fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);
    }
}
