/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interfaz;

import controlador.Secuenciador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import mundo.LineaCodigo;

/**
 *
 * @author jdgal
 */
public class InterfazApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> codigo = new ArrayList<>();
        Secuenciador secuenciador = new Secuenciador();
        File archivoEntrada = new File("Ejercicio3.in");
        File archivoSalida = new File("salida.txt");
        
        try (BufferedReader lector = new BufferedReader(new FileReader(archivoEntrada))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                codigo.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }
        
        secuenciador.procesarCodigo(codigo);
        
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoSalida))) {
            escritor.write("i     |           Expresion           | Jump\n");
            escritor.write("--------------------------------------------\n");
            ArrayList<LineaCodigo> lineas = secuenciador.getLineasArray();
            for (int i = 0; i < lineas.size(); i++) {
                LineaCodigo linea = lineas.get(i);
                escritor.write(linea.toString());
                escritor.newLine();
            }
            escritor.write("\n--- Historial de operaciones de la pila ---\n");
            ArrayList<String> historial = secuenciador.getHistorialPila();
            for (int i = 0; i < historial.size(); i++) {
                String log = historial.get(i);
                escritor.write(log);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
        System.out.println("Resultado en salida.txt");
    }
}
