CREATE SEQUENCE comanda_seq;
create sequence item_comanda_id;

create table comanda(
  comanda_id BIGINT DEFAULT nextval('comanda_seq'),
  observacoes CHARACTER VARYING(100),
  valor_Total NUMERIC(10,2) NOT NULL,
  iniciar_atendimento timestamp,

  cliente_id       INT NOT NULL,  
  camareiro_id     INT NOT NULL,
  FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id),
  FOREIGN KEY (camareiro_id) REFERENCES camareiro(camareiro_id),
  PRIMARY KEY (comanda_id)
);

create table item_comanda(
  item_comanda_id BIGINT DEFAULT nextval('item_comanda_id'),
  quantidade INTEGER NOT NULL,
  valor_unitario NUMERIC(10,2) NOT NULL,
  comanda_id   BIGINT NOT NULL, 
  produto_id   BIGINT NOT NULL,  
  FOREIGN KEY (comanda_id) REFERENCES comanda(comanda_id),
  FOREIGN KEY (produto_id) REFERENCES produto(produto_id),
  PRIMARY KEY (item_comanda_id)
);