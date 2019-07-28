package beans;

import dao.CourseDao;
import dto.Course;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class CourseData {

    private TableElement<Course> courseBean = new TableElement<>(new Course());
    private List<TableElement<Course>> courseBeans;
    private Map<String, Course> coursesMap;

    public CourseData() {
        refreshCourseBeans();
    }

    public TableElement<Course> getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(TableElement<Course> courseBean) {
        this.courseBean = courseBean;
    }

    public List<TableElement<Course>> getCourseBeans() {
        return courseBeans;
    }

    public Map<String, Course> getCoursesMap() {
        return coursesMap;
    }

    public void addCourse() {
        courseBeans.add(courseBean);
        Course element = courseBean.getElement();
        coursesMap.put(element.toString(), element);

        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseDao courseDao = new CourseDao(session);
        courseDao.save(courseBean.getElement());
        session.close();
        courseBean = new TableElement<>(new Course());
    }

    public void editCourse(TableElement<Course> courseBean) {
        coursesMap.remove(courseBean.getElement().toString());
        courseBean.setEditable(true);
    }

    public void updateCourse(TableElement<Course> courseBean) {
        Course element = courseBean.getElement();
        coursesMap.put(element.toString(), element);

        courseBean.setEditable(false);
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseDao courseDao = new CourseDao(session);
        courseDao.update(courseBean.getElement());
        session.close();
    }

    public void deleteCourse(TableElement<Course> courseBean) {
        courseBeans.remove(courseBean);
        coursesMap.remove(courseBean.getElement().toString());

        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseDao courseDao = new CourseDao(session);
        courseDao.delete(courseBean.getElement());
        session.close();
    }

    public String redirect() {
        refreshCourseBeans();
        return "courses.xhtml";
    }

    public void refreshCourseBeans() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseDao courseDao = new CourseDao(session);
        List<Course> courses = courseDao.getAll();
        courseBeans = courses.stream().map(TableElement<Course>::new).collect(Collectors.toList());
        session.close();
        refreshCoursesMap();
    }

    private void refreshCoursesMap() {
        coursesMap = new LinkedHashMap<>();
        courseBeans.stream().map(TableElement::getElement)
                .forEach(group -> coursesMap.put(group.toString(), group));
    }
}
