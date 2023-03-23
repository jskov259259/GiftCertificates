package ru.clevertec.ecl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.CertificateDao;
import ru.clevertec.ecl.dao.CertificateTagDao;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;

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

        if (filterParams.size() == 0) {
            return certificateDao.findAll();
        } else return findAllWithFilter(filterParams);
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

    private List<GiftCertificate> findAllWithFilter(Map<String, String> filterParams) {

        if (!isParamsCorrect(filterParams)) {
            throw new RuntimeException("Incorrect query params");
        }
        String query = createQuery(filterParams);
        System.out.println(query);
        return certificateDao.findAllWithFilter(query, filterParams);
    }

    private boolean isParamsCorrect(Map<String, String> filterParams) {

        return filterParams.keySet().stream().allMatch(key -> {
            if (key.equals("tagName") || (key.equals("certificateName")) || (key.equals("description"))
                    || key.contains("order") || key.equals("orderType"))
                return true;
            return false;
        });
//        for (String key : filterParams.keySet()) {
//            if (!(key.equals("tagName") || (key.equals("certificateName")) || (key.equals("description"))
//                    || key.equals("orderBy"))) {
//                return false;
//            }
//        }
//        return true;
    }

    private String createQuery(Map<String, String> filterParams) {

        StringBuilder queryBuilder = new StringBuilder("SELECT g.id, g.name, g.description, g.price, g.duration," +
                " g.create_date, g.last_update_date");
        if (filterParams.containsKey("tagName")) {
            queryBuilder.append(", t.name FROM gift_certificate g " +
                    "INNER JOIN certificates_tags ct On g.id = ct.certificate_id " +
                    "INNER JOIN tag t On ct.tag_id = t.id " +
                    "WHERE t.name = :tagName");
        } else {
            queryBuilder.append(" FROM gift_certificate g");
        }
        if (filterParams.containsKey("tagName") && (filterParams.containsKey("certificateName")
                || filterParams.containsKey("description"))) {
            if (filterParams.containsKey("certificateName")) {
                queryBuilder.append(" AND g.name LIKE '%:certificateName%'");
            }
            if (filterParams.containsKey("description")) {
                queryBuilder.append(" AND g.description LIKE '%:description%'");
            }
        } else {
            if (filterParams.containsKey("certificateName") || filterParams.containsKey("description")) {
                queryBuilder.append(" WHERE true");
                if (filterParams.containsKey("certificateName")) {
                    queryBuilder.append(" AND g.name LIKE '%:certificateName%'");
                }
                if (filterParams.containsKey("description")) {
                    queryBuilder.append(" AND g.description LIKE '%:description%'");
                }
            }
        }
        if (filterParams.containsKey("order1")) {
            queryBuilder.append(" ORDER BY :order1");
            if (filterParams.containsKey("order2")) {
                queryBuilder.append(", :order2");
            }
            if (filterParams.containsKey("orderType")) {
                queryBuilder.append(" :orderType");
            }
        }
        return queryBuilder.toString();
    }

    private void addTagsAndRelations(Long certificateId, List<Tag> tags) {

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
}
