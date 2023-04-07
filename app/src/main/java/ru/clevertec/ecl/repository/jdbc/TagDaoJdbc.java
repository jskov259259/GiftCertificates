package ru.clevertec.ecl.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.exceptions.TagNameNotUniqueException;
import ru.clevertec.ecl.exceptions.TagNotFoundException;
import ru.clevertec.ecl.model.Tag;
import ru.clevertec.ecl.repository.TagDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TagDaoJdbc implements TagDao {

    private static final String SQL_ALL_TAGS = "SELECT id, name FROM tag";
    private static final String SQL_GET_TAG_BY_ID = "SELECT id, name FROM tag WHERE id=:id";
    private static final String SQL_CREATE_TAG = "INSERT INTO tag(name) VALUES (:name)";
    private static final String SQL_UPDATE_TAG = "UPDATE tag SET name=:name WHERE id=:id";
    private static final String SQL_DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id=:id";
    private static final String SQL_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name=:name";
    private static final String SQL_TAG_COUNT_BY_NAME = "SELECT count(*) FROM tag " +
            "WHERE name=:name";
    private static final String SQL_ALL_TAGS_BY_CERTIFICATE_ID = "SELECT t.id, t.name FROM tag t " +
            "INNER JOIN certificates_tags ct ON t.id = ct.tag_id " +
            "INNER JOIN gift_certificate g ON ct.certificate_id = g.id " +
            "WHERE g.id = :certificateId";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Tag> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_TAGS, new TagRowMapper());
    }

    @Override
    public Tag findById(Long id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return namedParameterJdbcTemplate.queryForObject(SQL_GET_TAG_BY_ID, sqlParameterSource, new TagRowMapper());
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
        namedParameterJdbcTemplate.update(SQL_CREATE_TAG, sqlParameterSource, keyHolder, new String[] { "id" });
        return (Long) keyHolder.getKey();
    }

    @Override
    public Integer update(Tag tag) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("id", tag.getId());
        mapParams.put("name", tag.getName());
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParams);
        return namedParameterJdbcTemplate.update(SQL_UPDATE_TAG, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer tagId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", tagId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_TAG_BY_ID, sqlParameterSource);
    }

    @Override
    public Tag getTagByName(String name) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", name);
        return namedParameterJdbcTemplate.queryForObject(SQL_TAG_BY_NAME, sqlParameterSource, new TagRowMapper());
    }

    @Override
    public boolean isTagExists(Tag tag) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", tag.getName());
        Integer count = namedParameterJdbcTemplate.queryForObject(SQL_TAG_COUNT_BY_NAME, sqlParameterSource, Integer.class);
        return count > 0;
    }

    @Override
    public List<Tag> findAllByCertificateId(Long certificateId) {
        return namedParameterJdbcTemplate.query(SQL_ALL_TAGS_BY_CERTIFICATE_ID,
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
