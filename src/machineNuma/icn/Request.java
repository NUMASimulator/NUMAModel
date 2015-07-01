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
public class Request implements IRequest{

    private int index; 
    private int pageIndex;
    private String msg; 
    private IHierarchical source;
    private IHierarchical target;
    private boolean processing = false;
    
    public Request(){
        
    }
    
    public Request(int index, int pageIndex, IHierarchical source, IHierarchical target){
        this.index = index;
        this.pageIndex = pageIndex;
        this.source = source;
        this.target = target;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public void setSource(IHierarchical source) {
        this.source = source;
    }

    @Override
    public IHierarchical getTarget() {
        return target;
    }

    public void setTarget(IHierarchical target) {
        this.target = target;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }


    @Override
    public IHierarchical getSource() {
        return this.source;
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
    public boolean isPorcessing() {
        return this.processing;
    }
    
    @Override
    public void setPorcessing(boolean processing){
        this.processing = processing;
    }
    
}
