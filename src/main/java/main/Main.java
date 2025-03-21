package main;

import classes.Archivio;
import classes.ElementoCatalogo;
import classes.Libri;
import classes.Riviste;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String reset = "\u001B[0m";
    public static final String rosso = "\u001B[31m";
    public static final String verde = "\u001B[32m";
    public static final String giallo = "\u001B[33m";
    public static final String blu = "\u001B[34m";
    public static final String azzurro = "\u001B[36m";

    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);

        boolean operativo = true;
        boolean memoriaOn = false;

        while (operativo) {
            System.out.println();
            System.out.println(azzurro + "Benvenuti nella libreria virtuale di Vincenzo!" + reset);
            System.out.println();
            System.out.println("Per dare il via ad un'operazione, prego scegliere la vostra preferenza:");
            System.out.println();
            System.out.println(verde + "1" + reset + " - Aggiungi un titolo alla libreria");
            System.out.println(verde + "2" + reset + " - Rimuovi un titolo dalla libreria");
            System.out.println(verde + "3" + reset + " - Ricerca titolo tramite ISBN");
            System.out.println(verde + "4" + reset + " - Ricerca titolo per anno di pubblicazione");
            System.out.println(verde + "5" + reset + " - Ricerca titolo per autore");
            System.out.println(verde + "6" + reset + " - Aggiorna titolo nella libreria");
            System.out.println(verde + "7" + reset + " - Stampa numero di titoli presenti nella libreria");
            System.out.println(rosso + "8 - Esci" + reset);
            System.out.println();
            System.out.print(verde + "Inserisci la tua preferenza: " + reset);

            int scelta = scanner.nextInt();
            scanner.nextLine();

            if (!memoriaOn && scelta != 1 && scelta != 8) {
                System.out.println(rosso + "ATTENZIONE! Aggiungi prima qualche titolo alla libreria." + reset);
                continue;
            }

            try {
                switch (scelta) {
                    case 1:
                        aggiungiElemento(archivio, scanner);
                        memoriaOn = true;
                        break;
                    case 2:
                        rimuoviElemento(archivio, scanner);
                        break;
                    case 3:
                        ricercaPerIsbn(archivio, scanner);
                        break;
                    case 4:
                        ricercaPerAnno(archivio, scanner);
                        break;
                    case 5:
                        ricercaPerAutore(archivio, scanner);
                        break;
                    case 6:
                        aggiornaElemento(archivio, scanner);
                        break;
                    case 7:
                        archivio.stampaStatistiche();
                        break;
                    case 8:
                        operativo = false;
                        System.out.println();
                        System.out.println(azzurro + "Spero che la visita sia stata di tuo gradimento.A presto, viandante!" + reset);
                        break;
                    default:
                        System.out.println();
                        System.out.println(rosso + "Stai cercando mica di far esplodere la mia libreria?" + "Non provocarmi" + reset);
                }
            } catch (Exception e) {
                System.err.println("Errore durante l'operazione.");
            }
        }
        scanner.close();
    }

    private static void aggiungiElemento(Archivio archivio, Scanner scanner) throws Exception {
        try {
            System.out.println();
            System.out.print("Scegli tra " + blu + "libro" + reset + " o " + giallo + "rivista" + reset + ": ");
            String tipo = scanner.nextLine().toLowerCase();
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("Titolo: ");
            String titolo = scanner.nextLine();
            System.out.print("Anno di pubblicazione: ");
            int anno = scanner.nextInt();
            System.out.print("Numero di pagine: ");
            int pagine = scanner.nextInt();
            scanner.nextLine();
            if (tipo.equals("libro")) {
                System.out.print("Autore: ");
                String autore = scanner.nextLine();
                System.out.print("Genere: ");
                String genere = scanner.nextLine();
                archivio.aggiungiElemento(new Libri(isbn, titolo, anno, pagine, autore, genere));
                System.out.println();
                System.out.println(blu + "Libro" + reset + " aggiunto con successo!");
            } else if (tipo.equals("rivista")) {
                System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                String periodicita = scanner.nextLine().toUpperCase();
                Riviste.Periodicita period = Riviste.Periodicita.valueOf(periodicita);
                archivio.aggiungiElemento(new Riviste(isbn, titolo, anno, pagine, period));
                System.out.println();
                System.out.println(giallo + "Rivista" + reset +  " aggiunta con successo!");
            } else {
                System.out.println();
                System.out.println(rosso + "Il titolo che stai cercando di aggiungere alla libreria non è valido." + reset);
            }
        } catch (Exception e) {

            System.err.println("Errore");
            System.out.println();
        } };


    private static void rimuoviElemento(Archivio archivio, Scanner scanner) {
        System.out.println();
        System.out.print("Inserisci l'ISBN dell'elemento da rimuovere: ");
        String isbn = scanner.nextLine();
        archivio.rimuoviElemento(isbn);
        System.out.println();
        System.out.println(verde + "Elemento rimosso" + reset);
    }

    private static void ricercaPerIsbn(Archivio archivio, Scanner scanner) {
        System.out.println();
        System.out.print("Inserisci l'ISBN: ");
        String isbn = scanner.nextLine();
        ElementoCatalogo elemento = archivio.ricercaPerIsbn(isbn);
        if (elemento != null) {
            System.out.println();
            System.out.println(verde + "Titolo trovato: " + reset + elemento);
        } else {
            System.out.println();
            System.out.println(rosso + "Titolo non trovato." + reset);
        }
    }

    private static void ricercaPerAnno(Archivio archivio, Scanner scanner) {
        System.out.println();
        System.out.print("Inserisci l'anno: ");
        int anno = scanner.nextInt();
        scanner.nextLine();
        List<ElementoCatalogo> risultati = archivio.ricercaPerAnno(anno);
        if (!risultati.isEmpty()) {
            System.out.println();
            System.out.println(verde + "Titoli trovati:" + reset);
            risultati.forEach(System.out::println);
        } else {
            System.out.println(rosso + "Nessun titolo trovato per l'anno specificato." + reset);
        }
    }

    private static void ricercaPerAutore(Archivio archivio, Scanner scanner) {
        System.out.println();
        System.out.print("Inserisci il nome dell'autore: ");
        String autore = scanner.nextLine();
        List<Libri> risultati = archivio.ricercaPerAutore(autore);
        if (!risultati.isEmpty()) {
            System.out.println();
            System.out.println(verde + "Titoli trovati: " + reset);
            risultati.forEach(System.out::println);
        } else {
            System.out.println(rosso + "Nessun titolo trovato per l'autore specificato." + reset);
        }
    }

    private static void aggiornaElemento(Archivio archivio, Scanner scanner) throws Exception {
        System.out.println();
        System.out.print("Inserisci l'ISBN del titolo da aggiornare: ");
        String isbn = scanner.nextLine();
        System.out.println();
        System.out.println(giallo + "Inserisci i nuovi dati per il titolo selezionato." + reset);
        aggiungiElemento(archivio, scanner);
        archivio.rimuoviElemento(isbn);
        System.out.println();
        System.out.println(verde + "Titolo aggiornato con successo." + reset);
    }
}
