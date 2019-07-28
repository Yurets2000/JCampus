package utils;

import dao.CourseDao;
import dao.GroupDao;
import dao.StudentDao;
import dao.TeacherDao;
import dao.managers.CourseManager;
import dao.managers.GroupManager;
import dto.Course;
import dto.Group;
import dto.Student;
import dto.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HibernateUtil.createSessionFactory();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();
        StudentDao studentDao = new StudentDao(session);
        TeacherDao teacherDao = new TeacherDao(session);
        GroupDao groupDao = new GroupDao(session);
        CourseDao courseDao = new CourseDao(session);

        Student s1 = new Student("Yura", "Bezlyudnyy", 19, 'm', null);
        Student s2 = new Student("Stas", "Bezpalko", 18, 'm', null);
        Student s3 = new Student("Valentin", "Lesyk", 20, 'm', null);
        Student s4 = new Student("Yulia", "Koval", 19, 'w', null);
        Student s5 = new Student("Andrey", "Grizachenko", 19, 'm', null);
        Student s6 = new Student("Maria", "Grachova", 19, 'w', null);
        Student s7 = new Student("Misha", "Dragan", 19, 'm', null);
        studentDao.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7));

        Teacher t1 = new Teacher("Bogdan", "Mart", 35, 'm', "097-207-15-53");
        Teacher t2 = new Teacher("Serhiy", "Telenyk", 67, 'm', "063-203-18-43");
        teacherDao.saveAll(Arrays.asList(t1, t2));

        Group g1 = new Group("IA-71", "ia-71@gmail.com");
        Group g2 = new Group("IA-72", "ia-72@gmail.com");
        groupDao.saveAll(Arrays.asList(g1, g2));

        session.beginTransaction();
        Set<Student> groupStudents1 = new HashSet<>(Arrays.asList(s1, s2, s3, s4, s5));
        Set<Student> groupStudents2 = new HashSet<>(Arrays.asList(s6, s7));
        groupStudents1.forEach(student -> GroupManager.addStudentToGroup(student, g1));
        groupStudents2.forEach(student -> GroupManager.addStudentToGroup(student, g2));

        g1.setLeader(s1);
        g2.setLeader(s6);
        GroupManager.setCuratorToGroup(t1, g1);
        GroupManager.setCuratorToGroup(t2, g2);
        session.getTransaction().commit();

        Course c1 = new Course("Discrete math", 150, null);
        Course c2 = new Course("Trpz", 120, null);
        courseDao.saveAll(Arrays.asList(c1, c2));

        session.beginTransaction();
        CourseManager.addCourseToTeacher(c1, t2);
        CourseManager.addCourseToTeacher(c2, t1);
        CourseManager.addCourseToGroup(c1, g1);
        CourseManager.addCourseToGroup(c1, g2);
        CourseManager.addCourseToGroup(c2, g1);
        CourseManager.addCourseToGroup(c2, g2);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateUtil.shutdown();
    }
}
