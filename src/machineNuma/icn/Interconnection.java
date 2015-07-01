package machineNuma.icn;

import java.util.ArrayList;
import java.util.Arrays;
import machineNuma.Machine;
import systree.IHierarchical;
import systree.EventAdapter;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class Interconnection implements IInterConnection, IChannel {

    /**
     *
     */
    public static int level = 0; 


    /**
     *
     */
    protected int bandWidth;

    /**
     *
     */
    protected int lantency = 0;
    
    
    
    private IHierarchical[] nodes;
    
    private Request lRqt;
    private Response lRsp;
    
    
    // TODO event listner component
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     */
    public Interconnection(){
    }
    
    /**
     *
     * @param nodes
     * @param lantency
     */
    public Interconnection(IHierarchical[] nodes, int lantency){
        this.lantency = lantency;
        this.nodes = nodes;
        
    }

    //TODO

    /**
     *
     * @return
     */
    @Override
        public IHierarchical getParent(){
        return new RelationClass().getParent(this.getIdentity(),Machine.root);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<IHierarchical> getChildren(){
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        aryl.addAll(Arrays.asList(nodes));
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
    public IHierarchical[] getNodes() {
        return nodes;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getNodeCount() {
        return nodes.length;
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
    public Request getRequest() {
        return this.lRqt;
    }

    @Override
    public Response getResponse() {
        return this.lRsp;
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

    @Override
    public void send(IRequest rqt) {
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
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent", this.lantency, this));
                ((IChannel)rC.getByID(rC.getNextComponent(rqt.getTarget().getIdentity(), this), Machine.root)).send(rqt);
                this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent is done", this.lantency, this));
            }
            
        }

    }

   
}