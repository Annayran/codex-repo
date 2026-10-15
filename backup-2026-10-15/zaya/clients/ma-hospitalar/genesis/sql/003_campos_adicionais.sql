-- Campos adicionais conforme decisao de modelagem atual.
-- Decisao: controlar garantia e status por serie/equipamento em TGFSER.
-- Validar no ambiente Sankhya antes de executar.

ALTER TABLE TGFSER ADD AD_GARANTIA NUMBER(5);
ALTER TABLE TGFSER ADD AD_STATUSEST VARCHAR2(1) DEFAULT 'O';

-- Valores previstos para AD_STATUSEST:
-- O = Operacional
-- A = Alienado
-- I = Inoperante
-- V = Validacao
-- D = Desativado
-- R = Restricoes

-- Observacao: o documento 2764 descrevia inicialmente estes campos em TGFITE,
-- mas a decisao funcional atual e controlar por serie na TGFSER.