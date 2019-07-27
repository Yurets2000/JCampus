package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    void save(T t);

    void saveAll(List<T> list);

    void update(T t);

    void refresh(T t);

    void delete(T t);

}
