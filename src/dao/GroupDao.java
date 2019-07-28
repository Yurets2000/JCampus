package dao;

import dao.managers.GroupManager;
import dto.Group;
import dto.Student;
import dto.Teacher;
import org.hibernate.Session;

import java.util.Set;

public class GroupDao extends GenericDao<Group> {

    public GroupDao(Session session) {
        super(session);
    }

    @Override
    public void delete(Group group) {
        Set<Student> studentSet = group.getStudents();
        Student[] students = studentSet.toArray(new Student[studentSet.size()]);
        for(Student student : students){
            GroupManager.removeStudentFromGroup(student, group);
            session.merge(student);
        }
        Teacher curator = group.getCurator();
        GroupManager.setCuratorToGroup(null, group);
        session.merge(curator);
        Group updated = (Group) session.merge(group);
        super.delete(updated);
    }
}
