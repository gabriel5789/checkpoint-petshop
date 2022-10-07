
-- RM: 88866 - Nome: Israel Clementino da Silva Junior
-- RM: 88398 - Nome: Gabriel Guedes Silva

DROP TABLE tb_animal CASCADE CONSTRAINTS;
DROP TABLE tb_tipo_animal CASCADE CONSTRAINTS;
DROP TABLE tb_raca_animal CASCADE CONSTRAINTS;
DROP TABLE tb_estado CASCADE CONSTRAINTS;
DROP TABLE tb_cidade CASCADE CONSTRAINTS;
DROP TABLE tb_endereco CASCADE CONSTRAINTS;
DROP TABLE tb_cliente CASCADE CONSTRAINTS;
DROP TABLE tb_cliente_pj CASCADE CONSTRAINTS;
DROP TABLE tb_cliente_pf CASCADE CONSTRAINTS;
DROP TABLE tb_produtos_venda CASCADE CONSTRAINTS;
DROP TABLE tb_pedido CASCADE CONSTRAINTS;
DROP TABLE tb_vendedor CASCADE CONSTRAINTS;
DROP TABLE tb_loja CASCADE CONSTRAINTS;
DROP TABLE tb_reserva CASCADE CONSTRAINTS;
DROP TABLE tb_devolucao CASCADE CONSTRAINTS;
DROP TABLE tb_unidade_medida CASCADE CONSTRAINTS;
DROP TABLE tb_produto CASCADE CONSTRAINTS;
DROP TABLE tb_estoque CASCADE CONSTRAINTS;
DROP TABLE tb_fornece CASCADE CONSTRAINTS;
DROP TABLE tb_fornecedor CASCADE CONSTRAINTS;

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



CREATE TABLE tb_animal (
    id_animal  NUMBER(8) NOT NULL,
    nm_animal  VARCHAR2(50 CHAR) NOT NULL,
    id_cliente NUMBER(8) NOT NULL,
    id_tipo    NUMBER(3) NOT NULL,
    id_raca    NUMBER(10) NOT NULL,
    CONSTRAINT animal_pk PRIMARY KEY ( id_animal )
);

CREATE INDEX ix_tb_animal ON tb_animal(nm_animal, id_cliente);

CREATE TABLE tb_cidade (
    id_cidade    NUMBER(8) NOT NULL,
    nm_cidade    VARCHAR2(60 CHAR) NOT NULL,
    sg_estado VARCHAR2(2 CHAR) NOT NULL,
    CONSTRAINT cidade_pk PRIMARY KEY ( id_cidade,sg_estado )
);

CREATE INDEX ix_tb_cidade ON tb_cidade(nm_cidade, sg_estado);

CREATE TABLE tb_cliente (
    id_cliente      NUMBER(8) NOT NULL,
    nm_cliente      VARCHAR2(60 CHAR) NOT NULL,
    id_endereco     NUMBER(10) NOT NULL,
    ds_email        VARCHAR2(255) NOT NULL,
    ds_tipo_cliente VARCHAR2(2) NOT NULL,
    CONSTRAINT cliente_mail_val CHECK (ds_email LIKE '%_@_%._%'),
    CONSTRAINT cliente_mail_sk UNIQUE (ds_email),
    CONSTRAINT cliente_pk PRIMARY KEY ( id_cliente )
);

CREATE INDEX ix_cliente_nm_email ON tb_cliente(nm_cliente, ds_email);

CREATE TABLE tb_cliente_pf (
    nr_cpf     NUMBER(11) NOT NULL,
    id_cliente NUMBER(8) NOT NULL,
    CONSTRAINT cliente_pf_pk PRIMARY KEY ( id_cliente ),
    CONSTRAINT cliente_pf_sk UNIQUE ( nr_cpf )
);

CREATE TABLE tb_cliente_pj (
    nr_cnpj    NUMBER(14) NOT NULL,
    id_cliente NUMBER(8) NOT NULL,
    CONSTRAINT cliente_pj_pk PRIMARY KEY ( id_cliente ),
    CONSTRAINT cliente_pj_sk UNIQUE ( nr_cnpj )
);

CREATE TABLE tb_devolucao (
    id_cliente          NUMBER(8) NOT NULL,
    id_pedido           NUMBER(12) NOT NULL,
    id_produto          NUMBER(8) NOT NULL,
    dt_devolucao        DATE DEFAULT SYSDATE NOT NULL,
    qt_itens_devolvidos NUMBER(10, 2) NOT NULL,
    CONSTRAINT devolucao_pk PRIMARY KEY ( id_cliente, id_pedido, id_produto )
);

