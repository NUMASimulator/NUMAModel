package systree; 

import java.util.ArrayList;
import java.util.Collections;
import machineNuma.Machine;
import machineNuma.node.Node;
/**
 * this class is basically going to search on all children of each components
 * to find the root, what it means in this case a father. 
 * 
 * because all children are array list of IHierarchical interface we run a loop
 * to search for that specific Id and return its root.
 * @author Abed MAATALLA
 */
public class RelationClass {
    
    private IHierarchical root;

    /**
     *
     */
    public RelationClass() {
        this.root = Machine.root;
    }

    
    /**
     *
     * @param Id
     * @param root
     * @return
     */
    public IHierarchical getParent(int Id,IHierarchical root){
        
        ArrayList<IHierarchical> yetToDiscover = new ArrayList<>();
        yetToDiscover.add(root);
        
        while(!yetToDiscover.isEmpty()){
            IHierarchical child = yetToDiscover.get(0);
            if(child != null){
                if(child.getChildren() != null)
                //yetToDiscover.addAll(child.getChildren());
                for(IHierarchical chil : child.getChildren()){
                    if(chil != null){
                        yetToDiscover.add(chil);
                        if(chil.getIdentity() == Id)
                            return child;
                    }
                }
                
            }
            yetToDiscover.remove(0);
        }
            
        
        return null;
        
        
        /*
        ArrayList<IHierarchical> children = root.getChildren();
        
        for(IHierarchical child : children){
            if(child != null){
                if(child.getIdentity() == Id)
                    return root;
                else
                    return getParent(Id,child);
            }
        }
        return root;*/
    }
    
    public IHierarchical getByID(int Id,IHierarchical root){
        
        ArrayList<IHierarchical> yetToDiscover = new ArrayList<>();
        yetToDiscover.add(root);
        
        while(!yetToDiscover.isEmpty()){
            IHierarchical child = yetToDiscover.get(0);
            yetToDiscover.remove(0);
            if(child != null)
            if(child.getIdentity() == Id)
                return child;
            else{
                if(child.getChildren() != null)
                    yetToDiscover.addAll(child.getChildren());
            }
            
        }
        return null;
       
    }
    
    ArrayList<IHierarchical> ressourceByType = new ArrayList<>();

    public ArrayList<IHierarchical> getByType(String type,IHierarchical root){
        
        ArrayList<IHierarchical> yetToDiscover = new ArrayList<>();
        yetToDiscover.add(root);
        
        while(!yetToDiscover.isEmpty()){
            IHierarchical child = yetToDiscover.get(0);
            yetToDiscover.remove(0);
            if(child != null)
            if(child.isType().equals(type))
                ressourceByType.add(child);
            else{
                if(child.getChildren() != null)
                    yetToDiscover.addAll(child.getChildren());
            }
            
        }
        return ressourceByType;
        
    }
    
    public ArrayList<Integer> getPath(int ID){
        IHierarchical root = getByID(ID, Machine.root);
        ArrayList<Integer> aryl = new ArrayList<>();
        while(root != Machine.root){
            aryl.add(root.getIdentity());
            root = root.getParent();
        }
        aryl.add(root.getIdentity());
        Collections.reverse(aryl);
        return aryl;
    }
    
    public int getNextComponent(int ID, IHierarchical root){
        
        ArrayList<Integer> path = getPath(ID);
        int index = 0;
        for(int id : path){
            if(root.getIdentity() == id)
                break;
            index++;
        }
        index++;
        return path.get(index);
    }
    
    public int getCommunParent(ArrayList<Integer> comp1, ArrayList<Integer> comp2){
        Collections.reverse(comp2);
        Collections.reverse(comp1);
        for (int comp20 : comp1) {
            for (int comp : comp2) {
                if(comp == comp20)
                    return comp;
            }
        }
        return 0;
    }
    
  
    
    public ArrayList<IHierarchical> getCommunFatherOfType(int entity, String type, IHierarchical root){
        ArrayList<IHierarchical> aryl =  this.getByType(Node.type, root);
        
        for(IHierarchical en : aryl){
            IHierarchical r = this.getByID(entity, en);
            if(r != null)
               return  this.getByType(type,en);
        }
        return null;
        //return this.getByType(type, root);
    }
    
    public ArrayList<IHierarchical> getAll(IHierarchical root){
        ArrayList<IHierarchical> ressources = new ArrayList<>();
        
        
        ArrayList<IHierarchical> yetToDiscover = new ArrayList<>();
        yetToDiscover.add(root);
        
        while(!yetToDiscover.isEmpty()){
            IHierarchical child = yetToDiscover.get(0);
            yetToDiscover.remove(0);
            if(child != null){
            
                ressources.add(child);
                if(child.getChildren() != null)
                    yetToDiscover.addAll(child.getChildren());
            }
            
        }
        
        
        return ressources;
    }
}