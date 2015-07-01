package machineNuma.node.socket;

import task.Computation;
import java.util.ArrayList;
import machineNuma.Machine;
import machineNuma.icn.IBus;
import machineNuma.icn.IChannel;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import systree.EventAdapter;
import systree.IHierarchical;
import systree.INumaEventListener;
import systree.RelationClass;
import task.Communication;
import task.ITask;
import task.Storage;
import machineNuma.icn.Request;

/**
 *
 * @author Abed MAATALLA
 */
public class Core implements ICore, IChannel {

    private String[] data;
    
    private ArrayList<ArrayList<ITask>> tasks;
	
    
    // TODO event listner component
    
    @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     */
    public Core(){
            //this.fireEvent(new EventAdapter("Core", "New Core object", "new instance of core", 0, this));
    }

    public Core(ArrayList<ArrayList<ITask>> tasks){
        //this.fireEvent(new EventAdapter("Core", "New Core object", "new instance of core with tasks", 0, this));
        this.tasks = tasks;
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
            
            return null;
	}
	
    /**
     *
     * @return
     */
    

    @Override
    public String isType() {
        return ICore.type;
    }

    /**
     *
     * @return
     */
    @Override
    public int getIdentity() {
        return System.identityHashCode(this);

    }

    /**
     *
     * @param index
     * @param target
     * @return 
     */
    @Override
    public String[] load(int index, IHierarchical target) {
        // read from memory
        this.fireEvent(new EventAdapter("load", "communication", "loading data", 0, this));
        //((IBus)this.getParent()).send(ssss);
        IHierarchical parent = this.getParent();
        while(!parent.isType().equals(IBus.type)){
            if(parent.getParent() == null)
                break;
            parent = parent.getParent();
        }
        
        ((Bus)parent).send(new Request(index , -1, this, target));
        this.fireEvent(new EventAdapter("load", "communication", "loading data is done", 0, this));
        return this.data;
        
    }

    /**
     *
     * @param index
     * @param data
     */
    @Override
    public void store(int index, int data) {
        // write to memory
        this.fireEvent(new EventAdapter("store", "store", "Store data in to index: "+index, 0, this));
    }

    /**
     *
     */
    @Override
    public void compute() {
        // Computation Thread.sleep();
        this.tasks.stream().forEach((taskes) -> {
            taskes.stream().forEach((task) -> {
                Computation comp = null;
                Communication commu = null;
                Storage store = null;
                try {
                    comp = (Computation)task;
                } catch (Exception e) {
                }

                try {
                    commu = (Communication) task;
                } catch (Exception e) {
                }
                if(comp != null){
                    this.fireEvent(new EventAdapter("compute", "computation", "task computation", comp.time, this));
                    //System.out.println("Slept for "+ comp.time);
                    this.fireEvent(new EventAdapter("compute", "computation", "task computation is done", 0, this));

                }else if(commu != null){

                    String[] dataReturn = this.load(commu.index, commu.target);
                    
                    //System.out.println(dataReturn[0]);
                }else if(store != null){

                    this.store(0,0);
                }
            });
        });
    }
    
    @Override
    public void fireEvent(EventAdapter args){
        for(INumaEventListener evnt : this.numaEventListners)
            evnt.numaEventHandler(args);
    }
    
    @Override
    public void receive(IResponse rsp) {
        if(!rsp.isPorcessing()){
            // up
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child", 0, this));
            ((IChannel)this.getParent()).receive(rsp);
            
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from child is done", 0, this));
        }else{
            // down
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent", 0, this));
            this.data = rsp.getData();
            this.fireEvent(new EventAdapter("Receive", "Receiving message", "Receiving message from parent is done", 0, this));
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