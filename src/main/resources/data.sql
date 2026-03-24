INSERT INTO transaction_type (name)
SELECT 'INCOME' WHERE NOT EXISTS (SELECT 1 FROM transaction_type WHERE name = 'INCOME');

INSERT INTO transaction_type (name)
SELECT 'EXPENSE' WHERE NOT EXISTS (SELECT 1 FROM transaction_type WHERE name = 'EXPENSE');