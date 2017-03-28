package datamodel.petri.tests.UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datamodel.Position;
import datamodel.petri.Place;

public class PlaceUnitTest {

	private Place place;
	
	private final Position pos = new Position(2,3);
	
	@Before
	public void before(){
		place = new Place(pos);
	}
	
	@Test
	public void testPlace_getCanBeDeleted() {
		assertTrue(place.getCanBeDeleted());
	}
	
	@Test
	public void testPlace_Position() {
		assertTrue(place.getPos() == pos);
	}
	
	@Test
	public void testPlace_hasToken() {
		assertTrue(!place.hasToken());
	}

	@Test
	public void testSetToken() {
		place.setToken(true);
		
		assertTrue(place.hasToken());
	}

}
