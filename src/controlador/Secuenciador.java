/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.Stack;
import mundo.LineaCodigo;
import mundo.StackControl;

/**
 *
 * @author jdgal
 */
import java.util.ArrayList;
import java.util.Stack;

public class Secuenciador {

    private StackControl stackControl = new StackControl();
    private ArrayList<LineaCodigo> lineasArray = new ArrayList<>();
    private Stack<Integer> pilaCondiciones = new Stack<>();
    private Stack<String> contextoBloques = new Stack<>();
    private Stack<String[]> pilaPartesFor = new Stack<>();
    private boolean dentroDeBloque = false;

    public void procesarCodigo(ArrayList<String> codigoFuente) {
        int contador = 1;

        for (int i = 0; i < codigoFuente.size(); i++) {
            String linea = codigoFuente.get(i).trim();

            if (linea.startsWith("for")) {
                dentroDeBloque = true;
                String contenido = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')'));
                String[] partes = contenido.split(";");

                int startLine = contador;
                lineasArray.add(new LineaCodigo(contador++, partes[0].trim() + "", ""));
                int condicionLine = contador;
                lineasArray.add(new LineaCodigo(contador++, partes[1].trim() + "", "?"));

                stackControl.push(condicionLine);
                stackControl.push(startLine);
                pilaCondiciones.push(condicionLine);
                contextoBloques.push("for");
                pilaPartesFor.push(partes); // Guardar para más adelante

            } else if (linea.startsWith("if") || linea.startsWith("while")) {
                dentroDeBloque = true;
                String condicion = linea.substring(linea.indexOf('(') + 1, linea.lastIndexOf(')'));
                int condicionLine = contador;
                lineasArray.add(new LineaCodigo(contador++, condicion.trim(), "?"));
                stackControl.push(condicionLine);
                pilaCondiciones.push(condicionLine);
                
                if (linea.startsWith("if")) {
                    contextoBloques.push("if");
                } else {
                    contextoBloques.push("while");
                }

            } else if (linea.equals("{") || linea.equals("}")) {
                lineasArray.add(new LineaCodigo(contador++, "", ""));

                if (linea.equals("}")) {
                    if (!contextoBloques.isEmpty()) {
                        String tipoBloque = contextoBloques.pop();
                        int condicion = pilaCondiciones.pop();
                        int destino = contador;

                        if (tipoBloque.equals("for")) {
                            int start = stackControl.pop();
                            stackControl.pop();
                            String[] partes = pilaPartesFor.pop();

                            // Agregar el incremento aquí (al final del ciclo)
                            String expresionIncremento = partes[2].trim();
                            lineasArray.add(new LineaCodigo(contador++, expresionIncremento, "→ " + condicion));

                            // Actualizar salto de condición
                            actualizarJump(condicion, contador);

                        } else if (tipoBloque.equals("while")) {
                            lineasArray.add(new LineaCodigo(contador++, "", "→ " + condicion));
                            actualizarJump(condicion, contador);

                        } else if (tipoBloque.equals("if")) {
                            actualizarJump(condicion, contador);
                        }

                        if (contextoBloques.isEmpty()) {
                            dentroDeBloque = false;
                        }
                    }
                }

            } else {
                if (dentroDeBloque) {
                    lineasArray.add(new LineaCodigo(contador++, linea, ""));
                }
            }
        }
    }

    private void actualizarJump(int lineaInicio, int destino) {
        for (int i = 0; i < lineasArray.size(); i++) {
            LineaCodigo actualizarSalto = lineasArray.get(i);
            if (actualizarSalto.getNumeroLinea() == lineaInicio && actualizarSalto.getJump().equals("?")) {
                lineasArray.set(i, new LineaCodigo(
                        actualizarSalto.getNumeroLinea(),
                        actualizarSalto.getExpresion(),
                        "→ " + destino));
                break;
            }
        }
    }

    public ArrayList<LineaCodigo> getLineasArray() {
        return lineasArray;
    }

    public ArrayList<String> getHistorialPila() {
        return stackControl.getHistorialStack();
    }
}
