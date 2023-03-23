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
    public List<GiftCertificate> findAll() {

        return certificateDao.findAll();
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
