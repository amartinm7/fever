CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS EVENT;

CREATE TABLE EVENT
(
    ID                   UUID                     NOT NULL,
    PROVIDER_ID          VARCHAR                  NOT NULL,
    PROVIDER_BASE_ID     VARCHAR                  NOT NULL,
    ORGANIZER_COMPANY_ID VARCHAR                  ,
    TITLE                VARCHAR                  NOT NULL,
    START_DATE           TIMESTAMP WITH TIME ZONE NOT NULL,
    END_DATE             TIMESTAMP WITH TIME ZONE NOT NULL,
    SELL_FROM            TIMESTAMP WITH TIME ZONE NOT NULL,
    SELL_TO              TIMESTAMP WITH TIME ZONE NOT NULL,
    SOLD_OUT             BOOLEAN                  NOT NULL,
    ZONES                JSONB                    NOT NULL,
    MIN_PRICE            DOUBLE PRECISION         NOT NULL,
    MAX_PRICE            DOUBLE PRECISION         NOT NULL,
    CREATED_AT           TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (now()):: timestamp(0) without time zone,
    MODIFIED_AT          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (now()):: timestamp(0) without time zone
);

ALTER TABLE EVENT
    ADD CONSTRAINT PK_EVENT UNIQUE (ID);

CREATE INDEX IF NOT EXISTS IDX_EVENT_ID ON EVENT (ID);
CREATE INDEX IF NOT EXISTS IDX_EVENT_PROVIDER_ID ON EVENT (PROVIDER_ID);
CREATE INDEX IF NOT EXISTS IDX_EVENT_PROVIDER_BASE_ID ON EVENT (PROVIDER_BASE_ID);
CREATE INDEX IF NOT EXISTS IDX_EVENT_ORGANIZER_COMPANY_ID ON EVENT (ORGANIZER_COMPANY_ID);
CREATE INDEX IF NOT EXISTS IDX_EVENT_START_DATE ON EVENT (START_DATE);
CREATE INDEX IF NOT EXISTS IDX_EVENT_END_DATE ON EVENT (END_DATE);
CREATE INDEX IF NOT EXISTS IDX_EVENT_END_DATE ON EVENT (END_DATE);

-- TO REVIEW
CREATE INDEX IDX_EVENT_ZONE_ID ON EVENT USING gin ((ZONES->'zone_id'));