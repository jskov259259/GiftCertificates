package ru.clevertec.ecl.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.repository.CertificateTagDao;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CertificateTagDaoJdbc implements CertificateTagDao {

    private static final String SQL_CERTIFICATE_TAG_COUNT = "SELECT count(*) FROM certificates_tags " +
            "WHERE certificate_id = :certificateId AND tag_id = :tagId";
    private static final String SQL_CREATE_CERTIFICATE_TAG = "INSERT INTO certificates_tags(" +
            "certificate_id, tag_id) VALUES (:certificateId, :tagId)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean isCertificateTagExists(Long certificateId, Long tagId) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("certificateId", certificateId);
        mapParams.put("tagId", tagId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        Integer count = namedParameterJdbcTemplate.queryForObject(SQL_CERTIFICATE_TAG_COUNT, sqlParameterSource, Integer.class);
        return count > 0;
    }

    @Override
    public void create(Long certificateId, Long tagId) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("certificateId", certificateId);
        mapParams.put("tagId", tagId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        namedParameterJdbcTemplate.update(SQL_CREATE_CERTIFICATE_TAG, sqlParameterSource);
    }
}
