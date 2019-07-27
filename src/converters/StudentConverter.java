package converters;

import beans.StudentData;
import dto.Student;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converters.StudentConverter")
public class StudentConverter implements Converter<Student> {

    @Override
    public Student getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        StudentData data = (StudentData) facesContext.getExternalContext().getSessionMap().get("studentData");
        return data.getStudentsMap().get(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Student student) {
        return student == null ? "" : student.toString();
    }
}
