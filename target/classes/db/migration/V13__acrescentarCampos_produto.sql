ALTER TABLE produto
    ADD COLUMN abreviacao CHARACTER VARYING(15),
    ADD COLUMN EAN CHARACTER VARYING(15),
    ADD COLUMN preco_custo NUMERIC(10,2),
    ADD COLUMN margem_de_lucro INTEGER,
    ADD COLUMN desconto_maximo INTEGER,
    ADD COLUMN estoque_minimo INTEGER,
    ADD COLUMN estoque_atual INTEGER,
    ADD COLUMN fornecedor_id INT NOT NULL DEFAULT 1, 
    ADD COLUMN ultima_eompra timestamp,                
    ADD COLUMN ativo BOOLEAN DEFAULT true NOT NULL,
    ADD COLUMN controle_estoque BOOLEAN DEFAULT true NOT NULL,
    ADD CONSTRAINT fornfk  FOREIGN KEY(fornecedor_id) REFERENCES cliente(cliente_id);
