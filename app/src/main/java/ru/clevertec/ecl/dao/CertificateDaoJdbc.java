package ru.clevertec.ecl.dao;

import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.model.GiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Period;
import java.util.List;

@Component
public class CertificateDaoJdbc implements CertificateDao {

    private String sqlAllCertificates = "SELECT id, name, description, price, duration, create_date, last_update_date " +
            "FROM gift_certificate";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return namedParameterJdbcTemplate.query(sqlAllCertificates, new GiftCertificateRowMapper());
    }

    private class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
        @Override
        public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(resultSet.getInt("id"));
            certificate.setName(resultSet.getString("name"));
            certificate.setDescription(resultSet.getString("description"));
            certificate.setPrice(resultSet.getBigDecimal("price"));
            certificate.setDuration(new PGInterval(resultSet.getString("duration")));
            certificate.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
            certificate.setLastUpdateDate(resultSet.getTimestamp("last_update_date").toLocalDateTime());
            return certificate;
        }
    }
}
