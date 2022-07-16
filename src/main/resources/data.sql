INSERT INTO
    application_user (username, password)
VALUES
    ('kowal', '{noop}pass1'),
    ('admin', '{noop}hard'),
    ('asia', '{noop}asia');

INSERT INTO
    user_role (role)
VALUES
    ('ADMIN'),
    ('USER');

INSERT INTO
    user_roles (user_id, role_id)
VALUES
    (1, 2),
    (2, 1),
    (3, 2);