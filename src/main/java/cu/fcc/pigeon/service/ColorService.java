package cu.fcc.pigeon.service;

import cu.fcc.pigeon.domain.Color;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cu.fcc.pigeon.domain.Color}.
 */
public interface ColorService {
    /**
     * Save a color.
     *
     * @param color the entity to save.
     * @return the persisted entity.
     */
    Color save(Color color);

    /**
     * Updates a color.
     *
     * @param color the entity to update.
     * @return the persisted entity.
     */
    Color update(Color color);

    /**
     * Partially updates a color.
     *
     * @param color the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Color> partialUpdate(Color color);

    /**
     * Get all the colors.
     *
     * @return the list of entities.
     */
    List<Color> findAll();

    /**
     * Get the "id" color.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Color> findOne(Long id);

    /**
     * Delete the "id" color.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
