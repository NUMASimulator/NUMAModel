/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import machineNuma.icn.Interconnection;
import machineNuma.node.Node;
import machineNuma.node.memory.Cache;
import machineNuma.node.memory.Memory;
import machineNuma.node.memory.RamArchitecture;
import machineNuma.node.socket.Bus;
import machineNuma.node.socket.CPU;
import machineNuma.node.socket.Core;
import machineNuma.node.socket.Socket;
import systree.EventAdapter;
import systree.IHierarchical;
import systree.INumaEventListener;
import systree.RelationClass;
import task.Communication;
import task.Computation;

/**
 *
 * @author Abed MAATALLA
 */
public class ClassTest implements INumaEventListener{
    
    public ClassTest(){
        do_scn001();
    }
    
    public ClassTest(int scn){
        switch( scn) {
            case 1:
                do_scn001();
                break;
            case 2:
                do_scn002();
                break;
        }
    }
    
    //
    public void  do_scn001(){
        
        ArrayList<RamArchitecture> dataRam = new ArrayList<>();
        dataRam.add(new RamArchitecture(0, "data from Memory 0"));
        dataRam.add(new RamArchitecture(1,"data1 from Memory 0"));
        
        Memory ram = new Memory(dataRam, 100);
        
        ArrayList<RamArchitecture> dataRam1 = new ArrayList<>();
        dataRam1.add(new RamArchitecture(0, "data 0 from Memory 1"));
        dataRam1.add(new RamArchitecture(1,"data 1 from Memory 1"));
        dataRam1.add(new RamArchitecture(2,"data 2 from Memory 1"));
        dataRam1.add(new RamArchitecture(3, "data 3 from Memory1"));
        dataRam1.add(new RamArchitecture(4,"data 4 from memory 1"));
        
        Memory ram1 = new Memory(dataRam1, 100);
        
        
        ArrayList<task.ITask> task  = new ArrayList<>();
        task.add(new Communication(0,ram));
        task.add(new Computation(3));
        task.add(new Computation(20));
        task.add(new Computation(20));
        task.add(new Communication(3, ram1));
        
        ArrayList<ArrayList<task.ITask>> tasks  = new ArrayList<>();
        tasks.add(task);
        tasks.add(task);
        
        IHierarchical[] cores = new Core[2];
        cores[0] = new Core(tasks);
        cores[1] = new Core(tasks);
        
        
        Cache cache = new Cache();
        
        ArrayList<IHierarchical> children = new ArrayList<>();
        children.add(new CPU(cores));
        children.add(cache);
        
        Bus bus = new Bus(children, 1000, 14);
        
        
        Node[] nodes = new Node[2];
        
        children = new ArrayList<>();
        children.add(new Socket(bus));
        
        children.add(ram);
        nodes[0] = new Node(new Bus(children, 1000,26));
        
        //---------------------------------------
        
          
        
        ArrayList<task.ITask> task1  = new ArrayList<>();
        task1.add(new Communication(0,ram1));
        task1.add(new Computation(20));
        task1.add(new Computation(20));
        
        ArrayList<ArrayList<task.ITask>> tasks1  = new ArrayList<>();
        tasks1.add(task1);
        tasks1.add(task1);
        
        IHierarchical[] cores2 = new Core[2];
        cores2[0] = new Core(tasks1);
        cores2[1] = new Core(tasks1);
        
        
        Cache cache2 = new Cache();
        
        children = new ArrayList<>();
        children.add(new CPU(cores2));
        children.add(cache2);
        Bus bus2 = new Bus(children, 1000, 14);
        
        children = new ArrayList<>();
        children.add(new Socket(bus2));
        children.add(ram1);
        
        nodes[1] = new Node(new Bus(children, 1000,26));
        
        //--------------------------------------
        
        
        Interconnection[] icn = new Interconnection[1];
        icn[0] = new Interconnection(nodes, 25);
        
        mach = new machineNuma.Machine(icn);
        this.addEventlistnerToComponent(this);
        mach.Run();
    }

