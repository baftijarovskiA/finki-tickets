package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Event;

import java.util.List;

public interface EventRepository {

    List<Event> getAllEvents();

    List<Event> getAllApprovedEvents();

    List<Event> getUnexpiredEvents();

    List<Event> getEventsByCategory(Long categoryId);

    Event getEventById(Long id);

    Event getEventBySlug(String slug);

    void addNewEvent(Event event, String userId);

    void toggleApproved(Long id);

    void eventExpired(Long id);

    void deleteEventById(Long id);
}
