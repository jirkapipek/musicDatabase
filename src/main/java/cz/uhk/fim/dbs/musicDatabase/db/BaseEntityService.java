package cz.uhk.fim.dbs.musicDatabase.db;

import java.util.List;

public interface BaseEntityService<T> {
    List<T> findAll();
    T findById(long id);
    T create(T t);
    T update(T t);
    void deleteById(long id);
}
