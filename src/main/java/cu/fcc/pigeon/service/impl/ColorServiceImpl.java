package cu.fcc.pigeon.service.impl;

import cu.fcc.pigeon.domain.Color;
import cu.fcc.pigeon.repository.ColorRepository;
import cu.fcc.pigeon.service.ColorService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cu.fcc.pigeon.domain.Color}.
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorService {

    private final Logger log = LoggerFactory.getLogger(ColorServiceImpl.class);

    private final ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public Color save(Color color) {
        log.debug("Request to save Color : {}", color);
        return colorRepository.save(color);
    }

    @Override
    public Color update(Color color) {
        log.debug("Request to update Color : {}", color);
        return colorRepository.save(color);
    }

    @Override
    public Optional<Color> partialUpdate(Color color) {
        log.debug("Request to partially update Color : {}", color);

        return colorRepository
            .findById(color.getId())
            .map(existingColor -> {
                if (color.getNombreColor() != null) {
                    existingColor.setNombreColor(color.getNombreColor());
                }
                if (color.getCodigo() != null) {
                    existingColor.setCodigo(color.getCodigo());
                }

                return existingColor;
            })
            .map(colorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Color> findAll() {
        log.debug("Request to get all Colors");
        return colorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Color> findOne(Long id) {
        log.debug("Request to get Color : {}", id);
        return colorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Color : {}", id);
        colorRepository.deleteById(id);
    }
}
