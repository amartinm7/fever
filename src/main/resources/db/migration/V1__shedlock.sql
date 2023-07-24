DROP TABLE IF EXISTS SHEDLOCK;

CREATE TABLE SHEDLOCK
(
    NAME       VARCHAR(64),
    LOCK_UNTIL TIMESTAMP(3) NULL,
    LOCKED_AT  TIMESTAMP(3) NULL,
    LOCKED_BY  VARCHAR(255)
);

ALTER TABLE SHEDLOCK
    ADD CONSTRAINT PK_SHEDLOCK UNIQUE (NAME);

CREATE INDEX IF NOT EXISTS IDX_SHEDLOCK_NAME ON SHEDLOCK (NAME);

-- Perfomance queries
-- pg_stat_monitor: https://github.com/percona/pg_stat_monitor
--
-- SELECT datname, usename, state, wait_event_type, wait_event, query
-- FROM pg_stat_activity
-- WHERE state = 'active' AND wait_event IS NOT NULL;
--
-- SELECT relname, seq_scan, seq_tup_read, idx_scan, n_tup_ins, n_tup_upd, n_tup_del, n_tup_hot_upd
-- FROM pg_stat_user_tables
-- WHERE relname = 'zone';
