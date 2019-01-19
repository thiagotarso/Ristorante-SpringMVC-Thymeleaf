CREATE SEQUENCE seq_empresa;
CREATE SEQUENCE seq_usuario_empresa;

CREATE TABLE Empresa(
      id  BIGINT DEFAULT NEXTVAL('seq_empresa'),
      codigo CHARACTER VARYING(4),
      razao_social CHARACTER VARYING(35),
      fantasia CHARACTER VARYING(35),
      PRIMARY KEY(id)
);

CREATE TABLE empresa_usuario(
     id BIGINT DEFAULT NEXTVAL('seq_usuario_empresa'),
     id_empresa BIGINT,
     id_usuario BIGINT,
     FOREIGN KEY (id_empresa) REFERENCES empresa(id),
     FOREIGN KEY (id_usuario) REFERENCES usuario(id),
     PRIMARY KEY(id)
);