package classes;

import classes.ElementoCatalogo;
import classes.Libri;
import classes.Riviste;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static main.Main.*;

public class Archivio {
    private final Set<ElementoCatalogo> catalogo = new HashSet<>();

    // Aggiunta di un elemento dell'utente
    public void aggiungiElemento(ElementoCatalogo elemento) throws Exception {
        if (catalogo.stream().anyMatch(el -> el.getIsbn().equals(elemento.getIsbn()))) {
            throw new Exception("Elemento con lo stesso ISBN già presente.");
        }
        catalogo.add(elemento);
    }

    // Ricerca per ISBN
    public ElementoCatalogo ricercaPerIsbn(String isbn) {
        return catalogo.stream()
                .filter(elemento -> elemento.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    // Rimuovi un elemento con lo stesso ISBN
    public void rimuoviElemento(String isbn) {
        catalogo.removeIf(elemento -> elemento.getIsbn().equals(isbn));
    }

    // Ricerca per anno di pubblicazione
    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        return catalogo.stream()
                .filter(elemento -> elemento.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    // Ricerca per autore
    public List<Libri> ricercaPerAutore(String autore) {
        return catalogo.stream()
                .filter(elemento -> elemento instanceof Libri)
                .map(elemento -> (Libri) elemento)
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }


    // Infine ci sono le statistiche del catalogo
    public void stampaStatistiche() {
        int numeroLibri = (int) catalogo.stream().filter(elemento -> elemento instanceof Libri).count();
        int numeroRiviste = (int) catalogo.stream().filter(elemento -> elemento instanceof Riviste).count();

        System.out.println();
        System.out.println("Numero di " + blu + "libri" + reset + ": " + numeroLibri);
        System.out.println("Numero di " + giallo + "riviste" + reset + ": " + numeroRiviste);
        System.out.println("Numero totale di titoli: " + (numeroLibri + numeroRiviste));
    }
}