    public void  do_scn002(){
        
        ArrayList<RamArchitecture> dataRam2 = new ArrayList<>();
        dataRam2.add(new RamArchitecture(0, "data from Memory 2"));
        dataRam2.add(new RamArchitecture(1,"data1 from Memory 2"));
        
        Memory ram2 = new Memory(dataRam2, 100);
        
        ArrayList<RamArchitecture> dataRam3 = new ArrayList<>();
        dataRam3.add(new RamArchitecture(0, "data 0 from Memory 3"));
        dataRam3.add(new RamArchitecture(1,"data 1 from Memory 3"));
        dataRam3.add(new RamArchitecture(2,"data 2 from Memory 3"));
        dataRam3.add(new RamArchitecture(3, "data 3 from Memory 3"));
        dataRam3.add(new RamArchitecture(4,"data 4 from memory 3"));
        
        Memory ram3 = new Memory(dataRam3, 100);
        
        ArrayList<RamArchitecture> dataRam = new ArrayList<>();
        dataRam.add(new RamArchitecture(0, "data from Memory 0"));
        dataRam.add(new RamArchitecture(1,"data1 from Memory 0"));
        
        Memory ram = new Memory(dataRam, 100);
        
        ArrayList<RamArchitecture> dataRam1 = new ArrayList<>();
        dataRam1.add(new RamArchitecture(0, "data 0 from Memory 1"));
        dataRam1.add(new RamArchitecture(1,"data 1 from Memory 1"));
        dataRam1.add(new RamArchitecture(2,"data 2 from Memory 1"));
        dataRam1.add(new RamArchitecture(3, "data 3 from Memory1"));
        dataRam1.add(new RamArchitecture(4,"data 4 from memory 1"));
        
        Memory ram1 = new Memory(dataRam1, 100);
        
        
        ArrayList<task.ITask> task3  = new ArrayList<>();
        task3.add(new Communication(0,ram));
        task3.add(new Computation(3));
        task3.add(new Computation(20));
        task3.add(new Computation(20));
        task3.add(new Communication(3, ram1));
        
        ArrayList<ArrayList<task.ITask>> tasks3  = new ArrayList<>();
        tasks3.add(task3);
        tasks3.add(task3);
        
        IHierarchical[] cores3 = new Core[2];
        cores3[0] = new Core(tasks3);
        cores3[1] = new Core(tasks3);
        
        
        Cache cache3 = new Cache();
        
        ArrayList<IHierarchical> children3 = new ArrayList<>();
        children3.add(new CPU(cores3));
        children3.add(cache3);
        
        Bus bus3 = new Bus(children3, 1000, 14);
        
        
        Node[] nodes = new Node[4];
        
        children3 = new ArrayList<>();
        children3.add(new Socket(bus3));
        
        children3.add(ram3);
        nodes[0] = new Node(new Bus(children3, 1000,26));
        
        //---------------------------------------
        
          
        
        ArrayList<task.ITask> task2  = new ArrayList<>();
        task2.add(new Communication(0,ram1));
        task2.add(new Computation(20));
        task2.add(new Computation(20));
        
        ArrayList<ArrayList<task.ITask>> tasks2  = new ArrayList<>();
        tasks2.add(task2);
        tasks2.add(task2);
        
        IHierarchical[] cores2 = new Core[2];
        cores2[0] = new Core(tasks2);
        cores2[1] = new Core(tasks2);
        
        
        Cache cache2 = new Cache();
        
        children3 = new ArrayList<>();
        children3.add(new CPU(cores2));
        children3.add(cache2);
        Bus bus2 = new Bus(children3, 1000, 14);
        
        children3 = new ArrayList<>();
        children3.add(new Socket(bus2));
        children3.add(ram2);
        
        nodes[1] = new Node(new Bus(children3, 1000,26));
        
        //---------------------------------------
        
          
        
        ArrayList<task.ITask> task1  = new ArrayList<>();
        task1.add(new Communication(0,ram1));
        task1.add(new Computation(20));
        task1.add(new Computation(20));
        
        ArrayList<ArrayList<task.ITask>> tasks1  = new ArrayList<>();
        tasks1.add(task1);
        tasks1.add(task1);
        
        IHierarchical[] cores1 = new Core[2];
        cores1[0] = new Core(tasks1);
        cores1[1] = new Core(tasks1);
        
        
        Cache cache1 = new Cache();
        
        children3 = new ArrayList<>();
        children3.add(new CPU(cores1));
        children3.add(cache1);
        Bus bus1 = new Bus(children3, 1000, 14);
        
        children3 = new ArrayList<>();
        children3.add(new Socket(bus1));
        children3.add(ram1);
        
        nodes[2] = new Node(new Bus(children3, 1000,26));
        //---------------------------------------
        
          
        
        ArrayList<task.ITask> task   = new ArrayList<>();
        task.add(new Communication(0,ram1));
        task.add(new Computation(20));
        task.add(new Computation(20));
        
        ArrayList<ArrayList<task.ITask>> tasks  = new ArrayList<>();
        tasks.add(task);
        tasks.add(task);
        
        IHierarchical[] cores = new Core[2];
        cores[0] = new Core(tasks);
        cores[1] = new Core(tasks);
        
        
        Cache cache = new Cache();
        
        children3 = new ArrayList<>();
        children3.add(new CPU(cores));
        children3.add(cache);
        Bus bus = new Bus(children3, 1000, 14);
        
        children3 = new ArrayList<>();
        children3.add(new Socket(bus));
        children3.add(ram);
        
        nodes[3] = new Node(new Bus(children3, 1000,26));
        //--------------------------------------
        
        
        Interconnection[] icn = new Interconnection[1];
        icn[0] = new Interconnection(nodes, 25);
        
        mach = new machineNuma.Machine(icn);
        this.addEventlistnerToComponent(this);
        mach.Run();
    }
    
