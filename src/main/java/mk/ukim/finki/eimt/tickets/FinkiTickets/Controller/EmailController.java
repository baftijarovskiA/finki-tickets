package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Controller
public class EmailController {

    @RequestMapping("/sendmail")
    public String sendMail(String to, String name, int status) throws MessagingException {
        // status 0 = registered account, status 1 = update account, status 2 = removed account
        message(to,name,status);
        return "redirect:/";
    }

    public void ticketTransaction(User user, Transaction transaction, boolean isSeller) throws MessagingException{
        messageForTransaction(user,transaction,isSeller);
    }

    private void messageForTransaction(User user, Transaction transaction, boolean isSeller) throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noreply.tickets.finki@gmail.com", "FinkiTickets1");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("noreply.tickets.finki@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));

        if (isSeller){
            msg.setSubject("Продаден билет");
            msg.setContent("Почитувани, "+user.getName()+"<br/> " +
                            "продадени се "+transaction.getQuantity()+" билети за настанот <i>"+transaction.getTicket().getEvent().getName()+"</i> со вредност од "+transaction.getTotal()+" денари. <br/>" +
                            "Доколку количината е завршена, вашиот билет ќе биде отстранет од продажба."+
                            "Ви благодариме, FINKI Tickets Team",
                    "text/html; charset=UTF-8");
        } else {
            msg.setSubject("Информации за купениот билет");
            msg.setContent("Почитувани, "+user.getName()+"<br/> " +
                            "извршена е трансакција во вредност од "+transaction.getTotal()+" денари, за "+transaction.getQuantity()+" билети за настанот <i>"+transaction.getTicket().getEvent().getName()+"</i>  <br/>" +
                            "Вашиот код: <b>"+transaction.getTicketCode()+"</b> <br/>"+
                            "Информации за билетот: <br/>"+
                            "Датум: "+transaction.getTicket().getEvent().getDate()+" <br/>"+
                            "Локација: "+transaction.getTicket().getEvent().getLocation()+" <br/>"+
                            "Време: "+transaction.getTicket().getTime()+" <br/>"+
                            "Ви благодариме, FINKI Tickets Team",
                    "text/html; charset=UTF-8");
        }
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

    private void message(String to, String name, int status) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noreply.tickets.finki@gmail.com", "FinkiTickets1");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("noreply.tickets.finki@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));


        if (status == 0){
            msg.setSubject("Успешна регистрација");
            msg.setContent("Почитувани, "+name+"<br/> " +
                            "Вашата регистрација беше успешна! <br/>" +
                            "Ви благодариме, FINKI Tickets Team",
                    "text/html; charset=UTF-8");
        } else if (status == 1){
            msg.setSubject("Промена на податоци");
            msg.setContent("Почитувани, "+name+"<br/> " +
                            "Вашите информации се променети во нашиот систем! <br/>" +
                            "Ви благодариме, FINKI Tickets Team",
                    "text/html; charset=UTF-8");
        } else if(status == 2){
            msg.setSubject("Избришана сметка");
            msg.setContent("Почитувани, "+name+"<br/> " +
                            "вашата сметка е избришана од нашиот систем! <br/>" +
                            "Ви благодариме, FINKI Tickets Team",
                    "text/html; charset=UTF-8");
        }
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

}
