-- Somente leitura: valida os nomes das colunas, sem retornar dados de parceiros.
-- Tipo, tamanho e mapeamento DWF devem ser conferidos no Dicionario de Dados.
SELECT AD_EMAILRECEITA, AD_TELRECEITA
FROM TGFPAR
WHERE 1 = 0;
