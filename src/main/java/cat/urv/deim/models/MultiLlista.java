package cat.urv.deim.models;
import java.util.ArrayList;
import java.util.List;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;


public class MultiLlista<A extends Comparable<A>, B extends Comparable<B>> implements IMultiLlistaGenerica<A,B> {

    private IHashMap<A, Node<A, B>> hashA;
    private IHashMap<B, Node<A, B>> hashB;

    public MultiLlista() {
        this.hashA = new HashMapIndirecte<>(10);
        this.hashB= new HashMapIndirecte<>(10);
    }

    public class Node<A, B> { // Warning porque ya esta definido en otra parte del codigo pero no afecta al funcionamiento.
        private A a;
        private B b;
        private Node<A, B> nextA;
        private Node<A, B> nextB;
    
        public Node(A a, B b) {
            this.a = a;
            this.b = b;
            this.nextA = null;
            this.nextB = null;
        }
    
        public A getA() {
            return a;
        }
    
        public B getB() {
            return b;
        }
    
        public Node<A, B> getNextA() {
            return nextA;
        }
    
        public Node<A, B> getNextB() {
            return nextB;
        }
    
        public void setNextA(Node<A, B> nextA) {
            this.nextA = nextA;
        }
    
        public void setNextB(Node<A, B> nextB) {
            this.nextB = nextB;
        }
    }
    
    
    @Override
    public void inserir(A a, B b) throws ElementRepetit, ElementNoTrobat {
        //En primer lugar, el método comprueba si ya existe una relación (a, b)
        // en la estructura de datos. Si es así, lanza la excepción ElementRepetit, 
        // ya que no se permiten duplicados en la lista.

        if (existeix(a, b)) { 
            throw new ElementRepetit("La relació ja existeix.");
        }
    
        //Creamos un nuevo nodo
        Node<A, B> node = new Node<>(a, b);

        // Agregar el nuevo nodo a la lista asociada a la clave 'a'
        if (!hashA.buscar(a)) { //Miramos si ya existe la clave
            node.setNextA(null); //Como no existia la clave el siguiente es null
            hashA.inserir(a, node);
        } else {
            Node<A, B> current = hashA.element(a);
            while (current.getNextA() != null) { //Lecorremos la lista para ver si existe la relación
                if (current.getA().equals(a) && current.getB().equals(b)) {
                    throw new ElementRepetit("element repetit");
                }
                current = current.getNextA(); //Como no existe se asigna el nodo al ultimo
            }
            if (current.getA().equals(a) && current.getB().equals(b)) {
                throw new ElementRepetit("element repetit");
            }
            current.setNextA(node);

            //Esta linea de codigo se repite para evitar los nodos duplicados
        }
    
        // Repetimos lo anterior para agregar el nuevo nodo a la lista asociada a la clave 'b'
        if (!hashB.buscar(b)) {
            node.setNextB(null);
            hashB.inserir(b, node);
        } else {
            Node<A, B> current = hashB.element(b);
            while (current.getNextB() != null) {
                if (current.getA().equals(a) && current.getB().equals(b)) {
                    throw new ElementRepetit("element repetit");
                }
                current = current.getNextB();
            }
            if (current.getA().equals(a) && current.getB().equals(b)) {
                throw new ElementRepetit("element repetit");
            }
            current.setNextB(node);
        }
    }

    @Override
    public void esborrar(A a, B b) throws ElementNoTrobat {
        // Como en el metodo inserir, el método comprueba si ya existe una relación (a, b)
        // en la estructura de datos. Si es así, lanza la excepción ElementRepetit, 
        // ya que no se permiten duplicados en la lista.
        if (!existeix(a, b)) {
            throw new ElementNoTrobat("La relació no existeix.");
        }
    
        // Buscar y eliminar el nodo de la lista asociada a la clave 'a'
        Node<A, B> prevA = null;
        Node<A, B> currentA = hashA.element(a);
        while (currentA != null) {
            if (currentA.getA().equals(a) && currentA.getB().equals(b)) {
                if (prevA == null) {
                    // El nodo a eliminar es el primer nodo de la lista
                    hashA.esborrar(a);
                } else {
                    // El nodo a eliminar esta en medio o al final de la lista
                    prevA.setNextA(currentA.getNextA());
                }
                break;
            }
            prevA = currentA;
            currentA = currentA.getNextA();
        }
    
        // Buscar y eliminar el nodo de la lista asociada a la clave 'b'
        Node<A, B> prevB = null;
        Node<A, B> currentB = hashB.element(b);
        while (currentB != null) {
            if (currentB.getA().equals(a) && currentB.getB().equals(b)) {
                if (prevB == null) {
                    // El nodo a eliminar es el primer nodo de la lista
                    hashB.esborrar(b);
                } else {
                    // El nodo a eliminar esta en medio o al final de la lista
                    prevB.setNextB(currentB.getNextB());
                }
                break;
            }
            prevB = currentB;
            currentB = currentB.getNextB();
        }
    }

    // Devuelve los nodos de la fila
    @Override
    public List<B> fila(A a) throws ElementNoTrobat {
        if (!hashA.buscar(a)) {
            throw new ElementNoTrobat("La fila especificada no existe.");
        }
    
        // Como existe creamos una lista vacia de tipo arraylist debido al tipo de metodo de la interficie
        List<B> llistaB = new ArrayList<>();
        Node<A, B> current = hashA.element(a); // Puntero al primer nodo
    
        while (current != null) {
            llistaB.add(current.getB());
            current = current.getNextA();
        }
    
        return llistaB;
        }
        
    
    // Devuelve los nodos columna igual que el metodo anterior
    @Override
    public List<A> columna(B b) throws ElementNoTrobat {
        if (!hashB.buscar(b)) {
            throw new ElementNoTrobat("La clau b no existeix a la MultiLlista.");
        }
        List<A> llistaA = new ArrayList<>();
        Node<A, B> current = hashB.element(b);
        while (current != null) {
            llistaA.add(current.getA());
            current = current.getNextB();
        }
        return llistaA;

    }

    // Deveulve true si existe la relacion. No es necesario mirar tambien las columnas
    // por temas de eficiencia, porque solo necesita buscar la lista asociada a la clave
    // a en la ED (A, B), en lugar de buscar en todas. Se podria hacer lo mismo solo con columna()
    @Override
    public boolean existeix(A a, B b) {
        try {
            List<B> llistaB = fila(a);
            return llistaB.contains(b);
        } catch (ElementNoTrobat e) {
            return false;
        }
    }

}
