create sequence mesa_comanda_id;

create table mesa_comanda(
  mesa_comanda_id BIGINT DEFAULT nextval('mesa_comanda_id'),
  comanda_id   BIGINT NOT NULL, 
  mesa_id   BIGINT NOT NULL,  
  FOREIGN KEY (comanda_id) REFERENCES comanda(comanda_id),
  FOREIGN KEY (mesa_id) REFERENCES mesa(mesa_id),
  PRIMARY KEY (mesa_comanda_id)
);