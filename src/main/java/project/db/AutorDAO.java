package project.db;

import project.autor.Autor;
import project.buch.Buch;

import java.sql.*;

public class AutorDAO {

    public Autor getAutorById(int idAutor) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("select a.idAutor, a.Vorname, b.idBuch from autor a join buch b on a.idBuch= b.idBuch where a.idAutot=?");
        pstmt.setInt(1, idAutor);
        ResultSet rs= pstmt.executeQuery();

        Autor a = null;
        a.buch = new Buch();
        if (rs.next()) {
            a = new Autor();
            a.setIdAutor(rs.getInt(1));
            a.setVorname(rs.getString(2));
            a.buch.setIdBuch(rs.getInt(3));
        }
        con.close();
        return a;
    }
}
