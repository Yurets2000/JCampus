package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GroupCoursesData {

    @ManagedProperty(value = "#{courseData}")
    private CourseData courseData;
    @ManagedProperty(value = "#{groupData}")
    private GroupData groupData;

    public void setCourseData(CourseData courseData) {
        this.courseData = courseData;
    }

    public void setGroupData(GroupData groupData) {
        this.groupData = groupData;
    }

    public String redirect(){
        groupData.refreshGroupBeans();
        courseData.refreshCourseBeans();
        return "groupCourses.xhtml";
    }
}
