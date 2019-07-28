package dao.managers;

import dto.Course;
import dto.Group;
import dto.Teacher;

public class CourseManager {

    public static void addCourseToGroup(Course c, Group g){
        g.getCourses().add(c);
        c.getGroups().add(g);
    }

    public static void removeCourseFromGroup(Course c, Group g){
        g.getCourses().remove(c);
        c.getGroups().remove(g);
    }

    public static void addCourseToTeacher(Course c, Teacher t){
        t.getCourses().add(c);
        c.setTeacher(t);
    }

    public static void removeCourseFromTeacher(Course c, Teacher t){
        t.getCourses().remove(c);
        c.setTeacher(null);
    }
}
