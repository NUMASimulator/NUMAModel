/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

/**
 *
 * @author Abed MAATALLA
 */
public class Computation implements IComputation{
    public final static String type = "Computation";
    public long time;

    public Computation(long time) {
        this.time = time;
    }
}