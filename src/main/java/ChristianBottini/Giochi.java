package ChristianBottini;

public abstract class Giochi {
    //campi comuni a ogni gioco: identificativo univoco, titolo, anno di pubblicazione e prezzo
    private String id;
    private String title;
    private int year;
    private double price;

    //costruttore: inizializzo l'oggetto passando dai setter,
    //così riuso i controlli/validazioni in un solo punto
    public Giochi(String id, String title, int year, double price) {
        setId(id);
        setTitle(title);
        setYear(year);
        setPrice(price);
    }

    //Getter
    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public double getPrice() { return price; }

    //Setter

    //ID obbligatorio e non vuoto, trim per eliminare spazi accidentali
    public void setId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID obbligatorio");
        this.id = id.trim();
    }

    //titolo obbligatorio, trim per pulire spazi superflui
    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Titolo obbligatorio");
        this.title = title.trim();
    }

    //l'anno deve essere in un range plausibile
    public void setYear(int year) {
        if (year < 1950 || year > 2100) throw new IllegalArgumentException("Anno non valido (1950-2030)");
        this.year = year;
    }

    //il prezzo deve essere strettamente positivo, impedisco 0 o valori negativi
    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Il prezzo deve essere > 0");
        this.price = price;
    }

    @Override
    public String toString() {
        /* Rappresentazione leggibile dell'oggetto:
         uso il nome reale della sottoclasse (getSimpleName)
         e formatto il prezzo con due decimali per una stampa pulita
         */
        return getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", price=" + String.format("%.2f", price) +
                '}';
    }
}