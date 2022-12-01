package top.jiangliuhong.olcp.data.entity;

import java.util.*;

public class EntityListImpl implements EntityList {

    private final List<EntityValue> values;

    public EntityListImpl(int initialCapacity) {
        values = new ArrayList<>(initialCapacity);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public Iterator<EntityValue> iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(EntityValue entityValue) {
        return values.add(entityValue);
    }

    @Override
    public boolean remove(Object o) {
        return values.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return values.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends EntityValue> c) {
        return values.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends EntityValue> c) {
        return values.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return values.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return values.retainAll(c);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public EntityValue get(int index) {
        return values.get(index);
    }

    @Override
    public EntityValue set(int index, EntityValue element) {
        return values.set(index, element);
    }

    @Override
    public void add(int index, EntityValue element) {
        values.add(index, element);
    }

    @Override
    public EntityValue remove(int index) {
        return values.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @Override
    public ListIterator<EntityValue> listIterator() {
        return values.listIterator();
    }

    @Override
    public ListIterator<EntityValue> listIterator(int index) {
        return values.listIterator(index);
    }

    @Override
    public List<EntityValue> subList(int fromIndex, int toIndex) {
        return values.subList(fromIndex, toIndex);
    }
}
