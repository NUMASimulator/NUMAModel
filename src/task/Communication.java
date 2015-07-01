/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public class Communication implements ICommunication{
    public final static String type = "Communication";
    public int index;
    public IHierarchical target;
    
    public Communication(int index,IHierarchical target){
        this.index = index;
        this.target = target;
    }
}
