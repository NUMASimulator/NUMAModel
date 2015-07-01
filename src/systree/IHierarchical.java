package systree;
import java.util.ArrayList;
/**
 *
 * @author Abed MAATALLA
 */
public interface IHierarchical {
       
       public final ArrayList<INumaEventListener> numaEventListners = new ArrayList<>();
       
       public void addNumaEventListener(INumaEventListener numaEventListner);
    
    /**
     *
     * @return
     */
        public IHierarchical getParent();
        
    /**
     *
     * @return
     */
        public ArrayList<IHierarchical> getChildren();
        
    /**
     *
     * @return
     */
    public int getIdentity();
    
    /**
     *
     * @return
     */
        public String isType();
        
    public void fireEvent(EventAdapter args);
    
}