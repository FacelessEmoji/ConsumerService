package rut.miit.consumerservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.consumerservice.dtos.main.MasterDTO;
import rut.miit.consumerservice.models.entities.Master;
import rut.miit.consumerservice.models.enums.SpecializationType;
import rut.miit.consumerservice.repositories.MasterRepository;
import rut.miit.consumerservice.services.interfaces.MasterService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterServiceImpl implements MasterService<String> {
    private MasterRepository masterRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setMasterRepository(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MasterDTO> getAllMasters() {
        return masterRepository.findAll().stream()
                .map(m -> modelMapper.map(m, MasterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Master getMasterById(String s) {
        return masterRepository.findById(s).orElse(null);
    }

    @Override
    public MasterDTO createMaster(MasterDTO masterDTO) {
        Master master = modelMapper.map(masterDTO, Master.class);
        master = masterRepository.saveAndFlush(master);
        return modelMapper.map(master, MasterDTO.class);
    }

    @Override
    public MasterDTO updateMaster(String s, MasterDTO masterDTO) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setFirstName(masterDTO.getFirstName());
        master.setPhoneNumber(masterDTO.getPhoneNumber());
        master.setSpecialization(masterDTO.getSpecialization());
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }

    @Override
    public void deleteMaster(String s) {
        masterRepository.deleteById(s);
    }

    @Override
    public MasterDTO updateMasterName(String s, String name) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setFirstName(name);
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }

    @Override
    public MasterDTO updateMasterPhone(String s, String phone) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setPhoneNumber(phone);
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }

    @Override
    public MasterDTO updateMasterSpecialization(String s, SpecializationType specialization) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setSpecialization(specialization);
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }
}
