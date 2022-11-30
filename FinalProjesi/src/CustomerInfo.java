
import java.util.ArrayList;
import java.util.StringTokenizer;


public class CustomerInfo {
    private String adSoyad;
    private String adres;
    private ArrayList<String> numaralar;
    
    public CustomerInfo() {  // Parametresiz constructor
        adSoyad = "no-name";
        adres = "no-adres";
        numaralar = new ArrayList<String>();
    }

    public CustomerInfo(String adSoyad, String adres, ArrayList<String> numaralar) {  // Tüm verileri alan constructor
        this.adSoyad = adSoyad;
        this.adres = adres;
        this.numaralar = numaralar;
    }    


    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public ArrayList<String> getNumaralar() {
        return numaralar;
    }

    public void setNumaralar(ArrayList<String> numaralar) {
        this.numaralar = numaralar;
    }
    
    public String toString() {
        /*String numbers = "";
        for (String number: numaralar)
            numbers = numbers + number + ", ";*/
        return "Ad Soyad: " + adSoyad + ", Adres: " + adres + ", Numaralar: " + numaralar.toString();
    }
    
    // Ekstra Method: Soyad döndürür
    public String getSoyad() {
        StringTokenizer st = new StringTokenizer(adSoyad, " ");  // Ad soyadı boşluklardan token'lara ayıracak.
        String soyad = "";
        while(st.hasMoreTokens())  // Döngüden çıktığında soyad değişkenine son kelime yani soyad atanmış olacak.
            soyad = st.nextToken();
        return soyad;
    }
}
