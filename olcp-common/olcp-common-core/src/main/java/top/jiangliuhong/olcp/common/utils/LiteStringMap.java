package top.jiangliuhong.olcp.common.utils;

import java.io.Serializable;
import java.util.*;

/**
 * 使用数组实现的有序的map
 */
public class LiteStringMap<V> implements Map<String, V>, Serializable {

    private static final long serialVersionUID = 362498820763181265L;
    private static final int DEFAULT_CAPACITY = 8;
    private static final Map<String, String> internedMap = new HashMap<>();

    public static String internString(String orig) {
        String interned = internedMap.get(orig);
        if (interned != null) return interned;
        // don't even check for null until we have to
        if (orig == null) return null;
        interned = orig.intern();
        internedMap.put(interned, interned);
        return interned;
    }

    private String[] keyArray;
    private V[] valueArray;
    private int lastIndex = -1;
    private transient boolean useManualIndex = false;


    public LiteStringMap() {
        init(DEFAULT_CAPACITY);
    }

    public LiteStringMap(int initialCapacity) {
        init(initialCapacity);
    }

    public int findIndex(String keyOrig) {
        if (keyOrig == null) return -1;
        return findIndexIString(internString(keyOrig));
    }

    public int findIndexIString(String key) {
        for (int i = 0; i <= lastIndex; i++) {
            if (keyArray[i].equals(key)) return i;
        }
        return -1;
    }

    public LiteStringMap<V> ensureCapacity(int capacity) {
        if (keyArray.length < capacity) {
            keyArray = Arrays.copyOf(keyArray, capacity);
            valueArray = Arrays.copyOf(valueArray, capacity);
        }
        return this;
    }

    public LiteStringMap<V> useManualIndex() {
        useManualIndex = true;
        return this;
    }

    @SuppressWarnings("unchecked")
    private void init(int capacity) {
        keyArray = new String[capacity];
        valueArray = (V[]) new Object[capacity];
    }

    @Override
    public int size() {
        return lastIndex + 1;
    }

