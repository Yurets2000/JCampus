package dao.managers;

import dto.Group;
import dto.Student;
import dto.Teacher;

public class GroupManager {

    public static void addStudentToGroup(Student s, Group g){
        g.getStudents().add(s);
        s.setGroup(g);
    }

    public static void removeStudentFromGroup(Student s, Group g){
        g.getStudents().remove(s);
        s.setGroup(null);
    }

    public static void setCuratorToGroup(Teacher t, Group g){
        if(t == null){
            if(g.getCurator() != null){
                g.getCurator().setGroup(null);
            }
        }else{
            t.setGroup(g);
        }
        g.setCurator(t);
    }

}
