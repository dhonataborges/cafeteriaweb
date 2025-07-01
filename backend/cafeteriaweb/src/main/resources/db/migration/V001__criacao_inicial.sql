-- Tabela de bebidas personalizadas
CREATE TABLE tb_bebida (
    id SERIAL PRIMARY KEY,
    bebida_criada VARCHAR(150) NOT NULL
);

-- Tabela de ingredientes base
CREATE TABLE tb_ingrediente_base (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela de ingredientes adicionais
CREATE TABLE tb_ingrediente_adicional (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela de bebidas clássicas
CREATE TABLE tb_bebida_classica (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

-- Tabela intermediária: bebida x ingrediente base
CREATE TABLE tb_bebida_ingrediente_base (
    bebida_id INTEGER NOT NULL,
    ingrediente_base_id INTEGER NOT NULL,
    PRIMARY KEY (bebida_id, ingrediente_base_id),
    FOREIGN KEY (bebida_id) REFERENCES tb_bebida(id) ON DELETE CASCADE,
    FOREIGN KEY (ingrediente_base_id) REFERENCES tb_ingrediente_base(id) ON DELETE CASCADE
);

-- Tabela intermediária: bebida x ingrediente adicional
CREATE TABLE tb_bebida_ingrediente_adicional (
    bebida_id INTEGER NOT NULL,
    ingrediente_adicional_id INTEGER NOT NULL,
    PRIMARY KEY (bebida_id, ingrediente_adicional_id),
    FOREIGN KEY (bebida_id) REFERENCES tb_bebida(id) ON DELETE CASCADE,
    FOREIGN KEY (ingrediente_adicional_id) REFERENCES tb_ingrediente_adicional(id) ON DELETE CASCADE
);

-- Tabela intermediária: bebida clássica x ingrediente base
CREATE TABLE tb_bebida_classica_ingrediente_base (
    bebida_classica_id INTEGER NOT NULL,
    ingrediente_base_id INTEGER NOT NULL,
    PRIMARY KEY (bebida_classica_id, ingrediente_base_id),
    FOREIGN KEY (bebida_classica_id) REFERENCES tb_bebida_classica(id) ON DELETE CASCADE,
    FOREIGN KEY (ingrediente_base_id) REFERENCES tb_ingrediente_base(id) ON DELETE CASCADE
);


-- Tabela intermediária: bebida clássica x ingrediente adicionais
CREATE TABLE tb_bebida_classica_ingrediente_adicional (
    bebida_classica_id INTEGER NOT NULL,
    ingrediente_adicional_id INTEGER NOT NULL,
    PRIMARY KEY (bebida_classica_id, ingrediente_adicional_id),
    FOREIGN KEY (bebida_classica_id) REFERENCES tb_bebida_classica(id) ON DELETE CASCADE,
    FOREIGN KEY (ingrediente_adicional_id) REFERENCES tb_ingrediente_adicional(id) ON DELETE CASCADE
);