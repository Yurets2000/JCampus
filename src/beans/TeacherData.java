package beans;

import dao.TeacherDao;
import dto.Teacher;
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
public class TeacherData {

    private TableElement<Teacher> teacherBean = new TableElement<>(new Teacher());
    private List<TableElement<Teacher>> teacherBeans;
    private Map<String, Teacher> teachersMap;

    public TeacherData() {
        refreshTeacherBeans();
    }

    public TableElement<Teacher> getTeacherBean() {
        return teacherBean;
    }

    public void setTeacherBean(TableElement<Teacher> teacherBean) {
        this.teacherBean = teacherBean;
    }

    public List<TableElement<Teacher>> getTeacherBeans() {
        return teacherBeans;
    }

    public Map<String, Teacher> getTeachersMap() {
        return teachersMap;
    }

    public void addTeacher() {
        teacherBeans.add(teacherBean);
        Teacher element = teacherBean.getElement();
        teachersMap.put(element.toString(), element);

        Session session = HibernateUtil.getSessionFactory().openSession();
        TeacherDao teacherDao = new TeacherDao(session);
        teacherDao.save(teacherBean.getElement());
        session.close();
        teacherBean = new TableElement<>(new Teacher());
    }

    public void editTeacher(TableElement<Teacher> teacherBean) {
        teachersMap.remove(teacherBean.getElement().toString());
        teacherBean.setEditable(true);
    }

    public void updateTeacher(TableElement<Teacher> teacherBean) {
        Teacher element = teacherBean.getElement();
        teachersMap.put(element.toString(), element);

        teacherBean.setEditable(false);
        Session session = HibernateUtil.getSessionFactory().openSession();
        TeacherDao teacherDao = new TeacherDao(session);
        teacherDao.update(teacherBean.getElement());
        session.close();
    }

    public void deleteTeacher(TableElement<Teacher> teacherBean) {
        teacherBeans.remove(teacherBean);
        teachersMap.remove(teacherBean.getElement().toString());

        Session session = HibernateUtil.getSessionFactory().openSession();
        TeacherDao teacherDao = new TeacherDao(session);
        teacherDao.delete(teacherBean.getElement());
        session.close();
    }

    public String redirect() {
        refreshTeacherBeans();
        return "teachers.xhtml";
    }

    public void refreshTeacherBeans() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TeacherDao teacherDao = new TeacherDao(session);
        List<Teacher> teachers = teacherDao.getAll();
        teacherBeans = teachers.stream().map(TableElement<Teacher>::new).collect(Collectors.toList());
        session.close();
        refreshTeachersMap();
    }

    private void refreshTeachersMap() {
        teachersMap = new LinkedHashMap<>();
        teacherBeans.stream().map(TableElement::getElement)
                .forEach(teacher -> teachersMap.put(teacher.toString(), teacher));
    }
}
