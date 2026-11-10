# Complemento - Exclusao, Desfazer Particionamento e Rastreabilidade

## Contexto

Durante a validacao do particionamento, foi levantado um ponto nao mapeado originalmente no PDD/IntPDD:

- O que acontece se um usuario excluir apenas uma das OEs geradas pelo particionamento?
- Como manter rastreabilidade entre o pedido original e todas as OEs geradas?
- A exclusao/desfazimento deve ser via rotina nativa do Sankhya, evento de exclusao ou botao de acao?

## Risco Identificado

Exemplo:

- Pedido original gera 15 OEs particionadas.
- Usuario exclui manualmente 1 OE.
- O relacionamento entre pedido original e particionamentos fica inconsistente.
- O pedido original pode continuar marcado como `AD_PARTICIONADO = 'S'`, mas parte das OEs nao existe mais.

Esse comportamento pode quebrar auditoria, conferencia operacional e eventual necessidade de reprocessamento.

## Decisao Recomendada

Criar uma rotina controlada para desfazer/cancelar particionamento via botao de acao e bloquear a exclusao nativa dos pedidos gerados pela rotina.

Fluxo recomendado:

1. Usuario nao deve excluir manualmente uma OE particionada.
2. Se precisar desfazer, deve usar um botao de acao especifico, por exemplo:
   - `Desfazer Particionamento`
   - `Cancelar Particionamento`
3. A rotina deve localizar todas as OEs vinculadas ao pedido original.
4. Validar se as OEs ainda podem ser canceladas/excluidas.
5. Cancelar/excluir todas as OEs do particionamento em lote.
6. Voltar `TGFCAB.AD_PARTICIONADO` do pedido original para `N`.
7. Registrar log/historico da operacao.

## Bloqueio de Exclusao Nativa

A exclusao nativa deve ser bloqueada para OEs geradas pelo particionamento.

Mensagem sugerida:

```text
Este pedido de OE foi gerado por particionamento. Utilize a acao "Desfazer Particionamento" para cancelar o processo completo.
```

Formas possiveis:

- Evento Sankhya antes de exclusao de `TGFCAB`.
- Trigger de banco antes de delete em `TGFCAB`.
- Regra personalizada, se a base utilizar esse mecanismo.

O bloqueio deve verificar se a OE possui vinculo de particionamento.

## Rastreabilidade

Existem duas alternativas principais.

### Alternativa 1 - TGFVAR

Usar a `TGFVAR` se o processo for tratado como faturamento/variacao nativa entre pedido origem e OEs.

Vantagem:

- Usa rastreabilidade nativa do Sankhya.
- Evita criar estrutura paralela.
- Facilita consultas padrao de origem/destino, se o processo for reconhecido pelo ERP.

Ponto de atencao:

- A implementacao atual cria OEs via JAPE, como copia controlada.
- A `TGFVAR` pode nao ser populada automaticamente se nao for usado o fluxo nativo de faturamento/geracao.
- Precisa validar se a API/servico de faturamento usado pela base permite gerar as OEs e gravar a `TGFVAR` corretamente.

### Alternativa 2 - Tela/Tabela Adicional

Criar estrutura propria de relacionamento somente para o particionamento.

Sugestao de tabela:

```text
AD_PARTICIONAMENTO
```

Campos sugeridos:

```text
IDPART
NUNOTA_ORIGEM
NUNOTA_OE
NUM_CARRETA
SEQUENCIA_ITEM_ORIGEM
CODPROD
QTD_PALETES
QTD_UNIDADE
QTD_CAIXA
STATUS
DHCRIACAO
CODUSUCRIACAO
DHCANCELAMENTO
CODUSUCANCELAMENTO
MOTIVO_CANCELAMENTO
```

Vantagem:

- Controle total sobre rastreabilidade do particionamento.
- Fica restrito ao processo de OE particionada.
- Facilita botao de desfazer.
- Facilita auditoria e suporte.

Ponto de atencao:

- Exige criacao de nova tela/tabela.
- Exige manutencao da rotina de gravacao e cancelamento.

## Recomendacao Tecnica

Se a `TGFVAR` puder ser usada corretamente pelo fluxo nativo de faturamento/geracao da OE, ela e a melhor opcao por ser padrao Sankhya.

Se a geracao continuar sendo feita via JAPE/copia controlada, a recomendacao e criar tabela/tela adicional de rastreabilidade para nao depender de um vinculo que pode nao ser mantido automaticamente.

## Botao de Desfazer Particionamento

Validacoes sugeridas:

- Pedido original deve estar com `AD_PARTICIONADO = 'S'`.
- Deve existir vinculo/rastro das OEs geradas.
- Nenhuma OE pode estar faturada, confirmada, baixada, enviada ou em status que impeça cancelamento.
- Se alguma OE nao puder ser desfeita, bloquear a operacao inteira.

Comportamento:

1. Abrir transacao.
2. Bloquear pedido origem.
3. Buscar todas as OEs vinculadas.
4. Validar status de todas.
5. Cancelar/excluir OEs.
6. Atualizar rastreabilidade.
7. Marcar pedido origem como `AD_PARTICIONADO = 'N'`.
8. Confirmar transacao.

Se houver erro:

- Rollback total.
- Nenhuma OE deve ficar parcialmente desfeita.
- Pedido origem deve permanecer como estava.

## Performance

Ponto de atencao levantado: gerar e simples, mas desfazer pode ser mais pesado quando houver muitas OEs.

Cuidados:

- Trabalhar em lote.
- Evitar consulta item a item quando possivel.
- Buscar OEs por indice em `NUNOTA_ORIGEM` ou equivalente.
- Criar indice na tabela adicional, se usada.
- Evitar exclusao fisica quando cancelamento logico for suficiente.
- Validar tempo com massas grandes, como 15, 17 ou mais OEs.

## Documentacao

Atualizar as documentacoes de GMUD e usuario com imagens:

- Tela do pedido original.
- Parametro `QTDE_PALETES`.
- Execucao da acao de particionamento.
- Resultado com OEs geradas.
- Tela/consulta de validacao dos paletes.
- Futuramente, tela/botao de desfazer particionamento.
- Futuramente, tela de rastreabilidade, se criada.

