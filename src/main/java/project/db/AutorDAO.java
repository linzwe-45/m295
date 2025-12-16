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
        PreparedStatement pstmt= con.prepareStatement("update autor set vorname=?, nachname=?, gebdatum=?, umsatz=?, istAktiv=?, idBuch=? where idAutor=?");
        pstmt.setInt(7, a.getIdAutor());
        pstmt.setString(1, a.getVorname());
        pstmt.setString(2, a.getNachname());
        pstmt.setObject(3, a.getGebdatum());
        pstmt.setObject(4, a.getUmsatz());
        pstmt.setBoolean(5, a.getIstAktiv());
        pstmt.setInt(6, a.buch.getIdBuch());
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
        PreparedStatement pstmt= con.prepareStatement("delete from autor");
        int rows= pstmt.executeUpdate();
        con.close();
        return rows;
    }

    //Create
    public int createAutor(Autor a) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/literatur?user=lin&password=z3a");
        PreparedStatement pstmt= con.prepareStatement("insert into autor (idAutor, vorname, nachname, gebdatum, umsatz, istAktiv, idBuch) values (?, ?, ?, ?, ?, ?,?)");
        pstmt.setInt(1, a.getIdAutor());
        if (a.getVorname() != null) {
            pstmt.setString(2, a.getVorname());
        }
        else pstmt.setNull(2, Types.VARCHAR);
        //NUll möglich setzen
        pstmt.setString(3, a.getNachname());
        pstmt.setObject(4, a.getGebdatum());
        pstmt.setObject(5, a.getUmsatz());
        pstmt.setBoolean(6, a.getIstAktiv());
        pstmt.setInt(7, a.buch.getIdBuch());
        int row= pstmt.executeUpdate();
        con.close();
        return row; //1 bedeutet das 1 row erstellt wurde
    }

    //Create Tables!!



}
