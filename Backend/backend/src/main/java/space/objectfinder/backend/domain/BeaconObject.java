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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BeaconObject
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Entity
public class BeaconObject {
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("pictureOfObject")
	@OneToOne(targetEntity = PictureOfObject.class)
	private PictureOfObject pictureOfObject;

	@JsonProperty("beacon")
	@OneToOne(targetEntity = Beacon.class)
	private Beacon beacon = null;

	@JsonProperty("location")
	@ManyToOne(targetEntity = Location.class)
	private Location location = null;
	@JsonProperty("beaconObjectType")
	private String beaconObjectType = null;

	@JsonProperty("state")
	private Integer state;

	/**
	 * @return the beaconObjectType
	 */
	public String getBeaconObjectType() {
		return this.beaconObjectType;
	}

	public BeaconObject state(Integer state) {
		this.state = state;
		return this;
	}

	public BeaconObject beaconObjectType(String type) {
		this.beaconObjectType = type;
		return this;
	}

	/**
	 * @param type the beaconObjectType to set
	 */
	public void setBeaconObjectType(final String type) {
		this.beaconObjectType = type;
	}

	/**
	 * @return the status
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 * @param status the status to set
	 */
	public void setState(final Integer status) {
		this.state = status;
	}

	/**
	 * Get id
	 *
	 * @return id
	 **/

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public BeaconObject name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get name
	 *
	 * @return name
	 **/

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public BeaconObject pictureOfObject(final PictureOfObject pictureOfObject) {
		this.pictureOfObject = pictureOfObject;
		return this;
	}

	/**
	 * Get pictureOfObject
	 *
	 * @return pictureOfObject
	 **/

	public PictureOfObject getPictureOfObject() {
		return this.pictureOfObject;
	}

	public void setPictureOfObject(final PictureOfObject pictureOfObject) {
		this.pictureOfObject = pictureOfObject;
	}

	public BeaconObject beacon(final Beacon beacon) {
		this.beacon = beacon;
		return this;
	}

	/**
	 * Get beacon
	 *
	 * @return beacon
	 **/

	public Beacon getBeacon() {
		return this.beacon;
	}

	public void setBeacon(final Beacon beacon) {
		this.beacon = beacon;
	}

	public BeaconObject location(final Location location) {
		this.location = location;
		return this;
	}

	/**
	 * Get location
	 *
	 * @return location
	 **/

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.beacon == null ? 0 : this.beacon.hashCode());
		result = prime * result + (this.beaconObjectType == null ? 0 : this.beaconObjectType.hashCode());
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		result = prime * result + (this.location == null ? 0 : this.location.hashCode());
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());
		result = prime * result + (this.pictureOfObject == null ? 0 : this.pictureOfObject.hashCode());
		result = prime * result + (this.state == null ? 0 : this.state.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BeaconObject)) {
			return false;
		}
		final BeaconObject other = (BeaconObject) obj;
		if (this.beacon == null) {
			if (other.beacon != null) {
				return false;
			}
		} else if (!this.beacon.equals(other.beacon)) {
			return false;
		}
		if (this.beaconObjectType == null) {
			if (other.beaconObjectType != null) {
				return false;
			}
		} else if (!this.beaconObjectType.equals(other.beaconObjectType)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!this.location.equals(other.location)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.pictureOfObject == null) {
			if (other.pictureOfObject != null) {
				return false;
			}
		} else if (!this.pictureOfObject.equals(other.pictureOfObject)) {
			return false;
		}
		if (this.state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!this.state.equals(other.state)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\n");

		sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
		sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
		sb.append("    pictureOfObject: ").append(this.toIndentedString(this.pictureOfObject)).append("\n");
		sb.append("    beacon: ").append(this.toIndentedString(this.beacon)).append("\n");
		sb.append("    location: ").append(this.toIndentedString(this.location)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Diese klasse zu json
	 * 
	 * @param o {@link BeaconObject}
	 * @return String als Json
	 * @author Sven Marquardt
	 * @since 06.07.2017
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
