# Regras de Negócio

## Entidades Principais

- Empresa Sankhya.
- Parceiro: cliente ou fornecedor.
- Produto controlado por série.
- Série/equipamento.
- Nota e item da nota.
- Movimento de estoque: entrada ou saída.
- Base Instalada Genesis.
- TOP/tipo de operação elegível.
- Status Estoque.
- Garantia em meses.

## Processos

A MA Hospitalar precisa manter a Genesis atualizada com movimentos de estoque controlados por série registrados no Sankhya.

O processo deve:

1. Identificar movimentos de entrada e saída de estoque com TOP parametrizada.
2. Consolidar dados de empresa, parceiro, produto, nota, item, série, estoque e local.
3. Exibir os registros no dashboard de Base Instalada/Gestão Estoque por Série.
4. Enviar os movimentos elegíveis para a API Genesis no recurso Estoques.
5. Controlar envio, erro, tentativas e reprocessamento por movimento.

## Validações

- TOP não parametrizada deve ser ignorada.
- Movimento sem campos obrigatórios deve ser marcado como erro funcional.
- Produto sem série não deve ser enviado como movimento controlado por série.
- Status deve respeitar a lista O/A/I/V/D/R.
- Token/API indisponível deve gerar erro técnico/transitório ou alerta, sem perder rastreabilidade.
- Reenvio não pode duplicar registro na Genesis.

## Cálculos e Derivações

- `DTINSTALACAO` usa `DTNEG`.
- `PODER`: se `TGFEST.CODPARC = 0`, retorna `PROPRIO`; se diferente de `0`, retorna `TERCEIRO`.
- `TIPO`: derivado de `TGFESE.ATUALESTOQUE` e `TGFESE.ATUALESTTERC`.
- Produto e grupo devem ser enviados concatenando código e descrição quando requerido.
- Datas para Genesis usam epoch integer; unidade segundos/milissegundos a confirmar.
- CEP e telefone devem ser formatados.

## Restrições

- Escopo limitado a movimentos de estoque/base instalada controlados por série.
- Processos futuros fora de estoque exigem novo levantamento/escopo.
- URL, token e credenciais da Genesis não devem ser gravados em código, logs, dashboard ou documentação técnica versionada.
- O dashboard deve refletir a mesma base usada para a integração.

## Integrações que Impactam Regra de Negócio

- API Genesis V1, recurso Estoques.
- Método: `POST /api/v1/estoques/`.
- Body: `EstoqueJson`.
- Autenticação: header `token`/apiKey, alternativa basicAuth a confirmar.
- Sucesso esperado: HTTP 201.
- Campos relevantes do payload: `codigo`, `codigoExterno`, `descTecnica`, `nserie`, `status`, `rgAnvisa`, `periodoGarantia`, `qtd`, `vlrVenda`, `localizacao`, `dtEntrada`, `controlarEstoque`.

## Regras a Confirmar

- Contrato definitivo da API Genesis em homologação: obrigatórios, opcionais, upsert, rate limit e mensagens.
- Se `codigoExterno` será chave oficial de upsert/idempotência.
- Unidade de epoch em `dtEntrada`, `dtAlteracao` e `dtVencimento`.
- Uso de `idFornecedor`, `idFabricante` e `idCategoria` por ID Genesis ou por código/descrição Sankhya.
- Lista final de TOPs elegíveis.
- Periodicidade da ação agendada.
- Modelagem final do `Status Estoque`: TGFITE, TGFSER ou tabela adicional por produto+série+empresa/local/parceiro.
- Se o mesmo número de série pode existir em mais de uma empresa/local/parceiro.
- Nome final do dashboard.
