CREATE SEQUENCE cliente_seq;
CREATE SEQUENCE camareiro_seq;
CREATE SEQUENCE mesa_seq;
CREATE SEQUENCE produto_seq;
CREATE SEQUENCE categoria_seq;

CREATE TABLE cliente(
   cliente_id     BIGINT DEFAULT NEXTVAL('cliente_seq'),
   nome   CHARACTER VARYING(35),
   email  CHARACTER VARYING(30),  
   nome_fantasia  CHARACTER VARYING(35),
   numero_documento  CHARACTER VARYING(15),
   telefone  CHARACTER VARYING(15),
   cep  CHARACTER VARYING(35),
   provincia  CHARACTER VARYING(35),
   localidad  CHARACTER VARYING(35),
   direcion  CHARACTER VARYING(35),
   numero  CHARACTER VARYING(6),
   fornecerdor BOOLEAN DEFAULT true NOT NULL,
   PRIMARY KEY(cliente_id)
);

CREATE TABLE camareiro(
  camareiro_id BIGINT DEFAULT nextval('camareiro_seq'),
  nome CHARACTER VARYING(35),
  PRIMARY KEY (camareiro_id)
);

create table mesa( 
  mesa_id BIGINT  DEFAULT nextval('mesa_seq'),
  numero_mesa BIGINT NOT NULL,
  situacao_mesa CHARACTER VARYING(35),
  PRIMARY KEY (mesa_id)
);

CREATE TABLE categoria(
   categoria_id     BIGINT DEFAULT NEXTVAL('categoria_seq'),
   descricao   CHARACTER VARYING(35),
   cores  CHARACTER VARYING(30),                
   PRIMARY KEY(categoria_id)
);

CREATE TABLE produto(
  produto_id  BIGINT   DEFAULT nextval('produto_seq'),
  descricao  CHARACTER VARYING(35),
  valor NUMERIC(10,2) NOT NULL,
 
  categoria_id     INT NOT NULL, 
  FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id),  
  PRIMARY KEY (produto_id)
);