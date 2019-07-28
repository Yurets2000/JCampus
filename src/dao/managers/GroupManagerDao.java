package dao.managers;

import dao.GroupDao;
import dao.StudentDao;
import dao.TeacherDao;
import dto.Group;
import dto.Student;
import dto.Teacher;
import org.hibernate.Session;

public class GroupManagerDao {

    private Session session;

    public GroupManagerDao(Session session){
        this.session = session;
    }

    public void setStudentToGroup(Student student, Group group){
        if (student.getGroup() != null) {
            GroupManager.removeStudentFromGroup(student, student.getGroup());
        }
        GroupManager.addStudentToGroup(student, group);
        StudentDao studentDao = new StudentDao(session);
        GroupDao groupDao = new GroupDao(session);
        studentDao.update(student);
        groupDao.update(group);
    }

    public void setCuratorToGroup(Teacher teacher, Group group){
        Teacher oldTeacher = group.getCurator();
        Group oldGroup = teacher.getGroup();

        TeacherDao teacherDao = new TeacherDao(session);
        GroupDao groupDao = new GroupDao(session);

        if (oldTeacher != null) {
            GroupManager.setCuratorToGroup(null, group);
            teacherDao.update(oldTeacher);
        }
        if (oldGroup != null) {
            GroupManager.setCuratorToGroup(null, oldGroup);
            groupDao.update(oldGroup);
        }

        GroupManager.setCuratorToGroup(teacher, group);
        teacherDao.update(teacher);
        groupDao.update(group);
    }
}
