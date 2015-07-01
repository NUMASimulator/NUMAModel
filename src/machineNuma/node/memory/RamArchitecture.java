/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.node.memory;

/**
 *
 * @author Abed MAATALLA
 */
public class RamArchitecture {
    private int pageIndex;
    private int index;
    private String data;
    private String[] page;
  
    
    public RamArchitecture(){}
    public RamArchitecture(int index, String data){
        this.index = index;
        this.data = data;
    }
    public RamArchitecture(int pageIndex, String[] page){
        this.pageIndex = pageIndex;
        this.page = page;
    }
    
    public RamArchitecture(int index, int pageIndex, String data){
        this.pageIndex = pageIndex;
        this.index = index;
        this.data = data;
    }
    
    public int getIndex(){
        return this.index;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    /**
     *
     * @return
     */
    public int getPageIndex(){
        return this.pageIndex;
    }
    
    /**
     *
     * @param page
     */
    public void setPageIndex(int page){
        this.pageIndex = page;
    }
    
    /**
     *
     * @return
     */
    public String getData(){
        return this.data;
    }
    
    /**
     *
     * @param data
     */
    public void setData(String data){
        this.data = data;
    }
    
    public String[] getPage(){
        return this.page;
    }
    
    public void setPage(String[] page){
        this.page = page;
    }
}
