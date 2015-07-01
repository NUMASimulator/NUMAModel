/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.node.socket;

import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public interface ICore extends IHierarchical{

    /**
     *
     */
    public final String type = "Core"; 
    
    /**
     *
     * @param index
     * @param target
     * @return 
     */
    public String[] load(int index, IHierarchical target);

    /**
     *
     * @param index
     * @param data
     */
    public void store(int index, int data);

    /**
     *
     */
    public void compute();
    
}
