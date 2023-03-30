package ru.clevertec.ecl.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dao.CertificateTagDao;

import java.util.HashMap;
import java.util.Map;

@Component
public class CertificateTagDaoJdbc implements CertificateTagDao {

    private String sqlCertificateTagCount = "SELECT count(*) FROM certificates_tags " +
            "WHERE certificate_id = :certificateId AND tag_id = :tagId";
    private String sqlCreateCertificateTag = "INSERT INTO certificates_tags(" +
            "certificate_id, tag_id) VALUES (:certificateId, :tagId)";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateTagDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean isCertificateTagExists(Long certificateId, Long tagId) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("certificateId", certificateId);
        mapParams.put("tagId", tagId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCertificateTagCount, sqlParameterSource, Integer.class);
        return count > 0;
    }

    @Override
    public void create(Long certificateId, Long tagId) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("certificateId", certificateId);
        mapParams.put("tagId", tagId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        namedParameterJdbcTemplate.update(sqlCreateCertificateTag, sqlParameterSource);
    }
}
