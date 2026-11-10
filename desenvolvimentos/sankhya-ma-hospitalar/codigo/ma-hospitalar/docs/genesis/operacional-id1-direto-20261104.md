# Operacional com ID 1 direto - 04/11/2026

Regra explicitamente autorizada pelo usuario apos confirmar no cadastro:
IDGENESIS 1, Nome Operacional. Substitui para esse caso a exigencia de associar
codigo O e STATUSCONC=CONCILIADO nos Dominios.

Factory aplica situacao Operacional e idSituacaoEquipamento=1 quando origem
vazia/branca ou codigo O (normalizado). Status do cadastro continua Ativo.
Somente o candidato em memoria e alterado; nao grava TGFSER nem Dominios.
Outras situacoes preenchidas continuam preservadas e sujeitas a correspondencia
existente, sem transformar Inoperante/Desativado em Operacional.

REVISAO ja participa da selecao atual; nenhuma limpeza ou reset em massa.
Apos instalar o JAR, casos elegiveis bloqueados pela falta de conciliacao de O
podem retomar respeitando lote, intervalo e protecoes. Vinculos ambiguos,
cancelamentos, envio incerto, reserva pendente e movimentos fora da origem
nao sao liberados por essa regra. Sem envio remoto executado pelo agente.

Instalar somente JAR do modulo existente, com backup e homologacao controlada.
Nao substituir dashboard nem repetir os pacotes anteriores para essa melhoria.
Confirmar por GET real que payload/ID/situacao foram aceitos e registrar aceite
com Luiz. Rollback: restaurar biblioteca anterior, sem apagar controles ou
desfazer dados remotos automaticamente. Testes locais nao equivalem a API real.
