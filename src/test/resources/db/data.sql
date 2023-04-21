INSERT INTO gift_certificate VALUES (1, 'Beauty Saloon', 'Gift certificate to the beauty and health studio', 50, 20, '2023-03-15T08:00:00', '2023-03-16T12:10:15');
INSERT INTO gift_certificate VALUES (2, 'Spa Saloon', 'Gift certificate to the Spa Saloon', 100.25, 15, '2023-03-16 15:20:00', '2023-03-18 13:00:15');
INSERT INTO gift_certificate VALUES (3, 'Hardware store', 'Gift certificate to the Hardware Store', 75, 25, '2023-03-20 16:35:40', '2023-03-20 17:10:20');
INSERT INTO gift_certificate VALUES (4, 'IKEA', 'Gift certificate to the IKEA shop', 350, 30, '2023-03-19 19:00:00', '2023-03-21 08:30:15');
INSERT INTO gift_certificate VALUES (5, 'Plumbing', 'Gift certificate to the plumbing shop', 100.25, 10, '2023-03-21 08:30:00', '2023-03-22 09:30:00');
INSERT INTO gift_certificate VALUES (6, 'Restaurant', 'Gift certificate to the restaurant', 35, 17, '2023-03-10 12:10:35', '2023-03-13 14:00:10');
INSERT INTO gift_certificate VALUES (7, 'Supermarket', 'Gift certificate to the supermarket', 50.75, 35, '2023-03-18 11:05:00', '2023-03-20 19:30:43');
INSERT INTO gift_certificate VALUES (8, 'Sport shop', 'Gift certificate to the sport shop', 20, 10, '2023-03-21 11:50:00', '2023-03-20 19:30:43');

INSERT INTO tag VALUES (1, 'massage');
INSERT INTO tag VALUES (2, 'haircut');
INSERT INTO tag VALUES (3, 'furniture');
INSERT INTO tag VALUES (4, 'building tools');
INSERT INTO tag VALUES (5, 'food');
INSERT INTO tag VALUES (6, 'alcohol');
INSERT INTO tag VALUES (7, 'books');

INSERT INTO certificates_tags VALUES (1, 1);
INSERT INTO certificates_tags VALUES (2, 1);
INSERT INTO certificates_tags VALUES (3, 3);
INSERT INTO certificates_tags VALUES (3, 4);
INSERT INTO certificates_tags VALUES (4, 3);
INSERT INTO certificates_tags VALUES (5, 4);
INSERT INTO certificates_tags VALUES (6, 5);
INSERT INTO certificates_tags VALUES (6, 6);
INSERT INTO certificates_tags VALUES (7, 5);
INSERT INTO certificates_tags VALUES (7, 6);
INSERT INTO certificates_tags VALUES (7, 4);

INSERT INTO users VALUES (1, 'Stan', 25);
INSERT INTO users VALUES (2, 'John', 33);
INSERT INTO users VALUES (3, 'Eric', 15);
INSERT INTO users VALUES (4, 'Bob', 20);
INSERT INTO users VALUES (5, 'Petr', 42);
INSERT INTO users VALUES (6, 'Alexandr', 55);
INSERT INTO users VALUES (7, 'Mike', 41);

INSERT INTO orders(id, purchase_time, price, certificate_id, user_id) VALUES (1, '2023-03-20 16:35:40', 10, 1, 1);
INSERT INTO orders(id, purchase_time, price, certificate_id, user_id) VALUES (2, '2023-03-21 15:30:20', 100.25, 2, 1);
INSERT INTO orders(id, purchase_time, price, certificate_id, user_id) VALUES (3, '2023-03-21 16:50:50', 75, 3, 2);
INSERT INTO orders(id, purchase_time, price, certificate_id, user_id) VALUES (4, '2023-03-22 12:00:00', 350, 4, 3);
INSERT INTO orders(id, purchase_time, price, certificate_id, user_id) VALUES (5, '2023-03-22 16:20:10', 35, 6, 4);