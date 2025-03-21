use library_db;

create table users (
	id int primary key auto_increment,
    username varchar(50) not null
);
drop table users;
drop table books;

create table books (
	id int primary key auto_increment,
    title varchar(255),
    author varchar(255),
    release_year int,
    count int,
    remaining_count int
);

delete from books;


create table book_borrows (
	user_id int,
    book_id int,
    borrow_date date default (curdate()),
    return_date date not null,
    is_returned boolean default false,
    foreign key (user_id) references users(id) on delete cascade,
    foreign key (book_id) references books(id) on delete cascade
);


drop table book_borrows;

insert into books (title, author, release_year ,count, remaining_count) values
("Art of war", "Sun Tzu", 1990, 10, 10),
("How to win friends and influence people", "Carl Dige", 2014, 3, 3),
("Chi migi mashti", "Nemidoonam", 2025, 2, 2),
("Leader Lie", "William Jones", 1922, 1, 1)
;

insert into users (username) values
("MamadReza"),
("AliReza");

insert into book_borrows(user_id, book_id, return_date) values
(1, 1, "2025-12-12"),
(3, 2, "2025-11-11"),
(3, 1, "2023-01-12");

select * from book_borrows
inner join users on
users.id = book_borrows.user_id
inner join books on
books.id = book_borrows.book_id;

select * from books;


# 1
select * from books;

# 2
select books.* from books
inner join book_borrows on
books.id = book_borrows.book_id;

# 3
select books.* from book_borrows
inner join users on
users.id = book_borrows.user_id
inner join books on
books.id = book_borrows.book_id
where users.username = "Mamad";

# 4
select books.*, book_borrows.return_date from book_borrows
inner join books on
books.id = book_borrows.book_id;

# 5
select books.title, count(book_id) as borrow_count 
from book_borrows
inner join books on
books.id = book_borrows.book_id
group by book_id
order by borrow_count desc
limit 10;

# 6
select users.username, count(book_borrows.user_id) as borrow_count 
from book_borrows
right join users on
users.id = book_borrows.user_id
group by users.id
order by borrow_count desc
limit 10;

# 7
select * from books
where books.id not in 
(select book_borrows.book_id from book_borrows);

# 8
select books.title, book_borrows.return_date from book_borrows
inner join users on
users.id = book_borrows.user_id
inner join books on
books.id = book_borrows.book_id
where users.username = "Mamad" and book_borrows.return_date > "2025-01-01";

# 9
select avg(borrow_count) as average_borrow from
(select count(book_borrows.user_id) as borrow_count from users
left join book_borrows on
book_borrows.user_id = users.id
group by users.id) as users_borrow_count;

# 10
select username, borrow_count from
(select users.username as username, count(book_borrows.user_id) as borrow_count 
from book_borrows
right join users on
users.id = book_borrows.user_id
group by users.id) as users_borrow_count
order by borrow_count desc
limit 3;

# 12 
select books.*, book_borrows.return_date from book_borrows
inner join books on
books.id = book_borrows.book_id
where book_borrows.is_returned = false;

# 13
select books.*, book_borrows.* from book_borrows
inner join books on
books.id = book_borrows.book_id;

# indexing
alter table book_borrows add index (user_id);

# borrow transaction
start transaction;
insert into book_borrows(book_id, user_id, return_date) values (1, 1, "2025-11-11");
    
update books 
set remaining_count = remaining_count - 1
where books.id = 1;
commit;

# trigger
create trigger return_book_trigger before update on book_borrows
for each row set new.return_date = if(new.is_returned, curdate(), new.return_date);

drop trigger return_book_trigger;

# testing trigger
update book_borrows
set is_returned = true
where book_id = 1;

select * from book_borrows;

# view
create view borrowed_books as
select * from books
inner join book_borrows
on books.id = book_borrows.book_id
where book_borrows.is_returned = false;
