package cr;

import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.cr.Event;
import datamodel.petri.Petrinet;
import datamodel.petri.Transition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SemanticsIntegrationTest {
    private static CrGraph graph;
    private static Integer petriNetEventID;
    private static Integer pendingEventID;

    @BeforeEach
    public void setUp() throws Exception {
        graph = new CrGraph("CR Graph with nested Petri Net");

        // Construct petri net with 1 transition, non finalized stated
        Petrinet petrinet = new Petrinet("Nested Petri Net");
        int transitionID = petrinet.addTransition(new Position(0, 1), "Some Transition");
        petrinet.addArc(petrinet.getStart().getID(), transitionID);
        petrinet.addArc(transitionID, petrinet.getEnd().getID());

        Event petriNetEvent = new Event(new Position(0, 0), "Petri Net Event");
        petriNetEvent.setPetrinet(petrinet);

        Event pendingEvent = new Event(new Position(1, 1), "Pending Event");
        pendingEvent.setPending(true);

        petriNetEventID = graph.addEvent(petriNetEvent);
        pendingEventID  = graph.addEvent(pendingEvent);

        graph.addCondition(petriNetEventID, pendingEventID);
    }


    @Test
    public void testGetPossibleActionsPetriNetGraph() {
        List<Event> possibleActions = graph.getPossibleActions();

        // Assert that the non-finished petri net event is presented as a possible action
        Assertions.assertEquals(1, possibleActions.size());
        Assertions.assertEquals((int) petriNetEventID, possibleActions.get(0).getID());
    }

    @Test
    public void testExecuteActionPetriNetGraph() {
        List<Event> possibleActions = graph.getPossibleActions();

        // Assert exception is thrown when executing a non-finalized petri net
        Throwable exception = Assertions.expectThrows(IllegalArgumentException.class, () -> {
            graph.executeAction(possibleActions.get(0));
        });
        Assertions.assertEquals("Event's Petri net has not reached end place yet", exception.getMessage());
    }

    @Test
    public void testIsDonePetriNetGraph() {
        // Set petri net to a finalized state
        List<Event> events = graph.getPossibleActions();
        Petrinet petrinet = events.get(0).getPetrinet();
        List<Transition> transitions = petrinet.getPossibleActions();
        transitions.forEach(petrinet::executeAction);
        Assertions.assertEquals(true, petrinet.isDone());

        // Update Surrounding event
        Event petriNetEvent = events.get(0);
        petriNetEvent.setPetrinet(petrinet);
        graph.addEvent(petriNetEvent);

        // Assert petri net event still a possible action
        events = graph.getPossibleActions();
        Assertions.assertEquals(1, events.size());
        graph.executeAction(events.get(0));

        // Execute the finalized petri net surround event without exception being thrown
        events = graph.getPossibleActions();
        Assertions.assertEquals(2, events.size());
        Assertions.assertEquals((int) pendingEventID, events.get(1).getID());
        graph.executeAction(events.get(1));

        // Assert graph is now done
        Assertions.assertEquals(true, graph.isDone());
    }
}
