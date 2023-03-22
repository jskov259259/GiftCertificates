package ru.clevertec.ecl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.model.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TagDaoJdbc implements TagDao {

    private String sqlAllTags = "SELECT id, name FROM tag";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TagDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return namedParameterJdbcTemplate.query(sqlAllTags, new TagRowMapper());
    }

    private class TagRowMapper implements RowMapper<Tag> {
        @Override
        public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setId(resultSet.getInt("id"));
            tag.setName(resultSet.getString("name"));
            return tag;
        }
    }

}
