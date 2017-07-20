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

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * User
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-14T21:04:38.330+02:00")
@Entity
public class User {
	@JsonProperty("id")
	@Id
	@GeneratedValue
	private Long id = null;

	@JsonProperty("name")
	private String name = null;

	@JsonProperty("password")
	private String password = null;

	@JsonProperty("email")
	private String email = null;

	@JsonProperty("lastUpdate")
	@Column(name = "last_update", nullable = false)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DatetimeSerialize.class)
	private LocalDateTime lastUpdate = null;

	@JsonProperty("role")
	@ManyToOne(targetEntity = Role.class)
	private Role role = null;

	public User id(final Long id) {
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

	public User name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get name
	 *
	 * @return name
	 **/

	@NotNull
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public User password(final String password) {
		this.password = password;
		return this;
	}

	/**
	 * Get password
	 *
	 * @return password
	 **/

	@NotNull
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public User email(final String email) {
		this.email = email;
		return this;
	}

	/**
	 * Get email
	 *
	 * @return email
	 **/

	@NotNull
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public User lastUpdate(final LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	/**
	 * Get lastUpdate
	 *
	 * @return lastUpdate
	 **/

	@NotNull
	public LocalDateTime getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public User role(final Role role) {
		this.role = role;
		return this;
	}

	/**
	 * Get role
	 *
	 * @return role
	 **/

	@NotNull
	public Role getRole() {
		return this.role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final User user = (User) o;
		return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name)
				&& Objects.equals(this.password, user.password) && Objects.equals(this.email, user.email)
				&& Objects.equals(this.lastUpdate, user.lastUpdate) && Objects.equals(this.role, user.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.password, this.email, this.lastUpdate, this.role);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class User {\n");

		sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
		sb.append("    name: ").append(this.toIndentedString(this.name)).append("\n");
		sb.append("    password: ").append(this.toIndentedString(this.password)).append("\n");
		sb.append("    email: ").append(this.toIndentedString(this.email)).append("\n");
		sb.append("    lastUpdate: ").append(this.toIndentedString(this.lastUpdate)).append("\n");
		sb.append("    role: ").append(this.toIndentedString(this.role)).append("\n");
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
