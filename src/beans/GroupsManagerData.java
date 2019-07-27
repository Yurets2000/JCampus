package beans;

import dao.GroupDao;
import dao.StudentDao;
import dao.TeacherDao;
import dto.Group;
import dto.Student;
import dto.Teacher;
import dto.managers.GroupManager;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GroupsManagerData {

    @ManagedProperty(value = "#{groupData}")
    private GroupData groupData;
    @ManagedProperty(value = "#{studentData}")
    private StudentData studentData;
    @ManagedProperty(value = "#{teacherData}")
    private TeacherData teacherData;

    public void setGroupData(GroupData groupData) {
        this.groupData = groupData;
    }

    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }

    public void setTeacherData(TeacherData teacherData) {
        this.teacherData = teacherData;
    }

    public String redirect() {
        groupData.refreshGroupBeans();
        studentData.refreshStudentBeans();
        teacherData.refreshTeacherBeans();
        return "groupsManager.xhtml";
    }

    public void setStudentToGroup() {
        Student student = studentData.getStudentBean().getElement();
        Group group = groupData.getGroupBean().getElement();
        if (student.getGroup() != null) {
            GroupManager.removeStudentFromGroup(student, student.getGroup());
        }
        GroupManager.addStudentToGroup(student, group);
        Session session = HibernateUtil.getSessionFactory().openSession();
        StudentDao studentDao = new StudentDao(session);
        GroupDao groupDao = new GroupDao(session);
        studentDao.update(student);
        groupDao.update(group);
        session.close();
        studentData.setStudentBean(new TableElement<>(new Student()));
        groupData.setGroupBean(new TableElement<>(new Group()));
    }

    public void setCuratorToGroup() {
        Group group = groupData.getGroupBean().getElement();
        Teacher teacher = teacherData.getTeacherBean().getElement();
        Teacher oldTeacher = group.getCurator();
        Group oldGroup = teacher.getGroup();

        Session session = HibernateUtil.getSessionFactory().openSession();
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
        session.close();
        teacherData.setTeacherBean(new TableElement<>(new Teacher()));
        groupData.setGroupBean(new TableElement<>(new Group()));
    }

}
