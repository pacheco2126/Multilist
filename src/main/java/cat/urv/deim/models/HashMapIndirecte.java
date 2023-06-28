package cat.urv.deim.models;
import java.util.Iterator;
import java.util.NoSuchElementException;
import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapIndirecte<K,V> implements IHashMap<K,V> {

    private int mida;
    private int size;
    private Node<K, V>[] taula;

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % mida;
    }
    
    public HashMapIndirecte(int mida) {
        this.mida = mida;
        this.taula = new Node[mida];

    }

    @Override
    public void inserir(K key, V value) {
        if (factorCarrega() >= 0.75f) {
            redimensionar();
        }
        
        int hash = getIndex(key);
        Node<K, V> node = taula[hash];
        // Si la clave ya existe, se actualiza el valor
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        // Si la clave no existe, se agrega un nuevo nodo
        Node<K, V> newNode = new Node<>(key, value, taula[hash]);
        taula[hash] = newNode;
        // Solo se incrementa la longitud si se agrega un nuevo nodo
        size++;
            }

    @Override
    public void esborrar(K key) throws ElementNoTrobat {
        int index = getIndex(key);
        Node<K,V> node = (Node<K,V>) taula[index];
        if (node == null) {
            throw new ElementNoTrobat("No s'ha trobat l'element");
        }
        if (node.key.equals(key)) {
            taula[index] = node.next;
            size--;
            return;
        }
        while (node.next != null) {
            if (node.next.key.equals(key)) {
                node.next = node.next.next;
                size--;
                return;
            }
            node = node.next;
        }
        throw new ElementNoTrobat("No s'ha trobat l'element");
        }

    @Override
    public boolean buscar(K key) {
    int index = getIndex(key);
    Node<K,V> node = (Node<K,V>) taula[index];
    while (node != null) {
        if (node.key.equals(key)) {
            return true;
        }
        node = node.next;
    }
    return false;
    }

    @Override
    public boolean esBuida() {
        return size == 0;
    }

    @Override
    public int longitud() {
        return size;
   }
   
    @Override
    public V element(K key) throws ElementNoTrobat {
        int index = getIndex(key);
        Node<K,V> node = taula[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        throw new ElementNoTrobat("No s'ha trobat l'element");
        }

    @Override
    public float factorCarrega() {
        return (float) size / mida;
    }

    @Override
    public int midaTaula() {
        return mida;
    }
    private void redimensionar() {
        int novaMida = mida * 2;
        Node<K, V>[] novaTaula = new Node[novaMida];
        
        // Reinsertar todos los elementos en la nueva tabla hash
        for (int i = 0; i < mida; i++) {
            Node<K, V> node = taula[i];
            while (node != null) {
                K key = node.key;
                V value = node.value;
                int index = Math.abs(key.hashCode()) % novaMida;
                novaTaula[index] = new Node<>(key, value, novaTaula[index]);
                node = node.next;
            }
        }
        
        // Actualizar la tabla hash y la mida
        taula = novaTaula;
        mida = novaMida;
    }

    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public Object[] obtenirClaus() {
        Object[] claus = new Object[size];
        int i = 0;
        for (int j = 0; j < mida; j++) {
            Node<K, V> node = taula[j];
            while (node != null) {
                claus[i] = node.key;
                i++;
                node = node.next;
            }
        }
        return claus;

    }

    private class MyIterator implements Iterator<V> {
        private int index;
        private Node<K, V> current;
    
        public MyIterator() {
            this.index = -1;
            this.current = null;
            getNextNode();
        }
    
        private void getNextNode() {
            if (current != null && current.next != null) {
                current = current.next;
                return;
            }
            index++;
            while (index < mida && taula[index] == null) {
                index++;
            }
            current = (index < mida) ? taula[index] : null;
        }
    
        @Override
        public boolean hasNext() {
            return current != null;
        }
    
        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            V value = current.value;
            getNextNode();
            return value;
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new MyIterator();
    }
}