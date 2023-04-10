package ru.clevertec.ecl.repository.orm;


import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dto.SearchCriteria;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.util.CertificateSearchQueryCriteriaConsumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateDaoHibernate implements CertificateDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<GiftCertificate> findAll(List<SearchCriteria> params) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root r = query.from(GiftCertificate.class);

        Predicate predicate = builder.conjunction();

        CertificateSearchQueryCriteriaConsumer searchConsumer =
                new CertificateSearchQueryCriteriaConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<GiftCertificate> result = sessionFactory.openSession().createQuery(query).getResultList();
        return result;
    }

    @Override
    public GiftCertificate findById(Long id) {
        return (GiftCertificate) sessionFactory.openSession().getNamedQuery("GiftCertificate.findById")
                .setParameter("id", id).uniqueResult();
    }

    @Override
    public List<GiftCertificate> findAllWithFilter(String query) {
        return sessionFactory.openSession().createSQLQuery(query).list();
    }

    @Override
    public Long create(GiftCertificate certificate) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(certificate);
        session.getTransaction().commit();
        return certificate.getId();
    }

    @Override
    public Integer update(GiftCertificate certificate) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        GiftCertificate currentCertificate = session.get(GiftCertificate.class, certificate.getId());

        updateCertificate(currentCertificate, certificate);
        session.saveOrUpdate(currentCertificate);
        session.getTransaction().commit();
        return certificate.getId().intValue();
    }

    @Override
    public void delete(Integer certificateId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        GiftCertificate certificate = session.find(GiftCertificate.class, certificateId.longValue());
        session.delete(certificate);
        session.getTransaction().commit();
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
