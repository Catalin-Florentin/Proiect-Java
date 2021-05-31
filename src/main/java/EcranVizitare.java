import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Collectors;

public class EcranVizitare {
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
        String col[]={"id_articol","id_utilizator","titlu","descriere","continut","rating","nr_voturi","nr_comentarii"};
        DefaultTableModel model =  new DefaultTableModel(col,0);
        JTable table = new JTable(model);
        ArrayList<Articol> articols = Articol.gaseste(-1);
        System.out.println(articols.size());
        for(int i=0;i<articols.size();i++){
            int id_articol = articols.get(i).getId_articol();
            int id_utilizator = articols.get(i).getId_utilizator();
            String titlu = articols.get(i).getTitlu();
            String descriere = articols.get(i).getDescriere();
            String continut = articols.get(i).getContinut();
            double rating = articols.get(i).getRating();
            int nr_voturi = articols.get(i).getNr_voturi();
            int nr_comentarii = articols.get(i).getComentarii().size();
            Object [] data = {id_articol,id_utilizator,titlu,descriere,continut,rating,nr_voturi,nr_comentarii};
            model.addRow(data);
        }
        adauga(new JScrollPane(table),0,0,4,10);

        adauga(new JLabel("Adauga comentariu"),5,0,1,1);
        adauga(new JLabel("ID Articol"),5,1,1,1);
        JTextField txtArticol = new JTextField("",15);
        adauga(txtArticol,5,2,1,1);
        adauga(new JLabel("Username"),5,3,1,1);
        JTextField txtUsername = new JTextField("",15);
        adauga(txtUsername,5,4,1,1);
        adauga(new JLabel("Continut"),5,5,1,1);
        JTextField txtContinut = new JTextField("",15);
        adauga(txtContinut,5,6,1,1);
        JButton btnTrimitere = new JButton("Trimite comentariu");
        adauga(btnTrimitere,5,7,1,1);
        btnTrimitere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Articol articol: articols){
                    if(articol.getId_articol()==Integer.parseInt(txtArticol.getText())){
                        articol.adaugaComentariu(txtUsername.getText(),txtContinut.getText());
                        System.out.println("Trimis");
                    }
                }
            }
        });
        adauga(new JLabel("Adauga vot"),5,8,1,1);
        JTextField txtVot = new JTextField("",15);
        adauga(txtVot,5,9,1,1);
        JButton btnTrimitereVot = new JButton("Trimite vot");
        adauga(btnTrimitereVot,5,10,1,1);
        btnTrimitereVot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Articol articol: articols){
                    if(articol.getId_articol()==Integer.parseInt(txtArticol.getText())){
                        articol.adaugaVot(Integer.parseInt(txtVot.getText()));
                        System.out.println("Vot adaugat cu succes");
                    }
                }
            }
        });

        String col2[]={"id_articol","data_publicare","username","continut"};
        DefaultTableModel model2 =  new DefaultTableModel(col2,0);
        JTable table2 = new JTable(model2);
        for(Articol articol: articols){
            for(Comentariu comentariu: articol.getComentarii()){
                if(comentariu.getAprobat()==1) {
                    int id_articol = comentariu.getId_articol();
                    LocalDateTime data_publicare = comentariu.getData_publicare();
                    String username = comentariu.getUsername();
                    String continut = comentariu.getContinut();
                    Object[] data = {id_articol, data_publicare, username, continut};
                    model2.addRow(data);
                }
            }
        }
        adauga(new JScrollPane(table2),6,0,4,10);

        fereastra.setSize(new Dimension(1200, 1200));
        fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fereastra.setVisible(true);
    }
}
