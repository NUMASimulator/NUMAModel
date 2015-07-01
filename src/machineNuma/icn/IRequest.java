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
public interface IRequest {

    
    public IHierarchical getSource();
    
    public IHierarchical getTarget();
    public void setSource(IHierarchical source);
    public int getIndex();
    public int getPageIndex();
    
    public boolean isPorcessing();
    public void setPorcessing(boolean processing);
    

    /**
     *
     * @return
     */
        public String getMessage();
}
