INSERT INTO PRODUCT (PRODUCT_ID, PRODUCT_NAME) VALUES
    (2, 'Ball'),
    (3, 'Bat'),
    (5, 'Bandaid'),
    (6, 'Cotton Tips'),
    (8, 'Cola'),
    (9, 'Sandwich');

INSERT INTO SUPPLIER (SUPPLIER_ID, SUPPLIER_NAME) VALUES
    (1, 'Supplier_A'),
    (4, 'Supplier_B'),
    (7, 'Supplier_C');

INSERT INTO SUPPLIER_PRODUCTS (SUPPLIER_ID, PRODUCT_ID) VALUES
    (1, 2),
    (1, 3),
    (4, 5),
    (4, 6),
    (7, 8),
    (7, 9);
