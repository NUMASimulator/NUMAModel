package machineNuma.node.socket;

import java.util.ArrayList;
import java.util.Arrays;
import machineNuma.Machine;
import systree.IHierarchical;
import machineNuma.icn.IChannel;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import systree.EventAdapter;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class Socket implements ISocket, IChannel{


    /**
     *
     */
    protected IHierarchical bus;

    
	
        //
        // cache llc
        // IChannel
        // pUnit[] 
        
    // TODO event listner component
    @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     */
            
        public Socket(){
            
        }
        
    /**
     *
     * @param bus
     */
    public Socket(IHierarchical bus) {
            this.bus = bus;
	}

    /**
     *
     * @return
     */
    @Override
	public ArrayList<IHierarchical> getChildren() {	
            ArrayList<IHierarchical> aryl = new ArrayList<>() ;
            aryl.add(bus);
            
            return aryl;
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
    public String isType() {
        return this.type;
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical[] getCPU() {
        ArrayList<IHierarchical> CPU = new ArrayList<>();
        RelationClass rC = new RelationClass();
        ArrayList<IHierarchical> buses =  rC.getByType(Bus.type, this);
        buses.stream().forEach((b)-> {
            Bus bus = (Bus)b;
            if(bus.getCPU() != null){
                CPU.addAll(Arrays.asList(bus.getCPU()));
            }
        });
        return (IHierarchical[]) CPU.toArray();
    }
    

    /**
     *
     * @return
     */
    @Override
    public IHierarchical[] getCore() {
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        
        for(IHierarchical cpu : this.getCPU() ){
            IHierarchical[] cores = ((ICPU)cpu).getCores();
            aryl.addAll(Arrays.asList(cores));
        }
        return (IHierarchical[]) aryl.toArray();
    }

    /**
     *
     * @return
     */
    @Override
    public int getCPUCount() {
        return this.getCPU().length;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCoreCount() {
        return this.getCore().length;
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
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child", 0, this));
            ((IChannel)this.getParent()).receive(rsp);
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child is done", 0, this));
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
