/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systree;

/**
 *
 * @author Abed MAATALLA
 */
public class EventAdapter {
    public String eventName;
    public String eventType;
    public String message;
    public double eventTime;
    public IHierarchical component;

    public EventAdapter(String eventName, String eventType, String message, double eventTime, IHierarchical component) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.message = message;
        this.eventTime = eventTime;
        this.component = component;
    }
    
}
