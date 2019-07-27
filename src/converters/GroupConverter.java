package converters;

import beans.GroupData;
import dto.Group;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converters.GroupConverter")
public class GroupConverter implements Converter<Group> {

    @Override
    public Group getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        GroupData data = (GroupData) facesContext.getExternalContext().getSessionMap().get("groupData");
        return data.getGroupsMap().get(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Group group) {
        return group == null ? "" : group.toString();
    }
}
