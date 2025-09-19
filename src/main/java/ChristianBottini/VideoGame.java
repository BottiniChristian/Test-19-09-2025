package ChristianBottini;

public class VideoGame extends Giochi {
    private String platform;
    private int hours;
    private Genere genre;

    //Costruttore: inizializzo prima i campi comuni con super,
    //poi imposto quelli specifici validandoli tramite i setter
    public VideoGame(String id, String title, int year, double price, String platform, int hours, Genere genre) {
        super(id, title, year, price);
        setPlatform(platform);
        setHours(hours);
        setGenre(genre);
    }

    //Getter
    public String getPlatform() { return platform; }
    public int getHours() { return hours; }
    public Genere getGenre() { return genre; }

    //Setter con controlli base per evitare dati non validi

    //la piattaforma è obbligatoria, uso trim per pulire eventuali spazi
    public void setPlatform(String platform) {
        if (platform == null || platform.isBlank()) throw new IllegalArgumentException("Piattaforma obbligatoria");
        this.platform = platform.trim();
    }

    //le ore devono essere positive, escludo 0 o valori negativi
    public void setHours(int hours) {
        if (hours <= 0) throw new IllegalArgumentException("Le ore devono essere > 0");
        this.hours = hours;
    }

    //il genere è obbligatorio: non accetta null
    public void setGenre(Genere genre) {
        if (genre == null) throw new IllegalArgumentException("Genere obbligatorio");
        this.genre = genre;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", ", platform='" + platform + "', hours=" + hours + ", genre=" + genre + "}");
    }
}
