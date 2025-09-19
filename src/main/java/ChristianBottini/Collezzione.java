package ChristianBottini;

import java.util.*;
import java.util.stream.Collectors;

public class Collezzione {
    private final java.util.List<Giochi> giochi = new ArrayList<>();

    //aggiungo elemento ID
    public void add(Giochi g) {
        Objects.requireNonNull(g, "Gioco nullo");
        boolean exists = giochi.stream().anyMatch(x -> x.getId().equalsIgnoreCase(g.getId()));
        if (exists) throw new RuntimeException("Esiste già un gioco con ID: " + g.getId());
        giochi.add(g);
    }

    //ricerca per ID
    public Giochi findById(String id) {
        return giochi.stream()
                .filter(g -> g.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ID non trovato: " + id));
    }

    //ricerca per prezzo
    public java.util.List<Giochi> findByPriceLessThan(double maxPrice) {
        return giochi.stream()
                .filter(g -> g.getPrice() < maxPrice)
                .sorted(Comparator.comparingDouble(Giochi::getPrice))
                .collect(Collectors.toList());
    }

    //ricerca per numero di giocatori
    public java.util.List<GiocoTavolo> findByPlayers(int players) {
        return giochi.stream()
                .filter(g -> g instanceof GiocoTavolo)
                .map(g -> (GiocoTavolo) g)
                .filter(bg -> bg.getPlayers() == players)
                .sorted(Comparator.comparing(GiocoTavolo::getTitle))
                .collect(Collectors.toList());
    }

    //rimozione per ID
    public void removeById(String id) {
        boolean removed = giochi.removeIf(g -> g.getId().equalsIgnoreCase(id));
        if (!removed) throw new RuntimeException("Impossibile rimuovere: ID non trovato " + id);
    }

    //aggiornamento per ID sostituisco l'oggetto
    public void updateById(String id, Giochi nuovo) {
        for (int i = 0; i < giochi.size(); i++) {
            if (giochi.get(i).getId().equalsIgnoreCase(id)) {
                if (!nuovo.getId().equalsIgnoreCase(id)) {
                    throw new RuntimeException("L'ID non può cambiare durante l'aggiornamento");
                }
                giochi.set(i, nuovo);
                return;
            }
        }
        throw new RuntimeException("ID non trovato: " + id);
    }

    //statistiche: conteggi, max prezzo, media prezzi
    public void printStats() {
        long totVG = giochi.stream().filter(g -> g instanceof VideoGame).count();
        long totBG = giochi.stream().filter(g -> g instanceof GiocoTavolo).count();
        Optional<Giochi> max = giochi.stream().max(Comparator.comparingDouble(Giochi::getPrice));
        double media = giochi.stream().mapToDouble(Giochi::getPrice).average().orElse(0);

        System.out.println("Videogiochi: " + totVG + ", Giochi da tavolo: " + totBG);
        System.out.println("Gioco più caro: " + max.map(Object::toString).orElse("N/D"));
        System.out.printf("Prezzo medio: %.2f ", media);
    }

    //utility per vedere tutti
    public java.util.List<Giochi> getAll() { return giochi; }
}