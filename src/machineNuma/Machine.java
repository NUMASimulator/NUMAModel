package machineNuma;

import systree.IHierarchical;
import java.util.ArrayList;
import java.util.Arrays;
import machineNuma.icn.IChannel;
import machineNuma.icn.IInterConnection;
import machineNuma.icn.IRequest;
import machineNuma.icn.IResponse;
import machineNuma.icn.Interconnection;
import machineNuma.icn.Response;
import machineNuma.node.INode;
import machineNuma.node.socket.Core;
import systree.EventAdapter;
import systree.INumaEventListener;
import systree.RelationClass;

/**
 *
 * @author Abed MAATALLA
 */
public class Machine implements IMachine,IChannel {
   
    /**
     *
     */
    public static IHierarchical root;
      
    private Interconnection[] interConnection ;
        
    // TODO event listner component
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
	
    /**
     *
     */
    public Machine (){
        
        root = this;
    }
        //

    /**
     *
     * @param interconnection
     */
    	public Machine (Interconnection[] interconnection) {
		//this.nodes = nodes;		//this.memory = memory;
            this.interConnection = interconnection;
            
            root = this;
	}
        
     /**
     *
     * @param interconnection
     */
    	public Machine (Interconnection[] interconnection, INumaEventListener evnListner) {
            this.interConnection = interconnection;
            root = this;
	}
        
        
        public void Run(){
            // machine start running
            this.fireEvent(new EventAdapter("Run", "Starting", "NUMA machine", 0, this));
            if(this.interConnection != null){
                RelationClass rC = new RelationClass();
                ArrayList<IHierarchical> cores = rC.getByType(Core.type, root);
                for(IHierarchical cr : cores){
                    Core core;
                    try {
                        core = (Core)cr;
                        core.compute();
                    } catch (Exception e) {
                    }
                }
            }
            // machine stop running
            this.fireEvent(new EventAdapter("Run", "Stopping", "NUMA machine is done", 0, this));
        }

    /**
     * isNUMA function that will return true if interconnectionNetwork has more then one node.
     * and false so it UMA if it has only one node
     * @return 
     */
    @Override
    public boolean isNUMA() {
        return (this.getNodeCount() > 1);
    }

    /**
     *
     * @return
     */
    @Override
    public IHierarchical getParent() {
            return null;
    }

    /**
     * 
     * @return counted nodes in this machine
     */
    public int getNodeCount() {
        int nodeCount = 0;
        for(IHierarchical icn : this.interConnection)
            nodeCount += ((IInterConnection)icn).getNodeCount();
        return nodeCount;	
    }

    /**
     * 
     * @param nodes
     * @return counted cores in this machine
     */
    public int getCoreCount( INode[] nodes) {
        int coreCount = 0;

        if (isNUMA()){
            for (INode node : nodes)
                    coreCount += node.getCoreCount();
        }else{
            for(IChannel icn : this.interConnection)
                coreCount += ((INode) icn.getChildren().get(0)).getCoreCount();
        }
        return coreCount;
    }
        
    @Override
    public ArrayList<IHierarchical> getChildren() {
        ArrayList<IHierarchical> aryl = new ArrayList<>();
        aryl.addAll(Arrays.asList(interConnection));
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
    public IHierarchical getByID(int ID) {
        return new RelationClass().getByID(ID,Machine.root);
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
            rsp.setPorcessing(true);
            this.receive(new Response(rsp.getIndex(), rsp.getPageIndex(), rsp.getData(), rsp.getTarget(), rsp.getSource()));
        }else{
            // down
            if(this.getIdentity() != rsp.getTarget().getIdentity())
                ((IChannel)rC.getByID(rC.getNextComponent(rsp.getTarget().getIdentity(), this), this)).receive(rsp);
        }
    }

    @Override
    public void send(IRequest rqt) {
        RelationClass rC = new RelationClass();
        if(!rqt.isPorcessing()){
            // up
            rqt.setPorcessing(true);
            this.send(rqt);
        }else{
            // down
            if(this.getIdentity() != rqt.getTarget().getIdentity())
                ((IChannel)rC.getByID(rC.getNextComponent(this.getIdentity(), this), this)).send(rqt);
        }
    }
}