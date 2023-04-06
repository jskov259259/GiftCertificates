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

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CertificateServiceImpl implements CertificateService {

    private static final Integer PAGE_NUMBER_DEFAULT = 1;
    private static final Integer PAGE_SIZE_DEFAULT = 20;

    private final CertificateDao certificateDao;

    @Override
    public List<GiftCertificate> findAll(Map<String, String> filterParams) {
        List<GiftCertificate> certificates;
        Integer pageNumber, pageSize;
        if (filterParams.containsKey("pageNumber") && filterParams.containsKey("pageSize")) {
            pageNumber = Integer.parseInt(filterParams.get("pageNumber"));
            pageSize = Integer.parseInt(filterParams.get("pageSize"));
        } else {
            pageNumber = PAGE_NUMBER_DEFAULT;
            pageSize = PAGE_SIZE_DEFAULT;
        }
        if (isParamsEmptyOrContainsOnlyPageNumbers(filterParams)) {
            certificates = certificateDao.findAll(pageNumber, pageSize);
        } else certificates = findAllWithFilter(filterParams);
        return certificates;
    }

    @Override
    public GiftCertificate findById(Long id) {
        return certificateDao.findById(id);
    }

    @Override
    @Transactional
    public Long create(GiftCertificate certificate) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setCreateDate(currentTime);
        return certificateDao.create(certificate);
    }

    @Override
    @Transactional
    public Integer update(GiftCertificate certificate) {
        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setLastUpdateDate(currentTime);
        return certificateDao.update(certificate);
    }

    @Override
    @Transactional
    public void delete(Integer certificateId) {
        certificateDao.delete(certificateId);
    }

    private boolean isParamsEmptyOrContainsOnlyPageNumbers(Map<String, String> filterParams) {
        if (filterParams.size() == 0) return true;
        else {
            if (filterParams.size() == 2 && filterParams.containsKey("pageNumber")
                    && filterParams.containsKey("pageSize"))
                return true;
            else return false;
        }
    }

    private List<GiftCertificate> findAllWithFilter(Map<String, String> filterParams) {
        String query = createQuery(filterParams);
        return certificateDao.findAllWithFilter(query);
    }

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
