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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MaintainanceTask
 *
 * @author Sven Marquardt, Chris Deter
 * @since 17.06.2017
 */
@Entity
@JsonIgnoreProperties({ "lastEdited" })
public class MaintainanceTask extends Task {

	@JsonProperty("picture")
	@OneToOne(targetEntity = PictureOfObject.class, optional = true)
	private PictureOfObject picture = null;

	@JsonProperty("repeatTaskInDay")
	private Integer repeatTaskInDay = 0;
	@JsonProperty("subTasks")
	@OneToMany(targetEntity = AbstractSubTask.class)
	private List<AbstractSubTask> subTasks = new ArrayList<>();

	/**
	 * @return the subTasks
	 */
	public List<AbstractSubTask> getSubTasks() {
		return this.subTasks;
	}

	/**
	 * @param subTasks
	 *            the subTasks to set
	 */
	public void setSubTasks(final List<AbstractSubTask> subTasks) {
		this.subTasks = subTasks;
	}

	public MaintainanceTask() {
		super();

	}

	/**
	 * @return the picture
	 */
	public PictureOfObject getPicture() {
		return this.picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(final PictureOfObject picture) {
		this.picture = picture;
	}

	public MaintainanceTask picture(final PictureOfObject picture) {
		this.picture = picture;
		return this;
	}

	public Integer getRepeatTaskInDays() {
		return this.repeatTaskInDay;
	}

	public void setRepeatTaskInDays(final Integer rep) {
		this.repeatTaskInDay = rep;
	}

	public MaintainanceTask repeatTaskInDays(final Integer rep) {
		this.repeatTaskInDay = rep;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.picture == null ? 0 : this.picture.hashCode());
		result = prime * result + (this.repeatTaskInDay == null ? 0 : this.repeatTaskInDay.hashCode());
		result = prime * result + (this.subTasks == null ? 0 : this.subTasks.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof MaintainanceTask)) {
			return false;
		}
		final MaintainanceTask other = (MaintainanceTask) obj;
		if (this.picture == null) {
			if (other.picture != null) {
				return false;
			}
		} else if (!this.picture.equals(other.picture)) {
			return false;
		}
		if (this.repeatTaskInDay == null) {
			if (other.repeatTaskInDay != null) {
				return false;
			}
		} else if (!this.repeatTaskInDay.equals(other.repeatTaskInDay)) {
			return false;
		}
		if (this.subTasks == null) {
			if (other.subTasks != null) {
				return false;
			}
		} else if (!this.subTasks.equals(other.subTasks)) {
			return false;
		}
		return true;
	}

}
