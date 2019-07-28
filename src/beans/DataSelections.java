package beans;

import dto.Course;
import dto.Group;
import dto.Student;
import dto.Teacher;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean
@SessionScoped
public class DataSelections {

    @ManagedProperty(value = "#{studentData}")
    private StudentData studentData;
    @ManagedProperty(value = "#{courseData}")
    private CourseData courseData;
    @ManagedProperty(value = "#{groupData}")
    private GroupData groupData;

    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }

    public void setCourseData(CourseData courseData) {
        this.courseData = courseData;
    }

    public void setGroupData(GroupData groupData) {
        this.groupData = groupData;
    }

    public Set<Student> getStudentsByGroup(Group group) {
        if(group == null) return new LinkedHashSet<>();
        return group.getStudents();
    }

    public Set<Course> getCoursesByGroup(Group group) {
        if(group == null) return new LinkedHashSet<>();
        return group.getCourses();
    }

    public Set<Course> getCoursesByTeacher(Teacher teacher) {
        if(teacher == null) return new LinkedHashSet<>();
        return teacher.getCourses();
    }

    public Map<String, Student> getStudentsByGroupMap(Group group) {
        Map<String, Student> studentsMap = new LinkedHashMap<>();
        getStudentsByGroup(group).forEach(student -> studentsMap.put(student.toString(), student));
        return studentsMap;
    }

    public Map<String, Course> getCoursesByGroupMap(Group group){
        Map<String, Course> coursesMap = new LinkedHashMap<>();
        getCoursesByGroup(group).forEach(course -> coursesMap.put(course.toString(), course));
        return coursesMap;
    }

    public Map<String, Course> getMissingCoursesByGroupMap(Group group){
        if(group == null) return new LinkedHashMap<>();
        Map<String, Course> coursesMap = new LinkedHashMap<>();
        Set<Course> courseSet = new LinkedHashSet<>(courseData.getCoursesMap().values());
        courseSet.removeAll(group.getCourses());
        courseSet.forEach(course -> coursesMap.put(course.toString(), course));
        return coursesMap;
    }

    public Map<String, Course> getCoursesByTeacherMap(Teacher teacher){
        Map<String, Course> coursesMap = new LinkedHashMap<>();
        getCoursesByTeacher(teacher).forEach(course -> coursesMap.put(course.toString(), course));
        return coursesMap;
    }

    public Map<String, Course> getMissingCoursesByTeacherMap(Teacher teacher){
        if(teacher == null) return new LinkedHashMap<>();
        Map<String, Course> coursesMap = new LinkedHashMap<>();
        List<Course> courseList = new LinkedList<>(courseData.getCoursesMap().values());
        courseList.removeAll(teacher.getCourses());
        courseList.forEach(course -> coursesMap.put(course.toString(), course));
        return coursesMap;
    }

}
