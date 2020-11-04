package mk.ukim.finki.eimt.tickets.FinkiTickets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TestClass {



    public static void main(String[] args) {
        String name = "Cineplexx, John Wick 2";
        String lowerAndNonCharacters = name.toLowerCase().replaceAll("[^a-z0-9 ]", "");
        String onlyOneSpace = lowerAndNonCharacters.replaceAll("[ ]{2,}", "-");
        String result = onlyOneSpace.replace(' ', '-');
//        System.out.println(result);

//        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String localDateTime = localDate+" "+localTime;
        String date = "2020-02-05";

        String ticketCode = "2232HFHFHHFAAAA";
        int quantity = 2;

        String code = ticketCode+""+quantity+"F";

//        System.out.println(code);
        int total = 100*quantity*180;
        String _total = ""+total;
        System.out.println(total);
    }
}
