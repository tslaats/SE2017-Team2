package cr;

import datamodel.Position;
import datamodel.cr.CrGraph;
import datamodel.cr.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;


class SemanticsTest {
    /**
     * @return a CR Graph containing no Relations between events, 1 pending event and 1 optional event
     */
    private static CrGraph eventsOnlyGraph() {
        CrGraph graph = new CrGraph("EventsOnlyGraph");

        Event optionalEvent = new Event(new Position(0, 0), "optional");
        Event pendingEvent  = new Event(new Position(1, 0), "pending");
        pendingEvent.setPending(true);

        graph.addEvent(optionalEvent);
        graph.addEvent(pendingEvent);
        return graph;
    }

    /**
     * @return a CR Graph with no pending events
     */
    private static CrGraph noPendingEventsGraph() {
        CrGraph graph = new CrGraph("NoPendingEventsGraph");
        Event optionalEvent = new Event(new Position(0, 0), "A");
        graph.addEvent(optionalEvent);
        return graph;
    }

    /**
     * @return The graph shown in Lecture 3 slides, p. 28
     */
    private static CrGraph optionalEventGraph() {
        CrGraph graph = new CrGraph("SlidesExampleGraph");

        Event pendingEvent = new Event(new Position(2, 0), "I");
        pendingEvent.setPending(true);

        int I = graph.addEvent(pendingEvent);
        int E = graph.addEvent(new Position(0, 0), "E");
        int D = graph.addEvent(new Position(1, 0), "D");
        int C = graph.addEvent(new Position(2, 1), "C");

        try {
            graph.addCondition(E, D);
            graph.addCondition(D, I);
            graph.addCondition(I, C);

            graph.addResponse(C, I);
        }
        catch (Exception e) {
            return graph;
        }

        return graph;
    }

    /**
     * @return
     */
    private static CrGraph petriNetGraph() {
        // TODO
        return null;
    }

    /**
     * @param events
     * @return
     */
    private static List<String> getEventNames(List<Event> events) {
        List<String> e = events.stream().map(Event::getName).collect(Collectors.toList());
        Collections.sort(e);
        return e;
    }

    /**
     * Helper function
     * @param name
     * @param list
     * @return
     */
    private static Event getEventFromEventListByName(String name, List<Event> list) {
        for (Event e : list) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown name");
    }


    @Test
    public void testGetPossibleActionsConditionalCyclicGraph() throws Exception {
        CrGraph graph = new CrGraph("ConditionalCycleGraph");
        int A = graph.addEvent(new Position(0, 0), "A");
        int B = graph.addEvent(new Position(1, 0), "B");
        int C = graph.addEvent(new Position(0, 1), "C");
        int D = graph.addEvent(new Position(1, 1), "D");
        graph.addCondition(A, B);
        graph.addCondition(B, C);
        graph.addCondition(C, D);
        int DA = graph.addCondition(D, A);

        // All event are blocked due to cycle
        Assertions.assertEquals(0, graph.getPossibleActions().size());

        // Remove condition from D to A, unblocking A
        graph.deleteCondition(DA);

        // A is not a possible action
        Assertions.assertEquals(1, graph.getPossibleActions().size());
        Assertions.assertEquals("A", graph.getPossibleActions().get(0).getName());
    }

    @Test
    public void testGetPossibleActionsEmptyGraph() {
        CrGraph graph = new CrGraph("Empty");
        Assertions.assertEquals(0, graph.getPossibleActions().size());
    }

    @Test
    public void testGetPossibleActionsEventsOnlyGraph() {
        CrGraph graph = eventsOnlyGraph();
        Assertions.assertEquals(2, graph.getPossibleActions().size());
    }

    @Test
    public void testGetPossibleActionsPetriNetGraph() {
        // TODO
    }

    @Test()
    public void testExecuteActionBlockedEvent() throws Exception {
        CrGraph graph = new CrGraph("Graph");

        Event executable = new Event(new Position(0, 0), "A");
        Event blocked    = new Event(new Position(1, 0), "B");
        Event notInGraph = new Event(new Position(2, 0), "C");

        int executableId = graph.addEvent(executable);
        int blockedId = graph.addEvent(blocked);

        graph.addCondition(executableId, blockedId);

        Throwable exception = Assertions.expectThrows(IllegalArgumentException.class, () -> {
            graph.executeAction(blocked);
        });
        Assertions.assertEquals("Event not executable", exception.getMessage());
    }

