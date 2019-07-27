package beans;

import dao.GroupDao;
import dto.Group;
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
public class GroupData {

    private TableElement<Group> groupBean = new TableElement<>(new Group());
    private List<TableElement<Group>> groupBeans;
    private Map<String, Group> groupsMap;

    public GroupData() {
        refreshGroupBeans();
    }

    public TableElement<Group> getGroupBean() {
        return groupBean;
    }

    public void setGroupBean(TableElement<Group> groupBean) {
        this.groupBean = groupBean;
    }

    public List<TableElement<Group>> getGroupBeans() {
        return groupBeans;
    }

    public Map<String, Group> getGroupsMap() {
        return groupsMap;
    }

    public void addGroup() {
        groupBeans.add(groupBean);
        Group element = groupBean.getElement();
        groupsMap.put(element.toString(), element);

        Session session = HibernateUtil.getSessionFactory().openSession();
        GroupDao groupDao = new GroupDao(session);
        groupDao.save(groupBean.getElement());
        session.close();
        groupBean = new TableElement<>(new Group());
    }

    public void editGroup(TableElement<Group> groupBean) {
        groupsMap.remove(groupBean.getElement().toString());
        groupBean.setEditable(true);
    }

    public void updateGroup(TableElement<Group> groupBean) {
        Group element = groupBean.getElement();
        groupsMap.put(element.toString(), element);

        groupBean.setEditable(false);
        Session session = HibernateUtil.getSessionFactory().openSession();
        GroupDao groupDao = new GroupDao(session);
        groupDao.update(groupBean.getElement());
        session.close();
    }

    public void deleteGroup(TableElement<Group> groupBean) {
        groupBeans.remove(groupBean);
        groupsMap.remove(groupBean.getElement().toString());

        Session session = HibernateUtil.getSessionFactory().openSession();
        GroupDao groupDao = new GroupDao(session);
        groupDao.delete(groupBean.getElement());
        session.close();
    }

    public String redirect() {
        refreshGroupBeans();
        return "groups.xhtml";
    }

    public void refreshGroupBeans() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        GroupDao groupDao = new GroupDao(session);
        List<Group> groups = groupDao.getAll();
        groupBeans = groups.stream().map(TableElement<Group>::new).collect(Collectors.toList());
        session.close();
        refreshGroupsMap();
    }

    private void refreshGroupsMap() {
        groupsMap = new LinkedHashMap<>();
        groupBeans.stream().map(TableElement::getElement)
                .forEach(group -> groupsMap.put(group.toString(), group));
    }
}
