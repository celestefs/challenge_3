create table topicos(
    id BIGINT not null  auto_increment,
    mensaje varchar(200) not null,
    nombreCurso varchar(14) not null,
    titulo varchar(100) not null,
    activo tinyint not null,
    fechaCreacion datetime not null,
    usuario_id bigint not null,

    primary key (id),
    constraint fk_topicos_usuario_id foreign key (usuario_id) references usuarios(id)
);