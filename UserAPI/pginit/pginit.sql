-- 1. Crear la base de datos
SELECT 'CREATE DATABASE usersdb' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usersdb')\gexec

-- 2. Conectar a la base de datos
\c usersdb

-- 3. Dar permisos
ALTER USER postgres WITH ENCRYPTED PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE usersdb TO postgres;

-- 4. Crear tabla
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- 5. Insertar datos
INSERT INTO users (id, name, email) VALUES
    (1, 'Fernando', 'fernando@gmail.com'),
    (2, 'Lucia', 'lucia@outlook.com')
ON CONFLICT (id) DO NOTHING;

-- 6. Ajustar secuencia
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));