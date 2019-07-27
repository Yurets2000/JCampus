package dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 7803657307858633175L;

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToOne
    Student leader;
    @OneToOne
    Teacher curator;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "group")
    private Set<Student> students = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "groups")
    private Set<Course> courses = new HashSet<>();

    public Group() {
    }

    public Group(String name, String email) {
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student getLeader() {
        return leader;
    }

    public void setLeader(Student leader) {
        this.leader = leader;
    }

    public Teacher getCurator() {
        return curator;
    }

    public void setCurator(Teacher curator) {
        this.curator = curator;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id;
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
