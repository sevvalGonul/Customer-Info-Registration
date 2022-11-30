
public class DoublyLinkedList {

    // Node sınıfını inner class olarak yazıyorum.
    private class Node {

        private CustomerInfo customer;  // data
        private Node previous;
        private Node next;

        public Node() {
            customer = new CustomerInfo();
            previous = null;
            next = null;
        }

        public Node(CustomerInfo newCustomer, Node previousNode, Node nextNode) {
            customer = newCustomer;
            previous = previousNode;
            next = nextNode;
        }
    }  // Inner Node class'ının sonu
    
    private Node head;
    private Node tail;
    
    public DoublyLinkedList() {
        head = null;
        tail = null;
    }
    
    // CustomerInfo sınıfı tipinde bir parametre alan ve soyada göre sıralı olacak şekilde listeye ekleme yapan method:
    public void insertInOrder(CustomerInfo c) {
        String param_soyad = c.getSoyad();  // Parametre olarak gelen nesnenin soyadını çektim.
        if (isEmpty()) {  // Liste boş ise head ve tail yeni eklenen node'u gösterir.
            head = tail = new Node(c, null, null); // Bu ilk eklenen düğüm olduğu için previous ve next bağ sağaları null olmalı.       
        }
        else if(head.customer.getSoyad().compareToIgnoreCase(param_soyad) > 0 // Head node'da tutulan CustomerInfo nesnesinin soyadı parametredeki soyaddan sonra geliyor ise
                || ( head.customer.getSoyad().compareToIgnoreCase(param_soyad) == 0 // veya soyadlar aynı
                && head.customer.getAdSoyad().compareToIgnoreCase(c.getAdSoyad()) > 0 )) {  // ve head'deki isim parametredeki isimden sonra geliyor ise:
            // Gelen nesne alfabetik olarak head'den önce gelmeli yani yeni head olmalı:
            Node newHead = new Node(c, null, head);
            head.previous = newHead;
            head = newHead;        
        }
        else if (tail.customer.getSoyad().compareToIgnoreCase(param_soyad) < 0  // Tail node'da tutulan customer'ın soyadı parametre soyaddan önce geliyor ise
                || (tail.customer.getSoyad().compareToIgnoreCase(param_soyad) == 0  // veya (soyadlar eşit ve tail'daki isim parametredeki isimden
                && tail.customer.getAdSoyad().compareToIgnoreCase(c.getAdSoyad()) < 0)) {  // önce geliyor ise) :
            // Gelen nesne alfabetik olarak tail'dan sonra gelmeli yani yeni tail olmalı:
            Node newTail = new Node(c, tail, null);
            tail.next = newTail;
            tail = newTail;
        }
        else {  // Araya ekleme
            Node current = head.next;
            while(current != null && current.customer.getSoyad().compareToIgnoreCase(param_soyad) < 0
                    || (current.customer.getSoyad().compareToIgnoreCase(param_soyad) == 0)
                    && current.customer.getAdSoyad().compareToIgnoreCase(c.getAdSoyad()) < 0)
                current = current.next;
            // current'taki soyad parametredeki soyaddan alfabetik olarak sonra geldiği ilk anda
            // veya soyadlar eşit ve current'taki ad parametredeki addan sonra geldiği ilk anda
            // döngüden çıkacak. Dolayısıyla current'tan önceki pozisyona ekleme yapmalıyız:
            Node newNode = new Node(c, current.previous, current);
            current.previous.next = newNode;
            current.previous = newNode;            
        }
    }
    
    //Adı ve soyadı verilen bir müşteriyi listede arayarak eğer varsa bu müşterinin bilgilerini yazdıran method
    public void outputCustomer(String ad_soyad) {
        Node position = find(ad_soyad);
        if (position == null)
            System.out.println("*** Belirtilen ad-soyad'a sahip müsteri bulunamadı ***");
        else {
            System.out.println(position.customer);  // toString otomatik olarak çağrılır.
        }
    }
    
    // Adı ve soyadı verilen müşterinin listeden silinmesini sağlayan silme metodu
    public boolean deleteCustomer(String ad_soyad) {
        boolean silindi = true;
        Node position = find(ad_soyad);  // Adı soyadı verilen müşterinin listedeki pozisyonunu döndürür.
        
        if (position == null)  // Böyle bir müşteri bulunamadıysa:
            silindi = false;
        else if (position.previous == null && head == tail) {  // Listede tek eleman var ise:
            head = null;
            tail = null;
        }
        else if (position.previous == null) {  // head node silinecekse:
            head = head.next;
            head.previous = null;           
        }
        else if (position.next == null) {  // tail node silinecekse:
            tail = tail.previous;
            tail.next = null;
        }
        else {  // Aradan silme yapılacaksa:
            position.previous.next = position.next;
            position.next.previous = position.previous;            
        }
        return silindi;
    }
    
    // Tüm kayıtları artan alfabetik sırada (A’dan Z’ye) ekrana yazdıran metot
    public void outputListFromTop() {
        if (isEmpty())
            System.out.println("*** Listede müşteri bulunmuyor ***");
        Node current = head;
        while(current != null) {
            System.out.println(current.customer.toString());
            current = current.next;
        }
    }
    
    // Tüm kayıtları azalan alfabetik sırada (Z’den A’ya) ekrana yazdıran metot
    public void outputListFromBottom() {
        if (isEmpty())
            System.out.println("*** Listede müşteri bulunmuyor ***");
        Node current = tail;
        while(current != null) {
            System.out.println(current.customer.toString());
            current = current.previous;
        }
    }    
    
    // Ekstra method: Adı soyadı verilen bir müşteriyi listede arar, varsa pozisyonunu yoksa null döndürür.
    public Node find(String ad_soyad) {
        Node position = head;
        while (position != null) {
            if (position.customer.getAdSoyad().equalsIgnoreCase(ad_soyad))
                return position;
            position = position.next;
        }
        return null;  // Belirtilen ad soyad bulunamadı
    }
    
    public boolean isEmpty() {
        return (head == null);
    }
}
