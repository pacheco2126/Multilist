package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaPeliculesOrdenada extends LlistaPelicules {

    private LlistaDoblementEncadenadaOrdenada<Pelicula> llista;
    
    public LlistaPeliculesOrdenada() {
        llista = new LlistaDoblementEncadenadaOrdenada<>();
    }


     //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element
     // Cridem a la clase Encadenada y ordenada
    public void inserir(Pelicula e) {
        llista.inserir(e);
    }

    //Mètode per a esborrar un element de la llista
    // Cridem a la clase Encadenada y ordenada y a part comprobem que existeix sino tirem el throw

    public void esborrar(Pelicula e) throws ElementNoTrobat {
        try {
            llista.esborrar(e);
        } catch (ElementNoTrobat ex) {
            throw new ElementNoTrobat("No s'ha trobat l'element a esborrar");
        }
    }

    //Mètode per a comprovar si un element està a la llista
    // Cridem a la clase Encadenada y ordenada

    public boolean buscar(Pelicula pelicula) {
        return llista.buscar(pelicula);
    }


    
    //Mètode per a comprovar si la llista té elements
    // Cridem a la clase Encadenada y ordenada

    public boolean esBuida() {
        return llista.esBuida();
    }

    //Mètode per a obtenir el nombre d'elements de la llista
    // Cridem a la clase Encadenada y ordenada

    public int longitud() {
        return llista.longitud();
    }

    //Funcio que busca quantes películes hi ha d'un any en concret
    // recorrem les pelicules amb el "for p : pelicules" i simplement amb una comparacio aumentem el counter.
    public int comptarPeliculesAny(int any) {
        Pelicula[] pelicules = elements();
        int comptador = 0;
        for (Pelicula p : pelicules) {
            if (p.getAny() == any) {
                comptador++;
            }
        }
        return comptador;
    }

    //Funció que ens diu l'any en que va sortir una película
    //Cridem a buscarpelicula per reutilitzar codi, y si está tot correcte retorna l'any
    public int buscarAnyPelicula(String titol) throws ElementNoTrobat {
        Pelicula p = buscarPelicula(titol);
        if (p != null) {
            return p.getAny();
        } else {
            throw new ElementNoTrobat("No s'ha trobat la pelicula");
        }
    }

    //Funció que retorna la pelicula segons el titol
    // semblant al comptar any pero comparem el titol en aquest cas
    public Pelicula buscarPelicula(String titol) throws ElementNoTrobat {
        Pelicula[] pelicules = elements();
        for (Pelicula p : pelicules) {
            if (p.getTitol().equals(titol)) {
                return p;
            }
        }
        throw new ElementNoTrobat("No s'ha trobat la pelicula");
    }

        //Metode per a obtenir un array amb tots els elements
        public Pelicula[] elements() {
            Object[] elements = llista.elements();
            Pelicula[] pelicules = new Pelicula[elements.length];
            for (int i = 0; i < elements.length; i++) {
                pelicules[i] = (Pelicula) elements[i];
            }
            return pelicules;          
         }
}
