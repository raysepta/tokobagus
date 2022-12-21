create table t_shopping_cart (
    id              int not null auto_increment,
    id_user         int not null,
    primary key(id),
    foreign key(id_user) references t_user(id)
);

create table t_shopping_cart_item (
    id                  int not null auto_increment,
    id_shopping_cart    int not null,
    id_product          int not null,
    price               int not null,
    quantity            int not null,
    primary key(id),
    foreign key(id_product) references t_product(id),
    foreign key(id_shopping_cart) references t_shopping_cart(id)
);