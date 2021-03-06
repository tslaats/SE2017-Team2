// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package datamodel.petri;

import java.util.HashMap;
import java.util.Map;

import datamodel.Position;

/************************************************************/
/**
 * 
 */
public abstract class PetriObject {
	/**
	 * 
	 */
	protected Map<Integer, PetriObject> incoming;
	/**
	 * 
	 */
	protected Map<Integer, PetriObject> outgoing;
	
	public Map<Integer, PetriObject> getIncoming() {
		return incoming;
	}

	public Map<Integer, PetriObject> getOutgoing() {
		return outgoing;
	}

	private static int LatestID;
	/**
	 * 
	 */
	protected int id;
	/**
	 * 
	 */
	protected Position pos;
	


	/**
	 * If false, then this PetriObject can't be deleted from the Petri Net
	 */
	private Boolean canBeDeleted;
	
	protected PetriObject(Position pos) {
		this.pos = pos;
		LatestID++;
		this.id = LatestID;
		this.setCanBeDeleted(true);
		
		// Init HashMaps
		this.incoming = new HashMap<>();
		this.outgoing = new HashMap<>();
	}
	
	public int getID() {
		return this.id;
	}

	public Boolean getCanBeDeleted() {
		return canBeDeleted;
	}

	public void setCanBeDeleted(Boolean canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
	}
	
	/**
	 * Adds a PetriObject to the incoming HashMap
	 * 
	 * @param petriObject The PetriObject to be added
	 */
	public void addIncomingObject(PetriObject petriObject) {
		this.incoming.put(petriObject.id, petriObject);
	}
	
	/**
	 * Removes the PetriObject with the given ID from the incoming Map
	 * 
	 * @param petriObjectID ID of the object to be removed
	 * @throws Exception If the ID does not exist
	 */
	public void removeIncomingObject(int petriObjectID) throws Exception{
		if (!this.incoming.containsKey(petriObjectID)) {
			throw new Exception("The given petriObjectID does not exist in the incoming Map");
		}
		
		this.incoming.remove(petriObjectID);
	}
	
	/**
	 * Adds a PetriObject to the outgoing HashMap
	 * 
	 * @param petriObject The PetriObject to be added
	 */
	public void addOutgoingObject(PetriObject petriObject) {
		this.outgoing.put(petriObject.id, petriObject);
	}
	
	/**
	 * Removes the PetriObject with the given ID from the outgoing Map
	 * 
	 * @param petriObjectID ID of the object to be removed
	 * @throws Exception If the ID does not exist
	 */
	public void removeOutgoingObject(int petriObjectID) throws Exception{
		if (!this.outgoing.containsKey(petriObjectID)) {
			throw new Exception("The given petriObjectID does not exist in the outgoing Map");
		}
		
		this.outgoing.remove(petriObjectID);
	}
	
	/**
	 * Goes through the neighboring PetriObjects and removes this PetriObject from the neighboring's
	 * respective incoming and outgoing Maps.
	 * 
	 * @throws Exception If this PetriObject's ID does not exist at a neighbor PetriObject
	 */
	public void removeFromNeighbors() throws Exception {
		for(PetriObject po : this.incoming.values()) {
			if (po != null) {
				po.removeOutgoingObject(this.id);
			}
		}
		for(PetriObject po : this.outgoing.values()) {
			if (po != null) {
				po.removeIncomingObject(this.id);
			}
		}
	}
	
	public Position getPos() {
		return pos;
	}
	
	
	
};
