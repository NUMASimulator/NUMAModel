/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.node.memory;

import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public interface ICache  extends IHierarchical{

    /**
     *
     */
    public final String type = "Cache"; 
    
    
    /**
     *
     * @return
     */
        public long getSize();
    
    
    // to update the cache pages

    /**
     *
     */
        public void update();
    
    // to return true for hit and false for miss

    /**
     *
     * @return
     */
        public boolean hitOrMiss();
    
}
