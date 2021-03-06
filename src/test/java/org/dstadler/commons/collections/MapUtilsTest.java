package org.dstadler.commons.collections;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.dstadler.commons.testing.PrivateConstructorCoverage;

/**
 *
 * @author dominik.stadler
 */
public class MapUtilsTest {

	@Test
	public void testSortingByValue() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("One", 1);
		map.put("Two", 2);
		map.put("Three", 3);

		List<Map.Entry<String, Integer>> sorted = MapUtils.sortByValue(map);
		assertEquals("First", "One", sorted.get(0).getKey());
		assertEquals("Second", "Two", sorted.get(1).getKey());
		assertEquals("Third", "Three", sorted.get(2).getKey());
	}

	@Test
	public void testSortingByValueAndKey() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("One", 1);
		map.put("Two", 2);
		map.put("Three", 3);

		List<Map.Entry<String, Integer>> sorted = MapUtils.sortByValueAndKey(map);
		assertEquals("First", "One", sorted.get(0).getKey());
		assertEquals("Second", "Two", sorted.get(1).getKey());
		assertEquals("Third", "Three", sorted.get(2).getKey());

		map.put("Threa", 3);
		map.put("Threb", 3);
		map.put("Threc", 3);
		map.put("Thred", 3);
		map.put("Thref", 3);

		sorted = MapUtils.sortByValueAndKey(map);
		assertEquals("First", "One", sorted.get(0).getKey());
		assertEquals("Second", "Two", sorted.get(1).getKey());
		assertEquals("Third", "Threa", sorted.get(2).getKey());
		assertEquals("Third", "Threb", sorted.get(3).getKey());
		assertEquals("Third", "Threc", sorted.get(4).getKey());
		assertEquals("Third", "Thred", sorted.get(5).getKey());
		assertEquals("Third", "Three", sorted.get(6).getKey());
		assertEquals("Third", "Thref", sorted.get(7).getKey());
	}

	// helper method to get coverage of the unused constructor
	 @Test
	 public void testPrivateConstructor() throws Exception {
	 	PrivateConstructorCoverage.executePrivateConstructor(MapUtils.class);
	 }
}
