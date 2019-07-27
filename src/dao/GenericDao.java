package dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public abstract class GenericDao<T> implements Dao<T> {

    protected Session session;
    private Class<T> persistentClass;

    public GenericDao(Session session) {
        this.session = session;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> get(int id){
        return Optional.of(session.get(persistentClass, id));
    }

    @Override
    public List<T> getAll(){
        return getByCriteria();
    }

    @Override
    public void save(T t) {
        executeInTransaction(s -> s.save(t));
    }

    @Override
    public void saveAll(List<T> list) {
        list.forEach(this::save);
    }

    @Override
    public void update(T t) {
        executeInTransaction(s -> s.merge(t));
    }

    @Override
    public void refresh(T t) {
        executeInTransaction(s -> s.refresh(t));
    }

    @Override
    public void delete(T t) {
        executeInTransaction(s -> s.delete(t));
    }

    protected final void executeInTransaction(Consumer<Session> action){
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            action.accept(session);
            tx.commit();
        }catch (RuntimeException ex){
            tx.rollback();
            throw ex;
        }
    }

    protected final List<T> getByCriteria(Criterion... criterion) {
        Criteria crit = session.createCriteria(persistentClass);
        for (Criterion c : criterion) {
            crit.add(c);
        }

        return crit.list();
    }
}
