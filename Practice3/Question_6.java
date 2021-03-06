import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

enum SortOrder {
	ASC, DESC;
}

class AddressBook implements Iterable {
	private NameAddressPair[] addressBook;
	private int counter = 0;

	public AddressBook(int counter) {
		this.counter = counter;
	}

	public boolean create(String firstName, String lastName, String address) {
		for (int i = 0; i < counter; i++) {
			if (addressBook[i].person != null)
				if (addressBook[i].person.firstName.equals(firstName) && addressBook[i].person.lastName.equals(lastName)) {
					return false;
				}
		}
		List<NameAddressPair> list = new ArrayList<>(Arrays.asList(addressBook));
		list.add(new NameAddressPair(new AddressBook.NameAddressPair.Person(firstName, lastName), address));
		addressBook = list.toArray(new NameAddressPair[list.size()]);
		return true;
	}

	public String read(String firstName, String lastName) {
		for (NameAddressPair nameAddressPair : addressBook) {
			if (nameAddressPair.person != null)
				if (nameAddressPair.person.firstName.equals(firstName)
						&& nameAddressPair.person.lastName.equals(lastName))
					return "First name: " + nameAddressPair.person.firstName  + ", Last name: " + nameAddressPair.person.lastName + ", Address: "+nameAddressPair.address;
		}
		return null;
	}

	public boolean update(String firstName, String lastName, String address) {
		for (int i = 0; i < counter; i++) {
			if (addressBook[i].person != null)
				if (addressBook[i].person.firstName.equals(firstName)
						&& addressBook[i].person.lastName.equals(lastName)) {
					addressBook[i].address = address;
					return true;
				}
		}
		return false;
	}

	public boolean delete(String firstName, String lastName) {
		List<NameAddressPair> list = new ArrayList<>(Arrays.asList(addressBook));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null)
				if (list.get(i).person.firstName.equals(firstName)
						&& list.get(i).person.lastName.equals(lastName)) {
					list.remove(i);
					addressBook = list.toArray(new NameAddressPair[list.size()]);
					return true;
				}
		}
		return false;
	}

	public int size() {
		return addressBook.length;
	}

	public void sortedBy(SortOrder order) {
		switch(order) {
		    case ASC: 
		    	Arrays.sort(addressBook);
				break;
			case DESC: 
				Arrays.sort(addressBook, Collections.reverseOrder());
				break;
			default: 
			    break;
		}
	}

	public Iterator iterator() {
		return new AddressBookIterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(addressBook);
		result = prime * result + counter;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressBook other = (AddressBook) obj;
		if (!Arrays.equals(addressBook, other.addressBook))
			return false;
		if (counter != other.counter)
			return false;
		return true;
	}

	// остальное
	private static class NameAddressPair {
		final private Person person;
		private String address;

		private NameAddressPair(Person person, String address) {
			this.person = person;
			this.address = address;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((address == null) ? 0 : address.hashCode());
			result = prime * result + ((person == null) ? 0 : person.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NameAddressPair other = (NameAddressPair) obj;
			if (address == null) {
				if (other.address != null)
					return false;
			} else if (!address.equals(other.address))
				return false;
			if (person == null) {
				if (other.person != null)
					return false;
			} else if (!person.equals(other.person))
				return false;
			return true;
		}

		private static class Person {
			private String firstName;
			private String lastName;

			private Person(String firstName, String lastName) {
				this.firstName = firstName;
				this.lastName = lastName;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
				result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Person other = (Person) obj;
				if (firstName == null) {
					if (other.firstName != null)
						return false;
				} else if (!firstName.equals(other.firstName))
					return false;
				if (lastName == null) {
					if (other.lastName != null)
						return false;
				} else if (!lastName.equals(other.lastName))
					return false;
				return true;
			}
			
		}
	}


	private class AddressBookIterator implements Iterator {
		private int counter = 0;

		public boolean hasNext() {
			return counter < addressBook.length && addressBook[counter] != null;
		}

		public String next() {
			counter++;
			return "First name: " + addressBook[counter].person.firstName  + ", Last name: " + addressBook[counter].person.lastName + ", Address: "+addressBook[counter].address;
		}
	}
}