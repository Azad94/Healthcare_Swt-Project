/**
 *
 */
package space.objectfinder.backend.service;

import org.springframework.data.repository.CrudRepository;

import space.objectfinder.backend.domain.PictureOfObject;

/**
 * @author "Sven Marquardt"
 * @since 30.06.2017
 */
public interface PictureOfObjectRepository extends CrudRepository<PictureOfObject, Long> {

}
