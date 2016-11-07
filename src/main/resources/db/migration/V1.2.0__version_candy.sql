USE "presents";

ALTER TABLE "candy" ADD COLUMN "active" BOOLEAN NOT NULL DEFAULT 'true' AFTER "id";
ALTER TABLE "candy" ALTER COLUMN "candy_order" DOUBLE DEFAULT '1.1' NOT NULL;
ALTER TABLE "candy" ALTER COLUMN "candy_order" RENAME TO "order";

CREATE TABLE "candy_history" (
    "id" INT NOT NULL AUTO_INCREMENT,
    "candy" INT NOT NULL,
    "last" BOOLEAN NOT NULL,
    "name" NVARCHAR(50) NOT NULL,
    "firm" NVARCHAR(50) NULL,
    "price" DECIMAL(7, 2) NOT NULL,
    PRIMARY KEY ("id"),
    FOREIGN KEY ("candy") REFERENCES "candy" ("id")
);

INSERT INTO "candy_history" ("candy", "last", "name", "firm", "price")
SELECT "id", 'true', "name", "firm", "price"
FROM "candy";

DROP INDEX "candy_order";
CREATE INDEX "list_candy" ON "candy" ("active", "order");
CREATE INDEX "show_candy_history" ON "candy_history" ("candy", "last");

ALTER TABLE "candy_present" ADD COLUMN "candy_history" INT;
ALTER TABLE "candy_present" ADD CONSTRAINT "candy_history" FOREIGN KEY ("candy_history") REFERENCES "candy_history" ("id");

UPDATE "candy_present" AS "cp"
SET
    "cp"."candy_history" = (SELECT "ch"."id" FROM "candy_history" AS "ch" WHERE "ch"."candy" = "cp"."candy" AND "ch"."last" = 'true');

ALTER TABLE "candy_present" ALTER COLUMN "candy_history" INT NOT NULL;
ALTER TABLE "candy_present" DROP COLUMN "candy";