CREATE INDEX ix_devolucoes_cliente ON tb_devolucao(id_cliente, dt_devolucao);

CREATE TABLE tb_endereco (
    id_endereco    NUMBER(10) NOT NULL,
    nm_rua         VARCHAR2(100 CHAR) NOT NULL,
    nr_rua         NUMBER(5) NOT NULL,
    ds_complemento VARCHAR2(10 CHAR),
    nr_cep         NUMBER(8) NOT NULL,
    nm_bairro      VARCHAR2(50 CHAR) NOT NULL,
    id_cidade      NUMBER(8) NOT NULL,
    sg_estado   VARCHAR2(2 CHAR) NOT NULL,
    CONSTRAINT endereco_pk PRIMARY KEY ( id_endereco )
);

CREATE INDEX ix_nm_rua_endereco ON tb_endereco(nm_rua);

CREATE TABLE tb_estado (
    sg_estado VARCHAR2(2 CHAR) NOT NULL,
    nm_estado    VARCHAR2(35 CHAR) NOT NULL,
    CONSTRAINT estado_pk PRIMARY KEY ( sg_estado ),
    CONSTRAINT estado_sk UNIQUE ( nm_estado ),
    CONSTRAINT sg_estado_validation CHECK(sg_estado in 
        ('AC', 'AL', 'AP', 'AM', 'BA', 'CE', 
        'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 
        'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 
        'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 
        'SE', 'TO', 'DF')
    )
);

CREATE TABLE tb_estoque (
    id_produto NUMBER(8) NOT NULL,
    id_loja    NUMBER(5) NOT NULL,
    qt_estoque NUMBER(10, 2) NOT NULL,
    CONSTRAINT estoque_pk PRIMARY KEY ( id_loja, id_produto)
);

CREATE TABLE tb_fornece (
    id_produto    NUMBER(8) NOT NULL,
    id_fornecedor NUMBER(10) NOT NULL,
    CONSTRAINT fornece_pk PRIMARY KEY ( id_produto, id_fornecedor )
);

CREATE TABLE tb_fornecedor (
    id_fornecedor      NUMBER(10) NOT NULL,
    nm_fornecedor      VARCHAR2(50 CHAR) NOT NULL,
    nr_cnpj_fornecedor NUMBER(14) NOT NULL,
    id_endereco        NUMBER(10) NOT NULL,
    CONSTRAINT fornecedor_pk PRIMARY KEY ( id_fornecedor ),
    CONSTRAINT fornecedor_cnpj_sk UNIQUE ( nr_cnpj_fornecedor )
);

CREATE INDEX ix_nm_fornecedor ON tb_fornecedor(nm_fornecedor);

CREATE TABLE tb_loja (
    id_loja     NUMBER(5) NOT NULL,
    id_endereco NUMBER(10) NOT NULL,
    CONSTRAINT loja_pk PRIMARY KEY ( id_loja ),
    CONSTRAINT loja_sk UNIQUE ( id_endereco )
);

CREATE TABLE tb_pedido (
    id_pedido   NUMBER(12) NOT NULL,
    dt_pedido   DATE DEFAULT SYSDATE NOT NULL,
    id_cliente  NUMBER(8) NOT NULL,
    id_vendedor NUMBER(5) NOT NULL,
    id_loja     NUMBER(5) NOT NULL,
    CONSTRAINT pedido_pk PRIMARY KEY ( id_pedido )
);

CREATE INDEX ix_tb_pedidos ON tb_pedido(id_cliente, dt_pedido, id_loja);

CREATE TABLE tb_produto (
    id_produto        NUMBER(8) NOT NULL,
    nm_produto        VARCHAR2(60) NOT NULL,
    vl_unitario_base  NUMBER(8, 2) NOT NULL,
    id_unidade_medida NUMBER(3) NOT NULL,
    CONSTRAINT produto_pk PRIMARY KEY ( id_produto ),
    CONSTRAINT produto_nome_sk UNIQUE ( nm_produto )
);

CREATE INDEX ix_nm_produto ON tb_produto(nm_produto, id_unidade_medida);

CREATE TABLE tb_produtos_venda (
    id_pedido             NUMBER(12) NOT NULL,
    id_produto            NUMBER(8) NOT NULL,
    nr_quantidade         NUMBER(10, 2) NOT NULL,
    vl_preco_unitario     NUMBER(8, 2),
    id_vendedor_aprovador NUMBER(5),
    CONSTRAINT produtos_venda_pk PRIMARY KEY ( id_pedido, id_produto) 
);


