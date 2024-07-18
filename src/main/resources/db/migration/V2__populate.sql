INSERT INTO customers (
        first_name,
        last_name,
        address,
        phone,
        email,
        passport
    )
VALUES (
        'Pavel',
        'Khanov',
        'Earth pl.',
        '+7865353443',
        'rt@rt',
        3425332
    );
INSERT INTO accounts (date_open, account_type, customer_id)
VALUES (
        CURRENT_DATE,
        'Fx',
        (
            SELECT id
            from customers
            WHERE passport = 3425332
        )
    );