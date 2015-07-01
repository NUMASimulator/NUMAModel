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
public interface ICPU extends IHierarchical{

    /**
     *
     */
    public final String type = "CPU"; 
    
    /**
     *
     * @return
     */
    public int getCountCore();

    /**
     *
     * @return
     */
    public int getSpeed();

    /**
     *
     * @return
     */
    public IHierarchical[] getCores();
}
