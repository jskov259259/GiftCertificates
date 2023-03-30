package ru.clevertec.ecl.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.dao.exceptions.CertificateNameNotUniqueException;
import ru.clevertec.ecl.dao.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.dao.exceptions.TagNameNotUniqueException;
import ru.clevertec.ecl.dao.exceptions.TagNotFoundException;
import ru.clevertec.ecl.model.GiftCertificate;
import ru.clevertec.ecl.model.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class TagDaoJdbc implements TagDao {

    private String sqlAllTags = "SELECT id, name FROM tag";
    private String sqlGetTagById = "SELECT id, name FROM tag WHERE id=:id";
    private String sqlCreateTag = "INSERT INTO tag(name) VALUES (:name)";
    private String sqlUpdateTag = "UPDATE tag SET name=:name WHERE id=:id";
    private String sqlDeleteTagById = "DELETE FROM tag WHERE id=:id";
    private String sqlTagByName = "SELECT id, name FROM tag WHERE name=:name";
    private String sqlTagCountByName = "SELECT count(*) FROM tag " +
            "WHERE name=:name";
    private String sqlAllTagsByCertificateId = "SELECT t.id, t.name FROM tag t " +
            "INNER JOIN certificates_tags ct ON t.id = ct.tag_id " +
            "INNER JOIN gift_certificate g ON ct.certificate_id = g.id " +
            "WHERE g.id = :certificateId";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TagDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return namedParameterJdbcTemplate.query(sqlAllTags, new TagRowMapper());
    }

    @Override
    public Tag findById(Long id) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return namedParameterJdbcTemplate.queryForObject(sqlGetTagById, sqlParameterSource, new TagRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new TagNotFoundException(id);
        }
    }

    @Override
    public Long create(Tag tag) {

        if (isTagExists(tag)) {
            throw new TagNameNotUniqueException(tag.getName());
        }

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", tag.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateTag, sqlParameterSource, keyHolder, new String[] { "id" });
        return (Long) keyHolder.getKey();
    }

    @Override
    public Integer update(Tag tag) {

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("id", tag.getId());
        mapParams.put("name", tag.getName());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        return namedParameterJdbcTemplate.update(sqlUpdateTag, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer tagId) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", tagId);
        return namedParameterJdbcTemplate.update(sqlDeleteTagById, sqlParameterSource);
    }

    @Override
    public Tag getTagByName(String name) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", name);
        return namedParameterJdbcTemplate.queryForObject(sqlTagByName, sqlParameterSource, new TagRowMapper());
    }

    @Override
    public boolean isTagExists(Tag tag) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", tag.getName());
        Integer count = namedParameterJdbcTemplate.queryForObject(sqlTagCountByName, sqlParameterSource, Integer.class);
        return count > 0;
    }

    @Override
    public List<Tag> findAllByCertificateId(Long certificateId) {
        return namedParameterJdbcTemplate.query(sqlAllTagsByCertificateId,
                new MapSqlParameterSource("certificateId", certificateId), new TagRowMapper());
    }

    private class TagRowMapper implements RowMapper<Tag> {
        @Override
        public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setId(resultSet.getLong("id"));
            tag.setName(resultSet.getString("name"));
            return tag;
        }
    }

}
