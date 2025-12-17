package project.autor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import project.buch.Buch;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Autor {
    public int idAutor;
    @Size(min = 1, max = 25, message = "Vorname darf maximal 25 Zeichen lang sein")
    public String vorname;
    public String nachname;
    @Past(message = "Geburtsdatum muss in der Vergangeheit liegen")
    public LocalDate gebdatum;
    @DecimalMin(value = "0.00", message = "Umsatz darf nicht negativ sein")
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
    public Buch getBuch() { return buch; }
    public void setBuch(Buch buch) { this.buch = buch; }
}
