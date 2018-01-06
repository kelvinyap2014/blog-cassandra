package io.ky.cb.repository;

import io.ky.cb.domain.BlogEntry;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the BlogEntry entity.
 */
@Repository
public class BlogEntryRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<BlogEntry> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public BlogEntryRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(BlogEntry.class);
        this.findAllStmt = session.prepare("SELECT * FROM blogEntry");
        this.truncateStmt = session.prepare("TRUNCATE blogEntry");
    }

    public List<BlogEntry> findAll() {
        List<BlogEntry> blogEntriesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                BlogEntry blogEntry = new BlogEntry();
                blogEntry.setId(row.getUUID("id"));
                blogEntry.setBlog(row.getString("blog"));
                blogEntry.setTitle(row.getString("title"));
                blogEntry.setUser(row.getString("user"));
                blogEntry.setContent(row.getString("content"));
                blogEntry.setBlogDate(row.get("blogDate", Instant.class));
                return blogEntry;
            }
        ).forEach(blogEntriesList::add);
        return blogEntriesList;
    }

    public BlogEntry findOne(UUID id) {
        return mapper.get(id);
    }

    public BlogEntry save(BlogEntry blogEntry) {
        if (blogEntry.getId() == null) {
            blogEntry.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<BlogEntry>> violations = validator.validate(blogEntry);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(blogEntry);
        return blogEntry;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
