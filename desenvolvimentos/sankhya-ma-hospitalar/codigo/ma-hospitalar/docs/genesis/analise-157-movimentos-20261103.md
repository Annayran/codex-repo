# Analise dos 157 movimentos de 03/11/2026

## Complemento: 23 fora da selecao - XLS3

Resultado_da_Query (3).xls retornou23linhas, uma por controle esperado.
Em todos:nota existe,item existe,produto atual igual ao original,TOP1101
configurada,tipo M1 ouM3. Em todos:SERIE_MESMO_ITEM=NAO,SERIE_NA_NOTA=NAO,
SERIE_COM_OUTRA_CHAVE=NAO. A consulta compara serie/produto por igualdade
normalizada(UPPER/TRIM), nao pesquisa variantes de pontuacao nem outros produtos.
Conclusao: ausencia da serie original para aquele produto na nota explica
inelegibilidade atual. Nao comprova autor,data,motivo de alteracao ou defeito
de integracao; faltam series atuais e auditoria do movimento.
Nao rotular estes23 como erro atual de API. Mensagens sao historicas:
2timeouts,2cliente duplicado,1conflito de material,18sem mensagem.
Exemplos:controle550/nota118387/produto11576/serie204A-268600277;
576/120005/12151/25-066-S6;1124/122193/5035/8543.
Tratamento recomendado:exibir inelegibilidade/serie nao localizada separada
da fila ativa,com evidencia e historico,sem marcarENVIADO/CANCELADO nem liberar
novoPOST. Confirmar se houve correcao da serie com responsavel pelo movimento.
Serie substituta nao pode ser escolhida automaticamente pelo produto/nota.

## Complemento: consulta de retomada recebida

Resultado_da_Query (2).xls confirma os157:23 fora da selecao base
(18PENDENTE,2REPROCESSAR,3REVISAO);134 presentes(44PENDENTE,90REVISAO).
Nenhum cancelado, nenhum na fila de cancelamento, nenhum bloqueado por
protocolo+HTTP. Dos90REVISAO presentes,23 em backoff e67 fora da espera
calculada na captura. Backoff e temporario, nao resolve causa cadastral.
Todos44PENDENTE presentes foram atualizados em03/11: ha atividade da rotina,
sem prova de paralizacao geral. Nao equivale a conclusao das etapas.
Os37 sem mensagem do arquivo anterior nao podem ser classificados todos como
parados: entre as capturas houve atualizacoes; comparar por controle.
O arquivo anterior continua sendo retrato proprio, nao deve ser sobrescrito.

23fora nao sao retomados pelo seletor que parte da view; o painel ainda os
conta pois parte dos controles. Causa da saida ainda nao determinada: verificar
nota,item,serie,empresa,TOP,tipo e mudanca de chave. Nao marcar ENVIADO,
CANCELADO nem excluir controles por esse motivo.
Nova consulta: sql/genesis/diagnostico-fora-selecao-20261103.sql, somente leitura,
nao executada pelo agente. Inclui serie em outro item/chave para diferenciar
mudanca de identidade operacional de ausencia real.

Fontes: Resultado_da_Query.xls e Resultado_da_Query (1).xls recebidos do usuario,
exportados em 03/11/2026 11:35. Leitura local de BIFF via SheetJS existente;
sem alteracao das planilhas, sem API, DML ou consulta direta a producao.
Resumo de 27 grupos conciliado com detalhe de 157 movimentos:
93 REVISAO, 62 PENDENTE, 2 REPROCESSAR.

## 93 em revisao

| Motivo registrado | Quantidade |
| --- | ---: |
| Envio incerto: item-material | 29 |
| Envio incerto: cliente | 1 |
| Codigo do modelo divergente do HSP_IDEXTERNO | 17 |
| Identidade remota divergente: material | 15 |
| Material local ambiguo (registros 233/309, ambos ID remoto 233) | 11 |
| Item-material local ambiguo | 8 |
| POST cliente rejeitado por documento existente (7 CNPJ, 1 CPF) | 8 |
| Identidade remota divergente: cliente | 2 |
| Identidade remota divergente: modelo | 1 |
| Vinculo local pertence a outro cadastro | 1 |

17 modelos divergentes afetam produtos 11947,11946,13599,13089;
nao sao necessariamente os mesmos modelos investigados em outubro.
Bloqueio nao comprova defeito da rotina PUT; verificar controles da migracao.
Os 8 itens ambiguos correspondem a 8 casos produto/serie distintos.

## 64 pendentes/reprocessar

| Mensagem registrada | Quantidade |
| --- | ---: |
| Sem mensagem | 37 |
| Identidade Genesis divergente do ID consultado | 19 |
| Envio incerto | 1 |
| Timeout material HTTP408 | 3 |
| Material HTTP503 (2 Invalid Http response, 1 Bogus chunk size) | 3 |
| Material GET_ID HTTP200 com lista vazia | 1 |

37 sem mensagem: atualizacoes de 16 a 28/10; nao classificar como espera normal
em 03/11 sem verificar elegibilidade, cancelamento e agenda. Exemplos controles
550/nota118387,576/nota120005,1185/nota123171.
Dois REPROCESSAR (1158 e1124) tem proxima tentativa em26/10; nao e espera futura
na captura de03/11. Selecao efetiva deve ser verificada.

## Achado de rastreabilidade no codigo local

MovimentoGenesisRepository.atualizarPendente altera estado/data sem limpar
HTTP/MENSAGEM_ERRO; etapa altera somente ETAPA_ATUAL/ENTIDADE_BLOQ.
CadastroGenesisPreparationService grava AGUARDANDO_PROXIMA_ETAPA apos persistir
uma dependencia e encerra a rodada. Assim, pendente pode continuar exibindo
mensagem de falha anterior apos avancar. As 27 mensagens nao provam 27 falhas
atuais; confirmar historico antes de atribuir a causa atual. Melhoria recomendada:
separar ultimo erro historico do bloqueio corrente, com data/etapa de cada evento,
sem apagar evidencias.

Nao ha ORA/coluna invalida nos motivos apresentados. Isso nao inocenta codigo:
ha lacunas de reconciliacao, rastreabilidade e retomada a investigar. 8 POSTs de
cliente foram efetivamente rejeitados por documento ja existente; consulta por
codigo externo nao foi suficiente para resolver a identidade desses casos.
As mensagens de envio incerto registram HTTP200 sem cadastro encontrado: resposta
HTTP nao equivale a entidade localizada nem autoriza novo POST.

## Proxima evidencia

Executar sql/genesis/diagnostico-retomada-20261103.sql (SELECT apenas), depois
consultar controles de cadastros/migracao e historico HTTP dos grupos priorizados.
Triagem nao replica integralmente seletor Java (tipo/deduplicacao/lote) nem agenda.
Prioridade:37 sem mensagem e dois retries antigos;17 codigos de modelo;8 clientes
duplicados;11 referencias material233/309; identidade remota e envios incertos.
Sem reiniciar inclusoes ou limpar controles para reduzir o indicador.
