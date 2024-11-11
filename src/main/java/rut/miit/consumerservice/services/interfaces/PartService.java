package rut.miit.consumerservice.services.interfaces;

import rut.miit.consumerservice.dtos.main.PartDTO;
import rut.miit.consumerservice.models.entities.Part;

import java.math.BigDecimal;
import java.util.List;

public interface PartService<ID> {
    List<PartDTO> getAllParts();
    Part getPartById(ID id);
    PartDTO createPart(PartDTO partDTO);
    PartDTO updatePart(ID id, PartDTO partDTO);
    void deletePart(ID id);

    PartDTO updatePartName(ID id, String name);
    PartDTO updatePartQuantity(ID id, Integer quantity);
    PartDTO updatePartPrice(ID id, BigDecimal price);

    PartDTO getPartByName(String name);
}

