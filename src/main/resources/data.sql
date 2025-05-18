INSERT INTO country (cr_id, cr_phone_code, cr_name)
VALUES (0, '+91', 'India')
ON CONFLICT (cr_id) DO NOTHING;
