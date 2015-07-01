package machineNuma.node;

import java.util.ArrayList;
import machineNuma.Machine;
import systree.IHierarchical;
import machineNuma.icn.IBus;
import machineNuma.icn.IChannel;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import machineNuma.node.socket.ICPU;
import systree.EventAdapter;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class Node implements INode,IChannel {
	

    private long memory = 0;

    /**
     *
     */
    protected int osIndex;


    /**
     *
     */
    protected IHierarchical bus;


    // TODO event listner component
    @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }

    /**
     *
     */
    public Node() {
    }
    
    /**
     *
     * @param bus
     */
    public Node(IHierarchical bus){
        this.bus = bus;
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
        // TODO
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        if(this.bus != null)
            aryl.add(bus);
        
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
    public int getCoreCount() {
        int nCore = 0;
        
        for(IHierarchical c : ((IBus)bus).getCPU()){
            nCore += ((ICPU)c).getCountCore();
        }
        return nCore;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCPUCount() {
        return ((IBus)bus).getCPU().length;
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical getRam(){
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical[] getSocket(){
        return ((IBus)this.bus).getSocket();
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical getBus() {
        return ((IBus)this.bus);
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
        this.numaEventListners.stream().forEach((evnt) -> {
            evnt.numaEventHandler(args);
        });
    }
    
    @Override
    public void receive(IResponse rsp) {
        RelationClass rC = new RelationClass();
        if(!rsp.isPorcessing()){
            // up
            this.fireEvent(new EventAdapter("Receive", "Receive message", "Receive message from child", 0, this));
            ((IChannel)this.getParent()).receive(rsp);
            this.fireEvent(new EventAdapter("Receive", "Receive message", "Receive message from child is done", 0, this));
        }else{
            // down
            if(this.getIdentity() != rsp.getTarget().getIdentity()){
                this.fireEvent(new EventAdapter("Receive", "Receive message", "Receive message from parent", 0, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rsp.getTarget().getIdentity(), this), Machine.root)).receive(rsp);
                this.fireEvent(new EventAdapter("Receive", "Receive message", "Receive message from parent is done", 0, this));
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
                this.fireEvent(new EventAdapter("Sending", "Sending message", "Sending message to child", 0, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rqt.getTarget().getIdentity(), this), Machine.root)).send(rqt);
                this.fireEvent(new EventAdapter("Sending", "Sending message", "Sending message to child is done", 0, this));
            }
        }
    }
    
}