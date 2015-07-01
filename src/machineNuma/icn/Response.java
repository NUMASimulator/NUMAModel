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
public class Response implements IResponse{
    
    private int index; 
    private int pageIndex;
    private String[] data;
    private IHierarchical source;
    private IHierarchical target;
    private ArrayList<Integer> path;
    
    private boolean processing = false;
    
    public Response(){
        
    }

    @Override
    public ArrayList<Integer> getPath() {
        return path;
    }

    @Override
    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }
    
    public Response(int index, int pageIndex, String[] page, IHierarchical source, IHierarchical target ){
        this.index = index;
        this.index = pageIndex;
        this.data = page; 
        this.source = source;
        this.target = target;
    }
    
    


    @Override
    public IHierarchical getSource() {
        return this.source;
    }

    @Override
    public String[] getData() {
        
        return data;
    }

    @Override
    public IHierarchical getTarget() {
        return this.target;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public void setSource(IHierarchical source) {
        this.source = source;
    }

    @Override
    public void setTargate(IHierarchical target) {
        this.target = target;
    }

    @Override
    public boolean isPorcessing() {
        return this.processing;
    }

    @Override
    public void setPorcessing(boolean processing) {
        this.processing = processing;
    }
    
}
