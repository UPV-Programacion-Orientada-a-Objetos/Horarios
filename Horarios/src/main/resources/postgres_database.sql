DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS materia_usuario;
DROP TABLE IF EXISTS grupo_materia_profesor;
DROP TABLE IF EXISTS disponibilidad;
DROP TABLE IF EXISTS uso_aula_grupo;
DROP TABLE IF EXISTS aula_equipo;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS equipo;
DROP TABLE IF EXISTS materias;
DROP TABLE IF EXISTS plan_estudios;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS categorias_equipo;
DROP TABLE IF EXISTS aulas;
DROP TABLE IF EXISTS carrera;
DROP TABLE IF EXISTS grupos;

create table grupos
(
    clv_grupo varchar(10) not null PRIMARY KEY,
    turno boolean not null
);

create table carrera
(
    idcarrera smallint NOT NULL PRIMARY KEY,
    nombre_carrera varchar(100) NOT NULL
);

create table aulas
(
    id_aula varchar(10) not null PRIMARY KEY,
    nombre varchar(100) NOT NULL ,
    tipo varchar(20) NOT NULL ,
    capacidad numeric(11) NOT NULL,
    descripcion varchar(100) NULL,
    ubicacion VARCHAR(20) NULL
);

create table categorias_equipo
(
    id_categoria numeric(11) NOT NULL PRIMARY KEY ,
    nombre varchar(100) NOT NULL,
    descripcion varchar(300) NULL
);

create table usuarios
(
    clv_usuario varchar(50) NOT NULL PRIMARY KEY,
    idcarrera smallint NOT NULL,
    nombre_usuario varchar(50),
    nivel_ads varchar(5) NOT NULL,
    contrato varchar(3) NOT NULL,
    constraint FK_usuarios_carrera_idcarrera foreign key(idcarrera) references carrera(idcarrera)
);

create table plan_estudios
(
    clv_plan varchar(10) NOT NULL PRIMARY KEY,
    nombre_plan varchar(45) NOT NULL,
    nivel varchar(3) NOT NULL ,
    idcarrera smallint NOT NULL,
 constraint FK_planestudios_carrera_idcarrera foreign key(idcarrera) references carrera(idcarrera)
);

create table materias
(
    clv_materia varchar(10) NOT NULL PRIMARY KEY,
    nombre_materia varchar(50) NOT NULL,
    creditos smallint,
    cuatrimestre smallint NOT NULL,
    posicion smallint NOT NULL,
    clv_plan varchar(10) NOT NULL,
    horas_x_semana smallint NOT NULL,
    tipo_materia char(3) NOT NULL,
    constraint FK_materias_planestudios_clv_plan foreign key(clv_plan) references plan_estudios(clv_plan)
);

create table equipo
(
    id_equipo numeric(11) NOT NULL PRIMARY KEY,
    id_categoria numeric(11) NOT NULL,
    nombre varchar(100) NOT NULL,
    descripcion varchar(100) NOT NULL,
    constraint FK_equipo_categoriaequipo_id_categoria foreign key(id_categoria) references categorias_equipo(id_categoria)
);

create table login
(
    clv_usuario varchar(50) NOT NULL PRIMARY KEY,
    pass_usuario varchar(41) NOT NULL,
    tipo_usuario char(4) NOT NULL DEFAULT 'PROF' CHECK ( tipo_usuario IN ('DIRE', 'PROF')),
    constraint FK_login_usuarios_clv_usuario  foreign key(clv_usuario) references usuarios(clv_usuario)
);

create table aula_equipo
(
    id_equipo numeric(11) NOT NULL,
    id_aula varchar(10) NOT NULL,
    cantidad numeric(11) DEFAULT 0,
    PRIMARY KEY (id_equipo, id_aula),
    CONSTRAINT FK_aulaequipo_aula_id_aula FOREIGN KEY (id_aula) REFERENCES aulas(id_aula),
    CONSTRAINT FK_aulaequipo_equipo_id_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
);

