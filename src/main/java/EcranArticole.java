import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EcranArticole {
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
        fereastra = new JFrame("Articole");
        gridBag = new GridBagLayout();
        gbcons = new GridBagConstraints();
        gbcons.weightx = 1.0;
        gbcons.weighty = 1.0;

        gbcons.insets = new Insets(10, 10, 10, 10);
        fereastra.setLayout(gridBag);
        JLabel lblLogin = new JLabel("Articole", JLabel.CENTER);
        lblLogin.setFont(new Font(" Arial ", Font.BOLD, 24));
        gbcons.fill = GridBagConstraints.BOTH;
        adauga(lblLogin, 0, 0, 4, 2);

        JLabel lblArticol = new JLabel("ID Articol:");
        gbcons.fill = GridBagConstraints.NONE;
        gbcons.anchor = GridBagConstraints.EAST;
        adauga(lblArticol, 0, 2, 1, 1);

        JLabel lblComentariu = new JLabel("ID Comentariu:");
        adauga(lblComentariu, 0, 3, 1, 1);

        JTextField txtArticol = new JTextField("", 30);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        gbcons.anchor = GridBagConstraints.CENTER;
        adauga(txtArticol, 1, 2, 2, 1);

        JTextField txtComentariu = new JTextField("", 30);
        adauga(txtComentariu, 1, 3, 2, 1);

        ArrayList<Articol> articols = new ArrayList<>();
        JButton btnSalvare = new JButton(" Cautare dupa ID articol ");
        btnSalvare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Integer.parseInt(txtArticol.getText()) > 0) {
                        articols.clear();
                        articols.addAll(Articol.gaseste(Integer.parseInt(txtArticol.getText())));
                    } else {
                        articols.clear();
                        articols.addAll(Articol.gaseste(-1));
                    }
                } catch(NumberFormatException numberFormatException){
                    articols.clear();
                    articols.addAll(Articol.gaseste(-1));
                }
                System.out.println(articols);

            }
        });
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        adauga(btnSalvare, 1, 4, 1, 1);

        JButton btnAprobare = new JButton(" Aproba comentariu ");
        btnAprobare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    for(Articol articol: articols){
                        if((articol.getId_articol() == Integer.parseInt(txtArticol.getText()))) {
                            for (int i = 0; i < articol.getComentarii().size(); i++) {
                                if (i + 1 == Integer.parseInt(txtComentariu.getText())) {
                                    articol.getComentarii().get(i).aproba(Integer.parseInt(args[0]));
                                }
                            }
                        }
                    }
                } catch(NumberFormatException numberFormatException){
                    numberFormatException.printStackTrace();
                }
            }
        });
        adauga(btnAprobare, 2, 4, 1, 1);
        JButton btnStergere = new JButton(" Sterge comentariu ");
        btnStergere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    for(Articol articol: articols){
                        if((articol.getId_articol() == Integer.parseInt(txtArticol.getText()))) {
                            for (int i = 0; i < articol.getComentarii().size(); i++) {
                                if (i + 1 == Integer.parseInt(txtComentariu.getText())) {
                                    articol.getComentarii().get(i).sterge();
                                }
                            }
                        }
                    }
                } catch(NumberFormatException numberFormatException){
                    numberFormatException.printStackTrace();
                }
            }
        });
        adauga(btnStergere, 3, 4, 1, 1);


        fereastra.setSize(new Dimension(300, 300));
        fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);
    }
}
