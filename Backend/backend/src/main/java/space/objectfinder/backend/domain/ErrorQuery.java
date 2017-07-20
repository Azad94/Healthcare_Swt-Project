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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ErrorQuery
 *
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-04T11:10:30.256+02:00")

public class ErrorQuery {
	@JsonProperty("message")
	private String message = null;

	@JsonProperty("value")
	private String value = null;

	public ErrorQuery message(final String message) {
		this.message = message;
		return this;
	}

	/**
	 * Get message
	 *
	 * @return message
	 **/

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public ErrorQuery value(final String value) {
		this.value = value;
		return this;
	}

	/**
	 * Enthält bei falscher Query Parameter den Namen der Query
	 *
	 * @return value
	 **/
	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public boolean equals(final java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		final ErrorQuery errorQuery = (ErrorQuery) o;
		return Objects.equals(message, errorQuery.message) && Objects.equals(value, errorQuery.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, value);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("class ErrorQuery {\n");

		sb.append("    message: ").append(toIndentedString(message)).append("\n");
		sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
