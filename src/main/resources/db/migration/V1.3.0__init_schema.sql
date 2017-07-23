DROP SCHEMA "presents" IF EXISTS;

CREATE SCHEMA "presents";

USE "presents";

CREATE TABLE "candy" (
    "id" INT NOT NULL AUTO_INCREMENT,
    "active" BOOLEAN NOT NULL DEFAULT 'true',
    "name" NVARCHAR(50) NOT NULL,
    "firm" NVARCHAR(50) NULL,
    "order" DOUBLE NOT NULL DEFAULT '1.1',
    "price" DECIMAL(7, 2) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE INDEX "list_candy" ON "candy" ("active", "order");

CREATE TABLE "present" (
    "id" INT AUTO_INCREMENT NOT NULL,
    "name" NVARCHAR (50) NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "present_item" (
    "id" INT AUTO_INCREMENT NOT NULL,
    "present" INT NOT NULL,
    "candy" INT NOT NULL,
    "count" INT NOT NULL,
    PRIMARY KEY ("id"),
    FOREIGN KEY ("present") REFERENCES "present" ("id") ON DELETE CASCADE,
    FOREIGN KEY ("candy") REFERENCES "candy" ("id")
);