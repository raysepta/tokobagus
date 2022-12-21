CREATE TABLE 't_product' (
    'id' int NOT NULL AUTO_INCREMENT,
    'name' varchar(255) NOT NULL,
    'stock' int DEFAULT NULL,
    'price' int NOT NULL,
    PRIMARY KEY('id'));

alter table t_product add column id_user int;
alter table t_product
    add constraint foreign key(id_user)
    references t_user(id);