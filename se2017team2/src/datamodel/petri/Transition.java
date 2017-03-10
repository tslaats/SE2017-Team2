// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package datamodel.petri;

import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.petri.PetriObject;

/************************************************************/
/**
 * 
 */
public class Transition extends PetriObject {

	/**
	 * Name of this Transition
	 */
	private String name;
	/**
	 * The CrGraph that this Transition can refer to.
	 * Null if not set.
	 */
	private CrGraph crGraph;
		
	public Transition(Position pos, String name) {
		super(pos);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CrGraph getCrGraph() {
		return crGraph;
	}

	public void setCrGraph(CrGraph crGraph) {
		this.crGraph = crGraph;
	}
	
};
