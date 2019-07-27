package dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lesson implements Serializable {

    private static final long serialVersionUID = 3423519341365620525L;

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Course course;
    @Column(nullable = false)
    private String theme;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<StudentMark> marks;

    public Lesson() {
    }

    public Lesson(Course course, String theme) {
        this.course = course;
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Set<StudentMark> getMarks() {
        return marks;
    }

    public void setMarks(Set<StudentMark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return course + ": " + theme;
    }
}
