package guru.springframework.sfgpetclinic.services;

import java.util.Set;

public interface CrudService<T, ID> {

    public Set<T> findAll();

    public T findById(ID id);

    public T save(T t);

    public void delete(T t);

    public void deleteById(ID id);

}
