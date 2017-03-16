package datamodel.petri.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import datamodel.Position;
import datamodel.petri.Place;

public class PlaceTest {

	@Test
	public void testPlace() {
		Position pos = new Position(2,3);
		
		Place place = new Place(pos);
		
		assertTrue(place.getPos().x() == 2
				&& place.getPos().y() == 3
				&& place.getCanBeDeleted()
				&& !place.hasToken());
	}

	@Test
	public void testSetToken() {
		Position pos = new Position(2,3);
		
		Place place = new Place(pos);
		
		place.setToken(true);
		
		assertTrue(place.hasToken());
	}

}
