package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaDoblementEncadenadaOrdenada<E extends Comparable<E>> implements ILlistaGenerica<E> {

    private Node<E> head;  // Primer node de la llista
    private Node<E> tail;  // Últim node de la llista
    private int size;      // Tamany de la llista


    //Necessitarem tambe una clase node perque es una LDE ordenada
    // En aquest cas utilitzo una clase estatica perque no tingui que instanciarla
    // desde la clase LDEO. Tot i que es podria fer amb una no estatica pero aixi simplifiquem la 
    // implementacio del metodo getNode ja que no necessitem crear una instancia per tornar el node
    // desitjat. I també per tenir dues implementacions diferents.

     private static class Node<E> {
        E data;       //valor
        Node<E> prev; //anterior
        Node<E> next; // seguent 

        //contructor 
        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> getNode(int indice) {
        //mirem que l'index sigui correcte. Si index < 0 o >= tamany llista 
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException();
        }
        //una variable com la actual per recorre
        Node<E> current = head;
        for (int i = 0; i < indice; i++) {
            current = current.next;
        }
        return current;
    }

    //Mètode per insertar un element a la llista. SI importa la posició on s'afegeix l'element
    // En aquest metode com el professor va tenir una errada al template, el segon test sempre donaba error
    // Vaig pensar que el que podria fer era un algoritme de ordenacio. 
    // Ja que sense aquella correccio aquesta llista es recorria sensera sense trobar el valor

    public void inserir(E e) {

         Node<E> newNode = new Node<>(e, null, null);

        if (head == null) {  // Si la lista está vacía
            head = newNode;
            tail = newNode;
        } else {
            int index = binarySearch(e);
            //Si l'index es igual al tamany de la llista - current es null
            //Utilitzo els operadors ternaris per ferho mes concis. "condicio ? true : false"
            Node<E> current = (index == size) ? null : getNode(index);
            if (current == null) {  // Si l'element a inserir es major que tots els elements de la llista
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            } else if (current == head) {  // Si l'element a inserir es menor que tots els elements de la llista
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else {  // Si l'element a inserir està en el mig de la llista
                newNode.prev = current.prev;
                newNode.next = current;
                current.prev.next = newNode;
                current.prev = newNode;
            }
        }
    
        size++;
   }
        

        //  Implementacio del algoritme de busqueda binaria per determinar posicio adecuada
        private int binarySearch(E e) {
            int low = 0; //aquestes variables son el rang de la busqueda
            int high = size;
            // en cada iteracio es calcula l'index mig entre low - high
            while (low < high) {
                int mid = (low + high) / 2; 
                E midVal = getNode(mid).data; //aux per comparar
                int cmp = midVal.compareTo(e);

                //si valor mid < e llavors e insertar despues de mid
                if (cmp < 0) { // El rang de la busqueda s'ajusta a la segona mitat
                    low = mid + 1;

                //si valor mid >= e llavors e insertar abans o posicio de mid
                } else {
                    high = mid; // El rang s'ajusta a la primera mitat
                }
            }
            return low; //posicio on inserir 

            
        }

    //Mètode per a esborrar un element de la llista
    //Molt semblant al LDE.
    // utilitzem un auxliar
    public void esborrar(E e) throws ElementNoTrobat {
        Node<E> current = head;
        
        //Aqui no utilitzem una variable trobat per ferho mes simplificat i rapid.

        while (current != null && !current.data.equals(e)) {
            current = current.next;
        }

        if (current == null) {  // Si l'element no està a la llista
            throw new ElementNoTrobat("l'element no esta a la llista");
        }

        if (current == head) {  // Si l'element a esborrar és el primer node de la llista
            head = current.next;
            if (head != null) {
                head.prev = null;
            } else {  // Si la llista queda buida després de esborrar el primer node
                tail = null;
            }
        } else if (current == tail) {  // Si  l'element a esborrar és l'últim node de la llista
            tail = current.prev;
            tail.next = null;
        } else {  // Si l'element a esborrar està al mig de la llista.
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        size--;
    }

    //Mètode per a comprovar si un element està a la llista
    //Aqui es semblan a LDE pero ho hem implementat amb el algoritme binari 
    
    public boolean buscar(E e) {
       int index = binarySearch(e);
        // si l'índex obtingut està dins del rang de la llista i el valor es igual al buscat --> true 
        if (index < size && getNode(index).data.compareTo(e) == 0) {
            return true;
        }
        // si no, devuelve falso
        return false;

    }

    //Mètode per a comprovar si la llista té elements
    //Una altre manera de ferho tambe mira que el primer node sigui null
    public boolean esBuida() {
        return size == 0;
    }

    //Mètode per a obtenir el nombre d'elements de la llista
    public int longitud() {
        return size;
    }

    //Metode per a obtenir un array amb tots els elements
    //molt semblant al metode de les clases llistapel llistapelordenada pero amb el node.

    public Object[] elements() {
        Object[] arr = new Object[size];
        Node<E> actual = head;
        int i = 0;
        while (actual != null) {
            arr[i] = actual.data;
            actual = actual.next;
            i++;
        }
        return arr;
    }   
    
}