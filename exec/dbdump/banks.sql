INSERT INTO banks (bank_code, bank_name) VALUES
                                             ('001', '한국은행'),
                                             ('002', '산업은행'),
                                             ('003', '기업은행'),
                                             ('004', '국민은행'),
                                             ('011', '농협은행'),
                                             ('020', '우리은행'),
                                             ('023', 'SC제일은행'),
                                             ('027', '시티은행'),
                                             ('032', '대구은행'),
                                             ('034', '광주은행'),
                                             ('035', '제주은행'),
                                             ('037', '전북은행'),
                                             ('039', '경남은행'),
                                             ('045', '새마을금고'),
                                             ('081', 'KEB하나은행'),
                                             ('088', '신한은행'),
                                             ('090', '카카오뱅크'),
                                             ('999', '싸피은행');


UPDATE banks SET bank_name = '한국은행' WHERE bank_code = '001';
UPDATE banks SET bank_name = '산업은행' WHERE bank_code = '002';
UPDATE banks SET bank_name = '기업은행' WHERE bank_code = '003';
UPDATE banks SET bank_name = '국민은행' WHERE bank_code = '004';
UPDATE banks SET bank_name = '농협은행' WHERE bank_code = '011';
UPDATE banks SET bank_name = '우리은행' WHERE bank_code = '020';
UPDATE banks SET bank_name = 'SC제일은행' WHERE bank_code = '023';
UPDATE banks SET bank_name = '시티은행' WHERE bank_code = '027';
UPDATE banks SET bank_name = '대구은행' WHERE bank_code = '032';
UPDATE banks SET bank_name = '광주은행' WHERE bank_code = '034';
UPDATE banks SET bank_name = '제주은행' WHERE bank_code = '035';
UPDATE banks SET bank_name = '전북은행' WHERE bank_code = '037';
UPDATE banks SET bank_name = '경남은행' WHERE bank_code = '039';
UPDATE banks SET bank_name = '새마을금고' WHERE bank_code = '045';
UPDATE banks SET bank_name = 'KEB하나은행' WHERE bank_code = '081';
UPDATE banks SET bank_name = '신한은행' WHERE bank_code = '088';
UPDATE banks SET bank_name = '카카오뱅크' WHERE bank_code = '090';
UPDATE banks SET bank_name = '싸피은행' WHERE bank_code = '999';
