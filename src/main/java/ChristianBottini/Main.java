package ChristianBottini;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner sc = new Scanner(System.in);
    //istanza della collezione di giochi per eseguire tutte le operazioni
    private final Collezzione coll = new Collezzione();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean loop = true;
        while (loop) {
            printMenu();
            String choice = sc.nextLine().trim();

            try {
                //in base alla scelta dell’utente, eseguo il metodo corrispondente
                switch (choice) {
                    case "1" -> addVideoGame();
                    case "2" -> addGiocoTavolo();
                    case "3" -> searchById();
                    case "4" -> searchByPrice();
                    case "5" -> searchByPlayers();
                    case "6" -> removeById();
                    case "7" -> updateById();
                    case "8" -> coll.printStats();
                    case "9" -> printAll();
                    case "0" -> loop = false;
                    default -> System.out.println("Scelta non valida");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
            System.out.println();
        }
        System.out.println("BuonaGiornata!");
    }

    private void printMenu() {
        System.out.println("===== GESTIONE GIOCHI =====");
        System.out.println("1) Aggiungi Videogioco");
        System.out.println("2) Aggiungi Gioco da Tavolo");
        System.out.println("3) Cerca per ID");
        System.out.println("4) Cerca per prezzo");
        System.out.println("5) Cerca per numero di giocatori");
        System.out.println("6) Rimuovi per ID");
        System.out.println("7) Aggiorna per ID");
        System.out.println("8) Statistiche");
        System.out.println("9) Elenca tutti");
        System.out.println("0) Esci");
        System.out.print("Scelta: ");
    }

    private String ask(String label) {
        System.out.print(label + ": ");
        return sc.nextLine().trim();
    }

    private int askInt(String label) {
        while (true) {
            try { return Integer.parseInt(ask(label)); }
            catch (NumberFormatException e) { System.out.println("Inserisci un intero valido."); }
        }
    }

    private double askDouble(String label) {
        while (true) {
            try { return Double.parseDouble(ask(label).replace(",", ".")); }
            catch (NumberFormatException e) { System.out.println("Inserisci un numero valido."); }
        }
    }

    private Genere askGenre() {
        System.out.println("Generi: ACTION, ADVENTURE, RPG, STRATEGY, SPORTS, PUZZLE");
        while (true) {
            try { return Genere.valueOf(ask("Genere").toUpperCase()); }
            catch (IllegalArgumentException e) { System.out.println("Genere non valido, riprova."); }
        }
    }

    //operazioni sulla collezzione

    private void addVideoGame() {
        //raccolgo tutti i dati necessari per creare un Videogioco
        String id = ask("ID");
        String title = ask("Titolo");
        int year = askInt("Anno");
        double price = askDouble("Prezzo");
        String platform = ask("Piattaforma (PC/PS5/XBox)");
        int hours = askInt("Durata (ore)");
        Genere genre = askGenre();

        //creo l’oggetto Videogioco e lo aggiungo alla collezione
        coll.add(new VideoGame(id, title, year, price, platform, hours, genre));
        System.out.println("Videogioco aggiunto!");
    }

    private void addGiocoTavolo() {
        //raccolgo i dati per un Gioco da Tavolo
        String id = ask("ID");
        String title = ask("Titolo");
        int year = askInt("Anno");
        double price = askDouble("Prezzo");
        int players = askInt("Numero giocatori (2-10)");
        int minutes = askInt("Durata media (min)");

        //creo l’oggetto GiocoTavolo e lo aggiungo alla collezione
        coll.add(new GiocoTavolo(id, title, year, price, players, minutes));
        System.out.println("Gioco da tavolo aggiunto!"); // Conferma operazione
    }

    private void searchById() {
        //chiedo l’ID e stampo il gioco trovato
        String id = ask("ID");
        System.out.println(coll.findById(id));
    }

    private void searchByPrice() {
        //chiedo un prezzo massimo e prendo tutti i giochi
        double max = askDouble("Prezzo massimo");
        List<Giochi> res = coll.findByPriceLessThan(max);
        if (res.isEmpty()) System.out.println("Nessun risultato");
        else res.forEach(System.out::println);
    }

    private void searchByPlayers() {
        //chiedo il numero di giocatori e mostro i giochi da tavolo con quel valore
        int p = askInt("Numero di giocatori");
        var res = coll.findByPlayers(p);
        if (res.isEmpty()) System.out.println("Nessun risultato");
        else res.forEach(System.out::println);
    }

    private void removeById() {
        //chiedo l’ID da rimuovere e delego alla collezione
        String id = ask("ID da rimuovere");
        coll.removeById(id);
        System.out.println("Rimosso!");
    }

    private void updateById() {
        //chiedo l’ID da aggiornare
        String id = ask("ID da aggiornare");
        Giochi old = coll.findById(id);
        System.out.println("Elemento attuale: " + old);

        //chiedo quale tipo di oggetto voglio come risultato finale
        System.out.println("Imposta il TIPO finale: V = Videogioco, B = BoardGame");
        String tipo = ask("Tipo").toUpperCase();

        if ("V".equals(tipo)) {
            //raccolgo i campi necessari per costruire un nuovo Videogioco che sostituirà quello esistente
            String title = ask("Titolo");
            int year = askInt("Anno");
            double price = askDouble("Prezzo");
            String platform = ask("Piattaforma");
            int hours = askInt("Durata (ore)");
            Genere genre = askGenre();
            coll.updateById(id, new VideoGame(id, title, year, price, platform, hours, genre));
        } else if ("B".equals(tipo)) {
            //raccolgo i campi per un nuovo Gioco da Tavolo e sostituisco quello esistente
            String title = ask("Titolo");
            int year = askInt("Anno");
            double price = askDouble("Prezzo");
            int players = askInt("Numero giocatori (2-10)");
            int minutes = askInt("Durata media (min)");
            coll.updateById(id, new GiocoTavolo(id, title, year, price, players, minutes));
        } else {
            //se serve annullo l’operazione
            System.out.println("Tipo non valido: annullato");
            return;
        }

        System.out.println("Aggiornato!"); // Conferma finale
    }

    private void printAll() {
        //stampo in sequenza tutti i giochi presenti nella collezione
        coll.getAll().forEach(System.out::println);
    }

}
