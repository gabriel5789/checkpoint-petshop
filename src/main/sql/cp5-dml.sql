TRUNCATE TABLE tb_devolucao;
TRUNCATE TABLE tb_reserva;
TRUNCATE TABLE tb_estoque;
TRUNCATE TABLE tb_produtos_venda;
TRUNCATE TABLE tb_pedido;
TRUNCATE TABLE tb_vendedor;
TRUNCATE TABLE tb_loja;
TRUNCATE TABLE tb_fornece;
TRUNCATE TABLE tb_fornecedor;
TRUNCATE TABLE tb_produto;
TRUNCATE TABLE tb_unidade_medida;
TRUNCATE TABLE tb_animal;
TRUNCATE TABLE tb_tipo_animal;
TRUNCATE TABLE tb_raca_animal;
TRUNCATE TABLE tb_cliente_pj;
TRUNCATE TABLE tb_cliente_pf;
TRUNCATE TABLE tb_cliente;
TRUNCATE TABLE tb_endereco;
TRUNCATE TABLE tb_cidade;
TRUNCATE TABLE tb_estado;
COMMIT;

DROP SEQUENCE sq_id_raca;
DROP SEQUENCE sq_id_tipo;
DROP SEQUENCE sq_id_animal;
DROP SEQUENCE sq_id_cidade;
DROP SEQUENCE sq_id_endereco;
DROP SEQUENCE sq_id_cliente;
DROP SEQUENCE sq_id_reserva;
DROP SEQUENCE sq_id_un_medida;
DROP SEQUENCE sq_id_fornecedor;
DROP SEQUENCE sq_id_loja;
DROP SEQUENCE sq_id_produto;
DROP SEQUENCE sq_id_vendedor;
DROP SEQUENCE sq_id_pedido;
COMMIT;

CREATE SEQUENCE sq_id_raca START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_tipo START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_animal START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_cidade START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_endereco START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_cliente START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_reserva START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_un_medida START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_fornecedor START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_loja START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_produto START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_vendedor START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE sq_id_pedido START WITH 1 INCREMENT BY 1;
COMMIT;

INSERT INTO tb_tipo_animal(id_tipo, nm_tipo) VALUES(sq_id_tipo.nextval, 'Gato');
COMMIT;

INSERT INTO tb_tipo_animal(id_tipo, nm_tipo) VALUES(sq_id_tipo.nextval, 'Cachorro');
COMMIT;

INSERT INTO tb_raca_animal(id_raca, nm_raca) VALUES(sq_id_raca.nextval, 'Frajola');
COMMIT;

INSERT INTO tb_raca_animal(id_raca, nm_raca) VALUES(sq_id_raca.nextval, 'Siamês');
COMMIT;

INSERT INTO tb_raca_animal(id_raca, nm_raca) VALUES(sq_id_raca.nextval, 'Pitbull');
COMMIT;

INSERT INTO tb_raca_animal(id_raca, nm_raca) VALUES(sq_id_raca.nextval, 'SRD');
COMMIT;

INSERT INTO tb_raca_animal(id_raca, nm_raca) VALUES(sq_id_raca.nextval, 'Maltês');
COMMIT;

INSERT INTO tb_estado(sg_estado, nm_estado) VALUES('SP', 'São Paulo');
COMMIT;

INSERT INTO tb_estado(sg_estado, nm_estado) VALUES('MG', 'Minas Gerais');
COMMIT;

INSERT INTO tb_cidade(id_cidade, nm_cidade, sg_estado) VALUES(sq_id_cidade.nextval, 'São Paulo', 'SP');
COMMIT;

INSERT INTO tb_cidade(id_cidade, nm_cidade, sg_estado) VALUES(sq_id_cidade.nextval, 'Belo Horizonte', 'MG');
COMMIT;

INSERT INTO tb_cidade(id_cidade, nm_cidade, sg_estado) VALUES(sq_id_cidade.nextval, 'Cotia', 'SP');
COMMIT;

INSERT INTO tb_cidade(id_cidade, nm_cidade, sg_estado) VALUES(sq_id_cidade.nextval, 'Campinas', 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Avenida Carlos Lacerda', 2093, null, 5658895, 'Campo Limpo', 1, 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Avenida Lins de Vasconcelos', 2000, '3o andar', 54869777, 'Aclimação', 1, 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Avenida Abrahão Caram', 1001, null, 31275000, 'São José', 2, 'MG');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Rua Eusébio da Costa Braga', 34, null, 05632060, 'Jd. Monte Kemel', 1, 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Rua Santo Afonso', 117, 'Apto 58', 06708395, 'Vila Santo Antônio Costa', 3, 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Av. Rebouças', 2184, null, 05402300, 'Pinheiros', 1, 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Av. Afonso Pena', 381, null, 30130000, 'Centro', 2, 'MG');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Rua das Magnólias', 2405, null, 13050089, 'Jardim das Bandeiras', 4, 'SP');
COMMIT;

