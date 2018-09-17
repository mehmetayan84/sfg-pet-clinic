package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    protected Map<Long, T> map = new HashMap();

    Set<T> findAll() {
        return new HashSet<T>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(T t) {

        if(t != null) {
            if(t.getId() == null) {
                t.setId(this.getNextId());
            }
            map.put(t.getId(), t);
        }else {
            throw new RuntimeException("Object cannot be null");
        }

        return t;

    }

    void deleteById(ID id) {
        map.remove(id);
    }

    void delete(T t) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(t));
    }

    private Long getNextId() {
        if(map.size() == 0)
            return 1L;
        else
            return Collections.max(map.keySet()) + 1L;

//        Long nextId = null;
//
//        try {
//            nextId = Collections.max(map.keySet()) + 1L;
//        }catch(NoSuchElementException ex) {
//            nextId = 1L;
//        }
//
//        return nextId;


    }
}
