create table ebayclone.product_seq
(
    next_val bigint null
);

insert into ebayclone.product_seq value (1);

create table ebayclone.user
(
    id       int          not null
        primary key,
    balance  int          null,
    password varchar(255) null,
    username varchar(255) null
);

create table ebayclone.admin
(
    id int not null
        primary key,
    constraint FK1ja8rua032fgnk9jmq7du3b3a
        foreign key (id) references ebayclone.user (id)
);

create table ebayclone.buyer
(
    id int not null
        primary key,
    constraint FK516w53ywd7f16mktqnoo0qlyu
        foreign key (id) references ebayclone.user (id)
);

create table ebayclone.seller
(
    id int not null
        primary key,
    constraint FKaqjn1ppqads72a0js4g7044eq
        foreign key (id) references ebayclone.user (id)
);

create table ebayclone.product
(
    id             int          not null
        primary key,
    currency       varchar(255) null,
    current_bid    int          null,
    description    varchar(255) null,
    is_sold        bit          null,
    name           varchar(255) null,
    picture_link   varchar(255) null,
    purchase_price int          null,
    type           varchar(255) null,
    buyer_id       int          null,
    seller_id      int          null,
    constraint FKesd6fy52tk7esoo2gcls4lfe3
        foreign key (seller_id) references ebayclone.seller (id),
    constraint FKoxedx2y56mworpfxljluo6yts
        foreign key (buyer_id) references ebayclone.buyer (id)
);

create table ebayclone.buyer_order_history
(
    buyer_id         int not null,
    order_history_id int not null,
    constraint UK_gvnsa2d8ai6g6n18xmn5wawk0
        unique (order_history_id),
    constraint FK2ptidvsf1rpgntokj5og1xh46
        foreign key (buyer_id) references ebayclone.buyer (id),
    constraint FKeucb530aiclrelgp3jn6xokjq
        foreign key (order_history_id) references ebayclone.product (id)
);

create table ebayclone.seller_product_list
(
    seller_id       int not null,
    product_list_id int not null,
    constraint UK_pg95txjhmll7pu20567qt0dox
        unique (product_list_id),
    constraint FKhi315810et4shx7ed05u77yo
        foreign key (product_list_id) references ebayclone.product (id),
    constraint FKitxxb4d7kyum32qaj4l3hsyps
        foreign key (seller_id) references ebayclone.seller (id)
);

create table ebayclone.user_seq
(
    next_val bigint null
);

insert into ebayclone.user_seq value (1);