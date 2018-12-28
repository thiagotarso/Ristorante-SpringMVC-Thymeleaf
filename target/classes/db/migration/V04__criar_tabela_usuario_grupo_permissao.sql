CREATE SEQUENCE usuario_id_seq;
CREATE SEQUENCE grupo_id_seq;
CREATE SEQUENCE permissao_id_seq;

CREATE TABLE usuario (
    id BIGINT DEFAULT nextval('usuario_id_seq') NOT NULL,
    nome CHARACTER VARYING(50) NOT NULL,
    email CHARACTER VARYING(50) NOT NULL,
    senha CHARACTER VARYING(120) NOT NULL,
    ativo BOOLEAN DEFAULT true NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE grupo (
    id BIGINT DEFAULT nextval('grupo_id_seq')NOT NULL,
    nome CHARACTER VARYING(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE permissao (
    id  BIGINT DEFAULT nextval('permissao_id_seq')NOT NULL,
    nome CHARACTER VARYING(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE usuario_grupo (
    codigo_usuario BIGINT NOT NULL,
    codigo_grupo BIGINT NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_grupo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(id),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(id)
);

CREATE TABLE grupo_permissao (
    codigo_grupo BIGINT NOT NULL,
    codigo_permissao BIGINT NOT NULL,
    PRIMARY KEY (codigo_grupo, codigo_permissao),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(id),
    FOREIGN KEY (codigo_permissao) REFERENCES permissao(id)
);