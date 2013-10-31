package org.jbpm.ee.service.remote;

import java.io.Serializable;
import java.util.UUID;

public class RemoteFactHandle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072843786043040826L;

	public RemoteFactHandle() {
		id = UUID.randomUUID();
	}
	
	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RemoteFactHandle other = (RemoteFactHandle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
