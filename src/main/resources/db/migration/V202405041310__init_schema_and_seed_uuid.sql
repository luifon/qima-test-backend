CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE category (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    parent_id UUID REFERENCES category(id) ON DELETE SET NULL
);

CREATE TABLE product (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2),
    available BOOLEAN DEFAULT true NOT NULL,
    category_id UUID REFERENCES category(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO category (id, name, parent_id) VALUES
    ('8ac8aa74-9046-4d51-860a-7d8e74204ae0', 'Electronics', NULL),
    ('b6473621-7750-4816-81f1-c4020b4a06eb', 'Computers', '8ac8aa74-9046-4d51-860a-7d8e74204ae0'),
    ('dadd794f-6a13-4512-9cc4-3e766d930605', 'Laptops', 'b6473621-7750-4816-81f1-c4020b4a06eb'),
    ('57dfd834-2994-4323-9b75-df8185aff73b', 'Smartphones', '8ac8aa74-9046-4d51-860a-7d8e74204ae0'),
    ('c5288f5c-8b6e-4bf2-a05f-7244de41cbae', 'Home', NULL),
    ('f1f63ea9-d9f7-478e-b885-a55a4f8429b3', 'Kitchen', 'c5288f5c-8b6e-4bf2-a05f-7244de41cbae');

INSERT INTO product (id, name, description, price, available, category_id) VALUES
    (uuid_generate_v4(), 'MacBook Air', 'Lightweight laptop by Apple', 1299.99, true, 'dadd794f-6a13-4512-9cc4-3e766d930605'),
    (uuid_generate_v4(), 'Dell XPS 13', 'Premium ultrabook by Dell', 999.99, true, 'dadd794f-6a13-4512-9cc4-3e766d930605'),
    (uuid_generate_v4(), 'iPhone 15', 'Latest Apple smartphone', 1099.99, true, '57dfd834-2994-4323-9b75-df8185aff73b'),
    (uuid_generate_v4(), 'Blender', 'High-speed blender for kitchen', 89.99, true, 'f1f63ea9-d9f7-478e-b885-a55a4f8429b3');
