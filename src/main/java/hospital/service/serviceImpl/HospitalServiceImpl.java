package hospital.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hospital.model.Hospital;
import hospital.repository.HospitalRepository;
import hospital.service.HospitalService;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    @Autowired
    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }


    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.getAllHospitals();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        hospitalRepository.saveHospital(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.getHospitalById(id);
    }

    @Override
    public void deleteHospitalById(Long id) {
        hospitalRepository.deleteHospitalById(id);
    }

    @Override
    public void updateHospital(Hospital hospital) {
        Hospital hospital1 = getHospitalById(hospital.getId());
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        hospitalRepository.updateHospital(hospital1);
    }

    @Override
    public List<Hospital> search(String keyWord) {
        if (keyWord != null && !keyWord.trim().isEmpty()) {
            return hospitalRepository.search(keyWord);
        } else {
            return hospitalRepository.getAllHospitals();
        }
    }
}
