package classes;

public class Riviste extends ElementoCatalogo {

    public enum Periodicita {SETTIMANALE, MENSILE, SEMESTRALE};

    private Periodicita periodicita;

    public Riviste(String isbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {

        return periodicita;
    }

    @Override
    public String toString() {

        return super.toString() + ", Periodicità: " + periodicita;
    }
}
