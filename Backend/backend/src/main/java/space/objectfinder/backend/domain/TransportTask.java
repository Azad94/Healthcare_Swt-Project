/*******************************************************************************
 * Copyright 2017  Chris Deter, Arne Salveter, Sven Marquardt
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package space.objectfinder.backend.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sven Marquardt, Chris Deter
 * @since 17.06.2017
 */
@Entity
@JsonIgnoreProperties({ "lastEdited" })
public class TransportTask extends Task {

	@JsonProperty("targetLocation")
	@ManyToOne(targetEntity = Location.class)
	private Location targetLocation = null;

	/**
	 * @return the targetLocation
	 */
	public Location getTargetLocation() {
		return this.targetLocation;
	}

	/**
	 * @param targetLocation
	 *            the targetLocation to set
	 */
	public void setTargetLocation(final Location targetLocation) {
		this.targetLocation = targetLocation;
	}

	public TransportTask targetLocation(final Location location) {
		this.targetLocation = location;
		return this;
	}

	public TransportTask() {
		super();

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((targetLocation == null) ? 0 : targetLocation.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TransportTask))
			return false;
		TransportTask other = (TransportTask) obj;
		if (targetLocation == null) {
			if (other.targetLocation != null)
				return false;
		} else if (!targetLocation.equals(other.targetLocation))
			return false;
		return true;
	}

	

	
}
