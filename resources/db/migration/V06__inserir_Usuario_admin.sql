INSERT INTO usuario (id ,nome, email, senha, ativo) VALUES ( 1 ,'Admin', 'admin@ristorante.com', '$2a$10$akp82R6pyplMR.zYzzq44uwgIrba2FKncbTvts39EQqC8E9jM6Ep6', true);
INSERT INTO permissao (id, nome) VALUES(1, 'ROLE_CADASTRO_USUARIO');
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES(1, 1);
INSERT INTO usuario_grupo( codigo_usuario, codigo_grupo) VALUES ((SELECT id FROM usuario WHERE email = 'admin@ristorante.com') , 1);