-- auto-generated definition
CREATE TABLE users
(
    user_id      INT AUTO_INCREMENT
        PRIMARY KEY,
    username     VARCHAR(45)                   NOT NULL,
    password     VARCHAR(45)                   NOT NULL,
    profile_name VARCHAR(45)                   NOT NULL,
    image        MEDIUMTEXT                    NOT NULL,
    status       VARCHAR(10) DEFAULT 'offline' NOT NULL
);

-- auto-generated definition
CREATE TABLE messages
(
    message_id   INT AUTO_INCREMENT
        PRIMARY KEY,
    sender_id    INT                          NOT NULL,
    receiver_id  INT                          NOT NULL,
    content      VARCHAR(2000)                NOT NULL,
    timestamp    VARCHAR(45)                  NOT NULL,
    read_receipt VARCHAR(10) DEFAULT 'unread' NOT NULL,
    CONSTRAINT receiver_fk
        FOREIGN KEY (receiver_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT sender_fk
        FOREIGN KEY (sender_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE INDEX receiver_id_idx
    ON messages (receiver_id);

CREATE INDEX sender_id_idx
    ON messages (sender_id);

-- auto-generated definition
CREATE TABLE friends
(
    friend_id INT NOT NULL AUTO_INCREMENT
        PRIMARY KEY,
    x_id      INT NOT NULL,
    y_id      INT NOT NULL,
    last_msg  VARCHAR(255),
    CONSTRAINT friends_x__fk
        FOREIGN KEY (x_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT friends_y__fk
        FOREIGN KEY (y_id) REFERENCES users (user_id) ON DELETE CASCADE
);

########## DML QUERY #############
INSERT INTO users
VALUES (DEFAULT, 'oaish', 'qazi', 'Oaish Qazi', '', DEFAULT),
       (DEFAULT, 'dawg', '123', 'Abdurrahman Qureshi', '', DEFAULT),
       (DEFAULT, 'azlan', 'ahmed', 'Azlan Shaikh', '', DEFAULT),
       (DEFAULT, 'hassan', 'lassan', 'Hassan Shaikh', '', DEFAULT),
       (DEFAULT, 'moin', 'mutthal', 'Mohiuddin Merchant', '', DEFAULT);

INSERT INTO friends
VALUES (DEFAULT, '1', '2', ''),
       (DEFAULT, '2', '1', ''),
       (DEFAULT, '1', '3', ''),
       (DEFAULT, '3', '1', ''),
       (DEFAULT, '1', '4', ''),
       (DEFAULT, '4', '1', ''),
       (DEFAULT, '1', '5', ''),
       (DEFAULT, '5', '1', ''),
       (DEFAULT, '2', '3', ''),
       (DEFAULT, '3', '2', ''),
       (DEFAULT, '2', '4', ''),
       (DEFAULT, '4', '2', ''),
       (DEFAULT, '2', '5', ''),
       (DEFAULT, '5', '2', '');

########## FRIENDS QUERY #########
SELECT user_id, username, password, profile_name, status
FROM friends f
         JOIN users u ON u.user_id = f.y_id
WHERE x_id = '1';

SELECT x_id, u.username AS 'User', y_id, ux.username AS 'Friend'
FROM friends f
         JOIN users u ON u.user_id = f.x_id
         JOIN users ux ON ux.user_id = f.y_id;

########## LAST MSG QUERY ########
SELECT content
FROM messages
WHERE (sender_id = 1 AND receiver_id = 3)
   OR (sender_id = 3 AND receiver_id = 1)
ORDER BY message_id DESC
LIMIT 1;

SELECT user_id, username, password, profile_name, status
from users
WHERE user_id NOT IN (SELECT y_id FROM friends WHERE x_id = 'sender id aayegi yahan')
ORDER BY user_id DESC
LIMIT 7;

SELECT * FROM users;
