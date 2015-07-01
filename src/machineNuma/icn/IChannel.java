/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma.icn;

import systree.IHierarchical;

/**
 *
 * @author Abed MAATALLA
 */
public interface IChannel extends IHierarchical{

    /**
     *
     * @param rsp
     */
    public void receive(IResponse rsp);

    /**
     *
     * @param rqt
     */
    public void send(IRequest rqt);
}
