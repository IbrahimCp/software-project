package kz.ibrahim.SoftwareProject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @Column(name = "problem_url")
    private String url;

    @Column(name = "problem_name")
    private String name;

    public Problem( String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Problem() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
