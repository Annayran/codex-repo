-- Bindar CODEMP com a NOVA empresa e ORDEMCARGA com a ordem do pedido.
-- Ajustar sintaxe de parametros ao cliente SQL utilizado.
SELECT CODEMP, ORDEMCARGA, DTALTER
FROM TGFORD
WHERE CODEMP = :CODEMP AND ORDEMCARGA = :ORDEMCARGA;

-- Executar somente depois da criacao do campo pelo configurador Sankhya.
SELECT CODEMP, ORDEMCARGA, DTALTER, AD_ENTRACK
FROM TGFORD
WHERE CODEMP = :CODEMP AND ORDEMCARGA = :ORDEMCARGA;
