package project.db;

import project.autor.Autor;
import project.buch.Buch;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AutorDAO {
    //READ
    public ArrayList<Autor> getEveryModul() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("select a.idAutor, a.vorname, a.nachname, a.gebdatum, a.umsatz, a.istAktiv," +
                " b.idBuch, b.titel from autor a join buch b on a.idBuch= b.idBuch");
        ResultSet rs= pstmt.executeQuery();
        ArrayList<Autor> modulListe = new ArrayList<>();
        Autor a = null;
        while (rs.next()) {
            a = new Autor();
            a.buch = new Buch();
            a.setIdAutor(rs.getInt(1));
            a.setVorname(rs.getString(2));
            a.setNachname(rs.getString(3));
            a.setGebdatum(LocalDate.parse(rs.getString(4)));
            a.setUmsatz(rs.getBigDecimal(5));
            a.setIstAktiv(rs.getBoolean(6));
            a.buch.setIdBuch(rs.getInt(7));
            a.buch.setTitel(rs.getString(8));
            modulListe.add(a);
        }
        con.close();
        return modulListe;
    }

    public Autor getAutorById(int idAutor) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("select a.idAutor, a.vorname, a.nachname, a.gebdatum, a.umsatz, a.istAktiv," +
                " b.idBuch, b.titel from autor a join buch b on a.idBuch= b.idBuch where a.idAutor=?");
        pstmt.setInt(1, idAutor);
        ResultSet rs= pstmt.executeQuery();
        Autor a = null;
        if (rs.next()) {
            a = new Autor();
            a.buch = new Buch();
            a.setIdAutor(rs.getInt(1));
            a.setVorname(rs.getString(2));
            a.setNachname(rs.getString(3));
            a.setGebdatum(LocalDate.parse(rs.getString(4)));
            a.setUmsatz(rs.getBigDecimal(5));
            a.setIstAktiv(rs.getBoolean(6));
            a.buch.setIdBuch(rs.getInt(7));
            a.buch.setTitel(rs.getString(8));
        }
        con.close();
        return a;
    }

    public Autor getAutorByDate(LocalDate datum) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("select a.idAutor, a.vorname, a.nachname, a.gebdatum, a.umsatz, a.istAktiv," +
                " b.idBuch, b.titel from autor a join buch b on a.idBuch= b.idBuch where a.gebdatum=?");
        pstmt.setObject(1, datum);
        ResultSet rs= pstmt.executeQuery();
        Autor a = null;
        if (rs.next()) {
            a = new Autor();
            a.buch = new Buch();
            a.setIdAutor(rs.getInt(1));
            a.setVorname(rs.getString(2));
            a.setNachname(rs.getString(3));
            a.setGebdatum(LocalDate.parse(rs.getString(4)));
            a.setUmsatz(rs.getBigDecimal(5));
            a.setIstAktiv(rs.getBoolean(6));
            a.buch.setIdBuch(rs.getInt(7));
            a.buch.setTitel(rs.getString(8));
        }
        con.close();
        return a;
    }

    //Update
    public int updateAutor(Autor a) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("update autor set vorname=?, nachname=?, gebdatum=?, umsatz=?, istAktiv=? where idAutor=?");
        pstmt.setInt(6, a.getIdAutor());

        if (a.getVorname() != null) {pstmt.setString(1, a.getVorname());
        } else pstmt.setNull(1, Types.VARCHAR);

        if (a.getNachname() != null) {pstmt.setString(2, a.getNachname());
        } else pstmt.setNull(2, Types.VARCHAR);

        if (a.getGebdatum() != null) { pstmt.setDate(3, java.sql.Date.valueOf(a.getGebdatum()));
        } else pstmt.setNull(3, Types.DATE);

        if (a.getUmsatz() != null) { pstmt.setBigDecimal(4, a.getUmsatz());
        } else pstmt.setNull(4, Types.NUMERIC);

        if (a.getIstAktiv() != null) {pstmt.setBoolean(5, a.getIstAktiv());
        } else pstmt.setNull(5, Types.BOOLEAN);

        int row= pstmt.executeUpdate();
        con.close();
        return row; // 1 bedeutet das 1 row geändert wurde
    }

    //Delete
    public int deleteAutor(int id) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("delete from autor where idAutor=?");
        pstmt.setInt(1, id);
        int rows= pstmt.executeUpdate();
        con.close();
        return rows;
    }

    public int deleteAll() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmtAutor= con.prepareStatement("delete from autor");
        int rows= 0;
        rows += pstmtAutor.executeUpdate();
        PreparedStatement pstmtBuch= con.prepareStatement("delete from buch");
        rows += pstmtBuch.executeUpdate();
        con.close();
        return rows;
    }

    //Create
    public int createAutor(Autor a) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("insert into autor (idAutor, vorname, nachname, gebdatum, umsatz, istAktiv, idBuch) values (?, ?, ?, ?, ?, ?,?)");

        pstmt.setInt(1, a.getIdAutor());

        if (a.getVorname() != null) {pstmt.setString(2, a.getVorname());
        } else pstmt.setNull(2, Types.VARCHAR);

        if (a.getNachname() != null) {pstmt.setString(3, a.getNachname());
        } else pstmt.setNull(3, Types.VARCHAR);

        if (a.getGebdatum() != null) { pstmt.setDate(4, java.sql.Date.valueOf(a.getGebdatum()));
        } else pstmt.setNull(4, Types.DATE);

        if (a.getUmsatz() != null) { pstmt.setBigDecimal(5, a.getUmsatz());
        } else pstmt.setNull(5, Types.NUMERIC);

        if (a.getIstAktiv() != null) {pstmt.setBoolean(6, a.getIstAktiv());
        } else pstmt.setNull(6, Types.BOOLEAN);

        pstmt.setInt(7, a.getBuch().getIdBuch());

        int row= pstmt.executeUpdate();
        con.close();
        return row; //1 bedeutet das 1 row erstellt wurde
    }

    public int createTables() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        Statement stmt = con.createStatement();
        int rows= 0;
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS buch (" +
                        "idBuch INT NOT NULL, " +
                        "titel VARCHAR(45) NULL, " +
                        "PRIMARY KEY (idBuch))"
        );
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS autor (" +
                        "idAutor INT NOT NULL, " +
                        "vorname VARCHAR(25) NULL, " +
                        "nachname VARCHAR(25) NULL, " +
                        "gebdatum DATE NULL, " +
                        "umsatz DECIMAL(10,2) NULL, " +
                        "istAktiv TINYINT NULL, " +
                        "idBuch INT NOT NULL, " +
                        "PRIMARY KEY (idAutor), " +
                        "FOREIGN KEY (idBuch) REFERENCES buch(idBuch))"
        );
        stmt.executeUpdate("INSERT INTO buch (idBuch, titel) VALUES (1, 'Linas Reise')");
        stmt.executeUpdate("INSERT INTO buch (idBuch, titel) VALUES (2, 'Java lernen leicht gemacht')");

        // --- Startdaten autor ---
        stmt.executeUpdate("INSERT INTO autor (idAutor, vorname, nachname, gebdatum, umsatz, istAktiv, idBuch) " +
                "VALUES (1, 'Lina', 'Zweifel', '2004-05-03', 10.05, 1, 1)");
        rows += stmt.executeUpdate("INSERT INTO autor (idAutor, vorname, nachname, gebdatum, umsatz, istAktiv, idBuch) " +
                "VALUES (2, 'Tina', 'Muster', '2000-08-15', 15.50, 0, 2)");
        con.close();
        return rows;
    }



}
