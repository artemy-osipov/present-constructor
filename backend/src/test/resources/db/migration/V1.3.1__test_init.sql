insert into "candy" ("id", "name", "firm", "price", "order") values
('7a8d3659-81e8-49aa-80fb-3121fee7c29c', 'someName1', 'someFirm1', '2.5', '1.1'),
('a764c765-483c-492b-ac63-4f2c4f6d2ff4', 'someName2', 'someFirm2', '2.6', '7.4'),
('b08871d2-cc84-4be0-9671-8c73bf8658ae', 'someName3', 'someFirm3', '13213.11', '3');

insert into "present" ("id", "name", "price", "date") values ('9744b2ea-2328-447c-b437-a4f8b57c9985', 'someName', '12.35', '2017-01-01 12:00:00');

insert into "present_item" ("id", "present", "candy", "count") values
('a58051ef-ea6c-4565-aefd-0e2d260bf95d', '9744b2ea-2328-447c-b437-a4f8b57c9985', 'a764c765-483c-492b-ac63-4f2c4f6d2ff4', '2'),
('f93944ca-ab01-4f68-b635-6e239efdbb4f', '9744b2ea-2328-447c-b437-a4f8b57c9985', 'b08871d2-cc84-4be0-9671-8c73bf8658ae', '6');