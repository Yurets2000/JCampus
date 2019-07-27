package dao;

import dto.Group;
import dto.Student;
import dto.managers.GroupManager;
import org.hibernate.Session;

public class StudentDao extends GenericDao<Student> {

    public StudentDao(Session session) {
        super(session);
    }

    @Override
    public void save(Student student) {
        Group group = student.getGroup();
        if(group != null) {
            GroupManager.addStudentToGroup(student, group);
        }
        super.save(student);
    }

    @Override
    public void delete(Student student) {
        Group group = student.getGroup();
        if(group != null) {
            Student leader = group.getLeader();
            if(leader != null && student.equals(group.getLeader())){
                group.setLeader(null);
            }
            GroupManager.removeStudentFromGroup(student, group);
            session.merge(group);
        }
        Student updated = (Student) session.merge(student);
        super.delete(updated);
    }
}
