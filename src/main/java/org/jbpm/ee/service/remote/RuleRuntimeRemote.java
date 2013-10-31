package org.jbpm.ee.service.remote;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface RuleRuntimeRemote {

	int fireAllRules();
	
	int fireAllRules(int max);
	
	/**
     * Inserts a new fact into this entry point
     * 
     * @param object 
     *        the fact to be inserted
     *        
     * @return the fact handle created for the given fact
     */
    RemoteFactHandle insert(Object object);
    
    /**
     * Updates the fact for which the given FactHandle was assigned with the new
     * fact set as the second parameter in this method.
     *  
     * @param handle the FactHandle for the fact to be updated.
     * 
     * @param object the new value for the fact being updated.
     */
    void update(RemoteFactHandle remoteHandle,
                Object object);

    /**
     * Returns the fact handle associated with the given object. It is important to note that this 
     * method behaves in accordance with the configured assert behaviour for this knowledge base
     * (either IDENTITY or EQUALITY).
     *  
     * @param object 
     *               the fact for which the fact handle will be returned.
     * 
     * @return the fact handle for the given object, or null in case no fact handle was found for the
     *         given object.
     *         
     * @see org.kie.api.KieBaseConfiguration
     */
    RemoteFactHandle getFactHandle(Object object);
    
    /**
     * Returns the object associated with the given FactHandle.
     * 
     * @param factHandle
     * @return
     */
    Object getObject(RemoteFactHandle remoteHandle);

    /**
     * <p>
     * Returns all facts from the current session as a Collection.
     * </p>
     * 
     * <p>This class is <i>not</i> a general-purpose <tt>Collection</tt>
     * implementation!  While this class implements the <tt>Collection</tt> interface, it
     * intentionally violates <tt>Collection</tt> general contract, which mandates the
     * use of the <tt>equals</tt> method when comparing objects.</p>
     * 
     * <p>Instead the approach used when comparing objects with the <tt>contains(Object)</tt>
     * method is dependent on the WorkingMemory configuration, where it can be configured for <tt>Identity</tt>
     * or for <tt>Equality</tt>.</p> 
     * 
     * @return
     */
    Collection<? extends Object> getObjects();
    
    /**
     * Returns all <code>FactHandle</code>s from the current session.
     * 
     * @return
     */
    <T extends RemoteFactHandle> Collection< T > getFactHandles();
	
    /**
     * Returns the total number of facts currently in this entry point
     * 
     * @return
     */
    public long getFactCount();
}
