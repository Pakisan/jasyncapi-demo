INSERT INTO services (name, address, registered_at, health_check_endpoint, is_enabled) VALUES
    ('orders-service', 'https://orders.api.games.shop', now(), '/health', true),
    ('customers-service', 'https://customers.api.games.shop', now(), '/health', true),
    ('warehouse-service', 'https://warehouse.api.games.shop', now(), '/health', true),
    ('push-service', 'https://push.api.games.shop', now(), '/health', true),
    ('nginx', 'https://games.shop', now(), '/health', true),
    ('redis', 'https://redis.infra.games.shop', now(), '/health', true);