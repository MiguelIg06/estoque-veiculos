-- =====================================================================
-- Sistema Automotivo - Gestao de Estoque de Veiculos
-- Modelo fisico (DDL) para MySQL 8+
-- =====================================================================
DROP DATABASE IF EXISTS estoque_veiculos;
CREATE DATABASE estoque_veiculos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE estoque_veiculos;

-- ---------------------------------------------------------------------
-- Tabela: marcas
-- ---------------------------------------------------------------------
CREATE TABLE marcas (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(60)  NOT NULL,
    pais_origem  VARCHAR(60)  NULL,
    CONSTRAINT pk_marcas PRIMARY KEY (id),
    CONSTRAINT uk_marcas_nome UNIQUE (nome)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabela: modelos (N:1 com marcas)
-- ---------------------------------------------------------------------
CREATE TABLE modelos (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(80)  NOT NULL,
    marca_id  BIGINT       NOT NULL,
    CONSTRAINT pk_modelos PRIMARY KEY (id),
    CONSTRAINT uk_modelos_marca_nome UNIQUE (marca_id, nome),
    CONSTRAINT fk_modelos_marca FOREIGN KEY (marca_id)
        REFERENCES marcas (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabela: veiculos (N:1 com modelos)
-- ---------------------------------------------------------------------
CREATE TABLE veiculos (
    id             BIGINT         NOT NULL AUTO_INCREMENT,
    modelo_id      BIGINT         NOT NULL,
    ano            INT            NOT NULL,
    cor            VARCHAR(30)    NOT NULL,
    preco          DECIMAL(12,2)  NOT NULL,
    quilometragem  INT            NOT NULL DEFAULT 0,
    status         VARCHAR(15)    NOT NULL DEFAULT 'DISPONIVEL',
    data_cadastro  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_veiculos PRIMARY KEY (id),
    CONSTRAINT fk_veiculos_modelo FOREIGN KEY (modelo_id)
        REFERENCES modelos (id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT ck_veiculos_ano   CHECK (ano BETWEEN 1900 AND 2100),
    CONSTRAINT ck_veiculos_preco CHECK (preco > 0),
    CONSTRAINT ck_veiculos_km    CHECK (quilometragem >= 0),
    CONSTRAINT ck_veiculos_status CHECK (status IN ('DISPONIVEL', 'RESERVADO', 'VENDIDO'))
) ENGINE=InnoDB;

-- Indices para as colunas mais filtradas (RNF04 - desempenho)
CREATE INDEX idx_veiculos_modelo ON veiculos (modelo_id);
CREATE INDEX idx_veiculos_ano    ON veiculos (ano);
CREATE INDEX idx_veiculos_preco  ON veiculos (preco);
CREATE INDEX idx_veiculos_status ON veiculos (status);

-- ---------------------------------------------------------------------
-- Dados de exemplo
-- ---------------------------------------------------------------------
INSERT INTO marcas (nome, pais_origem) VALUES
    ('Toyota', 'Japao'),
    ('Honda', 'Japao'),
    ('Volkswagen', 'Alemanha'),
    ('Chevrolet', 'Estados Unidos'),
    ('Fiat', 'Italia');

INSERT INTO modelos (nome, marca_id) VALUES
    ('Corolla', 1), ('Hilux', 1),
    ('Civic', 2), ('HR-V', 2),
    ('Polo', 3), ('T-Cross', 3),
    ('Onix', 4), ('Tracker', 4),
    ('Argo', 5), ('Toro', 5);

INSERT INTO veiculos (modelo_id, ano, cor, preco, quilometragem, status) VALUES
    (1, 2022, 'Prata',    135900.00, 28000, 'DISPONIVEL'),
    (2, 2021, 'Branco',   245000.00, 45000, 'DISPONIVEL'),
    (3, 2020, 'Preto',    118500.00, 62000, 'RESERVADO'),
    (4, 2023, 'Cinza',    149900.00, 15000, 'DISPONIVEL'),
    (5, 2019, 'Vermelho',  72900.00, 70000, 'VENDIDO'),
    (6, 2022, 'Azul',     119900.00, 31000, 'DISPONIVEL'),
    (7, 2021, 'Branco',    69900.00, 52000, 'DISPONIVEL'),
    (8, 2023, 'Preto',    129900.00, 12000, 'RESERVADO'),
    (9, 2022, 'Vermelho',  74900.00, 24000, 'DISPONIVEL'),
    (10, 2021, 'Cinza',   124900.00, 39000, 'DISPONIVEL');
