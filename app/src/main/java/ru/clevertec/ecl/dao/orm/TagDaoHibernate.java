package ru.clevertec.ecl.dao.orm;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dao.TagDao;
import ru.clevertec.ecl.model.Tag;

import java.util.List;

@Component
public class TagDaoHibernate implements TagDao {

    private SessionFactory sessionFactory;

    @Autowired
    public TagDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tag> findAll() {
        return sessionFactory.openSession().createQuery("from Tag t").list();
    }

    @Override
    public Tag findById(Long id) {
        return sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    @Override
    public Long create(Tag tag) {
        sessionFactory.getCurrentSession().saveOrUpdate(tag);
        return tag.getId();
    }

    @Override
    public Integer update(Tag tag) {
        sessionFactory.getCurrentSession().saveOrUpdate(tag);
        return tag.getId().intValue();
    }

    @Override
    public Integer delete(Integer tagId) {

        Tag tag = findById(Long.valueOf(tagId));
        sessionFactory.getCurrentSession().delete(tag);
        return 1;
    }

    @Override
    public Tag getTagByName(String name) {
        return (Tag) sessionFactory.getCurrentSession().createQuery("from Tag where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

}
