package machineNuma.node.memory;

import java.util.ArrayList;
        

import machineNuma.Machine;
import machineNuma.icn.IChannel;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import machineNuma.icn.Response;
import systree.EventAdapter;
import systree.IHierarchical;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class Cache implements ICache,IChannel{
	

    /**
     *
     */
    protected long size;

    /**
     *
     */
    protected int lineSize;
    protected int lantency = 0;

    
    // TODO event listner component
    @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     * @param size
     * @param lineSize
     */
    public void Cache(long size, int lineSize) {
            this.size = size;
            this.lineSize = lineSize;
    }

    
    /**
     *
     */
        @Override
    public ArrayList<IHierarchical> getChildren() {
        
        return null;		
    }

    /**
     *
     */
    

    @Override
    public String isType() {
        return this.type;
    }

    
    /**
     *
     */
    @Override
    public long getSize() {
        return this.size;
    }

    /**
     *
     */
    @Override
    public void update() {
        // TODO
        this.fireEvent(new EventAdapter("Update", "Updating", "Updating in cache", this.lantency, this));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public boolean hitOrMiss() {
        // TODO 
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public int getIdentity() {
        return System.identityHashCode(this);
    }

    @Override
    public IHierarchical getParent() {
        return new RelationClass().getParent(this.getIdentity(),Machine.root);
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
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child", 0, this));
            rsp.setPorcessing(true);
            this.receive(new Response(rsp.getIndex(), rsp.getPageIndex(), rsp.getData(), rsp.getTarget(), rsp.getSource()));
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child is done", 0, this));
        }else{
            // down
            if(this.getIdentity() != rsp.getTarget().getIdentity()){
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent", 0, this));
                ((IChannel)rC.getByID(rC.getNextComponent(this.getIdentity(), this), this)).receive(rsp);
                this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent is done", 0, this));
            }
        }
    }

    @Override
    public void send(IRequest rqt) {
        RelationClass rC = new RelationClass();
        if(!rqt.isPorcessing()){
            // up
            this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent", 0, this));
            ((IChannel)this.getParent()).send(rqt);
            this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent is done", 0, this));
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