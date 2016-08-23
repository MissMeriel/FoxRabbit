package v1;

/*
 * Myers, OOPDA, Spring 2016
 * Foxes and Rabbits Lab
 * @author Russell Binaco
 * @author Meriel Stein
 * 
 */

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Cerberus extends Animal {

	// Characteristics shared by all three-headed dogs (class variables).

	// The age at which a three-headed dog can start to breed.
	private static final int BREEDING_AGE = 0;
	// The age to which a three-headed dog can live.
	private static final int MAX_AGE = Integer.MAX_VALUE;
	// The likelihood of a three-headed dog breeding.
	private static final double BREEDING_PROBABILITY = 0.0;
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 0;
	// A shared random number generator to control breeding.
	private static final Random rand = Randomizer.getRandom();
	private int stomachCapacity = 32;

	public Cerberus(boolean randomAge, Field field, Location location) {
		super(field, location);
		setAge(0);
		if (randomAge) {
			setAge(rand.nextInt(MAX_AGE));
		}
	} // end constructor

	/**
	 * This is what Cerberus does most of the time: it hunts for food. It cannot
	 * die.
	 * 
	 * @param field
	 *            The field currently occupied.
	 */
	public void act(List<Animal> newCerberus) {
		incrementAge();
		Location[] locations = findFood();
		locations[0] = getField().freeAdjacentLocation(getLocation());
		// }
		setLocation(locations[0]);
	} // end act

	/**
	 * Look for animals adjacent to the current location. Only the first three
	 * live animals are eaten.
	 * 
	 * @return Where food was found, or null if it wasn't.
	 */
	private Location[] findFood() {
		Field field = getField();
		List<Location> adjacent = field.moreAdjacentLocations(getLocation());
		Iterator<Location> it = adjacent.iterator();
		Location[] foodLocations = new Location[stomachCapacity];
		foodLocations[0] = getLocation();
		int animalsEaten = 0;
		while (it.hasNext()) {
			Location where = it.next();
			Object animal = field.getObjectAt(where);
			if (animal instanceof Rabbit) {
				Animal prey = (Animal) animal;
				if (prey.isAlive() && animalsEaten < stomachCapacity) {
					foodLocations[animalsEaten] = where;
					prey.setDead();
					animalsEaten++;

				}
			}
		}
		// Remove the dead animals from the field.
		return foodLocations;
	}

	protected int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}

	@Override
	protected double getBreedingProbability() {
		return BREEDING_PROBABILITY;
	}

	@Override
	protected int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}

	@Override
	protected Random getRand() {
		return rand;
	}

}
