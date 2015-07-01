/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.icn;

import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public interface IBus extends IHierarchical{

    /**
     *
     */
    public final String type = "Bus"; 
      //

    /**
     *
     * @return 
     */
    public IHierarchical getMemory();
    
    /**
     *
     * @return
     */
    public IHierarchical[] getSocket();
    
    /**
     *
     * @return
     */
    public int getSocketCount();
    
    
    public IHierarchical[] getCPU();
    
    public int getCPUCount();

    
    /**
     *
     * @return
     */
    public int getLevel();

    /**
     *
     * @param level
     */
    public void setLevel(int level);
}
