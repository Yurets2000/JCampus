package dao.managers;

import dao.CourseDao;
import dao.GroupDao;
import dao.TeacherDao;
import dto.Course;
import dto.Group;
import dto.Teacher;
import org.hibernate.Session;

public class CourseManagerDao {

    private Session session;

    public CourseManagerDao(Session session){
        this.session = session;
    }

    public void setCourseToTeacher(Course course, Teacher teacher) {
        CourseDao courseDao = new CourseDao(session);
        TeacherDao teacherDao = new TeacherDao(session);
        Teacher oldTeacher = course.getTeacher();
        CourseManager.removeCourseFromTeacher(course, oldTeacher);
        CourseManager.addCourseToTeacher(course, teacher);
        courseDao.update(course);
        teacherDao.update(teacher);
        teacherDao.update(oldTeacher);
    }

    public void removeCourseFromTeacher(Course course, Teacher teacher) {
        CourseDao courseDao = new CourseDao(session);
        TeacherDao teacherDao = new TeacherDao(session);
        CourseManager.removeCourseFromTeacher(course, teacher);
        courseDao.update(course);
        teacherDao.update(teacher);
    }

    public void addCourseToGroup(Course course, Group group) {
        CourseDao courseDao = new CourseDao(session);
        GroupDao groupDao = new GroupDao(session);
        CourseManager.addCourseToGroup(course, group);
        courseDao.update(course);
        groupDao.update(group);
    }

    public void removeCourseFromGroup(Course course, Group group) {
        CourseDao courseDao = new CourseDao(session);
        GroupDao groupDao = new GroupDao(session);
        CourseManager.removeCourseFromGroup(course, group);
        courseDao.update(course);
        groupDao.update(group);
    }
}
