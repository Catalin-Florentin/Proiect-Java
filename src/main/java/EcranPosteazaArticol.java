import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EcranPosteazaArticol {
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
        fereastra = new JFrame("Posteaza articol");
        gridBag = new GridBagLayout();
        gbcons = new GridBagConstraints();
        gbcons.weightx = 1.0;
        gbcons.weighty = 1.0;

        gbcons.insets = new Insets(10, 10, 10, 10);
        fereastra.setLayout(gridBag);
        JLabel lblLogin = new JLabel("ID Articol ", JLabel.CENTER);
        lblLogin.setFont(new Font(" Arial ", Font.BOLD, 24));
        gbcons.fill = GridBagConstraints.BOTH;
        adauga(lblLogin, 0, 0, 2, 2);

        JLabel lblTitlu = new JLabel("Titlu:");
        gbcons.fill = GridBagConstraints.NONE;
        gbcons.anchor = GridBagConstraints.EAST;
        adauga(lblTitlu, 0, 2, 1, 1);

        JLabel lblDescriere = new JLabel("Descriere:");
        adauga(lblDescriere, 0, 3, 1, 1);

        JLabel lblContinut = new JLabel("Continut:");
        adauga(lblContinut, 0, 4, 1, 1);

        JLabel lblTag = new JLabel("Adauga tag:");
        adauga(lblTag, 0, 5, 1, 1);


        JTextField txtTitlu = new JTextField("", 30);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        gbcons.anchor = GridBagConstraints.CENTER;
        adauga(txtTitlu, 1, 2, 2, 1);

        JTextField txtArticol = new JTextField("", 30);
        adauga(txtArticol, 3, 0, 2, 1);

        JTextField txtDescriere = new JTextField("", 30);
        adauga(txtDescriere, 1, 3, 2, 1);

        JTextField txtContinut = new JTextField("", 30);
        adauga(txtContinut, 1, 4, 2, 1);

        ArrayList<String> taguri = new ArrayList<>();
        JTextField txtTag = new JTextField("", 30);
        adauga(txtTag, 1, 5, 2, 1);

        JButton btnSalvare = new JButton(" Publica ");
        btnSalvare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Articol.creare(Integer.parseInt(args[0]),txtTitlu.getText(),txtDescriere.getText(),txtContinut.getText(),taguri,0);
            }
        });
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        adauga(btnSalvare, 1, 6, 1, 1);

        JButton btnIesire = new JButton(" Salveaza draft ");
        btnIesire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Articol.creare(Integer.parseInt(args[0]),txtTitlu.getText(),txtDescriere.getText(),txtContinut.getText(),taguri,1);
            }
        });
        adauga(btnIesire, 2, 6, 1, 1);

        JButton btnTag= new JButton(" Adauga tag ");
        btnTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taguri.add(txtTag.getText());
                System.out.println(taguri);
            }
        });
        adauga(btnTag, 3, 5, 1, 1);

        JButton btnEditare = new JButton(" Actualizeaza draft ");
        btnEditare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Articol articol = new Articol();
                articol.setId_articol(Integer.parseInt(txtArticol.getText()));
                articol.setTaguri(taguri);
                articol.setDescriere(txtDescriere.getText());
                articol.setTitlu(txtTitlu.getText());
                articol.setContinut(txtContinut.getText());
                articol.editare();
                System.out.println("Editat cu succes");
            }
        });
        adauga(btnEditare, 3, 6, 1, 1);

        JButton btnActiv = new JButton(" Publica draft ");
        btnActiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Articol articol = new Articol();
                articol.setId_articol(Integer.parseInt(txtArticol.getText()));
                articol.publicare();
                System.out.println("Facut public cu succes");
            }
        });
        adauga(btnActiv, 4, 6, 1, 1);

        JButton btnArticol = new JButton("Articol dupa ID ");
        btnArticol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Articol articol = Articol.gaseste_draft(Integer.parseInt(txtArticol.getText().trim())).get(0);
                if(articol!=null){
                    txtContinut.setText(articol.getContinut());
                    txtTitlu.setText(articol.getTitlu());
                    txtDescriere.setText(articol.getDescriere());
                    taguri.clear();
                    taguri.addAll(articol.getTaguri());
                    System.out.println(articol);
                }
            }
        });
        adauga(btnArticol, 5, 0, 1, 1);

        fereastra.setSize(new Dimension(300, 300));
        fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);
    }
}
