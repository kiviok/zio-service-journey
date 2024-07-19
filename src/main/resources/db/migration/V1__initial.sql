CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS customers (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name text NOT NULL,
    last_name text NOT NULL,
    address text NOT NULL,
    phone text NOT NULL,
    email text NOT NULL,
    passport int NOT NULL,
    UNIQUE (passport)
);
CREATE TABLE IF NOT EXISTS accounts (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    date_open date NOT NULL,
    account_type text NOT NULL,
    customer_id uuid NOT NULL REFERENCES customers (id) ON DELETE CASCADE,
    UNIQUE (customer_id, account_type)
);