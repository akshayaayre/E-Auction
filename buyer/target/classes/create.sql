create table Bid_Details(
bid_details_id number primary key,
product_id number --references Product(product_id),
bid_amount number,
user_email varchar2(50));

CREATE SEQUENCE seq_bid_details
START WITH 1
INCREMENT BY 1
NOCYCLE ;
