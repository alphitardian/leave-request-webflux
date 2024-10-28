CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO role(id,  name) values(1, 'manager');
INSERT INTO role(id,  name) values(2, 'staff');

CREATE TABLE employee (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role_id BIGSERIAL NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE leave (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE request (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGSERIAL NOT NULL,
    leave_id BIGSERIAL NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    FOREIGN KEY (leave_id) REFERENCES leave(id)
);