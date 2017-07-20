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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sven Marquardt
 * @since 22.06.2017
 */
public class Service {
	@JsonProperty("callObject")
	private String callObject;
	@JsonProperty("quantity")
	private Integer quantitiy;

	@JsonProperty("targetLocation")
	private Location targetLocation;
	@JsonProperty("creator")
	private User creator;

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(final User creator) {
		this.creator = creator;
	}

	/**
	 * @return the callObject
	 */
	public String getCallObject() {
		return callObject;
	}

	/**
	 * @return the targetLocation
	 */
	public Location getTargetLocation() {
		return targetLocation;
	}

	/**
	 * @param targetLocation
	 *            the targetLocation to set
	 */
	public void setTargetLocation(final Location targetLocation) {
		this.targetLocation = targetLocation;
	}

	/**
	 * @param callObject
	 *            the callObject to set
	 */
	public void setCallObject(final String callObject) {
		this.callObject = callObject;
	}

	/**
	 * @return the quantitiy
	 */
	public Integer getQuantitiy() {
		return quantitiy;
	}

	/**
	 * @param quantitiy
	 *            the quantitiy to set
	 */
	public void setQuantitiy(final Integer quantitiy) {
		this.quantitiy = quantitiy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callObject == null) ? 0 : callObject.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((quantitiy == null) ? 0 : quantitiy.hashCode());
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
		if (obj == null)
			return false;
		if (!(obj instanceof Service))
			return false;
		Service other = (Service) obj;
		if (callObject == null) {
			if (other.callObject != null)
				return false;
		} else if (!callObject.equals(other.callObject))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (quantitiy == null) {
			if (other.quantitiy != null)
				return false;
		} else if (!quantitiy.equals(other.quantitiy))
			return false;
		if (targetLocation == null) {
			if (other.targetLocation != null)
				return false;
		} else if (!targetLocation.equals(other.targetLocation))
			return false;
		return true;
	}

	
}
