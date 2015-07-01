package machineNuma.node.memory;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
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
public class Memory implements IMemory, IChannel{
    // 

    /**
     *
     */
        public ArrayList<RamArchitecture> ram = new ArrayList<>();

    /**
     *
     */
    public int pageSize;

    /**
     *
     */
    public long pageCount;

    /**
     *
     */
    public long pagesLeft;
    private int nodeID;
    
    
    /**
     *
     */
    protected int lantency = 0;
    
    // TODO event listner component
        @Override
    public void addNumaEventListener(INumaEventListener numaEventListner){
        if(!this.numaEventListners.contains(numaEventListner))
            this.numaEventListners.add(numaEventListner);
    }
    
    /**
     *
     * @param ram
     */
    public Memory(ArrayList<RamArchitecture> ram, int lantency){
        this.ram = ram;
        this.lantency = lantency;
    }

    /**
     *
     * @param pageSize
     * @param pageCount
     * @param lantency
     */
    public Memory (int pageSize, long pageCount, int lantency){
            this.lantency = lantency;
            this.pageSize = pageSize;
            this.pageCount = pageCount;
            this.pagesLeft = pageCount;
    }
    
    /**
     *
     * @param pageSize
     * @param pageCount
     * @param nodeID
     * @param lantency
     */
    public Memory (int pageSize, long pageCount, int nodeID, int lantency){
            this.lantency = lantency;
            this.pageSize = pageSize;
            this.pageCount = pageCount;
            this.pagesLeft = pageCount;
            this.nodeID = nodeID;
            
    }
        @Override
    public void setNodeID(int id){
        this.nodeID = id;
    }
    
        @Override
    public int getNodeID(){
        return this.nodeID;
    }
    
    public ArrayList<RamArchitecture> getRam(){
        return this.ram;
    }

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
        return null;
    }


    /**
     *
     * @param index
     * @return
     */
    @Override
    public String Read(int index) {
        this.fireEvent(new EventAdapter("Read", "Local Memory Access", "Searching for data by index", this.lantency, this));
        for(RamArchitecture r : ram){
            if(r.getIndex() == index){
                this.fireEvent(new EventAdapter("Read", "Local Memory Access", "Searching for data by index is done", this.lantency, this));
                return r.getData();
            }
        }
        this.fireEvent(new EventAdapter("Read", "Local Memory Access", "Searching for data by index is done", this.lantency, this));
        return "";
        
        // other ways is remote memory access
        
        //return this.remoteMemoryAccess(index, "index")[0];
        
    }
    
    
    /**
     *
     * @param page
     * @return
     */
    @Override
    public String[] readByPage(int page) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.fireEvent(new EventAdapter("ReadByPage", "Local memory access", "Searching for data page by page index", this.lantency, this));
        ArrayList result = new ArrayList();
        ram.stream().filter((r) -> (page == r.getPageIndex())).forEach((r) -> {
            result.add(r.getPage());
        });
        
        if(!result.isEmpty()){
            this.fireEvent(new EventAdapter("ReadByPage", "Local memory access", "Searching for data page by page index is done", this.lantency, this));
            return (String[]) result.toArray();
        }
        // other ways is remote memory access
        this.fireEvent(new EventAdapter("ReadByPage", "Local memory access", "Searching for data page by page index is done", this.lantency, this));
        return new String[]{""};
        //return this.remoteMemoryAccess(page, "page");
    }
    
    //TODO

    /**
     *
     * @param index
     * @param data
     */
        @Override
    public void write(int index, String data) {
        this.fireEvent(new EventAdapter("Write", "Local Memory update", "Updating data", this.lantency, this));
        ram.stream().filter((r) -> (r.getIndex() == index)).forEach((r) -> {
            r.setData(data);
        });
        this.fireEvent(new EventAdapter("Write", "Local Memory update", "Updating data is done", this.lantency, this));
    }
    
    @Override
    public void write(String data){
        // TODO stackoverflow 
        this.fireEvent(new EventAdapter("Write", "Local Memory Storage", "Storaging data", this.lantency, this));
        RamArchitecture ra = ram.get(ram.size()-1);
        ram.add(new RamArchitecture(ra.getIndex()+1, data));
        this.fireEvent(new EventAdapter("Write", "Local Memory Storage", "Storaging data is done", this.lantency, this));
    }
    
    
    /**
     *
     * @param pageIndex
     * @param data
     */
        @Override
    public void writeByPage(int pageIndex, String[] data) {
        
        this.fireEvent(new EventAdapter("WriteByPage", "Local Memory page Update", "Updating page of data", this.lantency, this));
        ram.stream().filter((r) -> (r.getPageIndex() == pageIndex)).forEach((r) -> {
            r.setPage(data);
        });
        this.fireEvent(new EventAdapter("WriteByPage", "Local Memory page Update", "Updating page of data is done", this.lantency, this));
    }
    
    /**
     *
     * @param data
     */
    @Override
    public void writeByPage(String[] data){
        this.fireEvent(new EventAdapter("WriteByPage", "Local Memory page storage", "Storaging page of data", this.lantency, this));
        RamArchitecture ra = ram.get(ram.size()-1);
        ram.add(new RamArchitecture(ra.getPageIndex()+1, data));
        this.fireEvent(new EventAdapter("WriteByPage", "Local Memory page storage", "Storaging page of data is done", this.lantency, this));
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
        if(!rqt.isPorcessing()){
            // up
            this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent", 0, this));
            ((IChannel)this.getParent()).send(rqt);
            this.fireEvent(new EventAdapter("Send", "Sending message", "Sending message to parent is done", 0, this));
        }else{
            // processing request
            if(this.getIdentity() == rqt.getTarget().getIdentity()){
                Response rsp;
                if(rqt.getIndex() != -1){
                    rsp = new Response(rqt.getIndex(), -1,new String[]{this.Read(rqt.getIndex())}, rqt.getTarget(), rqt.getSource());
                    this.receive(rsp);
                }else if(rqt.getPageIndex() != -1){
                    rsp = new Response(rqt.getIndex(), -1,this.readByPage(rqt.getPageIndex()), rqt.getTarget(), rqt.getSource());
                    this.receive(rsp);
                }
            }
        }
    }
    
}