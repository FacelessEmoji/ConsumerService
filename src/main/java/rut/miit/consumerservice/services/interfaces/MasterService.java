package rut.miit.consumerservice.services.interfaces;

import rut.miit.consumerservice.dtos.main.MasterDTO;
import rut.miit.consumerservice.models.entities.Master;
import rut.miit.consumerservice.models.enums.SpecializationType;

import java.util.List;

public interface MasterService<ID> {
    List<MasterDTO> getAllMasters();
    Master getMasterById(ID id);
    MasterDTO createMaster(MasterDTO masterDTO);
    MasterDTO updateMaster(ID id, MasterDTO masterDTO);
    void deleteMaster(ID id);

    MasterDTO updateMasterName(ID id, String name);
    MasterDTO updateMasterPhone(ID id, String phone);
    MasterDTO updateMasterSpecialization(ID id, SpecializationType specialization);
}

