/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.node;

import systree.IHierarchical;


/**
 *
 * @author Abed MAATALLA
 */
public interface INode extends IHierarchical{

    /**
     *
     */
    public final String type = "Node"; 
    
    /**
     *
     * @return
     */
    public int getCoreCount();

    /**
     *
     * @return
     */
    public int getCPUCount();

    /**
     *
     * @return
     */
    public IHierarchical getRam();

    /**
     *
     * @return
     */
    public IHierarchical[] getSocket();

    /**
     *
     * @return
     */
    public IHierarchical getBus();
    
    
}
