/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.icn;

import java.util.ArrayList;
import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public interface IResponse {
    
    public IHierarchical getSource();
    public IHierarchical getTarget();
    
    public String[] getData();
    
    public int getIndex();
    public int getPageIndex();
    
    public boolean isPorcessing();
    public void setPorcessing(boolean processing);
    
    public void setIndex(int index);
    public void setPageIndex(int pageIndex);
    public void setData(String[] data);
    public void setSource(IHierarchical source);
    public void setTargate(IHierarchical target);
    
    public void setPath(ArrayList<Integer> path);
    public ArrayList<Integer> getPath();
    
}
