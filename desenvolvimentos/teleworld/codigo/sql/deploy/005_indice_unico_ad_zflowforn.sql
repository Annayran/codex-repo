-- Executar somente apos validar que AD_ZFLOWFORN foi criada pelo importador de metadados.
-- Impede duas filas para o mesmo CPF/CNPJ normalizado.
CREATE UNIQUE INDEX UK_ZFLOWFORN_CNPJCPF
    ON AD_ZFLOWFORN (CNPJCPF);
