-- Executar antes e depois do deploy, conectado ao schema dono da trigger.
SELECT o.object_name, o.status AS compilacao, t.status AS estado_trigger
FROM user_objects o
JOIN user_triggers t ON t.trigger_name = o.object_name
WHERE o.object_type = 'TRIGGER'
  AND o.object_name = 'TRG_AVISO_CHEGOU_SEQ';

SELECT line, position, text
FROM user_errors
WHERE type = 'TRIGGER'
  AND name = 'TRG_AVISO_CHEGOU_SEQ'
ORDER BY sequence;

-- Confirmar uma nota de teste da TOP 1400 ou 1401.
-- Conferir o novo NUAVISO pelo horário e validar IMPORTANCIA = 0 e CODUSU = 0.
SELECT NUAVISO, TITULO, IDENTIFICADOR, IMPORTANCIA, TIPO,
       CODUSU, CODGRUPO, CODUSUREMETENTE, DHCRIACAO
FROM TSIAVI
WHERE IDENTIFICADOR = 'PERSONALIZADO'
  AND TIPO = 'P'
ORDER BY DHCRIACAO DESC NULLS LAST, NUAVISO DESC
FETCH FIRST 10 ROWS ONLY;
