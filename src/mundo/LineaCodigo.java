/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mundo;

/**
 *
 * @author jdgal
 */
public class LineaCodigo {

    private int numeroLinea;
    private String expresion;
    public String jump;

    public LineaCodigo(int numeroLinea, String expresion, String jump) {
        this.numeroLinea = numeroLinea;
        this.expresion = expresion;
        this.jump = jump;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public String getExpresion() {
        return expresion;
    }

    public String getJump() {
        return jump;
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-20s | %-5s", numeroLinea, expresion, jump);
    }
}
