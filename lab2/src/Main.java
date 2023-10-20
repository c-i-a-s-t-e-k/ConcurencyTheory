public class Main {
    public static void main(String[] args) throws InterruptedException {
        World world = new World();
        for (int i = 0; i < 5; i++){
            Thread client = new Thread(new Client(world));
            Thread producer = new Thread(new Producer(world));
            client.start();
            producer.start();
        }
    }
}

// monito  klasa interg dodaj jeden odejmij jeden,
// zerowanie bufor i ustawianie na jeden
// 2 wątki produkujący i konsumujący
// tylko 2 wątki

//więcej konsumentów, konsumentów