# --- !Ups
CREATE TABLE products (
	id   long,
	ean  long,
	name varchar,
	description varchar
);

CREATE TABLE warehouses (
	id	 long,
	name varchar
);

CREATE TABLE stock_items (
	id				long,
	product_id 		long,
	warehouse_id		long,
	quantity			long
);

INSERT INTO PRODUCTS (ID,EAN,NAME,DESCRIPTION) VALUES (1,12345,'A','TEST A');
INSERT INTO PRODUCTS (ID,EAN,NAME,DESCRIPTION) VALUES (2,22345,'B','TEST B');
INSERT INTO PRODUCTS (ID,EAN,NAME,DESCRIPTION) VALUES (3,32345,'C','TEST C');
INSERT INTO PRODUCTS (ID,EAN,NAME,DESCRIPTION) VALUES (4,42345,'D','TEST D');
INSERT INTO PRODUCTS (ID,EAN,NAME,DESCRIPTION) VALUES (5,52345,'E','TEST E');

# --- !Downs
DROP TABLE products;
DROP TABLE warehouses;
DROP TABLE stock_items;