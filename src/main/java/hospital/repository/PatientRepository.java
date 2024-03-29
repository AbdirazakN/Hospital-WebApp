package hospital.repository;

import hospital.model.Patient;

import java.io.IOException;
import java.util.List;

public interface PatientRepository {
    List<Patient> getAllPatient(Long patientId);

    void savePatient(Patient patient, Long hospitalId);

    Patient getPatientById(Long id);

    void deletePatientById(Long id);

    void updatePatient(Long patientId, Patient patient);

    void assignPatient(Long appointmentId, Long patientId) throws IOException;


}
