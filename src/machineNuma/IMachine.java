/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma;

import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public interface IMachine extends IHierarchical {

    /**
     *
     */
    public final String type = "Machine"; 
    
    /**
     *
     * @return
     */
    public boolean isNUMA();
    
    public IHierarchical getByID(int ID);
    
}
