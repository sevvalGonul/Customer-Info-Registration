import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    private static final int DOSYA_OKUT = 1;
    private static final int MUSTERI_EKLE = 2;
    private static final int MUSTERI_YAZDIR = 3;
    private static final int MUSTERI_SIL = 4;
    private static final int BASTAN_LISTEYI_YAZDIR = 5;
    private static final int SONDAN_LISTEYI_YAZDIR = 6;
    private static final int CIKIS = 7;

    public static void main(String[] args) {
        
        DoublyLinkedList list = new DoublyLinkedList();
        Scanner input = new Scanner(System.in);
        int choice = DOSYA_OKUT;  // Döngüye girmesi için CIKIS dışında rastgele bir değer
        
        while (choice != CIKIS) {
            
            System.out.println("\n(1) Dosya Okut" + "\n(2) Müşteri Ekle" + 
                    "\n(3) Müşteri Bilgilerini Ekrana Yazdır" +
                    "\n(4) Müşteri Sil" +
                    "\n(5) Tüm Kayıtları Artan Alfabetik Sırada (A’dan Z’ye) Yazdır" + 
                    "\n(6) Tüm Kayıtları Azalan Alfabetik Sırada (Z’den A’ya) Yazdır" +
                    "\n(7) ÇIKIŞ");
            System.out.print("\nYukarıdaki işlemlerden birini seçiniz.(1-7): ");
            choice = input.nextInt();
            
            switch(choice) {
                case DOSYA_OKUT:
                    try {
                        Scanner inputStream = new Scanner(new FileInputStream("customer.txt"));  // Dosyayı açma
                        
                        while (inputStream.hasNextLine()) {  // Dosyayı satır satır okuma
                            String line = inputStream.nextLine();  // Bir satırı okuma
                            
                            // Her satırda birden fazla kelime olduğu için bunları StringTokenizer ile virgüllerinden parçalıyorum:
                            StringTokenizer tokens = new StringTokenizer(line, ",");
                            
                            String ad_soyad = "";
                            String adres = "";
                            ArrayList<String> numaralar = new ArrayList<String>();
                            
                            int count = 0;
                            while (tokens.hasMoreTokens()) {
                                count++;
                                if (count == 1)  // Bu ilk token ise bu ad-soyaddır.
                                    ad_soyad = tokens.nextToken();
                                else if (count == 2)  // Bu ikinci token ise bu adrestir
                                    adres = tokens.nextToken();
                                else  // Geriye kalanlar numaradır ve bunları da ArrayList'e ekliyorum:
                                    numaralar.add(tokens.nextToken());                                
                            }
                            
                            // Her satır bir müşteriyi temsil ettiği için okuduğum bilgilerle bir CustomerInfo nesnesi oluşturuyorum:
                            CustomerInfo customer = new CustomerInfo(ad_soyad, adres, numaralar);
                            
                            // Bu müşteriyi DoublyLinkedList'e sıralı olarak ekliyorum:
                            list.insertInOrder(customer);
                            
                        }
                        
                        inputStream.close();  // Dosyayı kapatma
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("Dosya bulunamadı");
                        System.out.println("veya açılamadı.");
                    }
                    catch (IOException e) {
                    System.out.println("Dosya okutulurken bir hata meydana geldi.");
                    }
                    
                    System.out.println("*** Dosya okutuldu ***");
                    break;
                    
                case MUSTERI_EKLE:
                    CustomerInfo customer = new CustomerInfo();
                    
                    input.nextLine();  // nextInt'ten sonra nextLine alacağım için önlem
                    System.out.print("Müsterinin adını-soyadını giriniz: ");
                    customer.setAdSoyad(input.nextLine());
                    
                    System.out.print("Müşterinin adresini giriniz: ");
                    customer.setAdres(input.nextLine());
                    
                    ArrayList<String> numaralar = new ArrayList<>();
                    char devam = 'e';
                    while (devam != 'h') {
                        System.out.print("Müşterinin numarasını giriniz: ");
                        numaralar.add(input.nextLine());
                        System.out.print("Başka numara var mı? (e/h): ");
                        devam = input.nextLine().charAt(0);
                    }
                    customer.setNumaralar(numaralar);
                    
                    list.insertInOrder(customer);  // Müşteriyi listeye ekleme
                    System.out.println("*** Ekleme işlemi başarıyla tamamlandı ***");
                    
                    break;
                    
                case MUSTERI_YAZDIR:
                    input.nextLine();// nextInt'ten sonra nextLine alacağım için önlem
                    System.out.print("Bilgilerini yazdırmak istediğiniz müşterinin adını-soyadını giriniz: ");
                    String ad_soyad = input.nextLine();
                    list.outputCustomer(ad_soyad);
                    break;
                    
                case MUSTERI_SIL:
                    input.nextLine(); // nextInt'ten sonra nextLine alacağım için önlem
                    System.out.print("Silmek istediğiniz müşterinin adını-soyadını giriniz: ");
                    if (list.deleteCustomer(input.nextLine()))
                        System.out.println("*** Silme işlemi başarıyla tamamlandı ***");
                    else
                        System.out.println("*** Böyle bir müşteri bulunamadı ***");
                    break;
                    
                case BASTAN_LISTEYI_YAZDIR:
                    list.outputListFromTop();
                    break;
                    
                case SONDAN_LISTEYI_YAZDIR:
                    list.outputListFromBottom();
                    break;
            }
        }

    }
}
