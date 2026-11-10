# Sprint 0 e 1 - Particionamento Skala / Sankhya

## Objetivo do Dia

Tratar a Sprint 0 e iniciar/concluir a Sprint 1:
- Sprint 0: fechar campos, parametros Sankhya e pendencias criticas.
- Sprint 1: implementar e validar o algoritmo puro de particionamento, ainda sem dependencia real do Sankhya.

## Sprint 0 - Campos e Parametros

### Campos explicitamente especificados para criacao

Estes campos estao descritos no PDD como entregaveis e devem ser criados/configurados no Sankhya:

| Tabela | Campo | Tipo | Valores | Obrigatorio | Observacao |
| --- | --- | --- | --- | --- | --- |
| `TGFTOP` | `AD_PERMITEPART` | Texto 1 | `S` / `N` | Sim | Habilita a acao apenas para TOPs de venda nacional. Default recomendado: `N`. |
| `TGFCAB` | `AD_PARTICIONADO` | Texto 1 | `S` / `N` | Sim | Marca pedido original como ja particionado. Default recomendado: `N`. |

### Campos citados como placeholders ou pendentes

Estes nomes aparecem no IntPDD como referencia tecnica, mas precisam ser confirmados no ambiente Skala:

| Tabela | Campo citado | Finalidade | Status |
| --- | --- | --- | --- |
| `TGFPRO` | `AD_UNDPALETE` | Unidades por palete | Confirmar se ja existe ou criar equivalente. |
| `TGFPRO` | `AD_CXPALETE` | Caixas por palete | Confirmar se ja existe ou criar equivalente. |
| `TGFITE` OE | `AD_QTDPALETES` | Registrar qtd de paletes na OE | Confirmar se existe/sera criado. |
| `TGFITE` OE | `AD_QTDCAIXA` | Registrar qtd de caixas na OE | Confirmar se existe/sera criado. |

### Parametros a confirmar

| Item | Necessario para | Status |
| --- | --- | --- |
| `CODTIPOPER` da TOP de OE | Geracao dos pedidos de OE | Confirmar com Skala. |
| API/SDK Sankhya disponivel | Criacao/cancelamento de pedidos com regras nativas | Confirmar no ambiente dev. |
| Campos de cabecalho a copiar | Criar OE consistente com pedido original | Confirmar regra funcional. |
| Politica de rollback/cancelamento | Desfazer OEs se uma falhar | Confirmar melhor abordagem no Sankhya. |

## Decisao sobre Criar ou Reutilizar Campos

1. `TGFTOP.AD_PERMITEPART`: criar, se ainda nao existir.
2. `TGFCAB.AD_PARTICIONADO`: criar, se ainda nao existir.
3. `TGFPRO.AD_UNDPALETE` e `TGFPRO.AD_CXPALETE`: primeiro verificar se a Skala ja possui campos equivalentes. Se nao existir, criar campos adicionais no produto.
4. `TGFITE.AD_QTDPALETES` e `TGFITE.AD_QTDCAIXA`: criar apenas se a Skala quiser registrar paletes/caixas diretamente nos itens da OE. Para gerar a OE, o minimo obrigatorio e `QTDNEG` em unidades.

## Sprint 1 - Algoritmo Puro

### Entregaveis

- Models:
  - `ItemPedido`
  - `ItemCarreta`
  - `Carreta`
- Utilitario:
  - `NumericUtils`
- Servico:
  - `ParticionamentoService`
- Testes:
  - Cenario 1: simples sem split.
  - Cenario 2: split entre carretas.
  - Cenario 3: fracoes na segunda passada.
  - Cenario 4: fracoes abrindo nova carreta.
  - Cenario 5: capacidade customizada.
  - Cenario 6: tolerancia e itens invalidos.

### Criterio de aceite

O algoritmo deve gerar os mesmos resultados esperados no PDD e, quando a planilha VBA estiver disponivel, bater com a planilha `Gerador_de_Demanda.xlsm`.
