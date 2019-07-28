package converters;

import beans.CourseData;
import dto.Course;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converters.CourseConverter")
public class CourseConverter implements Converter<Course> {

    @Override
    public Course getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        CourseData data = (CourseData) facesContext.getExternalContext().getSessionMap().get("courseData");
        return data.getCoursesMap().get(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Course course) {
        return course == null ? "" : course.toString();
    }
}
