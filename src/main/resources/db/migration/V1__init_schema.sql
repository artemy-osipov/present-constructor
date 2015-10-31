DROP SCHEMA "presents" IF EXISTS;

CREATE SCHEMA "presents";

USE "presents";

CREATE TABLE "candy" (
    "id"          INT            NOT NULL AUTO_INCREMENT,
    "name"        NVARCHAR(50)   NOT NULL,
    "firm"        NVARCHAR(50)   NULL,
    "candy_order" DECIMAL(7, 1)  DEFAULT '1.1' NOT NULL,
    "price"       DECIMAL(7, 2)  NOT NULL,
    PRIMARY KEY ("id")
);

CREATE INDEX "candy_order" ON "candy" ("candy_order");

CREATE TABLE "present" (
    "id"    INT AUTO_INCREMENT NOT NULL,
    "name"  NVARCHAR (50) NOT NULL,
    "price" DECIMAL(8, 2) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "candy_present" (
    "id"      INT AUTO_INCREMENT NOT NULL,
    "present" INT NOT NULL,
    "candy"   INT NOT NULL,
    "count"   INT NOT NULL,
    PRIMARY KEY ("id"),
    FOREIGN KEY ("present") REFERENCES "present"("id") ON DELETE CASCADE,
    FOREIGN KEY ("candy") REFERENCES "candy"("id")
);