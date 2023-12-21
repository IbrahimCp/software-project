package kz.ibrahim.SoftwareProject.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "contests")
public class Contest {

    @Id
    @Column(name = "contest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contest_url")
    private String url;

    @Column(name = "contest_name")
    private String name;

    @Column(name = "contest_date")
    private LocalDateTime contestDate;

    public LocalDateTime getContestDate() {
        return contestDate;
    }

    public void setContestDate(LocalDateTime contestDate) {
        this.contestDate = contestDate;
    }



    public Contest() {

    }

    public Contest(Integer id, String url, String name, LocalDateTime contestDate) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.contestDate = contestDate;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", contestDate=" + contestDate +
                '}';
    }
}
