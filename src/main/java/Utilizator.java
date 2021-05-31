import java.sql.*;

public class Utilizator {
    private int id_utilizator;
    private String nume;
    private String prenume;
    private String email;
    private String parola;

    public Utilizator(int id_utilizator, String nume, String prenume, String email, String parola) {
        this.id_utilizator = id_utilizator;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
    }

    public static Utilizator inregistrare(String nume, String prenume, String email, String parola){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "insert into Utilizatori(nume,prenume,email,parola) values(?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,nume);
            pstmt.setString(2,prenume);
            pstmt.setString(3,email);
            pstmt.setString(4,parola);
            if(pstmt.executeUpdate()>0){
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                Utilizator utilizator = new Utilizator(rs.getInt(1),nume,prenume,email,parola);
                con.close();
                return utilizator;
            }
            con.close();
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static Utilizator conectare(String email, String parola){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "select * from Utilizatori where email=? and parola=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,email);
            pstmt.setString(2,parola);
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            if(rs.next()){
                Utilizator utilizator = new Utilizator(rs.getInt("id_utilizator"),rs.getString("nume"),rs.getString("prenume"),rs.getString("email"),rs.getString("parola"));
                con.close();
                return utilizator;
            }
            con.close();
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id_utilizator=" + id_utilizator +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }

    public int getId_utilizator() {
        return id_utilizator;
    }
}