    @Override
    public boolean isEmpty() {
        return lastIndex == -1;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) return false;
        return findIndex(key.toString()) != -1;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i <= lastIndex; i++) {
            if (valueArray[i] == null) {
                if (value == null) return true;
            } else {
                if (valueArray[i].equals(value)) return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (key == null) return null;
        int keyIndex = findIndex(key.toString());
        if (keyIndex == -1) return null;
        return valueArray[keyIndex];
    }

    public V getByString(String key) {
        int keyIndex = findIndex(key);
        if (keyIndex == -1) return null;
        return valueArray[keyIndex];
    }


    @Override
    public V put(String key, V value) {
        if (key == null) throw new IllegalArgumentException("LiteStringMap Key may not be null");
        return putByIString(internString(key), value);
    }

    public V putByIString(String key, V value) {
        // if ("pseudoId".equals(key)) { System.out.println("========= put no index " + key + ": " + value); new Exception("location").printStackTrace(); }
        int keyIndex = findIndexIString(key);
        if (keyIndex == -1) {
            lastIndex++;
            if (lastIndex >= keyArray.length) growArrays(null);
            keyArray[lastIndex] = key;
            valueArray[lastIndex] = value;
            return null;
        } else {
            V oldValue = valueArray[keyIndex];
            valueArray[keyIndex] = value;
            return oldValue;
        }
    }

    public V putByIString(String key, V value, int index) {
        // if ("pseudoId".equals(key)) { System.out.println("========= put index " + index + " key " + key + ": " + value); new Exception("location").printStackTrace(); }
        useManualIndex = true;
        if (index >= keyArray.length) growArrays(index + 1);
        if (index > lastIndex) lastIndex = index;
        if (keyArray[index] == null) {
            keyArray[index] = key;
            valueArray[index] = value;
            return null;
        } else {
            // identity compare for interned String
            if (key != keyArray[index])
                throw new IllegalArgumentException("Index " + index + " already has key " + keyArray[index] + ", cannot use with key " + key);
            V oldValue = valueArray[index];
            valueArray[index] = value;
            return oldValue;
        }
    }

    private void growArrays(Integer minLength) {
        int newLength = keyArray.length * 2;
        if (minLength != null && newLength < minLength) newLength = minLength;
        // System.out.println("=============================\n============= grow to " + newLength);
        keyArray = Arrays.copyOf(keyArray, newLength);
        valueArray = Arrays.copyOf(valueArray, newLength);
    }

    @Override
    public V remove(Object key) {
        if (key == null) return null;
        int keyIndex = findIndexIString(internString(key.toString()));
        return removeByIndex(keyIndex);
    }

    private V removeByIndex(int keyIndex) {
        if (keyIndex == -1) {
            return null;
        } else {
            V oldValue = valueArray[keyIndex];
            if (useManualIndex) {
                // with manual indexes don't shift entries, will cause manually specified indexes to be wrong
                keyArray[keyIndex] = null;
                valueArray[keyIndex] = null;
            } else {
                // shift all later values up one position
                for (int i = keyIndex; i < lastIndex; i++) {
                    keyArray[i] = keyArray[i + 1];
                    valueArray[i] = valueArray[i + 1];
                }
                // null the last values to avoid memory leak
                keyArray[lastIndex] = null;
                valueArray[lastIndex] = null;
                // decrement last index
                lastIndex--;
            }
            return oldValue;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        putAll(m, null);
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map<? extends String, ? extends V> map, Set<String> skipKeys) {
        if (map == null) return;
        boolean initialEmpty = lastIndex == -1;
        if (map instanceof LiteStringMap) {
            LiteStringMap<V> lsm = (LiteStringMap<V>) map;
            if (useManualIndex) {
                this.lastIndex = lsm.lastIndex;
                if (keyArray.length <= lsm.lastIndex) growArrays(lsm.lastIndex);
            }
            for (int i = 0; i <= lsm.lastIndex; i++) {
                if (skipKeys != null && skipKeys.contains(lsm.keyArray[i])) continue;
                if (useManualIndex) {
                    keyArray[i] = lsm.keyArray[i];
                    valueArray[i] = lsm.valueArray[i];
                } else if (initialEmpty) {
                    putNoFind(lsm.keyArray[i], lsm.valueArray[i]);
                } else {
                    putByIString(lsm.keyArray[i], lsm.valueArray[i]);
                }
            }
        } else {
            for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key == null) throw new IllegalArgumentException("LiteStringMap Key may not be null");
                if (skipKeys != null && skipKeys.contains(key)) continue;
                if (initialEmpty) {
                    putNoFind(internString(key), entry.getValue());
                } else {
                    putByIString(internString(key), entry.getValue());
                }
            }
        }
    }

    private void putNoFind(String key, V value) {
        lastIndex++;
        if (lastIndex >= keyArray.length) growArrays(null);
        keyArray[lastIndex] = key;
        valueArray[lastIndex] = value;
    }

    @Override
    public void clear() {
        lastIndex = -1;
        Arrays.fill(keyArray, null);
        Arrays.fill(valueArray, null);
    }

    @Override
    public Set<String> keySet() {
        return new KeySetWrapper(this);
    }

    @Override
    public Collection<V> values() {
        return new ValueCollectionWrapper<>(this);
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return new EntrySetWrapper<>(this);
    }


    public String getKey(int index) {
        return keyArray[index];
    }

    public V getValue(int index) {
        return (V) valueArray[index];
    }

    public boolean removeAllKeys(Collection<?> collection) {
        if (collection == null) return false;
        boolean removedAny = false;
        for (Object obj : collection) {
            // keys in LiteStringMap cannot be null
            if (obj == null) continue;
            int idx = findIndex(obj.toString());
            if (idx != -1) {
                removeByIndex(idx);
                removedAny = true;
            }
        }
        return removedAny;
    }

    public boolean removeValue(Object value) {
        boolean removedAny = false;
        for (int i = 0; i < valueArray.length; i++) {
            Object curVal = valueArray[i];
            if (value == null) {
                if (curVal == null) {
                    removeByIndex(i);
                    removedAny = true;
                }
            } else if (value.equals(curVal)) {
                removeByIndex(i);
                removedAny = true;
            }
        }
        return removedAny;
    }

    public boolean removeAllValues(Collection<?> collection) {
        if (collection == null) return false;
        boolean removedAny = false;
        // NOTE: could iterate over valueArray outer and collection inner but value array has no Iterator overhead so do that inner (and nice to reuse removeValue())
        for (Object obj : collection) {
            if (removeValue(obj)) removedAny = true;
        }
        return removedAny;
    }

    public static class KeyIterator implements Iterator<String> {
        private final LiteStringMap lsm;
        private int curIndex = -1;

        KeyIterator(LiteStringMap liteStringMap) {
            lsm = liteStringMap;
        }

        @Override
        public boolean hasNext() {
            return lsm.lastIndex > curIndex;
        }

        @Override
        public String next() {
            curIndex++;
            return lsm.keyArray[curIndex];
        }
    }

    public static class KeySetWrapper implements Set<String> {
        private final LiteStringMap lsm;

        KeySetWrapper(LiteStringMap liteStringMap) {
            lsm = liteStringMap;
        }

        @Override
        public int size() {
            return lsm.size();
        }

        @Override
        public boolean isEmpty() {
            return lsm.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return lsm.containsKey(o);
        }

        @Override
        public Iterator<String> iterator() {
            return new KeyIterator(lsm);
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(lsm.keyArray, lsm.lastIndex + 1);
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            int toCopy = ts.length > lsm.lastIndex ? lsm.lastIndex + 1 : ts.length;
            System.arraycopy(lsm.keyArray, 0, ts, 0, toCopy);
            return ts;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            if (collection == null) return false;
            for (Object obj : collection) if (obj == null || lsm.findIndex(obj.toString()) == -1) return false;
            return true;
        }

        @Override
        public boolean add(String s) {
            throw new UnsupportedOperationException("Key Set add not allowed");
        }

        @Override
        public boolean remove(Object o) {
            if (o == null) return false;
            int idx = lsm.findIndex(o.toString());
            if (idx == -1) {
                return false;
            } else {
                lsm.removeByIndex(idx);
                return true;
            }
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            throw new UnsupportedOperationException("Key Set add all not allowed");
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException("Key Set retain all not allowed");
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean removeAll(Collection<?> collection) {
            return lsm.removeAllKeys(collection);
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Key Set clear not allowed");
        }
    }

    public static class ValueCollectionWrapper<V> implements Collection<V> {
        private final LiteStringMap<V> lsm;

        ValueCollectionWrapper(LiteStringMap<V> liteStringMap) {
            lsm = liteStringMap;
        }

        @Override
        public int size() {
            return lsm.size();
        }

        @Override
        public boolean isEmpty() {
            return lsm.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return lsm.containsValue(o);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            if (collection == null || collection.isEmpty()) return true;
            for (Object obj : collection) {
                if (!lsm.containsValue(obj)) return false;
            }
            return true;
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator<V>(lsm);
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(lsm.valueArray, lsm.lastIndex + 1);
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            int toCopy = ts.length > lsm.lastIndex ? lsm.lastIndex + 1 : ts.length;
            System.arraycopy(lsm.valueArray, 0, ts, 0, toCopy);
            return ts;
        }

        @Override
        public boolean add(Object s) {
            throw new UnsupportedOperationException("Value Collection add not allowed");
        }

        @Override
        public boolean remove(Object o) {
            return lsm.removeValue(o);
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException("Value Collection add all not allowed");
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException("Value Collection retain all not allowed");
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return lsm.removeAllValues(collection);
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Value Collection clear not allowed");
        }
    }

    public static class ValueIterator<V> implements Iterator<V> {
        private final LiteStringMap<V> lsm;
        private int curIndex = -1;

        ValueIterator(LiteStringMap<V> liteStringMap) {
            lsm = liteStringMap;
        }

        @Override
        public boolean hasNext() {
            return lsm.lastIndex > curIndex;
        }

        @Override
        public V next() {
            curIndex++;
            return lsm.valueArray[curIndex];
        }
    }

    public static class EntryWrapper<V> implements Entry<String, V> {
        private final LiteStringMap<V> lsm;
        private final String key;
        private int curIndex;

        EntryWrapper(LiteStringMap<V> liteStringMap, int index) {
            lsm = liteStringMap;
            curIndex = index;
            key = lsm.keyArray[index];
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public V getValue() {
            String keyCheck = lsm.keyArray[curIndex];
            if (!Objects.equals(key, keyCheck)) curIndex = lsm.findIndex(key);
            if (curIndex == -1) return null;
            return lsm.valueArray[curIndex];
        }

        @Override
        public V setValue(V value) {
            String keyCheck = lsm.keyArray[curIndex];
            if (!Objects.equals(key, keyCheck)) curIndex = lsm.findIndex(key);
            if (curIndex == -1) return lsm.put(key, value);
            V oldValue = lsm.valueArray[curIndex];
            lsm.valueArray[curIndex] = value;
            return oldValue;
        }
    }

    public static class EntrySetWrapper<V> implements Set<Entry<String, V>> {
        private final LiteStringMap<V> lsm;

        EntrySetWrapper(LiteStringMap<V> liteStringMap) {
            lsm = liteStringMap;
        }

        @Override
        public int size() {
            return lsm.size();
        }

        @Override
        public boolean isEmpty() {
            return lsm.isEmpty();
        }

        @Override
        public boolean contains(Object obj) {
            if (obj instanceof Entry) {
                Entry entry = (Entry) obj;
                Object keyObj = entry.getKey();
                if (keyObj == null) return false;
                int idx = lsm.findIndex(keyObj.toString());
                if (idx == -1) return false;
                Object entryValue = entry.getValue();
                Object keyValue = lsm.valueArray[idx];
                return Objects.equals(entryValue, keyValue);
            } else {
                return false;
            }
        }

        @Override
        public Iterator<Entry<String, V>> iterator() {
            ArrayList<Entry<String, V>> entryList = new ArrayList<>(lsm.lastIndex + 1);
            for (int i = 0; i <= lsm.lastIndex; i++) {
                if (lsm.getKey(i) == null) continue;
                entryList.add(new EntryWrapper<V>(lsm, i));
            }
            return entryList.iterator();
        }

        @Override
        public V[] toArray() {
            //throw new UnsupportedOperationException("Entry Set to array not supported");
            return Arrays.copyOf(lsm.valueArray, lsm.lastIndex + 1);
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            throw new UnsupportedOperationException("Entry Set copy to array not supported");
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            if (collection == null) return false;
            for (Object obj : collection) if (obj == null || lsm.findIndex(obj.toString()) == -1) return false;
            return true;
        }

        @Override
        public boolean add(Entry<String, V> entry) {
            throw new UnsupportedOperationException("Entry Set add not allowed");
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("Entry Set remove not allowed");
        }

        @Override
        public boolean addAll(Collection<? extends Entry<String, V>> collection) {
            throw new UnsupportedOperationException("Entry Set add all not allowed");
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException("Entry Set retain all not allowed");
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException("Entry Set remove all not allowed");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Entry Set clear not allowed");
        }
    }
}
