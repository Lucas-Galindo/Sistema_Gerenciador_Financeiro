-- PostgreSQL Script convertido a partir do MySQL Workbench
-- Original: gerenciadorF - v2 (MySQL)
-- Convertido para: PostgreSQL 16+

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS mydb CASCADE;
CREATE SCHEMA IF NOT EXISTS mydb;
SET search_path TO mydb;

-- -----------------------------------------------------
-- Table: Estados
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Estados" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Estados" (
  "ESTADOS_ID"   INTEGER      NOT NULL,
  "ESTADOS_NOME" VARCHAR(45)  NOT NULL,
  PRIMARY KEY ("ESTADOS_ID")
);


-- -----------------------------------------------------
-- Table: Cidades
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Cidades" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Cidades" (
  "CIDADES_ID"   INTEGER      NOT NULL,
  "CIDADES_NOME" VARCHAR(45)  NOT NULL,
  PRIMARY KEY ("CIDADES_ID")
);


-- -----------------------------------------------------
-- Table: Localizacao
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Localizacao" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Localizacao" (
  "LOCAL_ID"             INTEGER     NOT NULL,
  "LOCAL_CEP"            VARCHAR(45) NULL,
  "Estados_ESTADOS_ID"   INTEGER     NOT NULL,
  "Cidades_CIDADES_ID"   INTEGER     NOT NULL,
  PRIMARY KEY ("LOCAL_ID"),
  CONSTRAINT fk_Localizacao_Estados1
    FOREIGN KEY ("Estados_ESTADOS_ID")
    REFERENCES mydb."Estados" ("ESTADOS_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Localizacao_Cidades1
    FOREIGN KEY ("Cidades_CIDADES_ID")
    REFERENCES mydb."Cidades" ("CIDADES_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_Localizacao_Estados1_idx ON mydb."Localizacao" ("Estados_ESTADOS_ID");
CREATE INDEX fk_Localizacao_Cidades1_idx ON mydb."Localizacao" ("Cidades_CIDADES_ID");


-- -----------------------------------------------------
-- Table: Usuario
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Usuario" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Usuario" (
  "USU_ID"               INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY,
  "USU_NOME"             VARCHAR(45)  NOT NULL,
  "USU_CPF"              VARCHAR(45)  NOT NULL,
  "USU_EMAIL"            VARCHAR(45)  NOT NULL,
  "USU_SENHA"            VARCHAR(45)  NOT NULL,
  "USU_TEL"              VARCHAR(45)  NOT NULL,
  "Localizacao_LOCAL_ID" INTEGER      NOT NULL,
  PRIMARY KEY ("USU_ID"),
  CONSTRAINT fk_Usuario_Localizacao1
    FOREIGN KEY ("Localizacao_LOCAL_ID")
    REFERENCES mydb."Localizacao" ("LOCAL_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE UNIQUE INDEX USU_CPF_UNIQUE   ON mydb."Usuario" ("USU_CPF");
CREATE UNIQUE INDEX USU_EMAIL_UNIQUE ON mydb."Usuario" ("USU_EMAIL");
CREATE UNIQUE INDEX USU_TEL_UNIQUE   ON mydb."Usuario" ("USU_TEL");
CREATE INDEX fk_Usuario_Localizacao1_idx ON mydb."Usuario" ("Localizacao_LOCAL_ID");


-- -----------------------------------------------------
-- Table: Categoria
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Categoria" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Categoria" (
  "CAT_ID"       INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY,
  "CAT_TIPO"     VARCHAR(45)  NOT NULL,
  "CAT_NOME"     VARCHAR(45)  NOT NULL,
  "CAT_DESCRICAO" VARCHAR(45) NULL,
  PRIMARY KEY ("CAT_ID")
);

-- Reservado para nomear a categoria (ex: "alimentação")
COMMENT ON COLUMN mydb."Categoria"."CAT_NOME" IS 'Atributo reservado para nomear aquela categoria, como "alimentação"';


-- -----------------------------------------------------
-- Table: TIPO_TRANSACAO
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."TIPO_TRANSACAO" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."TIPO_TRANSACAO" (
  "TIPO_TRANSACAO_ID"        INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY,
  "TIPO_TRANSACAO_NOME"      VARCHAR(45)  NOT NULL,
  "TIPO_TRANSACAO_DESCRICAO" VARCHAR(45)  NULL,
  PRIMARY KEY ("TIPO_TRANSACAO_ID")
);

CREATE UNIQUE INDEX TpTrans_Nome_UNIQUE ON mydb."TIPO_TRANSACAO" ("TIPO_TRANSACAO_NOME");


-- -----------------------------------------------------
-- Table: BANCOS
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."BANCOS" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."BANCOS" (
  "BANCOS_ID"   INTEGER     NOT NULL,
  "BANCOS_NOME" VARCHAR(45) NOT NULL,
  PRIMARY KEY ("BANCOS_ID")
);

COMMENT ON COLUMN mydb."BANCOS"."BANCOS_NOME" IS 'Enumera os bancos (Bradesco, Itaú e demais)';


-- -----------------------------------------------------
-- Table: Tipo_Contas
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Tipo_Contas" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Tipo_Contas" (
  "TIPOCONTAS_ID"   INTEGER     NOT NULL,
  "TIPOCONTAS_NOME" VARCHAR(45) NOT NULL,
  PRIMARY KEY ("TIPOCONTAS_ID")
);


-- -----------------------------------------------------
-- Table: Contas
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Contas" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Contas" (
  "CONTAS_ID"                  INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY,
  "CONTAS_NOME"                VARCHAR(45)  NOT NULL,
  "CONTAS_SALDO_INICIAL"       DECIMAL      NOT NULL,
  "Usuario_USU_ID"             INTEGER      NOT NULL,
  "BANCOS_BANCOS_ID"           INTEGER      NOT NULL,
  "Tipo_Contas_TIPOCONTAS_ID"  INTEGER      NOT NULL,
  PRIMARY KEY ("CONTAS_ID", "Usuario_USU_ID", "BANCOS_BANCOS_ID"),
  CONSTRAINT fk_Contas_Usuario1
    FOREIGN KEY ("Usuario_USU_ID")
    REFERENCES mydb."Usuario" ("USU_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Contas_BANCOS1
    FOREIGN KEY ("BANCOS_BANCOS_ID")
    REFERENCES mydb."BANCOS" ("BANCOS_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Contas_Tipo_Contas1
    FOREIGN KEY ("Tipo_Contas_TIPOCONTAS_ID")
    REFERENCES mydb."Tipo_Contas" ("TIPOCONTAS_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_Contas_Usuario1_idx    ON mydb."Contas" ("Usuario_USU_ID");
CREATE INDEX fk_Contas_BANCOS1_idx     ON mydb."Contas" ("BANCOS_BANCOS_ID");
CREATE INDEX fk_Contas_Tipo_Contas1_idx ON mydb."Contas" ("Tipo_Contas_TIPOCONTAS_ID");


-- -----------------------------------------------------
-- Table: Transacao
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Transacao" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Transacao" (
  "TRANS_ID"                         INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY,
  "TRANS_VALOR"                      DECIMAL      NOT NULL,
  "TRANS_DESCRICAO"                  VARCHAR(45)  NULL,
  "TRANS_DATAMOVIMENTACAO"           DATE         NOT NULL,  -- Convertido de VARCHAR para DATE
  "Categoria_CAT_ID"                 INTEGER      NOT NULL,
  "Usuario_USU_ID"                   INTEGER      NOT NULL,
  "TIPO_TRANSACAO_TIPO_TRANSACAO_ID" INTEGER      NOT NULL,
  "Contas_CONTAS_ID"                 INTEGER      NOT NULL,
  PRIMARY KEY (
    "TRANS_ID",
    "Categoria_CAT_ID",
    "Usuario_USU_ID",
    "TIPO_TRANSACAO_TIPO_TRANSACAO_ID",
    "Contas_CONTAS_ID"
  ),
  CONSTRAINT fk_Transacao_Categoria1
    FOREIGN KEY ("Categoria_CAT_ID")
    REFERENCES mydb."Categoria" ("CAT_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Transacao_Usuario1
    FOREIGN KEY ("Usuario_USU_ID")
    REFERENCES mydb."Usuario" ("USU_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Transacao_TIPO_TRANSACAO1
    FOREIGN KEY ("TIPO_TRANSACAO_TIPO_TRANSACAO_ID")
    REFERENCES mydb."TIPO_TRANSACAO" ("TIPO_TRANSACAO_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Transacao_Contas1
    FOREIGN KEY ("Contas_CONTAS_ID")
    REFERENCES mydb."Contas" ("CONTAS_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_Transacao_Categoria1_idx      ON mydb."Transacao" ("Categoria_CAT_ID");
CREATE INDEX fk_Transacao_Usuario1_idx        ON mydb."Transacao" ("Usuario_USU_ID");
CREATE INDEX fk_Transacao_TIPO_TRANSACAO1_idx ON mydb."Transacao" ("TIPO_TRANSACAO_TIPO_TRANSACAO_ID");
CREATE INDEX fk_Transacao_Contas1_idx         ON mydb."Transacao" ("Contas_CONTAS_ID");


-- -----------------------------------------------------
-- Table: Metas
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Metas" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Metas" (
  "METAS_ID"            INTEGER      NOT NULL,
  "METAS_NOME"         VARCHAR(45)  NOT NULL,  -- Nome mantido com espaço conforme original
  "METAS_VALOR_OBJETIVO" DECIMAL     NULL,
  "METAS_VALOR_ATUAL"   DECIMAL      NOT NULL,  -- Convertido de VARCHAR para DECIMAL
  "METAS_DATA_LIMITE"   DATE         NULL,       -- Convertido de VARCHAR para DATE
  "Usuario_USU_ID"      INTEGER      NOT NULL,
  PRIMARY KEY ("METAS_ID", "Usuario_USU_ID"),
  CONSTRAINT fk_Metas_Usuario1
    FOREIGN KEY ("Usuario_USU_ID")
    REFERENCES mydb."Usuario" ("USU_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_Metas_Usuario1_idx ON mydb."Metas" ("Usuario_USU_ID");


-- -----------------------------------------------------
-- Table: Cartoes
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb."Cartoes" CASCADE;

CREATE TABLE IF NOT EXISTS mydb."Cartoes" (
  "CARTOES_ID"         INTEGER      NOT NULL GENERATED ALWAYS AS IDENTITY,
  "CARTOES_NOME"       VARCHAR(45)  NOT NULL,
  "CARTOES_LIMITE"     DECIMAL      NOT NULL,
  "CARTOES_FECHAMENTO" DATE         NOT NULL,
  "CARTOES_VENCIMENTO" DATE         NULL,
  "Usuario_USU_ID"     INTEGER      NOT NULL,
  PRIMARY KEY ("CARTOES_ID", "Usuario_USU_ID"),
  CONSTRAINT fk_Cartoes_Usuario1
    FOREIGN KEY ("Usuario_USU_ID")
    REFERENCES mydb."Usuario" ("USU_ID")
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE INDEX fk_Cartoes_Usuario1_idx ON mydb."Cartoes" ("Usuario_USU_ID");
