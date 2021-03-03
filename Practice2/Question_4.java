import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee {
	private String name;
	private int experience;
	private BigDecimal basePayment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public BigDecimal getPayment() {
		return basePayment;
	}

	public void setPayment(BigDecimal basePayment) {
		this.basePayment = basePayment;
	}

	public Employee(String name, int experience, BigDecimal basePayment) {
		this.name = name;
		this.experience = experience;
		this.basePayment = basePayment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basePayment == null) ? 0 : basePayment.hashCode());
		result = prime * result + experience;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Employee other = (Employee) obj;
		if (basePayment == null) {
			if (other.basePayment != null)
				return false;
		} else if (!basePayment.equals(other.basePayment))
			return false;
		if (experience != other.experience)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

class Manager extends Employee {
	private double coefficient;

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public BigDecimal getPayment() {
		return BigDecimal.valueOf(super.getPayment().doubleValue() * this.coefficient);
	}

	public Manager(String name, int experience, BigDecimal basePayment, double coefficient) {
		super(name, experience, basePayment);
		this.coefficient = coefficient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(coefficient);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		if (Double.doubleToLongBits(coefficient) != Double.doubleToLongBits(other.coefficient))
			return false;
		return true;
	}
}

public class MyUtils {
	public List<Employee> largestEmployees(List<Employee> workers) {
		List<Employee> res = new ArrayList<>();
		List<Employee> quest = new ArrayList<>();
		for (int i = 0; i < workers.size(); i++) {
			if (workers.get(i) != null) {
				boolean flag = true;
				for (int j = i - 1; j >= 0; j--) {
					if (workers.get(i).equals(workers.get(j)))
						flag = false;
				}
				if (flag)
					quest.add(workers.get(i));
			}
		}
		int experience = 0;
		double payment = 0;
		for (Employee employee : quest) {
			if (employee instanceof Manager) {
				if (employee.getExperience() > experience)
					experience = employee.getExperience();
				if (((Manager) employee).getPayment().doubleValue() > payment)
					payment = employee.getPayment().doubleValue();
			} else {
				if (employee.getExperience() > experience)
					experience = employee.getExperience();
				if (employee.getPayment().doubleValue() > payment)
					payment = employee.getPayment().doubleValue();
			}
		}
		for (Employee employee : quest) {
			if (employee instanceof Manager) {
				if ((employee.getExperience() > experience)
						|| (((Manager) employee).getPayment().doubleValue() == payment))
					res.add(employee);
			} else {
				if ((employee.getExperience() == experience) || (employee.getPayment().doubleValue() == payment))
					res.add(employee);
			}
		}
		return res;
	}
}