import java.sql.*;
import java.util.ArrayList;

public class Articol {
    private int id_articol;
    private int id_utilizator;
    private int draft;
    private String titlu;
    private String descriere;
    private String continut;
    private int vizualizari;
    private ArrayList<String> taguri;
    private ArrayList<Comentariu> comentarii;
    private double rating;
    private int nr_voturi;

    public static Articol creare(int id_utilizator, String titlu, String descriere, String continut, ArrayList<String> taguri,int draft){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "insert into Articole(id_utilizator,titlu,descriere,continut,draft) values(?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id_utilizator);
            pstmt.setString(2,titlu);
            pstmt.setString(3,descriere);
            pstmt.setString(4,continut);
            pstmt.setInt(5,draft);
            if(pstmt.executeUpdate()>0){
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                Articol articol = new Articol();
                articol.setId_articol(rs.getInt(1));
                articol.setContinut(continut);
                articol.setDescriere(descriere);
                articol.setTitlu(titlu);
                articol.setDraft(draft);
                StringBuffer stringBuffer = new StringBuffer("insert into Taguri(id_articol,nume) values(?,?)");
                stringBuffer.append(", (?,?)".repeat(Math.max(0, taguri.size() - 1)));
                pstmt = con.prepareStatement(stringBuffer.toString());
                for(int i=0;i<taguri.size();i++){
                    pstmt.setInt(2*i+1,articol.getId_articol());
                    pstmt.setString(2*i+2,taguri.get(i));
                }
                if(pstmt.executeUpdate()>0){
                    articol.setTaguri(taguri);
                    con.close();
                    return articol;
                }
                con.close();
                return articol;
            }
            con.close();
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Articol> gaseste(int id_articol){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "select * from Articole where draft=0 ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            if(id_articol>=0) {
                sql=sql+" and id_articol=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, String.valueOf(id_articol));
            }
            ArrayList<Articol> articols = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Articol articol = new Articol();
                articol.setId_articol(rs.getInt("id_articol"));
                articol.setContinut(rs.getString("continut"));
                articol.setDescriere(rs.getString("descriere"));
                articol.setTitlu(rs.getString("titlu"));
                articol.setDraft(rs.getInt("draft"));
                articol.setId_utilizator(rs.getInt("id_utilizator"));
                articol.setVizualizari(rs.getInt("vizualizari"));
                ArrayList<String> taguri = new ArrayList<>();
                pstmt = con.prepareStatement("select * from Taguri where id_articol=?");
                pstmt.setInt(1,id_articol);
                ResultSet rs2 = pstmt.executeQuery();
                while(rs2.next()){
                    taguri.add(rs2.getString("nume"));
                }
                articol.setTaguri(taguri);
                articol.setComentarii(Comentariu.gaseste(articol.getId_articol()));
                pstmt=con.prepareStatement("select count(*) nr, avg(valoare) valoare from Voturi where id_articol=?");
                pstmt.setInt(1,articol.getId_articol());
                rs2 = pstmt.executeQuery();
                rs2.next();
                articol.setNr_voturi(rs2.getInt("nr"));
                articol.setRating(rs2.getDouble("valoare"));
                articols.add(articol);
            }
            con.close();
            return articols;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Articol> gaseste_draft(int id_articol){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "select * from Articole ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            if(id_articol>=0) {
                sql=sql+" where id_articol=?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, String.valueOf(id_articol));
            }
            ArrayList<Articol> articols = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Articol articol = new Articol();
                articol.setId_articol(rs.getInt("id_articol"));
                articol.setContinut(rs.getString("continut"));
                articol.setDescriere(rs.getString("descriere"));
                articol.setTitlu(rs.getString("titlu"));
                articol.setDraft(rs.getInt("draft"));
                articol.setId_utilizator(rs.getInt("id_utilizator"));
                articol.setVizualizari(rs.getInt("vizualizari"));
                ArrayList<String> taguri = new ArrayList<>();
                pstmt = con.prepareStatement("select * from Taguri where id_articol=?");
                pstmt.setInt(1,id_articol);
                ResultSet rs2 = pstmt.executeQuery();
                while(rs2.next()){
                    taguri.add(rs2.getString("nume"));
                }
                articol.setTaguri(taguri);
                articol.setComentarii(Comentariu.gaseste_draft(articol.getId_articol()));
                articols.add(articol);
            }
            con.close();
            return articols;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean editare(){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "update Articole set titlu=?, descriere=?, continut=? where id_articol=?";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(4,id_articol);
            pstmt.setString(1,titlu);
            pstmt.setString(2,descriere);
            pstmt.setString(3,continut);
            if(pstmt.executeUpdate()>0){
                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean publicare(){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "update Articole set draft=0 where id_articol=?";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id_articol);
            if(pstmt.executeUpdate()>0){
                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean increaseViews(){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "update Articole set vizualizari=(select vizualizari from (select * from Articole) as Articole2 where id_articol=?)+1 where id_articol=?";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id_articol);
            pstmt.setInt(2,id_articol);
            if(pstmt.executeUpdate()>0){
                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean adaugaComentariu(String username, String text){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "insert into Comentarii(id_articol,username,continut,ip) values(?,?,?,(select substring_index(host,\":\",1) from information_schema.processlist WHERE ID=connection_id()))";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id_articol);
            pstmt.setString(2,username);
            pstmt.setString(3,text);
            if(pstmt.executeUpdate()>0){
                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean adaugaVot(int vot){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "insert into Voturi(id_articol,valoare,ip) values(?,?,(select substring_index(host,\":\",1) from information_schema.processlist WHERE ID=connection_id()))";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id_articol);
            pstmt.setInt(2,vot);
            if(pstmt.executeUpdate()>0){
                con.close();
                return true;
            }
            con.close();
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public int getId_articol() {
        return id_articol;
    }
    public void setId_articol(int id_articol) {
        this.id_articol = id_articol;
    }
    public int getId_utilizator() {
        return id_utilizator;
    }
    public void setId_utilizator(int id_utilizator) {
        this.id_utilizator = id_utilizator;
    }
    public int getDraft() {
        return draft;
    }
    public void setDraft(int draft) {
        this.draft = draft;
    }
    public String getTitlu() {
        return titlu;
    }
    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }
    public String getDescriere() {
        return descriere;
    }
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    public String getContinut() {
        return continut;
    }
    public void setContinut(String continut) {
        this.continut = continut;
    }
    public int getVizualizari() {
        return vizualizari;
    }
    public void setVizualizari(int vizualizari) {
        this.vizualizari = vizualizari;
    }
    public ArrayList<String> getTaguri() {
        return taguri;
    }
    public void setTaguri(ArrayList<String> taguri) {
        this.taguri = taguri;
    }
    public ArrayList<Comentariu> getComentarii() {
        return comentarii;
    }
    public void setComentarii(ArrayList<Comentariu> comentarii) {
        this.comentarii = comentarii;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setNr_voturi(int nr_voturi) {
        this.nr_voturi = nr_voturi;
    }

    public int getNr_voturi() {
        return nr_voturi;
    }

    @Override
    public String toString() {
        return "Articol{" +
                "id_articol=" + id_articol +
                ", id_utilizator=" + id_utilizator +
                ", draft=" + draft +
                ", titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                ", continut='" + continut + '\'' +
                ", vizualizari=" + vizualizari +
                ", taguri=" + taguri +
                ", comentarii=" + comentarii +
                ", rating=" + rating +
                ", nr_voturi=" + nr_voturi +
                '}';
    }
}
