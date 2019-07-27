package dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

    private static final long serialVersionUID = 8702898212494691499L;

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    private int hours;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany
    @JoinTable(
            name = "course_groups",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    public Course() {
    }

    public Course(String name, int hours, Teacher teacher) {
        this.name = name;
        this.hours = hours;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