    public machineNuma.Machine mach;
    
    public void addEventlistnerToComponent(ClassTest test){
        RelationClass rC = new RelationClass();
        ArrayList<IHierarchical> all =  rC.getAll(test.mach);
        for(IHierarchical one : all){
            one.addNumaEventListener(test);
        }
    }

    /**
     *
     * @param args
     */
    @Override
    public void numaEventHandler(EventAdapter args) {
        Logger.getLogger(ClassTest.class.getName()).log(Level.INFO,"Method : "+ args.eventName +" Message: "+ args.message+" entity type: " + args.component.isType() );
        evntlist.add(args);
        if(!args.message.contains("done")){
            evntarray.add(new EventStartEndAdapter(args));
        }else{
            Collections.reverse(evntarray);
            for(EventStartEndAdapter ea : evntarray){
                if(ea.start.component.getIdentity() == args.component.getIdentity() 
                        && ea.end == null && args.message.contains( ea.start.message)){
                    ea.end = args;
                }
            }
            Collections.reverse(evntarray);
        }
        
    }
    
    public static ArrayList<ArrayList<EventStartEndAdapter>> getEventByCores(ArrayList<EventStartEndAdapter> tasklist){
        ArrayList<ArrayList<EventStartEndAdapter>> tasksCore = new ArrayList<>();
                ArrayList<EventStartEndAdapter> helperlist = new ArrayList<>();
                int id = -1;
                for(EventStartEndAdapter e : tasklist){
                    
                        if(e.start.component.isType().equals(Core.type) && id != e.start.component.getIdentity()){
                            id = e.start.component.getIdentity();
                            tasksCore.add(helperlist);
                            helperlist = new ArrayList<>();
                        }
                    helperlist.add(e);
                }
                tasksCore.add(helperlist);
                
                return tasksCore;
    }
    
  
    public ArrayList<EventAdapter> evntlist = new ArrayList<>();
    
    // it's an event array of ( end | start) with start time of each event.
    public static ArrayList<EventStartEndAdapter> evntarray = new ArrayList<>();

    //public static ArrayList<ArrayList<EventStartEndAdapter>> evntcore = new ArrayList<>();
}
