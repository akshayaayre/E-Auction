create table Product(
product_id number primary key,
product_name varchar2(30),
product_short_description varchar2(100),
product_description varchar2(1000),
product_category varchar2(30),
--product_category_id number references Product_Category(product_category_id),
starting_price number,
bid_end_date date,
user_email varchar2(50));

CREATE SEQUENCE seq_product
START WITH 1
INCREMENT BY 1
NOCYCLE ;

