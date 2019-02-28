ALTER TABLE item_comanda ADD COLUMN controle_atendimento  timestamp;  
ALTER TABLE item_comanda ADD COLUMN quantidade_adicionada INTEGER;
ALTER TABLE produto ADD COLUMN setor_preparo CHARACTER VARYING(50);