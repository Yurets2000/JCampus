package beans;

import dao.managers.CourseManagerDao;
import dto.Course;
import dto.Group;
import dto.Teacher;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CoursesManagerData {

    private Course course;
    private Teacher teacher;
    private Group group;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String redirect() {
        return "coursesManager.xhtml";
    }

    public void setCourseToTeacher(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseManagerDao courseManagerDao = new CourseManagerDao(session);
        courseManagerDao.setCourseToTeacher(course, teacher);
        session.close();
        course = null;
        teacher = null;
    }

    public void removeCourseFromTeacher(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseManagerDao courseManagerDao = new CourseManagerDao(session);
        courseManagerDao.removeCourseFromTeacher(course, teacher);
        session.close();
        course = null;
        teacher = null;
    }

    public void addCourseToGroup(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseManagerDao courseManagerDao = new CourseManagerDao(session);
        courseManagerDao.addCourseToGroup(course, group);
        session.close();
        course = null;
        group = null;
    }

    public void removeCourseFromGroup(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseManagerDao courseManagerDao = new CourseManagerDao(session);
        courseManagerDao.removeCourseFromGroup(course, group);
        session.close();
        course = null;
        group = null;
    }
}
