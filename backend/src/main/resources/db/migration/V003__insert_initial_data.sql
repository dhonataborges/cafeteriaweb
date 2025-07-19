-- Ingredientes base
INSERT INTO tb_ingrediente_base (id, nome) VALUES (1, 'Café expresso');
INSERT INTO tb_ingrediente_base (id, nome) VALUES (2, 'Leite');
INSERT INTO tb_ingrediente_base (id, nome) VALUES (3, 'Chocolate');
INSERT INTO tb_ingrediente_base (id, nome) VALUES (4, 'Sorvete');
INSERT INTO tb_ingrediente_base (id, nome) VALUES (5, 'Espuma');
INSERT INTO tb_ingrediente_base (id, nome) VALUES (6, 'Água quente');

-- Ingredientes adicionais
INSERT INTO tb_ingrediente_adicional (id, nome) VALUES (1, 'Canela');
INSERT INTO tb_ingrediente_adicional (id, nome) VALUES (2, 'Chantilly');
INSERT INTO tb_ingrediente_adicional (id, nome) VALUES (3, 'Calda de chocolate');
INSERT INTO tb_ingrediente_adicional (id, nome) VALUES (4, 'Baunilha');
INSERT INTO tb_ingrediente_adicional (id, nome) VALUES (5, 'Caramelo');

-- Bebidas clássicas
INSERT INTO tb_bebida_classica (id, nome) VALUES (1, 'Café com leite');
INSERT INTO tb_bebida_classica (id, nome) VALUES (2, 'Cappuccino');
INSERT INTO tb_bebida_classica (id, nome) VALUES (3, 'Mocha');
INSERT INTO tb_bebida_classica (id, nome) VALUES (4, 'Latte');
INSERT INTO tb_bebida_classica (id, nome) VALUES (5, 'Espresso');
INSERT INTO tb_bebida_classica (id, nome) VALUES (6, 'Americano');

-- Relacionamento ingredientes base com bebidas clássicas

-- Café com leite: Café expresso (1), Leite (2)
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (1, 1);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (1, 2);

-- Cappuccino: Café expresso (1), Leite (2), Espuma (5)
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (2, 1);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (2, 2);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (2, 5);

-- Mocha: Café expresso (1), Chocolate (3), Leite (2)
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (3, 1);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (3, 3);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (3, 2);

-- Latte: Café expresso (1), Leite (2), Espuma (5)
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (4, 1);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (4, 2);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (4, 5);

-- Espresso: Café expresso (1)
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (5, 1);

-- Americano: Café expresso (1), Água quente (6)
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (6, 1);
INSERT INTO tb_bebida_classica_ingrediente_base (bebida_classica_id, ingrediente_base_id) VALUES (6, 6);


-- Relacionamento ingredientes adicionais com bebidas clássicas

-- Café com leite: Canela (1)
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (1, 1);

-- Cappuccino: Calda de chocolate (3), Baunilha (4)
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (2, 3);
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (2, 4);

-- Mocha: Chantilly (2), Calda de chocolate (3)
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (3, 2);
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (3, 3);

-- Latte: Baunilha (4), Caramelo (5)
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (4, 4);
INSERT INTO tb_bebida_classica_ingrediente_adicional (bebida_classica_id, ingrediente_adicional_id) VALUES (4, 5);
