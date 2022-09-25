CREATE TABLE IF NOT EXISTS "candies" (
    "id" UUID PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "firm" VARCHAR(255) NOT NULL,
    "price" NUMERIC(8, 2) NOT NULL,
    "order" NUMERIC(6, 2) NOT NULL,
    "active" BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS "presents" (
    "id" UUID PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "price" NUMERIC(8, 2) NOT NULL,
    "date" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "present_items" (
    "present_id" UUID NOT NULL REFERENCES presents ("id") ON DELETE CASCADE,
    "candy_id" UUID NOT NULL REFERENCES candies ("id"),
    "count" INTEGER NOT NULL,
    PRIMARY KEY("present_id", "candy_id")
);
