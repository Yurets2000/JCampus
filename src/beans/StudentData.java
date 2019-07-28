package beans;

import dao.StudentDao;
import dto.Student;
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
public class StudentData {

    private TableElement<Student> studentBean = new TableElement<>(new Student());
    private List<TableElement<Student>> studentBeans;
    private Map<String, Student> studentsMap;

    public StudentData() {
        refreshStudentBeans();
    }

    public TableElement<Student> getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(TableElement<Student> studentBean) {
        this.studentBean = studentBean;
    }

    public List<TableElement<Student>> getStudentBeans() {
        return studentBeans;
    }

    public Map<String, Student> getStudentsMap() {
        return studentsMap;
    }

    public void addStudent() {
        studentBeans.add(studentBean);
        Student element = studentBean.getElement();
        studentsMap.put(element.toString(), element);

        Session session = HibernateUtil.getSessionFactory().openSession();
        StudentDao studentDao = new StudentDao(session);
        studentDao.save(studentBean.getElement());
        session.close();
        studentBean = new TableElement<>(new Student());
    }

    public void editStudent(TableElement<Student> studentBean) {
        studentsMap.remove(studentBean.getElement().toString());
        studentBean.setEditable(true);
    }

    public void updateStudent(TableElement<Student> studentBean) {
        Student element = studentBean.getElement();
        studentsMap.put(element.toString(), element);

        studentBean.setEditable(false);
        Session session = HibernateUtil.getSessionFactory().openSession();
        StudentDao studentDao = new StudentDao(session);
        studentDao.update(studentBean.getElement());
        session.close();
    }

    public void deleteStudent(TableElement<Student> studentBean) {
        studentBeans.remove(studentBean);
        studentsMap.remove(studentBean.getElement().toString());

        Session session = HibernateUtil.getSessionFactory().openSession();
        StudentDao studentDao = new StudentDao(session);
        studentDao.delete(studentBean.getElement());
        session.close();
    }

    public String redirect() {
        refreshStudentBeans();
        return "students.xhtml";
    }

    public void refreshStudentBeans() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        StudentDao studentDao = new StudentDao(session);
        List<Student> students = studentDao.getAll();
        studentBeans = students.stream().map(TableElement<Student>::new).collect(Collectors.toList());
        session.close();
        refreshStudentsMap();
    }

    private void refreshStudentsMap() {
        studentsMap = new LinkedHashMap<>();
        studentBeans.stream().map(TableElement::getElement)
                .forEach(student -> studentsMap.put(student.toString(), student));
    }
}
