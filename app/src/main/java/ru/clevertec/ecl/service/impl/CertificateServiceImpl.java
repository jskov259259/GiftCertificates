package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.CertificateDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.service.CertificateService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDao certificateDao;

    @Override
    public List<GiftCertificate> findAll(Map<String, String> filterParams) {
        List<GiftCertificate> certificates = null;
        if (filterParams.size() == 0) {
            certificates = certificateDao.findAll();
        } else return certificates;
//        else certificates = findAllWithFilter(filterParams);
        return certificates;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return certificateDao.findById(id);
    }

    @Override
    @Transactional
    public Long save(GiftCertificate certificate) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setCreateDate(currentTime);
        return certificateDao.save(certificate).getId();
    }
//
//    @Override
//    @Transactional
//    public Integer update(GiftCertificate certificate) {
//        LocalDateTime currentTime = LocalDateTime.now();
//        certificate.setLastUpdateDate(currentTime);
//        return certificateDao.update(certificate);
//    }

    @Override
    @Transactional
    public void deleteById(Integer certificateId) {
        certificateDao.deleteById(certificateId);
    }

//    private List<GiftCertificate> findAllWithFilter(Map<String, String> filterParams) {
//        String query = createQuery(filterParams);
//        return certificateDao.findAllWithFilter(query);
//    }

    private String createQuery(Map<String, String> filterParams) {
        StringBuilder queryBuilder = new StringBuilder("SELECT g.id, g.name, g.description, g.price, g.duration," +
                " g.create_date, g.last_update_date");
        if (filterParams.containsKey("tagName")) {
            queryBuilder.append(", t.name FROM gift_certificate g " +
                    "INNER JOIN certificates_tags ct On g.id = ct.certificate_id " +
                    "INNER JOIN tag t On ct.tag_id = t.id " +
                    "WHERE t.name = '" + filterParams.get("tagName") + "'");
        } else {
            queryBuilder.append(" FROM gift_certificate g");
        }
        if (filterParams.containsKey("tagName") && (filterParams.containsKey("certificateName")
                || filterParams.containsKey("description"))) {
            if (filterParams.containsKey("certificateName")) {
                queryBuilder.append(" AND g.name LIKE '%" + filterParams.get("certificateName") + "%'");
            }
            if (filterParams.containsKey("description")) {
                queryBuilder.append(" AND g.description LIKE '%" + filterParams.get("description") + "%'");
            }
        } else {
            if (filterParams.containsKey("certificateName") || filterParams.containsKey("description")) {
                queryBuilder.append(" WHERE true");
                if (filterParams.containsKey("certificateName")) {
                    queryBuilder.append(" AND g.name LIKE '%" + filterParams.get("certificateName") + "%'");
                }
                if (filterParams.containsKey("description")) {
                    queryBuilder.append(" AND g.description LIKE '%" + filterParams.get("description") + "%'");
                }
            }
        }
        if (filterParams.containsKey("order1")) {
            queryBuilder.append(" ORDER BY " + filterParams.get("order1"));
            if (filterParams.containsKey("order2")) {
                queryBuilder.append(", " + filterParams.get("order2"));
            }
            if (filterParams.containsKey("orderType")) {
                queryBuilder.append(" " + filterParams.get("orderType"));
            }
        }
        return queryBuilder.toString();
    }
}
