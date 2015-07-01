/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.node.socket;

import java.util.ArrayList;
import java.util.Arrays;
import machineNuma.Machine;
import machineNuma.icn.IChannel;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import systree.EventAdapter;
import systree.IHierarchical;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class CPU implements ICPU, IChannel{


    /**
     *
     */
    public int speed = 0;

    
    /**
     *
     */
    public IHierarchical[] cores;
    
    // TODO event listner component
    @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     */
    public CPU(){
    }
    
    public CPU(IHierarchical[] cores){
        this.cores = cores;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getCountCore() {
        return cores.length;
    }

    /**
     *
     * @return
     */
    @Override
    public int getSpeed() {
        return this.speed;
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical[] getCores() {
        return this.cores;
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
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        if(this.cores != null )
            aryl.addAll(Arrays.asList(cores));
        return aryl;
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
    public int getIdentity() {
        return System.identityHashCode(this);
    }
    
    @Override
    public void fireEvent(EventAdapter args){
        for(INumaEventListener evnt : this.numaEventListners)
            evnt.numaEventHandler(args);
    }
    
    @Override
    public void receive(IResponse rsp) {
        RelationClass rC = new RelationClass();
        if(!rsp.isPorcessing()){
            // up
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from Child", 0, this));
            ((IChannel)this.getParent()).receive(rsp);
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from Child is done", 0, this));
        }else{
            // down
            if(this.getIdentity() != rsp.getTarget().getIdentity()){
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent", 0, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rsp.getTarget().getIdentity(), this), Machine.root)).receive(rsp);
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent is done", 0, this));
            }
        }
    }

    @Override
    public void send(IRequest rqt) {
        RelationClass rC = new RelationClass();
        if(!rqt.isPorcessing()){
            // up
            this.fireEvent(new EventAdapter("Send", "Sending message", "Sedning message to parent", 0, this));
            ((IChannel)this.getParent()).send(rqt);
            this.fireEvent(new EventAdapter("Send", "Sending message", "Sedning message to parent is done", 0, this));
        }else{
            // down
            if(this.getIdentity() != rqt.getTarget().getIdentity()){
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to child", 0, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rqt.getTarget().getIdentity(), this), Machine.root)).send(rqt);
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to child is done", 0, this));
            }
        }
    }
    
}
