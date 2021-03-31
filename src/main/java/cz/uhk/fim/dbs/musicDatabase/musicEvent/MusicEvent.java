package cz.uhk.fim.dbs.musicDatabase.musicEvent;

import cz.uhk.fim.dbs.musicDatabase.user.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;

@Entity
@Table(name = "music_event")
public class MusicEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] poster;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User user;


    public MusicEvent() {
    }

    public MusicEvent(long id, String title, String content, Date startDate, Date endDate, byte[] poster, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.poster = poster;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public byte[] getPoster() {
        return poster;
    }

    public String getImageBase64() {
        String encodedImage = Base64.getEncoder().encodeToString(poster);
        return encodedImage;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
