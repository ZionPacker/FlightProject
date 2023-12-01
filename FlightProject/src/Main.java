import java.util.Scanner;

public class Main {
    private String date;
    private String time;
    private boolean[] seats;

    public Main(String date, String time, int numSeats) {
        this.date = date;
        this.time = time;
        this.seats = new boolean[numSeats];
    }

    public String readFlightInformation() {
        int availableSeats = 0;
        for (boolean seat : seats) {
            if (!seat) availableSeats++;
        }
        return "Flight Information: Date: " + date + ", Time: " + time + ", Total Seats: " + seats.length + ", Available Seats: " + availableSeats;
    }

    public int reserveTicket() throws AllTicketsSold {
        for (int i = 0; i < seats.length; i++) {
            if (!seats[i]) {
                seats[i] = true;
                return i + 1;
            }
        }
        throw new AllTicketsSold("All tickets sold out for flight on " + date + " at " + time);
    }

    public void cancelReservation(int seatNumber) {
        if (seatNumber > 0 && seatNumber <= seats.length && seats[seatNumber - 1]) {
            seats[seatNumber - 1] = false;
            System.out.println("Reservation for seat " + seatNumber + " cancelled. The seat is now available.");
        } else {
            System.out.println("Invalid or already available seat number.");
        }
    }
    static class AllTicketsSold extends Exception {
        public AllTicketsSold(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        Main flight = new Main("2023-12-24", "3:00 PM", 20);
        System.out.println(flight.readFlightInformation());
        try {
            int reservedSeat = flight.reserveTicket();
            System.out.println("Seat number " + reservedSeat + " reserved. Departure: " + flight.date + " at " + flight.time + ".");

            try{
                System.out.println("Would you like to cancel your flight?"  + "(Yes) / (No)");
                Scanner myObj = new Scanner(System.in);
                String userInput = myObj.nextLine();
                if( userInput.equals("Yes")){
                    flight.cancelReservation(reservedSeat);
                    System.out.println(flight.readFlightInformation());
                } else {
                    System.out.println("Flight has been reserved");
                }

            } catch(Exception e){
                System.out.println("Invalid input, please try again");
            }

        } catch (AllTicketsSold e) {
            System.out.println(e.getMessage());
        }
    }


}
