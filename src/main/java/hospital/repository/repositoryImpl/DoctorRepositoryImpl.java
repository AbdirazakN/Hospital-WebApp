package hospital.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import hospital.exception.NotFoundException;
import hospital.model.Appointment;
import hospital.model.Doctor;
import hospital.model.Hospital;
import hospital.repository.DoctorRepository;

import java.io.IOException;
import java.util.List;
@Repository
@Transactional
public class DoctorRepositoryImpl implements DoctorRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public DoctorRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Doctor> getAllDoctors(Long doctorId) {
        return entityManager.createQuery("select d from Doctor d where d.hospital.id=:doctorId",
                Doctor.class).setParameter("doctorId",doctorId).getResultList();
    }

    @Override
    public void saveDoctor(Doctor doctor,Long hospitalId) {
        Doctor doctor1 = new Doctor();
        if (entityManager.find(Hospital.class, hospitalId) == null){
            throw new NotFoundException(String.format("Hospital with id %d not found",hospitalId));
        }
        Hospital hospital = entityManager.find(Hospital.class, hospitalId);
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPosition(doctor.getPosition());
        doctor1.setHospital(hospital);
        hospital.plusDoctor();
        entityManager.persist(doctor1);
    }

    @Override
    public void saveDoctorInfo(Doctor doctor) {
        entityManager.persist(doctor);
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return entityManager.find(Doctor.class,id);
    }

    @Override
    public void deleteDoctorById(Long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        doctor.getHospital().minusDoctor();
        entityManager.remove(doctor);
    }

    @Override
    public void updateDoctor(Long doctorId, Doctor doctor) {
        Doctor doctor1 = entityManager.find(Doctor.class, doctorId);
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPosition(doctor.getPosition());
        entityManager.merge(doctor1);
    }

    @Override
    public void assignDoctor(Long appointmentId, Long doctorId) throws IOException {
        Doctor doctor = entityManager.find(Doctor.class, doctorId);
        Appointment appointment = entityManager.find(Appointment.class, appointmentId);
        if(appointment.getDoctor()!=null){
            for (Doctor d: appointment.getHospital().getDoctors()) {
                if(d.getId()==doctorId){
                    throw new IOException("This is already assigned...");
                }
            }
        }
        doctor.addAppointments(appointment);
        appointment.setDoctor(doctor);
        entityManager.merge(doctor);
        entityManager.merge(appointment);
    }
}