CREATE TABLE tb_raca_animal (
    id_raca NUMBER(10) NOT NULL,
    nm_raca VARCHAR2(50 CHAR) NOT NULL,
    CONSTRAINT raca_animal_pk PRIMARY KEY ( id_raca ),
    CONSTRAINT raca_animal_sk UNIQUE ( nm_raca )
);

CREATE TABLE tb_reserva (
    id_reserva      NUMBER(10) NOT NULL,
    id_cliente      NUMBER(8) NOT NULL,
    id_loja         NUMBER(5) NOT NULL,
    id_produto      NUMBER(8) NOT NULL,
    dt_reserva      DATE NOT NULL,
    dt_retirada     DATE NOT NULL,
    nr_qtde_reserva NUMBER(10, 2) NOT NULL,
    CONSTRAINT reserva_pk PRIMARY KEY ( id_reserva )
);

CREATE INDEX ix_tb_reserva ON tb_reserva(id_cliente, id_produto, id_loja);

CREATE TABLE tb_tipo_animal (
    id_tipo NUMBER(3) NOT NULL,
    nm_tipo VARCHAR2(50 CHAR),
    CONSTRAINT tipo_animal_pk PRIMARY KEY ( id_tipo ),
    CONSTRAINT tipo_animal_sk UNIQUE ( nm_tipo )
);

CREATE TABLE tb_unidade_medida (
    id_unidade_medida NUMBER(3) NOT NULL,
    nm_unidade_medida VARCHAR2(25 CHAR) NOT NULL,
    CONSTRAINT unidade_medida_pk PRIMARY KEY ( id_unidade_medida ),
    CONSTRAINT um_desc_sk UNIQUE ( nm_unidade_medida )
);

CREATE TABLE tb_vendedor (
    id_vendedor    NUMBER(5) NOT NULL,
    nm_vendedor    VARCHAR2(60 CHAR) NOT NULL,
    id_coordenador NUMBER(5),
    CONSTRAINT vendedor_pk PRIMARY KEY ( id_vendedor )
);

CREATE INDEX ix_nm_vendedor ON tb_vendedor(nm_vendedor);

ALTER TABLE tb_animal
    ADD CONSTRAINT animal_cliente_fk FOREIGN KEY ( id_cliente )
        REFERENCES tb_cliente ( id_cliente );

ALTER TABLE tb_animal
    ADD CONSTRAINT animal_raca_animal_fk FOREIGN KEY ( id_raca )
        REFERENCES tb_raca_animal ( id_raca );

ALTER TABLE tb_animal
    ADD CONSTRAINT animal_tipo_animal_fk FOREIGN KEY ( id_tipo )
        REFERENCES tb_tipo_animal ( id_tipo );

ALTER TABLE tb_cidade
    ADD CONSTRAINT cidade_estado_fk FOREIGN KEY ( sg_estado )
        REFERENCES tb_estado ( sg_estado );

ALTER TABLE tb_cliente
    ADD CONSTRAINT cliente_endereco_fk FOREIGN KEY ( id_endereco )
        REFERENCES tb_endereco ( id_endereco );

ALTER TABLE tb_cliente_pf
    ADD CONSTRAINT cliente_pf_fk FOREIGN KEY ( id_cliente )
        REFERENCES tb_cliente ( id_cliente );

ALTER TABLE tb_cliente_pj
    ADD CONSTRAINT cliente_pj_fk FOREIGN KEY ( id_cliente )
        REFERENCES tb_cliente ( id_cliente );

ALTER TABLE tb_devolucao
    ADD CONSTRAINT devolucao_cliente_pf_fk FOREIGN KEY ( id_cliente )
        REFERENCES tb_cliente_pf ( id_cliente );

ALTER TABLE tb_devolucao
    ADD CONSTRAINT devolucao_produtos_venda_fk FOREIGN KEY ( id_pedido,
                                                             id_produto )
        REFERENCES tb_produtos_venda ( id_pedido,
                                    id_produto );

ALTER TABLE tb_endereco
    ADD CONSTRAINT endereco_cidade_fk FOREIGN KEY ( id_cidade,
                                                    sg_estado )
        REFERENCES tb_cidade ( id_cidade,
                            sg_estado );

ALTER TABLE tb_estoque
    ADD CONSTRAINT estoque_loja_fk FOREIGN KEY ( id_loja )
        REFERENCES tb_loja ( id_loja );

ALTER TABLE tb_estoque
    ADD CONSTRAINT estoque_produto_fk FOREIGN KEY ( id_produto )
        REFERENCES tb_produto ( id_produto );