    @Test()
    public void testExecuteActionEventNotInGraph() throws Exception {
        CrGraph graph = new CrGraph("Graph");

        Event executable = new Event(new Position(0, 0), "A");
        Event notInGraph = new Event(new Position(2, 0), "C");

        graph.addEvent(executable);

        Throwable exception = Assertions.expectThrows(IllegalArgumentException.class, () -> {
            graph.executeAction(notInGraph);
        });
        Assertions.assertEquals("Event not executable", exception.getMessage());
    }

    @Test
    public void testExecuteActionPetriNetGraph() {
        // TODO
        CrGraph graph = petriNetGraph();
    }

    @Test
    public void testIsDoneEventsOnlyGraph() {
        CrGraph graph = eventsOnlyGraph();
        List<Event> events = graph.getPossibleActions();

        graph.executeAction(getEventFromEventListByName("optional", events));
        Assertions.assertEquals(false, graph.isDone());

        graph.executeAction(getEventFromEventListByName("pending", events));
        Assertions.assertEquals(true, graph.isDone());
    }

    @Test
    public void testIsDoneNoPendingEventsGraph() {
        CrGraph graph = noPendingEventsGraph();
        Assertions.assertEquals(true, graph.isDone());
    }


    @Test
    public void testIsDonePetriNetGraph() {
        // TODO
    }

    @Test
    public void testIsDoneResponseCyclicGraph() throws Exception {
        CrGraph graph = new CrGraph("ResponseCycleGraph");
        Event eventA = new Event(new Position(0, 0), "A");
        Event eventB = new Event(new Position(1, 0), "B");
        Event eventC = new Event(new Position(0, 1), "C");
        Event eventD = new Event(new Position(1, 1), "D");

        eventA.setPending(true);

        int A = graph.addEvent(eventA);
        int B = graph.addEvent(eventB);
        int C = graph.addEvent(eventC);
        int D = graph.addEvent(eventD);

        graph.addCondition(A, B);
        graph.addCondition(B, C);
        graph.addCondition(C, D);

        graph.addResponse(A, B);
        graph.addResponse(B, C);
        graph.addResponse(C, D);
        int DA = graph.addResponse(D, A);

        graph.executeAction(eventA);
        Assertions.assertEquals(false, graph.isDone());
        graph.executeAction(eventB);
        Assertions.assertEquals(false, graph.isDone());
        graph.executeAction(eventC);
        Assertions.assertEquals(false, graph.isDone());
        graph.executeAction(eventD);
        Assertions.assertEquals(false, graph.isDone());

        // Break cycle
        graph.deleteResponse(DA);

        graph.executeAction(eventA);
        Assertions.assertEquals(false, graph.isDone());
        graph.executeAction(eventB);
        Assertions.assertEquals(false, graph.isDone());
        graph.executeAction(eventC);
        Assertions.assertEquals(false, graph.isDone());
        graph.executeAction(eventD);
        Assertions.assertEquals(true, graph.isDone());
    }

    @Test
    public void testSemanticsOptionalEventGraph() {
        CrGraph graph = optionalEventGraph();
        List<Event> possible;

        possible = graph.getPossibleActions();
        Assertions.assertEquals(getEventNames(possible), Collections.singletonList("E"));

        // Execute E
        graph.executeAction(getEventFromEventListByName("E", possible));
        possible = graph.getPossibleActions();
        Assertions.assertEquals( Arrays.asList("D", "E"), getEventNames(possible));

        // Execute D
        graph.executeAction(getEventFromEventListByName("D", possible));
        possible = graph.getPossibleActions();
        Assertions.assertEquals(Arrays.asList("D", "E", "I"), getEventNames(possible));

        // Execute I
        graph.executeAction(getEventFromEventListByName("I", possible));
        possible = graph.getPossibleActions();
        Assertions.assertEquals(Arrays.asList("C", "D", "E", "I"), getEventNames(possible));
        Assertions.assertEquals(true, graph.isDone());

        // Execute C
        graph.executeAction(getEventFromEventListByName("C", possible));
        possible = graph.getPossibleActions();
        Assertions.assertEquals(Arrays.asList("C", "D", "E", "I"), getEventNames(possible));
        Assertions.assertEquals(false, graph.isDone());

        // Execute I 2nd time
        graph.executeAction(getEventFromEventListByName("I", possible));
        possible = graph.getPossibleActions();
        Assertions.assertEquals(Arrays.asList("C", "D", "E", "I"), getEventNames(possible));
        Assertions.assertEquals(true, graph.isDone());
    }
}