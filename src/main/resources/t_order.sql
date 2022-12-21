create table t_order (
    id                  int not null auto_increment,
    id_user             int not null,
    trx_date            datetime not null,
    total_price         int not null,
    total_quantity      int not null,
    primary key(id),
    foreign key(id_user) references t_user(id)
);
create table t_order_item (
    id                  int not null auto_increment,
    id_order            int not null,
    id_product          int not null,
    price               int not null,
    quantity            int not null,
    primary key(id),
    foreign key(id_order) references t_order(id),
    foreign key(id_product) references t_product(id)
);