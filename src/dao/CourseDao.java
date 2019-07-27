package dao;

import dto.Course;
import org.hibernate.Session;

public class CourseDao extends GenericDao<Course> {

    public CourseDao(Session session) {
        super(session);
    }

}
