package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaDoblementEncadenada<E extends Comparable<E>> implements ILlistaGenerica<E> {

    private Node<E> primer;
    private Node<E> ultim;
    private int longitud;

    //constructor
    public LlistaDoblementEncadenada() {
        primer = null;
        ultim = null;
        longitud = 0;
    }

    //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element.
    // 
    public void inserir(E e) {

        Node<E> nouNode = new Node<E>(e);
        //si esta buida 
        if (primer == null) {
            primer = nouNode;
            ultim = nouNode;
        } else {
            ultim.setSeguent(nouNode);
            nouNode.setAnterior(ultim);
            ultim = nouNode;
        }
        longitud++;
        return;
    }

    //Mètode per a esborrar un element de la llista.
    // Actual seria com una variable auxiliar 

    public void esborrar(E e) throws ElementNoTrobat {
        Node<E> actual = primer;
        boolean trobat = false;
        //Mentres no arribes final llista i no s'ha trobat el element - comparem
        while (actual != null && !trobat) {
            if (actual.getValor().compareTo(e) == 0) {
                trobat = true;
            } else {
                actual = actual.getSeguent();
            }
        }
        //Si es troba 
        if (trobat) {
            //si es l'unic element de la llista  
            if (actual == primer && actual == ultim) {
                primer = null;
                ultim = null;
                //si el element buscat esta a la primera posicio 
            } else if (actual == primer) {
                primer = primer.getSeguent();
                primer.setAnterior(null);
                //Si l'element es l'ultim de la llista 
            } else if (actual == ultim) {
                ultim = ultim.getAnterior();
                ultim.setSeguent(null);
                //si esta al mig 
            } else {
                actual.getAnterior().setSeguent(actual.getSeguent());
                actual.getSeguent().setAnterior(actual.getAnterior());
            }
            longitud--;
            // Si no es troba
        } else {
            throw new ElementNoTrobat("L'element no es troba a la llista.");
        }
    }

    //Mètode per a comprovar si un element està a la llista
    //Lo mateix acual es el auxiliar molt semblant al anterior
    public boolean buscar(E e) {
        Node<E> actual = primer;
        boolean trobat = false;

        while (actual != null && !trobat) {
            if (actual.getValor().compareTo(e) == 0) {
                trobat = true;
            } else {
                actual = actual.getSeguent();
            }
        }

        return trobat;
    }

    //Mètode per a comprovar si la llista té elements
    //si el primer es null es perque esta buida
    public boolean esBuida() {
        return primer == null;
    }

    //Mètode per a obtenir el nombre d'elements de la llista
    //per aixo sumen i restem depenen del metode la variable longitud
    public int longitud() {
        return longitud;
    }

    //Metode per a obtenir un array amb tots els elements
    //molt semblant al metode de les clases llistapel llistapelordenada pero amb el node.
    public Object[] elements() {
        Object[] arrayElements = new Object[longitud];
        Node<E> actual = primer;
        int i = 0;
        while (actual != null) {
            arrayElements[i] = actual.getValor();
            actual = actual.getSeguent();
            i++;
        }
        return arrayElements;
   }

   // Necessitem la clase node per poder duu a terme una LDE
   // Dintre de la clase tambe necessitarem els getters i setters
   // En aquest cas es una clase no estatica, perque nomes hem de accedir als metodes i variables
   // i no hi ha necessitat de crear multiples instancies de la clase Node fora de la clase LDE
   // a part la clase node te atributs com anterior i seguent que es relacionen directament i te sentit
   // llavors manterirla dintre de la clase LDE

   private class Node<T> {      
        private T valor;
        private Node<T> anterior;
        private Node<T> seguent;

        // Constructor 
        public Node(T valor) {
            this.valor = valor;
            anterior = null;
            seguent = null;
        }
        public T getValor() {
            return valor;
        }

        public Node<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(Node<T> anterior) {
            this.anterior = anterior;
        }

        public Node<T> getSeguent() {
            return seguent;
        }

        public void setSeguent(Node<T> seguent) {
            this.seguent = seguent;
        }
    } 
}