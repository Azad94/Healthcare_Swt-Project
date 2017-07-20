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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Sven Marquardt
 * @since 17.06.2017
 */
@Entity
public class Location {

	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;

	@JsonProperty("building")
	private Integer building = null;

	@JsonProperty("floor")
	private Integer floor = null;

	@JsonProperty("room")
	private Integer room = null;

	public Location id(final Long id) {
		this.id = id;
		return this;
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

	public Location building(final Integer building) {
		this.building = building;
		return this;
	}

	/**
	 * Get building
	 *
	 * @return building
	 **/

	@NotNull
	public Integer getBuilding() {
		return this.building;
	}

	public void setBuilding(final Integer building) {
		this.building = building;
	}

	public Location floor(final Integer floor) {
		this.floor = floor;
		return this;
	}

	/**
	 * Get floor
	 *
	 * @return floor
	 **/

	@NotNull
	public Integer getFloor() {
		return this.floor;
	}

	public void setFloor(final Integer floor) {
		this.floor = floor;
	}

	public Location room(final Integer room) {
		this.room = room;
		return this;
	}

	/**
	 * Get room
	 *
	 * @return room
	 **/

	@NotNull
	public Integer getRoom() {
		return this.room;
	}

	public void setRoom(final Integer room) {
		this.room = room;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final Location location = (Location) o;
		return Objects.equals(this.id, location.id) && Objects.equals(this.building, location.building)
				&& Objects.equals(this.floor, location.floor) && Objects.equals(this.room, location.room);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.building, this.floor, this.room);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\n");

		sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
		sb.append("    building: ").append(this.toIndentedString(this.building)).append("\n");
		sb.append("    floor: ").append(this.toIndentedString(this.floor)).append("\n");
		sb.append("    room: ").append(this.toIndentedString(this.room)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(final java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
