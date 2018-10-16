create table folder(
  id_folder serial primary key,
  name varchar(100) not null,
  create_date date not null
);

create table note(
  id_note serial primary key,
  name varchar(100) not null,
  text varchar(1000),
  create_date date not null,
  id_folder int references folder(id_folder) ON DELETE CASCADE
);

create table action(
  id_action serial primary key,
  text varchar(500),
  passed boolean not null,
  id_note int references note(id_note) ON DELETE CASCADE
);