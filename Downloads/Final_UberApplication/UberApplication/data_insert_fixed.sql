
INSERT INTO app_user (name, email, password) VALUES
('Aarav Sharma', 'aarav.sharma@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.Mrq4H3dQjLQ5Fj/aB8sGd7GjR4b.FFe'),
('Vivaan Khanna', 'vivaan.khanna@example.com', '$2a$10$bWtUf5xO8vO1WZQYZ5yR4eQhLd9vC5b5j5Xz3aK7nN9rJ1vY6WXzG'),
('Aditya Verma', 'aditya.verma@example.com', '$2a$10$cXp.Z8Tq3YvO1WZQYZ5yR4eQhLd9vC5b5j5Xz3aK7nN9rJ1vY6WXzG'),
('Vihaan Kapoor', 'vihaan.kapoor@example.com', '$2a$10$dYp.A9Uq4ZvP2XRY6zT5ReQiMd9vD5c6k7Yz4bL8mO9nK1wY7XyZ0'),
('Arjun Patel', 'arjun.patel@example.com', '$2a$10$eZq.B0Vr5YwQ3SZ7a1U6SfRjNe9wE6d7k8Zz5cD9nO0mK2x8YzA1'),
('Sai Reddy', 'sai.reddy@example.com', '$2a$10$fAr.C1Ws6XxR4TY8b2V7TgSkOf0xF7e8l9Aa6dE0oP1nL3y9ZbB2'),
('Ananya Nair', 'ananya.nair@example.com', '$2a$10$gBs.D2Xt7YyS5U9c3W8UUhTlPg1yG8f9mBb7eF1pQ2oM4z0AcC3'),
('Ishaan Thakur', 'ishaan.thakur@example.com', '$2a$10$hCt.E3Yu8ZzT6V0d4X9VViUmQh2zH9g0nCc8fG2qR3pN5y1BdD4'),
('Aryan Rao', 'aryan.rao@example.com', '$2a$10$iDu.F4Zv9AaU7W1e5Y0WXjVnRi3AI0h1oDd9gH3rS4qO6z2CeE5'),
('Diya Singh', 'diya.singh@example.com', '$2a$10$jEv.G5Aw0BbV8X2f6Z1XYkWoSj4BJ1i2pEe0hI4sT5rP7z3DfF6');


INSERT INTO user_roles (user_id, roles) VALUES
(1, 'RIDER'),
(2, 'RIDER'),
(2, 'DRIVER'),
(3, 'DRIVER'),
(4, 'DRIVER'),
(5, 'DRIVER'),
(6, 'DRIVER'),
(7, 'DRIVER'),
(8, 'DRIVER'),
(9, 'DRIVER'),
(10, 'DRIVER');


INSERT INTO rider (user_id, rating) VALUES
(1, 4.9);


INSERT INTO driver (user_id, rating, available, current_location) VALUES
(2, 4.7, true, ST_SetSRID(ST_MakePoint(77.1025, 28.7041), 4326),
(3, 4.8, true, ST_SetSRID(ST_MakePoint(77.2167, 28.6667), 4326),
(4, 4.6, true, ST_SetSRID(ST_MakePoint(77.2273, 28.6353), 4326),
(5, 4.9, true, ST_SetSRID(ST_MakePoint(77.2500, 28.5500), 4326),
(6, 4.3, true, ST_SetSRID(ST_MakePoint(77.2000, 28.6200), 4326),
(7, 4.4, true, ST_SetSRID(ST_MakePoint(77.2800, 28.5900), 4326),
(8, 4.5, true, ST_SetSRID(ST_MakePoint(77.2600, 28.6800), 4326),
(9, 4.6, true, ST_SetSRID(ST_MakePoint(77.2200, 28.6400), 4326),
(10, 4.7, true, ST_SetSRID(ST_MakePoint(77.2700, 28.6700), 4326);


INSERT INTO wallet (user_id, balance) VALUES
(1, 1000.00),
(2, 500.00),
(3, 750.00),
(4, 600.00),
(5, 850.00),
(6, 400.00),
(7, 450.00),
(8, 550.00),
(9, 650.00),
(10, 700.00);
