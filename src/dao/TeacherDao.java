package dao;

import dto.Group;
import dto.Teacher;
import dto.managers.GroupManager;
import org.hibernate.Session;

public class TeacherDao extends GenericDao<Teacher> {

    public TeacherDao(Session session) {
        super(session);
    }

    @Override
    public void delete(Teacher teacher) {
        Group group = teacher.getGroup();
        if(group != null) {
            GroupManager.setCuratorToGroup(null, group);
            session.merge(group);
        }
        Teacher updated = (Teacher) session.merge(teacher);
        super.delete(updated);
    }
}