INSERT INTO tb_endereco(id_endereco, nm_rua, nr_rua, ds_complemento, nr_cep, nm_bairro, id_cidade, sg_estado)
    VALUES(sq_id_endereco.nextval, 'Av. Emb. Macedo Soares', 10735, '2º andar', 05035901, 'Vila Anastácio', 1, 'SP');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'Ana', 1, 'ana.eloiza@gmail.com', 'PF');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'FIAP', 2, 'relacoes.empresariais@fiap.com', 'PJ');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'Cruzeiro', 3, 'ronaldo.fenomeno@cruzeiro.com.br', 'PJ');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'Gabriel', 4, 'gabriel.guedes@gmail.com', 'PF');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'Polianna', 4, 'polianna223@gmail.com', 'PF');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'Gabi', 5, 'gabi.sch@gmail.com', 'PF');
COMMIT;

INSERT INTO tb_cliente(id_cliente, nm_cliente, id_endereco, ds_email, ds_tipo_cliente) VALUES(sq_id_cliente.nextval, 'Neide', 5, 'neide.neres22@gmail.com', 'PF');
COMMIT;

INSERT INTO tb_cliente_pj(nr_cnpj, id_cliente) VALUES(878982430001, 3);
COMMIT;

INSERT INTO tb_cliente_pj(nr_cnpj, id_cliente) VALUES(282523810001, 2);
COMMIT;

INSERT INTO tb_cliente_pf(nr_cpf, id_cliente) VALUES(54896584759, 1);
COMMIT;

INSERT INTO tb_cliente_pf(nr_cpf, id_cliente) VALUES(51476896801, 4);
COMMIT;

INSERT INTO tb_cliente_pf(nr_cpf, id_cliente) VALUES(56489621548, 5);
COMMIT;

INSERT INTO tb_cliente_pf(nr_cpf, id_cliente) VALUES(89748562315, 6);
COMMIT;

INSERT INTO tb_cliente_pf(nr_cpf, id_cliente) VALUES(13478958243, 7);
COMMIT;

INSERT INTO tb_animal(id_animal, nm_animal, id_cliente, id_tipo, id_raca) VALUES(sq_id_animal.nextval, 'Amy', 4, 1, 1);
COMMIT;

INSERT INTO tb_animal(id_animal, nm_animal, id_cliente, id_tipo, id_raca) VALUES(sq_id_animal.nextval, 'Milton', 2, 1, 2);
COMMIT;

INSERT INTO tb_animal(id_animal, nm_animal, id_cliente, id_tipo, id_raca) VALUES(sq_id_animal.nextval, 'Floki', 5, 2, 5);
COMMIT;

INSERT INTO tb_vendedor(id_vendedor, nm_vendedor, id_coordenador) VALUES(sq_id_vendedor.nextval, 'Rogério Ceni', null);
COMMIT;

INSERT INTO tb_vendedor(id_vendedor, nm_vendedor, id_coordenador) VALUES(sq_id_vendedor.nextval, 'Dorival Jr.', 1);
COMMIT;

INSERT INTO tb_vendedor(id_vendedor, nm_vendedor, id_coordenador) VALUES(sq_id_vendedor.nextval, 'Abel Ferreira', 1);
COMMIT;

INSERT INTO tb_loja(id_loja, id_endereco) VALUES(sq_id_loja.nextval, 6);
COMMIT;

INSERT INTO tb_loja(id_loja, id_endereco) VALUES(sq_id_loja.nextval, 7);
COMMIT;

INSERT INTO tb_unidade_medida(id_unidade_medida, nm_unidade_medida) VALUES(sq_id_un_medida.nextval, 'UNIDADE');
COMMIT;

INSERT INTO tb_unidade_medida(id_unidade_medida, nm_unidade_medida) VALUES(sq_id_un_medida.nextval, 'KG');
COMMIT;

INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) 
    VALUES(sq_id_produto.nextval, 'Pacote Ração Golden Gatos Frango 5KG', 50, 1);
COMMIT;

INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) 
    VALUES(sq_id_produto.nextval, 'Ração Golden Gatos Frango', 15, 2);
COMMIT;

INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) 
    VALUES(sq_id_produto.nextval, 'Pacote Ração Gran Plus Cachorro Frango e Carne 10,1KG', 105, 1);
COMMIT;

INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) 
    VALUES(sq_id_produto.nextval, 'Ração Gran Plus Cachorro Frango e Carne', 13, 2);
COMMIT;

INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) 
    VALUES(sq_id_produto.nextval, 'Granulado Higiênico Kat Bom Tradicional Para Gatos 3KG', 39.99, 1);
COMMIT;

INSERT INTO tb_produto(id_produto, nm_produto, vl_unitario_base, id_unidade_medida) 
    VALUES(sq_id_produto.nextval, 'Revolution Zoetis 6% 0.75ml para Gatos 2,6Kg a 7,5Kg', 86.90, 1);
COMMIT;

--loja 1
INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(1, 1, 50);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(1, 2, 90);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(1, 3, 45);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(1, 4, 90);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(1, 5, 70);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(1, 6, 10);
COMMIT;

