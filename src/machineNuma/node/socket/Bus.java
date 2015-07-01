/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.node.socket;

import java.util.ArrayList;
import machineNuma.Machine;
import systree.IHierarchical;
import machineNuma.icn.IBus;
import machineNuma.icn.IChannel;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import machineNuma.node.memory.IMemory;
import machineNuma.node.memory.Memory;
import systree.EventAdapter;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class Bus implements IBus,IChannel{

    /**
     *
     */
    public static int level = 0; 

    


    /**
     *
     */
    protected ArrayList<IHierarchical> children;
    
    /**
     *
     */
    protected int lantency = 0;
    
    /**
     *
     */
    protected int bandWidth = 0;
    
    @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     */
    public Bus(){
    }
    
    public Bus(int bandwidth, int lantency){
        this.bandWidth = bandwidth;
        this.lantency = lantency;
    }
    
    public Bus(ArrayList<IHierarchical> children,int bandwidth, int lantency){

        this.children = children;
        this.bandWidth = bandwidth;
        this.lantency = lantency;
    }
    
    /**
     *
     * @return
     */
    @Override
    public IHierarchical getParent() {
        return new RelationClass().getParent(this.getIdentity(),Machine.root);
        
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<IHierarchical> getChildren() {
        return this.children;
    }

    /**
     *
     * @return
     */
    @Override
    public String isType() {
        return this.type;
    }

    
    /**
     *
     * @return
     */
    

    @Override
    public IHierarchical getMemory() {
        for(IHierarchical comp : this.children)
            if(comp.isType().equals(IMemory.type))
                return comp;
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical[] getSocket() {
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        this.children.stream().filter((comp) -> (comp.isType().equals(IMemory.type))).forEach((comp) -> {
            aryl.add(comp);
        });
        return (IHierarchical[]) aryl.toArray();
    }

    /**
     *
     * @return
     */
    @Override
    public int getSocketCount() {
        return this.getSocket().length;
    }
    
    @Override
    public IHierarchical[] getCPU(){
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        this.children.stream().filter((comp) -> (comp.isType().equals(IMemory.type))).forEach((comp) -> {
            aryl.add(comp);
        });
        return (IHierarchical[]) aryl.toArray();
    }
    
    @Override
    public int getCPUCount(){
        return this.getCPU().length;
    }
    
    @Override
    public void send(IRequest rqt){
        RelationClass rC = new RelationClass();
        if(!rqt.isPorcessing()){
            // up
            
            if(this.getIdentity() != rC.getCommunParent(rC.getPath(rqt.getSource().getIdentity()), rC.getPath(rqt.getTarget().getIdentity()))){
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent", this.lantency, this));
                ((IChannel)this.getParent()).send(rqt);
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent is done", this.lantency, this));
            }else{
                // fire proccessing rqt
                rqt.setPorcessing(true);
                this.send(rqt);
            }
        }else{
            // down
            if(this.getIdentity() != rqt.getTarget().getIdentity()){
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to child", this.lantency, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rqt.getTarget().getIdentity(), this), Machine.root)).send(rqt);
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to child is done", this.lantency, this));
            }
        }          
    }
    
    @Override
    public void receive(IResponse rsp){
        RelationClass rC = new RelationClass();
        if(!rsp.isPorcessing()){
            // up
            
            if(this.getIdentity() != rC.getCommunParent(rC.getPath(rsp.getSource().getIdentity()), rC.getPath(rsp.getTarget().getIdentity()))){
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child", this.lantency, this));
                ((IChannel)this.getParent()).receive(rsp);
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child is done", this.lantency, this));
            }else{
                // fire proccessing rsp
                rsp.setPorcessing(true);
                this.receive(rsp);
            }
        }else{
            // down
            if(this.getIdentity() != rsp.getTarget().getIdentity()){
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent", this.lantency, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rsp.getTarget().getIdentity(), this), Machine.root)).receive(rsp);
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent is done", this.lantency, this));
            }
        }          
    }
     

    /**
     *
     * @return
     */
    @Override
    public int getLevel() {
        return this.level;
    }

    /**
     *
     * @param level
     */
    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return
     */
    @Override
    public int getIdentity() {
        return System.identityHashCode(this);
    }
    
    
    @Override
    public void fireEvent(EventAdapter args){
        for(INumaEventListener evnt : this.numaEventListners)
            evnt.numaEventHandler(args);
    }  
}
