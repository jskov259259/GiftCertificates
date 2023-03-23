package ru.clevertec.ecl.dao;

import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.model.GiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CertificateDaoJdbc implements CertificateDao {

    private String sqlAllCertificates = "SELECT id, name, description, price, duration, create_date, last_update_date " +
            "FROM gift_certificate";
    private String sqlCreateCertificate = "INSERT INTO gift_certificate(name, description, price, duration, create_date)" +
            "VALUES (:name, :description, :price, :duration, :create_date)";
    private String sqlDeleteCertificateById = "DELETE FROM gift_certificate WHERE id=:id";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return namedParameterJdbcTemplate.query(sqlAllCertificates, new GiftCertificateRowMapper());
    }

    @Override
    public List<GiftCertificate> findAllWithFilter(String query) {
        return namedParameterJdbcTemplate.query(query, new GiftCertificateRowMapper());
    }

    @Override
    public Long create(GiftCertificate certificate) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("name", certificate.getName());
        mapParams.put("description", certificate.getDescription());
        mapParams.put("price", certificate.getPrice());
        mapParams.put("duration", certificate.getDuration());
        mapParams.put("create_date", certificate.getCreateDate());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateCertificate, sqlParameterSource, keyHolder, new String[] { "id" });
        return (Long) keyHolder.getKey();
    }

    @Override
    public Integer update(GiftCertificate certificate) {

        StringBuilder sqlUpdateCertificate = new StringBuilder("UPDATE gift_certificate SET");
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("id", certificate.getId());
        if (certificate.getName() != null) {
            sqlUpdateCertificate.append(" name=:name,");
            mapParams.put("name", certificate.getName());
        }
        if (certificate.getDescription() != null) {
            sqlUpdateCertificate.append(" description=:description,");
            mapParams.put("description", certificate.getDescription());
        }
        if (certificate.getPrice() != null) {
            sqlUpdateCertificate.append(" price=:price,");
            mapParams.put("price", certificate.getPrice());
        }
        if (certificate.getDuration() != null) {
            sqlUpdateCertificate.append(" duration=:duration,");
            mapParams.put("duration", certificate.getDuration());
        }
        sqlUpdateCertificate.append(" last_update_date=:last_update_date,");
        mapParams.put("last_update_date", certificate.getLastUpdateDate());
        sqlUpdateCertificate.deleteCharAt(sqlUpdateCertificate.length() - 1);
        sqlUpdateCertificate.append(" WHERE id=:id");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        return namedParameterJdbcTemplate.update(sqlUpdateCertificate.toString(), sqlParameterSource);
    }

    @Override
    public Integer delete(Integer certificateId) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", certificateId);
        return namedParameterJdbcTemplate.update(sqlDeleteCertificateById, sqlParameterSource);
    }

    private class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
        @Override
        public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(resultSet.getLong("id"));
            certificate.setName(resultSet.getString("name"));
            certificate.setDescription(resultSet.getString("description"));
            certificate.setPrice(resultSet.getBigDecimal("price"));
            certificate.setDuration(new PGInterval(resultSet.getString("duration")));
            certificate.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
            if (resultSet.getTimestamp("last_update_date") != null) {
                certificate.setLastUpdateDate(resultSet.getTimestamp("last_update_date").toLocalDateTime());
            }
            return certificate;
        }
    }
}
