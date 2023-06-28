package cat.urv.deim.io;

import java.io.File;
import java.util.Scanner;

import cat.urv.deim.models.ILlistaPelicules;
import cat.urv.deim.models.Pelicula;

public class FileLoader {

    public static void carregarFitxer(String path, ILlistaPelicules llista) {
        
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                
                String[] linea = scanner.nextLine().split("###");

                if (linea.length != 3 || linea[1].equals("NULL")) {
                    continue; // Saltar a la siguiente línea
                }
                
                int idP = Integer.parseInt(linea[0]);
                String anyY = linea[1];
                String titol = linea[2];

                if (anyY.equals("NULL")) {
                    throw new Exception("Estructura incorrecta en la línea: " + linea);
                }
                int any = Integer.parseInt(anyY);
                if (any < 1888 || any > 2023) {
                    throw new Exception("El año en la línea " + linea + " no está entre 1888 y 2023");
                }

                llista.inserir(new Pelicula(idP, titol, any));
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }

}
