package mk.ukim.finki.eimt.tickets.FinkiTickets.Model;

import javax.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;  // choose

    private String name;  // enter

    private String slug;

    private String location;  // enter

    private String cover;  // enter

    private String date; // enter

    private boolean expired;

    private boolean approved;

    private String created;

    private String updated;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }

    public void setSlug(String slug) { this.slug = slug; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getCover() { return cover; }

    public void setCover(String cover) { this.cover = cover; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public boolean isExpired() { return expired; }

    public void setExpired(boolean expired) { this.expired = expired; }

    public boolean isApproved() { return approved; }

    public void setApproved(boolean approved) { this.approved = approved; }

    public String getCreated() { return created; }

    public void setCreated(String created) { this.created = created; }

    public String getUpdated() { return updated; }

    public void setUpdated(String updated) { this.updated = updated; }
}
