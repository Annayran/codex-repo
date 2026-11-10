# Entrega candidata: reativacao e diagnostico

Data: 03/11/2026. Fonte: projeto MA Hospitalar / Genesis.

## Alteracoes

- Reativacao de equipamento conhecido por GET/PUT/GET no mesmo item-material.
- Situacao ausente ou nao conciliada bloqueia o envio, sem valor presumido.
- Cliente, setor, modelo ou serie divergentes bloqueiam sem transferencia.
- Reserva duravel antes da tentativa impede que falha/timeout seja ignorada
  na proxima rodada, mesmo com cadastro remoto ativo.
- Confirmacao revalida a reserva sob lock, impedindo que outra execucao apague
  uma pendencia. Reserva e retirada apenas apos GET e preservacao confirmados.
- Mensagens do painel orientam usuario a conciliar a causa.

## Instalacao controlada

Este pacote e candidato de homologacao, nao liberacao automatica em producao.
Salvar copia do modulo e do painel atualmente instalados. Substituir a biblioteca
do mesmo modulo pelo JAR fornecido; nao criar um segundo job concorrente.
Importar o ZIP React no dashboard correspondente. O painel e somente leitura.
Validar compilacao JSP, permissoes e consultas no Sankhya antes da liberacao.
Nao substituir a view de origem ou executar DML junto desta entrega.

Configurar empresas/locais no parametro 1 e conciliar as situacoes antes de
habilitar processamento. Com situacoes pendentes, o bloqueio e intencional.
Testar equipamento inativo com vinculos corretos, vinculo divergente, situacao
vazia, timeout e retomada; conferir a API real e obter validacao do Luiz.
Se houver REATIVACAO_PENDENTE, manter em revisao, conferir GET e historico do
mesmo ID e solicitar conciliacao tecnica. Nao remover a reserva por tentativa,
nem usar novo POST para contornar o bloqueio. Nao ha desbloqueio automatico.

Rollback: restaurar JAR e ZIP anteriores e suspender processamento para analise;
nao apagar controles nem desfazer alteracoes remotas automaticamente.

## Limpeza nao executada

Usuario autorizou avaliar todo o periodo do desenvolvimento, excluindo somente
duplicatas comprovadas e sem dependencias. Os scripts entregues sao SELECTs:
nao foi preparado DELETE generico. Movimentos fora da consulta nao sao lixo
automaticamente. Confirmacoes, reservas, historico API e vinculos distintos
precisam ser preservados. Exportar candidatos e dependencias antes de preparar
um lote de exclusao por IDs, com backup, validacao e rollback definidos.

## Validacao e limites

213 testes Java locais aprovados no build final homologacao-20261103-143713,
incluindo a barreira de concorrencia. Diagnostico JS e painel validados localmente.
Oito testes HTTP loopback excluidos por limitacao Java/Windows. Sem teste real
de API, compilacao JSP/Oracle ou transacao Jape. Cancelamento sem item local
permanece em revisao protegida. Nenhuma exclusao ou implantacao foi executada.
