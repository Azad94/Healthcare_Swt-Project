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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Sven Marquardt, Chris Deter
 * @since 17.06.2017
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = MaintainanceTask.class, name = "maintainanceTask"),
		@Type(value = CleaningTask.class, name = "cleaningTask"),
		@Type(value = TransportTask.class, name = "transportTask") })
public abstract class Task {
	@Id
	@GeneratedValue
	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("creator")
	@ManyToOne(targetEntity = User.class, optional = false)
	private User creator = null;

	@JsonProperty("editor")
	@ManyToOne(targetEntity = User.class, optional = true)
	private User editor;

	@JsonProperty("description")
	private String description = "Kein Kommentar";

	@JsonProperty("level")
	private Integer level = 1;

	@JsonProperty("creationTime")
	@Column(name = "creation_time", nullable = false)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DatetimeSerialize.class)
	@NotNull
	private LocalDateTime creationTime;

	@JsonProperty("acceptedTime")
	@Column(name = "accepted_time", nullable = true)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DatetimeSerialize.class)
	private LocalDateTime acceptedTime = null;

	@JsonProperty("closedTime")
	@Column(name = "closed_time", nullable = true)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DatetimeSerialize.class)
	private LocalDateTime closedTime = null;

	@JsonProperty("state")
	private Integer state;

	@JsonProperty("beaconObject")
	@ManyToOne(targetEntity = BeaconObject.class, optional = false)
	@NotNull
	private BeaconObject beaconObject;
	@Column(name = "last_edited", nullable = false)
	@JsonIgnore
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DatetimeSerialize.class)
	private LocalDateTime lastEdited = LocalDateTime.now();
	@JsonProperty("role")
	@ManyToOne(targetEntity = Role.class)
	private Role role = null;

	/**
	 * @return the role
	 */
	public Role getRole() {
		return this.role;
	}

	public Task role(final Role role) {
		this.role = role;
		return this;
	}

	public Task name(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(final Role role) {
		this.role = role;
	}

	/**
	 * @return the lastEdited
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public LocalDateTime getLastEdited() {
		return this.lastEdited;
	}

	/**
	 * @param lastEdited the lastEdited to set
	 */
	public void setLastEdited(final LocalDateTime lastEdited) {
		this.lastEdited = lastEdited;
	}

	public Task acceptedTime(final LocalDateTime time) {
		this.acceptedTime = time;
		return this;
	}

	public Task closedTime(final LocalDateTime time) {
		this.acceptedTime = time;
		return this;
	}

	public Task creationTime(final LocalDateTime time) {
		this.creationTime = time;
		return this;
	}

	public void setCreationTime(final LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @param acceptedTime the acceptedTime to set
	 */
	public void setAcceptedTime(final LocalDateTime acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	/**
	 * @param closedTime the closedTime to set
	 */
	public void setClosedTime(final LocalDateTime closedTime) {
		this.closedTime = closedTime;
	}

	public Task beaconObect(final BeaconObject beaconObect) {
		this.beaconObject = beaconObect;
		return this;
	}

	public Task creator(final User creator) {
		this.creator = creator;
		return this;
	}

	public Task discription(final String discription) {
		this.description = discription;
		return this;
	}

	public Task editor(final User editor) {
		this.editor = editor;
		return this;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final Task Task = (Task) o;
		return Objects.equals(this.id, Task.id) && Objects.equals(this.creator, Task.creator)
				&& Objects.equals(this.editor, Task.editor) && Objects.equals(this.description, Task.description)
				&& Objects.equals(this.level, Task.level) && Objects.equals(this.creationTime, Task.creationTime)
				&& Objects.equals(this.acceptedTime, Task.acceptedTime)
				&& Objects.equals(this.closedTime, Task.closedTime) && Objects.equals(this.state, Task.state)
				&& Objects.equals(this.beaconObject, Task.beaconObject);
	}

	/**
	 * Get acceptedTime
	 *
	 * @return acceptedTime
	 **/

	/**
	 * Get beaconObject
	 *
	 * @return beaconObject
	 **/

	public BeaconObject getBeaconObject() {
		return this.beaconObject;
	}

	/**
	 * Get closedTime
	 *
	 * @return closedTime
	 **/

	/**
	 * Get creator
	 *
	 * @return creator
	 **/

	@NotNull
	public User getCreator() {
		return this.creator;
	}

	/**
	 * Get description
	 *
	 * @return description
	 **/

	@NotNull
	public String getDescription() {
		return this.description;
	}

	/**
	 * Get editor
	 *
	 * @return editor
	 **/

	public User getEditor() {
		return this.editor;
	}

	/**
	 * Get id
	 *
	 * @return id
	 **/

	public Long getId() {
		return this.id;
	}

	/**
	 * Get level
	 *
	 * @return level
	 **/

	@NotNull
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * Get state
	 *
	 * @return state
	 **/

	public Integer getState() {
		return this.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.creator, this.editor, this.description, this.level, this.creationTime,
				this.acceptedTime, this.closedTime, this.state, this.beaconObject);
	}

	public Task id(final Long id) {
		this.id = id;
		return this;
	}

	public Task level(final Integer level) {
		this.level = level;
		return this;
	}

	public void setBeaconObject(final BeaconObject beaconObect) {
		this.beaconObject = beaconObect;
	}

	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public LocalDateTime getCreationTime() {
		return this.creationTime;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public LocalDateTime getAcceptedTime() {
		return this.acceptedTime;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public LocalDateTime getClosedTime() {
		return this.closedTime;
	}

	public void setDescription(final String discription) {
		this.description = discription;
	}

	public void setEditor(final User editor) {
		this.editor = editor;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setLevel(final Integer level) {
		this.level = level;
	}

	public void setState(final Integer state) {
		this.state = state;
	}

	public Task state(final Integer state) {
		this.state = state;
		return this;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class Task {\n");

		sb.append("    id: ").append(this.toIndentedString(this.id)).append("\n");
		sb.append("    creator: ").append(this.toIndentedString(this.creator)).append("\n");
		sb.append("    editor: ").append(this.toIndentedString(this.editor)).append("\n");
		sb.append("    description: ").append(this.toIndentedString(this.description)).append("\n");
		sb.append("    level: ").append(this.toIndentedString(this.level)).append("\n");
		sb.append("    creationTime: ").append(this.toIndentedString(this.creationTime)).append("\n");
		sb.append("    acceptedTime: ").append(this.toIndentedString(this.acceptedTime)).append("\n");
		sb.append("    closedTime: ").append(this.toIndentedString(this.closedTime)).append("\n");
		sb.append("    state: ").append(this.toIndentedString(this.state)).append("\n");
		sb.append("    beaconObject: ").append(this.toIndentedString(this.beaconObject)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	public enum State {
		OPEN(1), CLOSED(2), PROCESSING(3);
		private final int value;

		/**
		 *
		 */
		private State(final int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

}
