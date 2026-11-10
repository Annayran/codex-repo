# Item-material: envio separado por ID

> Historico da primeira etapa. Substituido pelo fluxo de base unica POST/GET
> documentado em [base-unica-post-get.md](base-unica-post-get.md).

## Implementado em 09/10/2026

O job IntegracaoGenesisScheduleAction seleciona no maximo um equipamento M1
por execucao. Prioriza controles mais antigos, respeita backoff, cooldown
persistente e bloqueios de cancelamento. Nao limita o total de tentativas.

1. Registra o controle na AD_GENMOVEST.
2. Le cliente conciliado na AD_GENCLIENTE, setor na AD_GENSECTOR e modelo
   conciliado na AD_GENMODELO. Le o item por produto/serie na AD_GENITEMMAT.
3. Exige IDs positivos. Ausencia de vinculo resulta em REVISAO com mensagem
   AGUARDA_CADASTRO. O job tenta novamente apos o backoff, sem criar cadastros.
4. GET /api/v1/clientes/{id}: exige ID e documento correspondentes.
5. Item novo, sem ID local: POST /api/v1/itens-materiais/.
6. Item vinculado: GET /api/v1/itens-materiais/{id}. Exige ID, serie e modelo
   correspondentes. Se o payload ja corresponde, dispensa PUT; caso contrario,
   PUT /api/v1/itens-materiais/{id}.
7. Registra protocolo/hash no controle e atualiza a base instalada. Apenas apos
   essas gravacoes marca ENVIADO. A persistencia depende do commit Sankhya.

O fluxo de equipamentos nao pesquisa codigo externo, nao cadastra nem altera
clientes, setores, materiais, fabricantes ou modelos. Os IDs destes cadastros
continuam necessarios; material/fabricante sao dependencias do modelo, nao do
POST de equipamento.

## Falhas e protecoes

- GET 404, 429, 500 ou identidade divergente nunca autorizam POST alternativo.
- POST sem confirmacao, conflito, 5xx ou retorno sem ID: ENVIO_INCERTO.
  Nao entra na selecao automatica de envio. Exige reconciliacao do resultado.
- Erro local apos retorno remoto: ENVIO_INCERTO, preservando protocolo quando
  a gravacao intermediaria foi possivel.
- ENVIADO e CANCELADO continuam protegidos. Controle com protocolo e HTTP 2xx
  tambem fica fora da selecao, mesmo que seu status tenha sido alterado.
- No cancelamento, ENVIO_INCERTO e preservado na EXCFILA em REVISAO; nao
  autoriza DELETE sem identidade e origem confirmadas.
- Diagnostico do dashboard inclui ENVIO_INCERTO.

## Responsabilidades

| Processo | Responsabilidade |
| --- | --- |
| Base instalada / conciliacao existente | Manter IDs locais corretos de cliente, setor, material, fabricante e modelo |
| IntegracaoGenesisScheduleAction | Validar cliente por ID e enviar um equipamento |
| Eventos de cancelamento | Desvincular nota e preservar demanda na EXCFILA |
| InativacaoGenesisScheduleAction | Inativar itens cuja identidade/origem estejam confirmadas |
| EstoqueGenesisScheduleAction | Catalogo de pecas/servicos e precos; independente do equipamento |

Nao foi criado um novo job automatico de conciliacao nesta entrega. O metodo
legado de sincronizacao de cadastros continua disponivel para compatibilidade,
mas nao e chamado pelo runner de equipamentos.

## Homologacao e limites

Validar: novo item (GET cliente + POST item), item existente alinhado (dois GETs),
item alterado (dois GETs + PUT), cliente ausente/divergente, modelo duplicado,
cadastro ausente, POST incerto, 429 e cancelamento de envio incerto.
Conferir protocolo, origem, status, base instalada e EXCFILA apos commit real.
Um equipamento por execucao nao significa uma unica requisicao HTTP.
O limite global por minuto nao foi presumido; os outros jobs tambem usam a API.

Continua pendente uma estrategia transacional duravel antes do POST: se o
processo cair depois de criar remotamente e antes do commit local, o controle
pode ser revertido. Nao ha garantia de exactly-once ou transacao distribuida.
Por isso este pacote e candidato para homologacao, nao liberacao automatica.

## Deploy e rollback

Sem CREATE TABLE, DDL ou migracao de campos. STATUS_INTEGRACAO deve comportar
ENVIO_INCERTO (13 caracteres). Entregar JAR e JSP ZIP separados.
Antes de substituir, preservar os artefatos vigentes e pausar os jobs.
Rollback: restaurar JAR/JSP anteriores com jobs pausados; manter controles
ENVIO_INCERTO e filas para reconciliacao. Nao converter esses controles para
PENDENTE/ERRO nem apaga-los para forcar POST. Nenhum deploy remoto realizado.
