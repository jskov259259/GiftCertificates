package ru.clevertec.ecl.repository.orm;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.repository.TagDao;
import ru.clevertec.ecl.model.Tag;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagDaoHibernate implements TagDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<Tag> findAll() {
        return sessionFactory.openSession().createQuery("from Tag t").list();
    }

    @Override
    public Tag findById(Long id) {
        return sessionFactory.openSession().get(Tag.class, id);
    }

    @Override
    public Long create(Tag tag) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(tag);
        session.getTransaction().commit();
        return tag.getId();
    }

    @Override
    public Integer update(Tag tag) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tag currentTag = session.get(Tag.class, tag.getId());

        currentTag.setName(tag.getName());
        session.saveOrUpdate(currentTag);
        session.getTransaction().commit();
        return tag.getId().intValue();
    }

    @Override
    public void delete(Integer tagId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tag tag = session.find(Tag.class, tagId.longValue());
        session.delete(tag);
        session.getTransaction().commit();
    }

    @Override
    public Tag getTagByName(String name) {
        return (Tag) sessionFactory.getCurrentSession().createQuery("from Tag where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }
}
