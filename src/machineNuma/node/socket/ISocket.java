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
public interface ISocket extends IHierarchical {

    /**
     *
     */
    public final String type = "Socket"; 
    
    /**
     *
     * @return
     */
    public IHierarchical[] getCPU();

    /**
     *
     * @return
     */
    public IHierarchical[] getCore();
    
    /**
     *
     * @return
     */
    public int getCPUCount();

    /**
     *
     * @return
     */
    public int getCoreCount();
    
}
