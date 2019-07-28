package beans;

import dao.managers.GroupManagerDao;
import dto.Group;
import dto.Student;
import dto.Teacher;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GroupsManagerData {

    private Teacher teacher;
    private Student student;
    private Group group;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String redirect() {
        return "groupsManager.xhtml";
    }

    public void setStudentToGroup() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        GroupManagerDao groupManagerDao = new GroupManagerDao(session);
        groupManagerDao.setStudentToGroup(student, group);
        session.close();
        student = null;
        group = null;
    }

    public void setCuratorToGroup() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        GroupManagerDao groupManagerDao = new GroupManagerDao(session);
        groupManagerDao.setCuratorToGroup(teacher, group);
        session.close();
        teacher = null;
        group = null;
    }

}
