package ru.clevertec.ecl.dao.orm;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;

import java.util.List;

@Component
public class CertificateDaoHibernate implements CertificateDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<GiftCertificate> findAll() {

        return sessionFactory.openSession().createQuery("from GiftCertificate g").list();
    }

    @Override
    public GiftCertificate findById(Long id) {
        return (GiftCertificate) sessionFactory.getCurrentSession().getNamedQuery("GiftCertificate.findById")
                .setParameter("id", id).uniqueResult();
    }

    @Override
    public List<GiftCertificate> findAllWithFilter(String query) {
        return sessionFactory.getCurrentSession().createSQLQuery(query).list();
    }

    @Override
    public Long create(GiftCertificate certificate) {

        sessionFactory.getCurrentSession().saveOrUpdate(certificate);
        return certificate.getId();
    }

    @Override
    public Integer update(GiftCertificate certificate) {

        GiftCertificate currentCertificate = findById(certificate.getId());
        updateCertificate(currentCertificate, certificate);
        sessionFactory.getCurrentSession().saveOrUpdate(currentCertificate);
        return 1;
    }

    @Override
    public void delete(Integer certificateId) {

        GiftCertificate certificate = findById(Long.valueOf(certificateId));
        sessionFactory.getCurrentSession().delete(certificate);
    }

    private void updateCertificate(GiftCertificate currentCertificate, GiftCertificate newCertificate) {

        if (newCertificate.getName() != null) {
            currentCertificate.setName(newCertificate.getName());
        }
        if (newCertificate.getDescription() != null) {
            currentCertificate.setDescription(newCertificate.getDescription());
        }
        if (newCertificate.getPrice() != null) {
            currentCertificate.setPrice(newCertificate.getPrice());
        }
        if (newCertificate.getDuration() != null) {
            currentCertificate.setDuration(newCertificate.getDuration());
        }
            currentCertificate.setLastUpdateDate(newCertificate.getLastUpdateDate());
        if (newCertificate.getTags() != null) {
            newCertificate.getTags().stream().forEach(currentCertificate::addTag);
        }
    }
}
