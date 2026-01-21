/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mundo;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author jdgal
 */
public class StackControl {

    private Stack<Integer> stack = new Stack<>();
    private ArrayList<String> historialStack = new ArrayList<>();
    private Stack<String> forExtra = new Stack<>();

    public void push(int linea) {
        stack.push(linea);
        guardarHistorial("PUSH", linea);
    }

    public int pop() {
        int linea;
        if (stack.isEmpty()) {
            return -1;
        } else {
            linea = stack.pop();
            guardarHistorial("POP", linea);
            return linea;
        }
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    private void guardarHistorial(String operacion, int linea) {
        String estado = "[" + operacion + "] Linea " + linea + " -> Stack: " + stack;
        historialStack.add(estado);
    }

    public ArrayList<String> getHistorialStack() {
        return historialStack;
    }

    public void pushForExtra(String parteFinal) {
        forExtra.push(parteFinal);
    }

    public String popForExtra() {
        return forExtra.pop();
    }
}
