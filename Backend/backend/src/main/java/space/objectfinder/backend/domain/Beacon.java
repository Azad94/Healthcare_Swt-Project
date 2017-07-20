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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Beacon
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@Entity
public class Beacon {

	@Id
	@JsonProperty("uuid")
	@Column(length = 33)
	private String uuid;

	@JsonProperty("major")
	private Integer major = null;

	@JsonProperty("minor")
	private Integer minor = null;

	public Beacon uuid(final String uuid) {
		this.uuid = uuid;
		return this;
	}

	/**
	 * Get uuid
	 *
	 * @return uuid
	 **/

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public Beacon major(final Integer major) {
		this.major = major;
		return this;
	}

	/**
	 * Get major
	 *
	 * @return major
	 **/

	public Integer getMajor() {
		return this.major;
	}

	public void setMajor(final Integer major) {
		this.major = major;
	}

	public Beacon minor(final Integer minor) {
		this.minor = minor;
		return this;
	}

	/**
	 * Get minor
	 *
	 * @return minor
	 **/

	public Integer getMinor() {
		return this.minor;
	}

	public void setMinor(final Integer minor) {
		this.minor = minor;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final Beacon beacon = (Beacon) o;
		return Objects.equals(this.uuid, beacon.uuid) && Objects.equals(this.major, beacon.major)
				&& Objects.equals(this.minor, beacon.minor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uuid, this.major, this.minor);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\n");

		sb.append("    uuid: ").append(this.toIndentedString(this.uuid)).append("\n");
		sb.append("    major: ").append(this.toIndentedString(this.major)).append("\n");
		sb.append("    minor: ").append(this.toIndentedString(this.minor)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 *
	 * @param o {@link Beacon}
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
