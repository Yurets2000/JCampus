package converters;

import beans.TeacherData;
import dto.Teacher;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converters.TeacherConverter")
public class TeacherConverter implements Converter<Teacher> {

    @Override
    public Teacher getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        TeacherData data = (TeacherData) facesContext.getExternalContext().getSessionMap().get("teacherData");
        return data.getTeachersMap().get(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Teacher teacher) {
        return teacher == null ? "" : teacher.toString();
    }
}
