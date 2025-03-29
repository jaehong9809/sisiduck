INSERT INTO accounts (
    user_id,
    account_no,
    balance,
    interest_rate,
    status,
    bank_code,
    account_pw,
    account_type_unique_no,
    account_type_code,
    account_name,
    account_description,
    currency,
    daily_transfer_limit,
    one_time_transfer_limit,
    last_transaction_date,
    account_expiry_date,
    min_subscription_balance,
    max_subscription_balance,
    created_at,
    modified_at
) VALUES (
             1, -- user_id
             '0013509698246275', -- account_no
             10000.00, -- balance
             0.015, -- interest_rate (1.5%)
             'ACTIVE', -- status
             '001', -- bank_code
             1234, -- account_pw
             'SAVING001', -- account_type_unique_no
             '1', -- account_type_code
             '한국은행 수시입출금 상품명', -- account_name
             '원하는 만큼 저축하는 자유로운 적금', -- account_description
             'KRW', -- currency
             5000000.00, -- daily_transfer_limit
             1000000.00, -- one_time_transfer_limit
             NOW(), -- last_transaction_date
             DATE_ADD(NOW(), INTERVAL 2 YEAR), -- account_expiry_date (2년 후)
             1000.00, -- min_subscription_balance
             10000000.00, -- max_subscription_balance
             NOW(), -- created_at
             NOW() -- updated_at
         );