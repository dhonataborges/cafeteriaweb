-- 1️⃣ Desabilitar a verificação de FOREIGN KEYs
ALTER TABLE tb_produto DISABLE TRIGGER ALL;
ALTER TABLE tb_estoque DISABLE TRIGGER ALL;
ALTER TABLE tb_venda_produto DISABLE TRIGGER ALL;
ALTER TABLE tb_foto_produto DISABLE TRIGGER ALL;

-- 2️⃣ Excluir os dados de todas as tabelas
DELETE FROM tb_produto;
DELETE FROM tb_estoque;
DELETE FROM tb_venda_produto;
DELETE FROM tb_foto_produto;

-- 3️⃣ Habilitar a verificação de FOREIGN KEYs novamente
ALTER TABLE tb_produto ENABLE TRIGGER ALL;
ALTER TABLE tb_estoque ENABLE TRIGGER ALL;
ALTER TABLE tb_venda_produto ENABLE TRIGGER ALL;
ALTER TABLE tb_foto_produto ENABLE TRIGGER ALL;

-- 4️⃣ Reiniciar o contador de IDs das tabelas (sequências)
-- Em PostgreSQL, o auto_increment é tratado com SEQUENCES
-- O comando abaixo reinicia a sequência para cada tabela
DO $$
BEGIN
    -- Reinicia a sequência de tb_produto
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tb_produto' AND column_name = 'id') THEN
        EXECUTE 'ALTER SEQUENCE tb_produto_id_seq RESTART WITH 1;';
    END IF;

    -- Reinicia a sequência de tb_estoque
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tb_estoque' AND column_name = 'id') THEN
        EXECUTE 'ALTER SEQUENCE tb_estoque_id_seq RESTART WITH 1;';
    END IF;

    -- Reinicia a sequência de tb_venda_produto
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'tb_venda_produto' AND column_name = 'id') THEN
        EXECUTE 'ALTER SEQUENCE tb_venda_produto_id_seq RESTART WITH 1;';
    END IF;
END $$;
