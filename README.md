# SE2017-Team2
Repository for the 2017 Software Engineering course at KU - Team 2

Structure of the src folder:

|-- src
    |-- datamodel
    |   |-- Graph.java
    |   |-- Position.java
    |   |-- Semantics.java
    |   |-- SimulationObject.java
    |   |-- Visualization.java
    |   |-- cr
    |   |   |-- Conditional.java
    |   |   |-- CrDrawing.java
    |   |   |-- CrGraph.java
    |   |   |-- CrObject.java
    |   |   |-- Event.java
    |   |   |-- Relation.java
    |   |   |-- Response.java
    |   |   |-- tests
    |   |       |-- IntegrationTests
    |   |       |   |-- ConditionalIntegrationTests.java
    |   |       |   |-- CRIntegrationTests.java
    |   |       |   |-- EventIntegrationTests.java
    |   |       |   |-- ResponseIntegrationTests.java
    |   |       |-- UnitTests
    |   |           |-- ConditionalUnitTests.java
    |   |           |-- CRUnitTests.java
    |   |           |-- EventUnitTests.java
    |   |           |-- ResponseUnitTest.java
    |   |-- petri
    |       |-- Petrinet.java
    |       |-- PetriObject.java
    |       |-- Place.java
    |       |-- Transition.java
    |       |-- tests
    |           |-- IntegrationTests
    |           |   |-- PetriIntegrationTests.java
    |           |   |-- TransitionIntegrationTest.java
    |           |-- UnitTests
    |               |-- PetrinetUnitTests.java
    |               |-- PlaceUnitTest.java
    |               |-- TransitionUnitTest.java
    |-- gui
    |   |-- Action.java
    |   |-- ActionPane.java
    |   |-- CustomOptionPane.java
    |   |-- DeleteSubGraphOptionPane.java
    |   |-- GraphTab.java
    |   |-- GuiController.java
    |   |-- GUIPane.java
    |   |-- HintTextField.java
    |   |-- InitPage.java
    |   |-- Main.java
    |   |-- Menu.java
    |   |-- MessageField.java
    |   |-- images
    |   |   |-- crIcon.gif
    |   |   |-- middle.gif
    |   |   |-- PIcon.gif
    |   |-- tests
    |       |-- IntegrationTests
    |       |   |-- GuiControllerTests.java
    |       |-- unitTests
    |           |-- GuiControllerUnitTests.java
    |-- petriVisualization
        |-- PetriConverter.java
        |-- PetriDrawer.java
        |-- PetriWindow.java
        |-- Graph
        |   |-- Edge.java
        |   |-- Node.java
        |-- Tests
            |-- WhiteBoxTesting.java