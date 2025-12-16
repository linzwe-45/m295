package project.autor;

import project.buch.Buch;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Autor {
    public int idAutor;
    public String vorname;
    public String nachname;
    public LocalDate gebdatum;
    public BigDecimal umsatz;
    public Boolean istAktiv;
    public Buch buch;

    public int getIdAutor() {
        return idAutor;
    }
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public String getNachname() {
        return nachname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public LocalDate getGebdatum() {
        return gebdatum;
    }
    public void setGebdatum(LocalDate gebdatum) {
        this.gebdatum = gebdatum;
    }
    public BigDecimal getUmsatz() {
        return umsatz;
    }
    public void setUmsatz(BigDecimal umsatz) {
        this.umsatz = umsatz;
    }
    public Boolean getIstAktiv() {
        return istAktiv;
    }
    public void setIstAktiv(Boolean istAktiv) {
        this.istAktiv = istAktiv;
    }
}
