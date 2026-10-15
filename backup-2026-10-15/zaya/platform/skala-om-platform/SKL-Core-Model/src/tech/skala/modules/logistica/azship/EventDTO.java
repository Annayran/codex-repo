package tech.skala.modules.logistica.azship;

import java.sql.Timestamp;
import java.util.Objects;

import tech.skala.core.commons.dd.DTO;

public class EventDTO implements DTO {
	
	private Timestamp 								date;
	private String  								name;
	private String  								observation;
	private String  								owner;
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, name, observation, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventDTO))
			return false;
		EventDTO other = (EventDTO) obj;
		return Objects.equals(date, other.date) && Objects.equals(name, other.name)
				&& Objects.equals(observation, other.observation) && Objects.equals(owner, other.owner);
	}

	@Override
	public String toString() {
		return String.format("EventDTO [date=%s, name=%s, observation=%s, owner=%s]", date, name, observation, owner);
	}

}
