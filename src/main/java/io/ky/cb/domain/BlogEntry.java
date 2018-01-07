package io.ky.cb.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * A BlogEntry.
 */
@Table(name = "blogEntry")
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String blog;

    @NotNull
    private String title;

    private String user;

    @NotNull
    private String content;

    @NotNull
    private Instant blogDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return this.id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getBlog() {
        return this.blog;
    }

    public BlogEntry blog(final String blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(final String blog) {
        this.blog = blog;
    }

    public String getTitle() {
        return this.title;
    }

    public BlogEntry title(final String title) {
        this.title = title;
        return this;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getUser() {
        return this.user;
    }

    public BlogEntry user(final String user) {
        this.user = user;
        return this;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public BlogEntry content(final String content) {
        this.content = content;
        return this;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Instant getBlogDate() {
        return this.blogDate;
    }

    public BlogEntry blogDate(final Instant blogDate) {
        this.blogDate = blogDate;
        return this;
    }

    public void setBlogDate(final Instant blogDate) {
        this.blogDate = blogDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BlogEntry blogEntry = (BlogEntry) o;
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
