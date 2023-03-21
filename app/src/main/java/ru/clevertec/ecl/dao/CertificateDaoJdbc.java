package ru.clevertec.ecl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.model.Hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CertificateDaoJdbc implements CertificateDao {

    private String getAllHello = "SELECT order_id, hello FROM hello";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CertificateDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Hello> getHello() {
        return namedParameterJdbcTemplate.query(getAllHello, new HelloRowMapper());
    }

    private class HelloRowMapper implements RowMapper<Hello> {
        @Override
        public Hello mapRow(ResultSet resultSet, int i) throws SQLException {
            Hello hello = new Hello();
            hello.setId(resultSet.getInt("order_id"));
            hello.setHello(resultSet.getString("hello"));
            return hello;
        }
    }
}
