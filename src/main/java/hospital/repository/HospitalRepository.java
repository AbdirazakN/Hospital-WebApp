package hospital.repository;

import hospital.model.Hospital;

import java.util.List;

public interface HospitalRepository {

    List<Hospital> getAllHospitals();

    void saveHospital(Hospital hospital);

    Hospital getHospitalById(Long id);

    void deleteHospitalById(Long id);

    void updateHospital(Hospital hospital);
    List<Hospital> search(String keyWord);

}