create table uso_aula_grupo
(
    dia smallint NOT NULL,
    espacio_tiempo smallint NOT NULL,
    id_aula varchar(10) NOT NULL,
    clv_grupo varchar(10) NOT NULL,
    clv_materia varchar(10) NOT NULL,
    PRIMARY KEY (dia, espacio_tiempo, id_aula, clv_grupo, clv_materia),
    CONSTRAINT UNK_dia_espaciotiempo_idaula UNIQUE (dia, espacio_tiempo, id_aula),
    CONSTRAINT UNK_dia_espaciotiempo_clvgrupo UNIQUE (dia, espacio_tiempo, clv_grupo),
    CONSTRAINT FK_usoaulagrupo_grupos_clv_grupo FOREIGN KEY (clv_grupo) REFERENCES grupos(clv_grupo),
    CONSTRAINT FK_usoaulagrupo_materias_clv_materia FOREIGN KEY (clv_materia) REFERENCES materias(clv_materia),
    CONSTRAINT FK_usoaulagrupo_aulas_id_aula FOREIGN KEY (id_aula) REFERENCES aulas(id_aula)
);

-- -----------------------------------------------------
-- Table `disponibilidad`
-- -----------------------------------------------------

CREATE TABLE disponibilidad(
    dia SMALLINT NOT NULL,
    espacio_tiempo SMALLINT NOT NULL,
    clv_usuario VARCHAR(50),
    PRIMARY KEY (dia, espacio_tiempo, clv_usuario),
    CONSTRAINT FK_disponibilidad_usuarios_clv_usuario FOREIGN KEY (clv_usuario) REFERENCES usuarios (clv_usuario)
);

-- -----------------------------------------------------
-- Table `grupo_materia_profesor`
-- -----------------------------------------------------

CREATE TABLE grupo_materia_profesor(
    clv_grupo VARCHAR(10) NOT NULL,
    clv_materia VARCHAR(10) NOT NULL,
    clv_usuario VARCHAR(50) NOT NULL,
    PRIMARY KEY(clv_grupo, clv_materia, clv_usuario),
    CONSTRAINT fk_grupomateriaprofesor_grupos_clv_grupo FOREIGN KEY(clv_grupo) REFERENCES grupos(clv_grupo),
    CONSTRAINT fk_grupomateriaprofesor_materias_clv_materia FOREIGN KEY(clv_materia) REFERENCES materias(clv_materia),
    CONSTRAINT fk_grupomateriaprofesor_usuarios_clv_usuario FOREIGN KEY(clv_usuario) REFERENCES usuarios (clv_usuario)
);

-- -----------------------------------------------------
-- Table 'materia_usario'
-- -----------------------------------------------------

CREATE TABLE materia_usuario
(
    clv_materia VARCHAR(10) NOT NULL,
    clv_plan VARCHAR(10) NOT NULL,
    clv_usuario VARCHAR(50) NOT NULL,
    puntos_confianza SMALLINT,
    puntos_director SMALLINT,
    PRIMARY KEY (clv_materia, clv_plan, clv_usuario),
    CONSTRAINT FK_materiausuario_materias_clv_materia FOREIGN KEY (clv_materia) REFERENCES materias(clv_materia),
    CONSTRAINT FK_materiausuario_usuarios_clv_usuario FOREIGN KEY (clv_usuario) REFERENCES usuarios(clv_usuario),
    CONSTRAINT FK_materiausuario_planestudios_clv_plan FOREIGN KEY (clv_plan) REFERENCES plan_estudios(clv_plan)
);

-- -----------------------------------------------------
-- Table 'prestamos'
-- -----------------------------------------------------

CREATE TABLE prestamos
(
    clv_usuario VARCHAR(50) NOT NULL,
    idcarrera SMALLINT NOT NULL,
    PRIMARY KEY (clv_usuario, idcarrera),
    CONSTRAINT FK_prestamos_usuarios_clv_usuario FOREIGN KEY (clv_usuario) REFERENCES usuarios (clv_usuario),
    CONSTRAINT FK_prestamos_carrera_idcarrera FOREIGN KEY (idcarrera) REFERENCES carrera (idcarrera)
);
