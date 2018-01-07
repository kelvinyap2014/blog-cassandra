package io.ky.cb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.ky.cb.domain.BlogEntry;
import io.ky.cb.repository.BlogEntryRepository;
import io.ky.cb.security.SecurityUtils;
import io.ky.cb.web.rest.errors.BadRequestAlertException;
import io.ky.cb.web.rest.util.HeaderUtil;

/**
 * REST controller for managing BlogEntry.
 */
@RestController
@RequestMapping("/api")
public class BlogEntryResource {

    private final Logger log = LoggerFactory.getLogger(BlogEntryResource.class);

    private static final String ENTITY_NAME = "blogEntry";

    private final BlogEntryRepository blogEntryRepository;

    public BlogEntryResource(final BlogEntryRepository blogEntryRepository) {
        this.blogEntryRepository = blogEntryRepository;
    }

    /**
     * POST  /blog-entries : Create a new blogEntry.
     *
     * @param blogEntry the blogEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blogEntry, or with status 400 (Bad Request) if the blogEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blog-entries")
    @Timed
    public ResponseEntity<BlogEntry> createBlogEntry(@Valid @RequestBody final BlogEntry blogEntry) throws URISyntaxException {
        this.log.debug("REST request to save BlogEntry : {}", blogEntry);
        if (blogEntry.getId() != null) {
            throw new BadRequestAlertException("A new blogEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
		patchBlogEntry(blogEntry);
        final BlogEntry result = this.blogEntryRepository.save(blogEntry);
        return ResponseEntity.created(new URI("/api/blog-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blog-entries : Updates an existing blogEntry.
     *
     * @param blogEntry the blogEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blogEntry,
     * or with status 400 (Bad Request) if the blogEntry is not valid,
     * or with status 500 (Internal Server Error) if the blogEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blog-entries")
    @Timed
    public ResponseEntity<BlogEntry> updateBlogEntry(@Valid @RequestBody final BlogEntry blogEntry) throws URISyntaxException {
        this.log.debug("REST request to update BlogEntry : {}", blogEntry);
        if (blogEntry.getId() == null) {
            return createBlogEntry(blogEntry);
        }
		patchBlogEntry(blogEntry);
        final BlogEntry result = this.blogEntryRepository.save(blogEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blogEntry.getId().toString()))
            .body(result);
    }

	/** Insert values for BlogEntry.blog and BlogEntry.user. */
	private void patchBlogEntry(final BlogEntry blogEntry) {
		final Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
		if (userLogin.isPresent()) {
			blogEntry.setUser(userLogin.get());
			blogEntry.setBlog(userLogin + "'s blog");
		}
	}

    /**
     * GET  /blog-entries : get all the blogEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blogEntries in body
     */
    @GetMapping("/blog-entries")
    @Timed
    public List<BlogEntry> getAllBlogEntries() {
        this.log.debug("REST request to get all BlogEntries");
        return this.blogEntryRepository.findAll();
        }

    /**
     * GET  /blog-entries/:id : get the "id" blogEntry.
     *
     * @param id the id of the blogEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blogEntry, or with status 404 (Not Found)
     */
    @GetMapping("/blog-entries/{id}")
    @Timed
    public ResponseEntity<BlogEntry> getBlogEntry(@PathVariable final String id) {
        this.log.debug("REST request to get BlogEntry : {}", id);
        final BlogEntry blogEntry = this.blogEntryRepository.findOne(UUID.fromString(id));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blogEntry));
    }

    /**
     * DELETE  /blog-entries/:id : delete the "id" blogEntry.
     *
     * @param id the id of the blogEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blog-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlogEntry(@PathVariable final String id) {
        this.log.debug("REST request to delete BlogEntry : {}", id);
        this.blogEntryRepository.delete(UUID.fromString(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
