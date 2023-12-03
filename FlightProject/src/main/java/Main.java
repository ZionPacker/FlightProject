public class Main {

    public static void main(String[] args) {
        // Setup init flights;
        Flight[] flights = new Flight[4];
        flights[0] = new Flight("Greensboro Flight A", "2023-12-24", "3:00 PM", 20);
        flights[1] = new Flight("Greensboro Flight B", "2023-12-24", "5:00 PM", 20);
        flights[2] = new Flight("Newark Flight A", "2023-12-24", "3:00 PM", 20);
        flights[3] = new Flight("Newark Flight B", "2023-12-24", "5:00 PM", 20);

        // Start the reservation system with the pre-defined flights and use System.out and System.in for I/O.
        ReservationSystem reservationSystem = new ReservationSystem(flights, System.out, System.in);

        // Begin executing the reservation system.
        reservationSystem.start();
    }

}
