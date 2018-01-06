
CREATE TABLE IF NOT EXISTS blogEntry (
    id uuid,
    blog text,
    title text,
    user text,
    content text,
    blogDate timestamp,
    PRIMARY KEY(id)
);
