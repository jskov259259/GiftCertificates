package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.dao.CertificateTagDao;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateDao certificateDao;
    private TagDao tagDao;
    private CertificateTagDao certificateTagDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, TagDao tagDao, CertificateTagDao certificateTagDao) {
        this.certificateDao = certificateDao;
        this.tagDao = tagDao;
        this.certificateTagDao = certificateTagDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> findAll(Map<String, String> filterParams) {

        List<GiftCertificate> certificates;
        if (filterParams.size() == 0) {
            certificates = certificateDao.findAll();
        } else certificates = findAllWithFilter(filterParams);
        certificates.stream().forEach(certificate -> {
            certificate.setTags(tagDao.findAllByCertificateId(certificate.getId()));
        });
        return certificates;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(Long id) {

        return certificateDao.findById(id);
    }

    @Override
    @Transactional
    public Long create(GiftCertificate certificate) {

        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setCreateDate(currentTime);
        Long certificateId = certificateDao.create(certificate);
        if (certificate.getTags() != null) {
            addTagsAndRelations(certificateId, certificate.getTags());
        }
        return certificateId;
    }

    @Override
    @Transactional
    public Integer update(GiftCertificate certificate) {

        LocalDateTime currentTime = LocalDateTime.now();
        certificate.setLastUpdateDate(currentTime);
        if (certificate.getTags() != null) {
            addTagsAndRelations(certificate.getId(), certificate.getTags());
        }
        return certificateDao.update(certificate);
    }

    @Override
    @Transactional
    public Integer delete(Integer departmentId) {

        return certificateDao.delete(departmentId);
    }

    void addTagsAndRelations(Long certificateId, List<Tag> tags) {

        tags.stream().forEach(tag -> {

            Long tagId = 0L;
            if (!tagDao.isTagExists(tag)) {
                tagId = tagDao.create(tag);
            } else {
                tagId = tagDao.getTagByName(tag.getName()).getId();
            }
            if (!certificateTagDao.isCertificateTagExists(certificateId, tagId)) {
                certificateTagDao.create(certificateId, tagId);
            }
        });

    }

    List<GiftCertificate> findAllWithFilter(Map<String, String> filterParams) {

        String query = createQuery(filterParams);
        return certificateDao.findAllWithFilter(query);
    }

    String createQuery(Map<String, String> filterParams) {

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
