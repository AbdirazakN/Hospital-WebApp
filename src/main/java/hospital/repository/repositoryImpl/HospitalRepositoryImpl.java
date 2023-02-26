package hospital.repository.repositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import hospital.model.Hospital;
import hospital.repository.HospitalRepository;

import java.util.List;
@Repository
@Transactional
public class HospitalRepositoryImpl implements HospitalRepository {

    @PersistenceContext
    private  final EntityManager entityManager;

    @Autowired
    public HospitalRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Hospital> getAllHospitals() {
        return entityManager.createQuery("select h from Hospital h",Hospital.class).getResultList();
    }

    @Override
    public void saveHospital(Hospital hospital) {
        entityManager.persist(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return entityManager.find(Hospital.class,id);
    }

    @Override
    public void deleteHospitalById(Long id) {
        entityManager.remove(entityManager.find(Hospital.class,id));
    }

    @Override
    public void updateHospital(Hospital hospital) {
        Hospital hospital1 = getHospitalById(hospital.getId());
        hospital1.setName(hospital.getName());
        hospital1.setAddress(hospital.getAddress());
        entityManager.merge(hospital1);
    }

    @Override
    public List<Hospital> search(String keyWord) {
        return entityManager.createQuery("select h from Hospital h where h.name ilike  (:keyWord) or h.address ilike  (:keyWord)",Hospital.class).setParameter("keyWord","%"+keyWord+"%").getResultList();
    }
}
