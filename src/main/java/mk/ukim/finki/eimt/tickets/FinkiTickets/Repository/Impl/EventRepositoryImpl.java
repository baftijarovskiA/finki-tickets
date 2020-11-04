package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Event;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.EventRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.EventJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private EventJpaRepository repository;
    private UserJpaRepository userJpaRepository;

    public EventRepositoryImpl(EventJpaRepository repository, UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.repository = repository;
    }


    @Override
    public List<Event> getAllEvents() {
        for (Event e : repository.findAll()) {
            if (checkDueDate(e.getDate())){
                eventExpired(e.getId());
            }
        }
        return repository.findAll();
    }

    @Override
    public List<Event> getAllApprovedEvents() {
        List<Event> eventList = new ArrayList<>();
        for (Event e : repository.findAll()) {
            if (e.isApproved()){
                eventList.add(e);
            }
        }
        return eventList;
    }

    @Override
    public List<Event> getUnexpiredEvents() {
        List<Event> eventList = new ArrayList<>();
        for (Event e : repository.findAll()) {
            if (checkDueDate(e.getDate())){
                eventExpired(e.getId());
                continue;
            }
            if (!e.isExpired() && e.isApproved()){
                eventList.add(e);
            }
        }
        return eventList;
    }

    @Override
    public List<Event> getEventsByCategory(Long categoryId) {
        List<Event> eventList = new ArrayList<>();
        for (Event e : repository.findAll()) {
            if (!e.isExpired() && e.isApproved()) {
                if (e.getCategory().getId().equals(categoryId)) {
                    eventList.add(e);
                }
            }
        }
        return eventList;
    }

    @Override
    public Event getEventById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Event getEventBySlug(String slug) {
        Event event = null;
        for (Event e : getAllApprovedEvents()) {
            if (e.getSlug().equals(slug)){
                event = e;
            }
        }
        return event;
    }

    @Override
    public void addNewEvent(Event event, String userId) {
        event.setUser(currentUser(userId));
        event.setCreated(formattedDateTime());
        event.setUpdated(formattedDateTime());
        event.setApproved(false);
        event.setExpired(false);
        event.setSlug(slugMaker(event.getName()));
        repository.save(event);
    }

    @Override
    public void toggleApproved(Long id) {

        Event e = repository.findById(id).get();
        e.setApproved(!e.isApproved());

        e.setUpdated(formattedDateTime());
        repository.save(e);
    }

    @Override
    public void eventExpired(Long id) {
        Event e = repository.findById(id).get();
        e.setExpired(true);

        e.setUpdated(formattedDateTime());
        repository.save(e);
    }

    @Override
    public void deleteEventById(Long id) {
        repository.delete(repository.findById(id).get());
    }

    private User currentUser(String Id){
        User u = null;
        if (!Id.equals("nan")){
            u = userJpaRepository.findById(Long.parseLong(Id)).get();
        }
        return u;
    }

    private String slugMaker(String name) {
        String lowerAndNonCharacters = name.toLowerCase().replaceAll("[^a-z0-9 ]", "");
        String onlyOneSpace = lowerAndNonCharacters.replaceAll("[ ]{2,}", "-");
        return onlyOneSpace.replace(' ', '-');
    }

    private String formattedDateTime(){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        return FORMATTER.format(localDateTime);
    }

    private boolean checkDueDate(String date){
        LocalDate currentDate = LocalDate.now();
        LocalDate localDate = LocalDate.parse(date);
        return currentDate.equals(localDate.plusDays(1));
    }
}
