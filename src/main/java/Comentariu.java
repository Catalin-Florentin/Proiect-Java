import java.lang.management.GarbageCollectorMXBean;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comentariu {
    private int id_articol;
    private LocalDateTime data_publicare;
    private String username;
    private String continut;
    private int aprobat;
    private String ip;

    public static ArrayList<Comentariu> gaseste(int id_articol){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "select * from Comentarii where id_articol=? and aprobat=1";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id_articol);
            ArrayList<Comentariu> comentarius = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Comentariu comentariu = new Comentariu();
                comentariu.setId_articol(rs.getInt("id_articol"));
                comentariu.setContinut(rs.getString("continut"));
                comentariu.setAprobat(rs.getInt("aprobat"));
                comentariu.setData_publicare(rs.getTimestamp("data_publicare").toLocalDateTime());
                comentariu.setUsername(rs.getString("username"));
                comentariu.setIp(rs.getString("ip"));
                comentarius.add(comentariu);
            }
            con.close();
            return comentarius;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }
    public static ArrayList<Comentariu> gaseste_draft(int id_articol){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "select * from Comentarii where id_articol=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id_articol);
            ArrayList<Comentariu> comentarius = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Comentariu comentariu = new Comentariu();
                comentariu.setId_articol(rs.getInt("id_articol"));
                comentariu.setContinut(rs.getString("continut"));
                comentariu.setAprobat(rs.getInt("aprobat"));
                comentariu.setData_publicare(rs.getTimestamp("data_publicare").toLocalDateTime());
                comentariu.setUsername(rs.getString("username"));
                comentariu.setIp(rs.getString("ip"));
                comentarius.add(comentariu);
            }
            con.close();
            return comentarius;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public boolean aproba(int id_utilizator){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "update Comentarii as com\n" +
                    "join Articole as art on com.id_articol=art.id_articol\n" +
                    "set com.aprobat=1\n" +
                    "where com.id_articol=? and com.data_publicare=? and art.id_utilizator=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id_articol);
            pstmt.setTimestamp(2,Timestamp.valueOf(data_publicare));
            pstmt.setInt(3,id_utilizator);
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
    public boolean sterge(){
        try {
            Connection con= DriverManager.getConnection ("jdbc:mysql://localhost:3306/blog", "blog","password");
            String sql = "delete from Comentarii where id_articol=? and data_publicare=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id_articol);
            pstmt.setTimestamp(2,Timestamp.valueOf(data_publicare));
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

    public void setId_articol(int id_articol) {
        this.id_articol = id_articol;
    }
    public void setData_publicare(LocalDateTime data_publicare) {
        this.data_publicare = data_publicare;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setContinut(String continut) {
        this.continut = continut;
    }
    public void setAprobat(int aprobat) {
        this.aprobat = aprobat;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getId_articol() {
        return id_articol;
    }
    public LocalDateTime getData_publicare() {
        return data_publicare;
    }
    public String getUsername() {
        return username;
    }
    public String getContinut() {
        return continut;
    }
    public int getAprobat() {
        return aprobat;
    }

    @Override
    public String toString() {
        return "Comentariu{" +
                "id_articol=" + id_articol +
                ", data_publicare=" + data_publicare +
                ", username='" + username + '\'' +
                ", continut='" + continut + '\'' +
                ", aprobat=" + aprobat +
                ", ip='" + ip + '\'' +
                '}';
    }



}


