package ru.clevertec.ecl.dao.orm;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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

    @Override
    public List<GiftCertificate> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from GiftCertificate g").list();
    }

    @Override
    public GiftCertificate findById(Long id) {
        return null;
    }

    @Override
    public List<GiftCertificate> findAllWithFilter(String query) {
        return null;
    }

    @Override
    public Long create(GiftCertificate certificate) {
        return null;
    }

    @Override
    public Integer update(GiftCertificate certificate) {
        return null;
    }

    @Override
    public Integer delete(Integer certificateId) {
        return null;
    }
}
