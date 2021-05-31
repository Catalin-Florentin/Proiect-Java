create database blog;
create user 'blog'@'localhost' identified by 'password';
grant all privileges on * . * to 'blog'@'localhost';

drop table Comentarii;
drop table Voturi;
drop table Taguri;
drop table Articole;
drop table Utilizatori;

create table Utilizatori(
	id_utilizator int auto_increment primary key,
    nume varchar(20) not null,
    prenume varchar(20) not null,
    email varchar(30) not null unique,
    parola varchar(30) not null
);

create table Articole(
	id_articol int auto_increment primary key,
    id_utilizator int,
    draft int default 1,
    titlu  varchar(20) not null,
    descriere varchar(50),
    continut varchar(2000) not null,
    vizualizari int default 0,
    constraint foreign key(id_utilizator) references Utilizatori(id_utilizator) on delete cascade
);

create table Taguri(
	nume varchar(20),
    id_articol int,
    constraint foreign key(id_articol) references Articole(id_articol) on delete cascade,
    constraint primary key(nume,id_articol)
);

create table Voturi(
	id_vot int auto_increment primary key,
    id_articol int,
    valoare int,
    ip varchar(50),
    constraint foreign key(id_articol) references Articole(id_articol) on delete cascade,
    constraint check(valoare<=10 AND valoare>=0),
    constraint unique (id_articol,ip)
);
drop table Voturi;
create table Comentarii(
	id_articol int,
    data_publicare timestamp default current_timestamp,
    username varchar(20) not null,
    continut varchar(100) not null,
    aprobat int default 0,
    ip varchar(40),
	constraint primary key (id_articol,data_publicare),
    constraint foreign key (id_articol) references Articole(id_articol) on delete cascade
);

select * from Utilizatori;
select * from Articole;
select * from Taguri;
select * from Comentarii;
select * from Voturi;

delete from Comentarii where 1=1 in (
                                        select id_articol
                                        from Comentarii 
                                        join Articole using(id_articol) 
                                        join Utilizatori using(id_utilizator) 
                                        where id_utilizator=1 and id_articol=1 and data_publicare='2021-05-31 13:27:51' );
delete from (select id_articol, data_publicare 
                                        from Comentarii 
                                        join Articole using(id_articol) 
                                        join Utilizatori using(id_utilizator) 
                                        where id_utilizator=1 and id_articol=1 and data_publicare='2021-05-31 13:27:51' ) com2;                                        