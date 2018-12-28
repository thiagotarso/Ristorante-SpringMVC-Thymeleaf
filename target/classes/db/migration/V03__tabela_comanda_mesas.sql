create table comanda_mesa (
   id_comanda BIGINT NOT NULL,
   id_mesa BIGINT NOT NULL,
   PRIMARY KEY(id_comanda, id_mesa),
   FOREIGN KEY (id_comanda) REFERENCES comanda(comanda_id),
   FOREIGN KEY (id_mesa) REFERENCES mesa(mesa_id)
)
