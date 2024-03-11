DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS books;

CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL UNIQUE,
                       author_name VARCHAR(255) NOT NULL,
                       genre VARCHAR(255) NOT NULL,
                       price DECIMAL(19, 4) NOT NULL,
                       quantity INT NOT NULL,
                       description TEXT NOT NULL,
                       image_url VARCHAR(255)
);

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone_number VARCHAR(255) NOT NULL UNIQUE,
                       account_balance DECIMAL(10, 2)
);

CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        quantity INT NOT NULL,
                        total_price DECIMAL(10, 2),
                        completed_at DATE,
                        closed TINYINT(1) NOT NULL,
                        CONSTRAINT user_id_foreign_key FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE order_items (
                             order_id INT NOT NULL,
                             book_id INT NOT NULL,
                             quantity INT NOT NULL,
                             total_price DECIMAL(10,2) NOT NULL,
                             CONSTRAINT order_id_foreign_key FOREIGN KEY (order_id) REFERENCES Orders(id),
                             CONSTRAINT book_id_foreign_key FOREIGN KEY (book_id) REFERENCES Books(id)
);

INSERT INTO books (title, author_name, genre, price, quantity, description, image_url)
VALUES
    ('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', 10.99, 100, 'The story primarily concerns the young and mysterious millionaire Jay Gatsby.', 'https://book-store-app-files.s3.eu-central-1.amazonaws.com/book-images/The+Great+Gatsby.jpg'),
    ('To Kill a Mockingbird', 'Harper Lee', 'Fiction', 12.99, 50, 'To Kill a Mockingbird is a novel by Harper Lee published in 1960.', 'https://book-store-app-files.s3.eu-central-1.amazonaws.com/book-images/To+Kill+a+Mockingbird.jpg'),
    ('Go Set a Watchman', 'Harper Lee', 'Fiction', 14.99, 30, 'Go Set a Watchman is a novel by Harper Lee published in 2015.', 'https://book-store-app-files.s3.eu-central-1.amazonaws.com/book-images/Go+Set+a+Watchman.jpg'),
    ('Pride and Prejudice', 'Jane Austen', 'Romance', 8.50, 60, 'Pride and Prejudice is a romantic novel of manners written by Jane Austen in 1813.', 'https://book-store-app-files.s3.eu-central-1.amazonaws.com/book-images/Pride+and+Prejudice.jpg');
INSERT INTO users (username, password, email, phone_number, account_balance)
VALUES
    ('alex_jones', 'alexpass', 'alex.jones@example.com', '+380685214759', 120.00),
    ('another_user', 'anotherpass', 'another.user@example.com', '+380987654321', 140.00);

INSERT INTO orders (user_id, quantity, total_price, completedAt, closed) VALUES
                    (1, 5, 51.96, '2024-03-01', true),
                    (2, 2, 23.49, null, false);


INSERT INTO order_items (order_id, book_id, quantity, total_price) VALUES
                (1, 1, 1, 25.99),
                (1, 2, 2, 25.98),
                (1, 3, 1, 14.99),
                (2, 3, 1, 14.99),
                (2, 4, 1, 8.50);



