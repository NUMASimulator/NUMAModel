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
public interface IMemory extends IHierarchical{
    
    /**
     *
     */
    public final String type = "Ram"; 

    // read method 

    /**
     *
     * @param index
     * @return
     */
        public String Read(int index);
    
    /**
     *
     * @param page
     * @return
     */
    public String[] readByPage(int page);
    
    // 

    public void write(String data);
    public void writeByPage(String[] page);
    /**
     *
     * @param index
     * @param data
     */
        public void write(int index , String data);
 
    /**
     *
     * @param pageIndex
     * @param data
     */
        public void writeByPage(int pageIndex , String[] data);
        
        public int getNodeID();
        
        public void setNodeID(int id);
        
        
}
