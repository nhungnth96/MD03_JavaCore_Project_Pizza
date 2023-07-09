package ra.controller;

import java.util.List;

public interface IGenericController<T,E> {
    public List<T> getAll();
    public void save(T t) ;
    public void delete(E id);
    public T findById(E id);
    public int getNewId();
}
