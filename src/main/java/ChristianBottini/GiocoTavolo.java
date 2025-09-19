package ChristianBottini;

public class GiocoTavolo extends Giochi {
    private int players;
    private int avgMinutes;

    //Costruttore in cui inizializzo prima la parte comune (super),
    //poi imposto i campi specifici validandoli tramite i setter
    public GiocoTavolo(String id, String title, int year, double price, int players, int avgMinutes) {
        super(id, title, year, price);
        setPlayers(players);
        setAvgMinutes(avgMinutes);
    }

    //Getter
    public int getPlayers() { return players; }
    public int getAvgMinutes() { return avgMinutes; }

    //Setter con controlli:
    //accetto solo giochi per 2/10 giocatori
    public void setPlayers(int players) {
        if (players < 2 || players > 10) throw new IllegalArgumentException("Giocatori deve essere tra 2 e 10");
        this.players = players;
    }

    //la durata media deve essere positiva, escludo 0 o negativi
    public void setAvgMinutes(int avgMinutes) {
        if (avgMinutes <= 0) throw new IllegalArgumentException("Durata media deve essere > 0");
        this.avgMinutes = avgMinutes;
    }

    @Override
    public String toString() {
        //riutilizzo toString della classe base e aggancio i campi specifici
        //sostituendo la graffa finale con una stringa che include players e avgMinutes
        return super.toString().replace("}", ", players=" + players + ", avgMinutes=" + avgMinutes + "}");
    }
}
