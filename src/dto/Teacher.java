package dto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    private static final long serialVersionUID = -6935029403082646670L;

    @Column(unique = true)
    private String phone;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();
    @OneToOne
    private Group group;

    public Teacher() {
    }

    public Teacher(String name, String surname, int age, char sex, String phone) {
        super(name, surname, age, sex);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Course> getCourses() {
        return courses;
    }
}
