package mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Event;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl.EventRepositoryImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventRepositoryImpl repository;

    public EventServiceImpl(EventRepositoryImpl repository) { this.repository = repository; }

    @Override
    public List<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    @Override
    public List<Event> getAllApprovedEvents() {
        return repository.getAllApprovedEvents();
    }

    @Override
    public List<Event> getUnexpiredEvents() {
        return repository.getUnexpiredEvents();
    }

    @Override
    public List<Event> getEventsByCategory(Long categoryId) {
        return repository.getEventsByCategory(categoryId);
    }

    @Override
    public Event getEventById(Long id) {
        return repository.getEventById(id);
    }

    @Override
    public Event getEventBySlug(String slug) {
        return repository.getEventBySlug(slug);
    }

    @Override
    public void addNewEvent(Event event, String userId) {
        repository.addNewEvent(event, userId);
    }

    @Override
    public void toggleApproved(Long id) {
        repository.toggleApproved(id);
    }

    @Override
    public void eventExpired(Long id) {
        repository.eventExpired(id);
    }

    @Override
    public void deleteEventById(Long id) {
        repository.deleteEventById(id);
    }
}
