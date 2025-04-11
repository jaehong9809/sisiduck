INSERT INTO accounts (
    user_id,
    account_no,
    amount,
    status,
    bank_id,
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
    created_at,
    modified_at
) VALUES (
             4, -- user_id
             '0018037473008926', -- account_no
             10000.00, -- amount
             'ACTIVE', -- status
             1, -- bank_code
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
             NOW(), -- created_at
             NOW() -- updated_at
         );