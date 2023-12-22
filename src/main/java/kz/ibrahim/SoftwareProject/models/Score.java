package kz.ibrahim.SoftwareProject.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "score_table")
public class Score {
    @Id
    @Column(name = "rank")
    private Integer rank;

    @Column(name = "score")
    private Integer point;

    public Score(Integer rank, Integer point) {
        this.rank = rank;
        this.point = point;
    }

    public Score() {
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
