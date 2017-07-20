/**
 * /*******************************************************************************
 * Copyright 2017 Daniel Dzimitrowicz
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
 ******************************************************************************
 */

using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace HealthcareApp.Model.Serializer
{

    /// <summary>
    /// <see cref="BinarySerializer"/>-Class. Allows for all [Serializable] field's to be serialized and reconstructed
    /// </summary>
    public class BinarySerializer
    {
        private static readonly BinaryFormatter Formatter = new BinaryFormatter();

        /// <summary>
        /// Serializes Object into Byte-Array
        /// </summary>
        /// <param name="toSerialize">Object to serialize</param>
        /// <returns>Byte-Array of serialized object</returns>
        public byte[] Serialize(object toSerialize)
        {
            using (var stream = new MemoryStream())
            {
                Formatter.Serialize(stream, toSerialize);
                return stream.ToArray();
            }
        }

        /// <summary>
        /// Reconstructs a previously serialized object
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="serialized">Byte-Array that represents serialized object</param>
        /// <returns>reconstructed object</returns>
        public T Deserialize<T>(byte[] serialized)
        {
            using (var stream = new MemoryStream(serialized))
            {
                var result = (T)Formatter.Deserialize(stream);
                return result;
            }
        }
    }
}
