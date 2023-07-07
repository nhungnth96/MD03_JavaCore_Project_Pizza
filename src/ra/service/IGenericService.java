package ra.service;

import ra.model.product.Category;
import ra.model.user.User;

import java.util.List;

public interface IGenericService<T,E> {
    List<T> getAll();
    void save(T t);
    void delete(E e);
    T findById(E e);
    int getNewId();
}
