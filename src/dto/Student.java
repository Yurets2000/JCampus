package dto;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person {

    private static final long serialVersionUID = -6429325332949398199L;

    @ManyToOne
    private Group group;

    public Student() {
    }

    public Student(String name, String surname, int age, char sex, Group group) {
        super(name, surname, age, sex);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
