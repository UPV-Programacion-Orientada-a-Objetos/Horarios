CREATE SCHEMA IF NOT EXISTS horarios DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE horarios;

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

-- -----------------------------------------------------
-- Table `grupos`
-- -----------------------------------------------------

CREATE TABLE grupos(
    clv_grupo VARCHAR(10) NOT NULL PRIMARY KEY,
    turno BOOLEAN  
);

-- -----------------------------------------------------
-- Table 'carrera'
-- -----------------------------------------------------

CREATE TABLE carrera(
    idcarrera TINYINT NOT NULL PRIMARY KEY,
    nombre_carrera VARCHAR(100)
);

-- -----------------------------------------------------
-- Table 'aulas'
-- -----------------------------------------------------

CREATE TABLE aulas(
    id_aula VARCHAR(10) NOT NULL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    capacidad INT(11) NOT NULL,
    descripcion VARCHAR(100) NULL,
    ubicacion VARCHAR(20) NULL
);

-- -----------------------------------------------------
-- Table 'categorias_equipo';
-- -----------------------------------------------------

CREATE TABLE categorias_equipo(
    id_categoria INT(11) NOT NULL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300)
);

-- -----------------------------------------------------
-- Table 'usuarios'
-- -----------------------------------------------------

CREATE TABLE usuarios(
    clv_usuario VARCHAR(50) NOT NULL PRIMARY KEY,
    idcarrera TINYINT NOT NULL,
    nombre_usuario VARCHAR(50),
    nivel_ads VARCHAR(5) NOT NULL, -- falta el check
    contrato VARCHAR(3) NOT NULL,   -- falta el check
    CONSTRAINT FK_usuarios_carrera_idcarrera FOREIGN KEY (idcarrera) REFERENCES carrera(idcarrera)  ON DELETE NO ACTION ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table 'plan_estudios'
-- -----------------------------------------------------

CREATE TABLE plan_estudios(
    clv_plan VARCHAR(10) NOT NULL PRIMARY KEY,
    nombre_plan VARCHAR(45) NOT NULL,
    nivel VARCHAR(3) NOT NULL, -- falta el check
    idcarrera TINYINT NOT NULL, 
    CONSTRAINT FK_planestudios_carrera_idcarrera FOREIGN KEY (idcarrera) REFERENCES carrera (idcarrera)      
    );

-- -----------------------------------------------------
-- Table 'materias'
-- -----------------------------------------------------

CREATE TABLE materias(
    clv_materia VARCHAR(10) NOT NULL PRIMARY KEY,
    nombre_materia VARCHAR(50) NOT NULL,    
    creditos TINYINT NULL,
    cuatrimestre TINYINT NOT NULL,
    posicion TINYINT NOT NULL,
    clv_plan VARCHAR(10) NOT NULL,
    horas_x_semana TINYINT NOT NULL,
    tipo_materia CHAR(3) NOT NULL,
    CONSTRAINT FK_materias_planestudios_clv_plan FOREIGN KEY (clv_plan) REFERENCES plan_estudios(clv_plan)
);

-- -----------------------------------------------------
-- Table 'equipo'
-- -----------------------------------------------------

CREATE TABLE equipo(
    id_equipo INT(11) NOT NULL PRIMARY KEY,
    id_categoria INT(11) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    CONSTRAINT FK_equipo_categoriaequipo_id_categoria FOREIGN KEY (id_categoria) REFERENCES categorias_equipo(id_categoria)
);

-- -----------------------------------------------------
-- Table `login`
-- -----------------------------------------------------

CREATE TABLE login(
    clv_usuario VARCHAR(50) NOT NULL PRIMARY KEY,
    pass_usuario CHAR(41) NOT NULL,
    tipo_usuario CHAR(4) NOT NULL DEFAULT 'PROF' CHECK (tipo_usuario IN('DIRE','PROF')),
    CONSTRAINT FK_login_usuarios_clv_usuario FOREIGN KEY (clv_usuario) REFERENCES usuarios (clv_usuario)      
);

-- -----------------------------------------------------
-- Table 'aula_equipo'
-- -----------------------------------------------------

CREATE TABLE aula_equipo(
    id_equipo INT(11) NOT NULL,
    id_aula VARCHAR(10) NOT NULL,
    cantidad INT(11) DEFAULT 0,
    PRIMARY KEY(id_equipo, id_aula),
    CONSTRAINT FK_aulaequipo_aula_id_aula FOREIGN KEY (id_aula) REFERENCES aulas(id_aula),
    CONSTRAINT FK_aulaequipo_equipo_id_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
);

-- -----------------------------------------------------
-- Table 'uso_aula_grupo'
-- -----------------------------------------------------

CREATE TABLE uso_aula_grupo(
    dia TINYINT NOT NULL,
    espacio_tiempo TINYINT NOT NULL,
    id_aula VARCHAR(10) NOT NULL,
    clv_grupo VARCHAR(10) NOT NULL,
    clv_materia VARCHAR(10) NOT NULL,
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
    dia TINYINT NOT NULL,
    espacio_tiempo TINYINT NOT NULL,
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

CREATE TABLE materia_usuario(
    clv_materia VARCHAR(10) NOT NULL,
    clv_plan VARCHAR(10) NOT NULL,
    clv_usuario VARCHAR(50) NOT NULL,
    puntos_confianza TINYINT,
    puntos_director TINYINT,
    PRIMARY KEY (clv_materia, clv_plan, clv_usuario),
    CONSTRAINT FK_materiausuario_materias_clv_materia FOREIGN KEY (clv_materia) REFERENCES materias(clv_materia),    
    CONSTRAINT FK_materiausuario_usuarios_clv_usuario FOREIGN KEY (clv_usuario) REFERENCES usuarios(clv_usuario),    
    CONSTRAINT FK_materiausuario_planestudios_clv_plan FOREIGN KEY (clv_plan) REFERENCES plan_estudios(clv_plan)    
    );
    
-- -----------------------------------------------------
-- Table 'prestamos'
-- -----------------------------------------------------

CREATE TABLE prestamos(
    clv_usuario VARCHAR(50) NOT NULL,
    idcarrera TINYINT NOT NULL,
    PRIMARY KEY (clv_usuario, idcarrera),
    CONSTRAINT FK_prestamos_usuarios_clv_usuario FOREIGN KEY (clv_usuario) REFERENCES usuarios (clv_usuario),
    CONSTRAINT FK_prestamos_carrera_idcarrera FOREIGN KEY (idcarrera) REFERENCES carrera (idcarrera)      
);
