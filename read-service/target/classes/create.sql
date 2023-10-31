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

create table Bid_Details(
bid_details_id number primary key,
product_id number references Product(product_id),
bid_amount number,
user_email varchar2(50));

create table User_Details(
first_name varchar2(30),
last_name varchar2(25),
email varchar2(50)  primary key,
password varchar2(20),
address varchar2(1000),
city varchar2(50),
state varchar2(50),
pin number,
phone number(10),
user_type varchar2(10) -- buyer or seller
);