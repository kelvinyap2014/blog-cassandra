package io.ky.cb.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A BlogEntry.
 */
@Table(name = "blogEntry")
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private String blog;

    @NotNull
    private String title;

    @NotNull
    private String user;

    @NotNull
    private String content;

    @NotNull
    private Instant blogDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBlog() {
        return blog;
    }

    public BlogEntry blog(String blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getTitle() {
        return title;
    }

    public BlogEntry title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public BlogEntry user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public BlogEntry content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getBlogDate() {
        return blogDate;
    }

    public BlogEntry blogDate(Instant blogDate) {
        this.blogDate = blogDate;
        return this;
    }

    public void setBlogDate(Instant blogDate) {
        this.blogDate = blogDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BlogEntry blogEntry = (BlogEntry) o;
        if (blogEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blogEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlogEntry{" +
            "id=" + getId() +
            ", blog='" + getBlog() + "'" +
            ", title='" + getTitle() + "'" +
            ", user='" + getUser() + "'" +
            ", content='" + getContent() + "'" +
            ", blogDate='" + getBlogDate() + "'" +
            "}";
    }
}