ALTER TABLE tb_fornece
    ADD CONSTRAINT fornece_fornecedor_fk FOREIGN KEY ( id_fornecedor )
        REFERENCES tb_fornecedor ( id_fornecedor );

ALTER TABLE tb_fornece
    ADD CONSTRAINT fornece_produto_fk FOREIGN KEY ( id_produto )
        REFERENCES tb_produto ( id_produto );

ALTER TABLE tb_fornecedor
    ADD CONSTRAINT fornecedor_endereco_fk FOREIGN KEY ( id_endereco )
        REFERENCES tb_endereco ( id_endereco );

ALTER TABLE tb_loja
    ADD CONSTRAINT loja_endereco_fk FOREIGN KEY ( id_endereco )
        REFERENCES tb_endereco ( id_endereco );

ALTER TABLE tb_pedido
    ADD CONSTRAINT pedido_cliente_fk FOREIGN KEY ( id_cliente )
        REFERENCES tb_cliente ( id_cliente );

ALTER TABLE tb_pedido
    ADD CONSTRAINT pedido_loja_fk FOREIGN KEY ( id_loja )
        REFERENCES tb_loja ( id_loja );

ALTER TABLE tb_pedido
    ADD CONSTRAINT pedido_vendedor_fk FOREIGN KEY ( id_vendedor )
        REFERENCES tb_vendedor ( id_vendedor );

ALTER TABLE tb_produto
    ADD CONSTRAINT produto_unidade_medida_fk FOREIGN KEY ( id_unidade_medida )
        REFERENCES tb_unidade_medida ( id_unidade_medida );

ALTER TABLE tb_produtos_venda
    ADD CONSTRAINT produtos_venda_pedido_fk FOREIGN KEY ( id_pedido )
        REFERENCES tb_pedido ( id_pedido );

ALTER TABLE tb_produtos_venda
    ADD CONSTRAINT produtos_venda_produto_fk FOREIGN KEY ( id_produto )
        REFERENCES tb_produto ( id_produto );

ALTER TABLE tb_produtos_venda
    ADD CONSTRAINT produtos_venda_vendedor_fk FOREIGN KEY ( id_vendedor_aprovador )
        REFERENCES tb_vendedor ( id_vendedor );

ALTER TABLE tb_reserva
    ADD CONSTRAINT reserva_cliente_pj_fk FOREIGN KEY ( id_cliente )
        REFERENCES tb_cliente_pj ( id_cliente );

ALTER TABLE tb_reserva
    ADD CONSTRAINT reserva_estoque_fk FOREIGN KEY ( id_loja,
                                                    id_produto )
        REFERENCES tb_estoque ( id_loja,
                             id_produto );

ALTER TABLE tb_vendedor
    ADD CONSTRAINT vendedor_vendedor_fk FOREIGN KEY ( id_coordenador )
        REFERENCES tb_vendedor ( id_vendedor );
        
ALTER TABLE tb_cliente ADD ds_comentario VARCHAR2(500);

ALTER TABLE tb_cliente MODIFY ds_comentario VARCHAR2(800);

CREATE OR REPLACE TRIGGER arc_tipo_cliente_pf BEFORE
    INSERT OR UPDATE OF id_cliente ON tb_cliente_pf
    FOR EACH ROW
DECLARE
    d VARCHAR(2);
BEGIN
    SELECT
        a.ds_tipo_cliente
    INTO d
    FROM
        tb_cliente a
    WHERE
        a.id_cliente = :new.id_cliente;

    IF ( d IS NULL OR d <> 'PF' ) THEN
        raise_application_error(
                               -20223,
                               'FK CLIENTE_PF_FK in Table TB_CLIENTE_PF violates Arc constraint on Table CLIENTE - discriminator column id_cliente doesn''t have value ''PF'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

CREATE OR REPLACE TRIGGER arc_tipo_cliente_pj BEFORE
    INSERT OR UPDATE OF id_cliente ON tb_cliente_pj
    FOR EACH ROW
DECLARE
    d VARCHAR2(2);
BEGIN
    SELECT
        a.ds_tipo_cliente
    INTO d
    FROM
        tb_cliente a
    WHERE
        a.id_cliente = :new.id_cliente;

    IF ( d IS NULL OR d <> 'PJ' ) THEN
        raise_application_error(
                               -20223,
                               'FK CLIENTE_PJ_FK in Table TB_CLIENTE_PJ violates Arc constraint on Table CLIENTE - discriminator column id_cliente doesn''t have value ''PJ'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

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