-- loja 2
INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(2, 1, 60);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(2, 2, 70);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(2, 3, 50);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(2, 4, 55);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(2, 5, 50);
COMMIT;

INSERT INTO tb_estoque(id_loja, id_produto, qt_estoque) VALUES(2, 6, 0);
COMMIT;

INSERT INTO tb_fornecedor(id_fornecedor, nm_fornecedor, nr_cnpj_fornecedor, id_endereco) VALUES(sq_id_fornecedor.nextval, 'Gran Plus LTDA', 45710423000133, 8);
COMMIT;

INSERT INTO tb_fornecedor(id_fornecedor, nm_fornecedor, nr_cnpj_fornecedor, id_endereco) VALUES(sq_id_fornecedor.nextval, 'Golden Racoes LTDA', 04196935000901, 9);
COMMIT;

INSERT INTO tb_fornece(id_produto, id_fornecedor) VALUES(1, 2);
COMMIT;

INSERT INTO tb_fornece(id_produto, id_fornecedor) VALUES(2, 2);
COMMIT;

INSERT INTO tb_fornece(id_produto, id_fornecedor) VALUES(3, 1);
COMMIT;

INSERT INTO tb_fornece(id_produto, id_fornecedor) VALUES(4, 1);
COMMIT;

INSERT INTO tb_fornece(id_produto, id_fornecedor) VALUES(5, 2);
COMMIT;

INSERT INTO tb_fornece(id_produto, id_fornecedor) VALUES(6, 1);
COMMIT;

INSERT INTO tb_reserva(id_reserva, id_cliente, id_loja, id_produto, dt_reserva, dt_retirada, nr_qtde_reserva) 
    VALUES(sq_id_reserva.nextval, 2, 2, 2, SYSDATE, '01/11/2022', 20);
COMMIT;

INSERT INTO tb_reserva(id_reserva, id_cliente, id_loja, id_produto, dt_reserva, dt_retirada, nr_qtde_reserva) 
    VALUES(sq_id_reserva.nextval, 3, 1, 1, SYSDATE, '01/12/2022', 10);
COMMIT;

INSERT INTO tb_pedido(id_pedido, dt_pedido, id_cliente, id_vendedor, id_loja) 
    VALUES(sq_id_pedido.nextval, SYSDATE, 1, 1, 1);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(1, 1, 1, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 1), 1);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(1, 5, 1, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 5), 1);
COMMIT;

INSERT INTO tb_pedido(id_pedido, dt_pedido, id_cliente, id_vendedor, id_loja) 
    VALUES(sq_id_pedido.nextval, SYSDATE, 2, 3, 1);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(2, 2, 5, (SELECT vl_unitario_base * 0.95 FROM tb_produto WHERE id_produto = 2), 3);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(2, 4, 5, (SELECT vl_unitario_base * 0.925 FROM tb_produto WHERE id_produto = 4), 3);
COMMIT;

INSERT INTO tb_pedido(id_pedido, dt_pedido, id_cliente, id_vendedor, id_loja) 
    VALUES(sq_id_pedido.nextval, SYSDATE, 3, 3, 1);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(3, 2, 3, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 2), 1);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(3, 5, 5, (SELECT vl_unitario_base * 0.95 FROM tb_produto WHERE id_produto = 5), 1);
COMMIT;

INSERT INTO tb_pedido(id_pedido, dt_pedido, id_cliente, id_vendedor, id_loja) 
    VALUES(sq_id_pedido.nextval, SYSDATE, 4, 2, 2);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(4, 6, 1, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 6), 1);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(4, 5, 1, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 5), 1);
COMMIT;

INSERT INTO tb_pedido(id_pedido, dt_pedido, id_cliente, id_vendedor, id_loja) 
    VALUES(sq_id_pedido.nextval, SYSDATE, 5, 3, 2);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(5, 3, 1, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 3), 2);
INSERT INTO tb_produtos_venda(id_pedido, id_produto, nr_quantidade, vl_preco_unitario, id_vendedor_aprovador) 
    VALUES(5, 6, 1, (SELECT vl_unitario_base FROM tb_produto WHERE id_produto = 6), 2);
COMMIT;

INSERT INTO tb_devolucao(id_cliente, id_pedido, id_produto, dt_devolucao, qt_itens_devolvidos) 
    VALUES(1, 1, 1, SYSDATE, 1);
COMMIT;

INSERT INTO tb_devolucao(id_cliente, id_pedido, id_produto, dt_devolucao, qt_itens_devolvidos) 
    VALUES(4, 5, 6, SYSDATE, 1);
COMMIT;

UPDATE tb_cliente
    SET nm_cliente = 'Ana Eloiza'
    WHERE id_cliente = 1;
COMMIT;

UPDATE tb_estoque
    SET qt_estoque = qt_estoque + 10
    WHERE id_loja = 1 AND id_produto = 1;
COMMIT;

DELETE FROM tb_devolucao
    WHERE id_pedido = 5;
DELETE FROM tb_produtos_venda
    WHERE id_pedido = 5;
DELETE FROM tb_pedido
    WHERE id_pedido = 5;
COMMIT;

