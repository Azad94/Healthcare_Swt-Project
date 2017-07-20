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
package space.objectfinder.backend.web;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-04T11:10:30.256+02:00")

/**
 * @author Sven Marquardt, Chris Deter, Arne Salveter
 * @since 01.05.2017
 */
public class NotFoundException extends ApiException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final int code;

	public NotFoundException(final int code, final String msg) {
		super(code, msg);
		this.code = code;
	}
}
