/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineNuma;

import machineNuma.icn.IChannel;

/**
 *
 * @author Abed MAATALLA
 */
public interface ICompent {

    /**
     *
     */
    public IChannel bus = null;
    
    /**
     *
     */
    public ICompent [] compents = null;
}
