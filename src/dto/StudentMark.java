package dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studentMarks")
public class StudentMark implements Serializable {

    private static final long serialVersionUID = 1055106861068596695L;

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Lesson lesson;
    @OneToOne
    private Student student;
    private float mark;

    public StudentMark() {
    }

    public StudentMark(Lesson lesson, Student student, float mark) {
        this.lesson = lesson;
        this.student = student;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
