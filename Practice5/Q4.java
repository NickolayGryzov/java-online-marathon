import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NameException extends RuntimeException {
	public NameException(String msg) {
		super(msg);
	}

	public NameException() {
	}
}

class CodeException extends RuntimeException {
	public CodeException(String msg) {
		super(msg);
	}

	public CodeException() {
	}
}

class Person {
	private String firstName, lastName, idCode;
	private Pattern patternForName = Pattern.compile("^([A-Z]{1}[a-z]{1,30})$");
	private Pattern patternForCode = Pattern.compile("^[0-9]{10}$");

	public void setFirstName(String firstName) throws NameException {
		Matcher m = patternForName.matcher(firstName);
		if (m.matches()) {
			this.firstName = firstName;
		} else {
			throw new NameException();
		}
	}

	public void setLastName(String lastName) throws NameException {
		Matcher m = patternForName.matcher(firstName);
		if (m.matches()) {
			this.lastName = lastName;
		} else {
			throw new NameException();
		}
	}

	public void setIdCode(String idCode) throws CodeException {
		Matcher m = patternForCode.matcher(firstName);
		if (m.matches()) {
			this.idCode = idCode;
		} else {
			throw new NameException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((idCode == null) ? 0 : idCode.hashCode());
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
		if (idCode == null) {
			if (other.idCode != null)
				return false;
		} else if (!idCode.equals(other.idCode))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return firstName + lastName + ": " + idCode;
	}
	public static Person buildPerson(String firstName, String lastName, String idCode) throws IllegalArgumentException, NameException, CodeException {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setIdCode(idCode);
		return person;
	}
}