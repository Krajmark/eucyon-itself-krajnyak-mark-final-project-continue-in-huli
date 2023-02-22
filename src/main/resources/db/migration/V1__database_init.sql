create table product_seq
(
    next_val bigint null
);

insert into product_seq value (1);


create table user
(
    id       int          not null
        primary key,
    balance  int          null,
    password varchar(255) null,
    username varchar(255) null
);

create table admin
(
    id int not null
        primary key,
    constraint FK1ja8rua032fgnk9jmq7du3b3a
        foreign key (id) references user (id)
);

create table buyer
(
    id int not null
        primary key,
    constraint FK516w53ywd7f16mktqnoo0qlyu
        foreign key (id) references user (id)
);

create table seller
(
    id int not null
        primary key,
    constraint FKaqjn1ppqads72a0js4g7044eq
        foreign key (id) references user (id)
);

create table product
(
    id                        int          not null
        primary key,
    currency                  varchar(255) null,
    current_bid               int          null,
    description               varchar(255) null,
    is_sold                   bit          null,
    name                      varchar(255) null,
    picture_link              varchar(255) null,
    purchase_price            int          null,
    type                      varchar(255) null,
    buyer_id                  int          null,
    current_highest_bidder_id int          null,
    seller_id                 int          null,
    constraint FKesd6fy52tk7esoo2gcls4lfe3
        foreign key (seller_id) references seller (id),
    constraint FKjq1ly6a5saamkl2kn0sojdda2
        foreign key (current_highest_bidder_id) references buyer (id),
    constraint FKoxedx2y56mworpfxljluo6yts
        foreign key (buyer_id) references buyer (id)
);

create table buyer_bid_history
(
    buyer_id       int not null,
    bid_history_id int not null,
    constraint UK_6jqrr2x2wxqcsa5giquldw49g
        unique (bid_history_id),
    constraint FK7as233axmdkmd14as87iqo2xp
        foreign key (bid_history_id) references product (id),
    constraint FK90ixc9ihvtw6a2b3j7rond0o0
        foreign key (buyer_id) references buyer (id)
);

create table buyer_order_history
(
    buyer_id         int not null,
    order_history_id int not null,
    constraint UK_gvnsa2d8ai6g6n18xmn5wawk0
        unique (order_history_id),
    constraint FK2ptidvsf1rpgntokj5og1xh46
        foreign key (buyer_id) references buyer (id),
    constraint FKeucb530aiclrelgp3jn6xokjq
        foreign key (order_history_id) references product (id)
);

create table seller_product_list
(
    seller_id       int not null,
    product_list_id int not null,
    constraint UK_pg95txjhmll7pu20567qt0dox
        unique (product_list_id),
    constraint FKhi315810et4shx7ed05u77yo
        foreign key (product_list_id) references product (id),
    constraint FKitxxb4d7kyum32qaj4l3hsyps
        foreign key (seller_id) references seller (id)
);

create table user_seq
(
    next_val bigint null
);

insert into user_seq value (1);
