# Contexto do Projeto Zaya / Skala / Sankhya

Fonte:
- `C:\Users\annayran.ferreira\Downloads\Zaya_PDD_Particionamento_Skala_v1.0.pdf`
- `C:\Users\annayran.ferreira\Downloads\Zaya_IntPDD_Particionamento_Skala_v1.0.pdf`

## Resumo

Projeto para substituir uma planilha Excel com macro VBA usada pela Skala por uma acao customizada Java nativa no Sankhya. A acao deve particionar pedidos de venda nacionais em pedidos de OE, gerando um pedido de OE por carreta, respeitando a capacidade de paletes informada pelo usuario.

Documentos:
- PDD `ZY-SKL-2026-PART-PDD-001`, v1.0, 13/05/2026: escopo, requisitos, entregaveis, RACI, roadmap e riscos.
- IntPDD `ZY-SKL-2026-PART-INTPDD-001`, v1.0, 13/05/2026: especificacao tecnica Java/Sankhya, classes, SQL, algoritmo, geracao de OEs, validacoes e testes.

Responsaveis e participantes:
- Responsavel tecnico: Andre Siqueira / Zaya IT.
- Gerente de projeto: Wesley Lima / Zaya IT.
- Skala: Edson Silva e Leonardo Carvalho.

## Problema Atual

O processo atual depende de exportacao/transcricao manual:
- Usuario abre pedido de venda no Sankhya.
- Leva dados para a planilha "Gerador de Demanda".
- Informa paletes por carreta.
- Macro VBA gera a aba `PEDIDOS_CARRETA`.
- Usuario cria manualmente N pedidos de OE no Sankhya.

Principais dores:
- Risco de erro de transcricao.
- Sem rastreabilidade entre pedido original e OEs.
- Dependencia de Excel/macro externa.
- Processo lento e pouco auditavel.

## Solucao Proposta

Criar botao de acao manual no Sankhya:
- Nome: `Particionar Pedidos por Qtde de Paletes`.
- Tela: Pedidos de Venda / GPED.
- Contexto: cabecalho do pedido / `TGFCAB`.
- Classe prevista: `br.com.zaya.skala.ParticionarPedidosAction`.
- Entrada do usuario: capacidade de paletes por carreta, default `28`.

Fluxo:
1. Usuario abre pedido de venda nacional com TOP habilitada.
2. Aciona o botao.
3. Sistema valida TOP, duplicidade e permissao.
4. Sistema solicita capacidade da carreta.
5. Le itens do pedido em `TGFITE` e dados de palete em `TGFPRO`.
6. Executa algoritmo de duas passadas: primeiro paletes inteiros, depois fracoes.
7. Gera N pedidos de OE, um por carreta.
8. Marca pedido original como particionado.
9. Exibe mensagem de sucesso com notas geradas.

## Escopo Incluido

- Campos adicionais:
  - `TGFTOP.AD_PERMITEPART`: flag `S/N` para habilitar particionamento por TOP.
  - `TGFCAB.AD_PARTICIONADO`: flag `S/N` para bloquear reprocessamento.
- Acao customizada Java.
- Algoritmo fiel ao VBA.
- Split de item entre carretas quando excede capacidade livre.
- Calculo de quantidade em unidade e caixa.
- Geracao de pedidos de OE via API/SDK Sankhya.
- Controle de acesso por usuario/grupo.
- Validacao de execucao duplicada.
- Tratamento robusto de numeros nulos, vazios e com separadores variaveis.

## Fora do Escopo

- Pedidos de exportacao, transferencia, devolucao ou outros tipos nao nacionais.
- Integracao com WMS ou logistica externa.
- Relatorios e dashboards.
- Estorno/cancelamento automatico de particionamento ja realizado.
- Parametrizacao de capacidade por tipo de veiculo.
- Notificacoes automaticas por e-mail ou mensageria.

## Regras de Negocio

- A acao deve aparecer/executar apenas quando a TOP possuir `AD_PERMITEPART = 'S'`.
- Pedido com `AD_PARTICIONADO = 'S'` nao pode ser processado novamente.
- Capacidade informada deve ser maior que zero.
- Itens sem codigo ou com quantidade de palete menor/igual a zero sao ignorados.
- Fracao decimal menor ou igual a `0.000001` deve ser ignorada.
- Ao concluir com sucesso, marcar `TGFCAB.AD_PARTICIONADO = 'S'`.
- Se erro ocorrer durante geracao de OEs, cancelar/rollback das OEs criadas nesta execucao.

## Tabelas Sankhya Envolvidas

- `TGFTOP`: tipo de operacao. Campo adicional `AD_PERMITEPART`.
- `TGFCAB`: cabecalho do pedido original e cabecalho das OEs. Campos relevantes: `NUNOTA`, `CODTIPOPER`, `CODPARC`, `AD_PARTICIONADO`.
- `TGFITE`: itens do pedido original e itens das OEs. Campos relevantes: `CODPROD`, `QTDNEG`, `SEQUENCIA`.
- `TGFPRO`: cadastro de produtos. Deve conter campos de unidades por palete e caixas por palete.

Campos de produto pendentes de confirmacao com Skala:
- `AD_UNDPALETE`: placeholder para unidades por palete.
- `AD_CXPALETE`: placeholder para caixas por palete.

Campos de OE mencionados:
- `TGFITE(OE).QTDNEG`: quantidade em unidades calculada.
- `TGFITE(OE).AD_QTDPALETES`: quantidade em paletes, se existir/criado.
- `TGFITE(OE).AD_QTDCAIXA`: quantidade em caixas, se existir/criado.

## Algoritmo de Particionamento

Entrada por item:
- Codigo do produto.
- Descricao.
- Quantidade de paletes demandada.
- Unidades por palete.
- Caixas por palete.

Capacidade:
- Informada pelo usuario.
- Valor padrao: `28`.

Constante:
- `TOL = 0.000001`.

Passada 1:
- Para cada item valido, pegar a parte inteira da quantidade de paletes.
- Alocar essa parte nas carretas em ordem, respeitando capacidade livre.
- Abrir nova carreta quando a atual nao tiver capacidade.

Passada 2:
- Para cada item valido, calcular `parteDecimal = roundTo6(qtdPalete - parteInteira)`.
- Se `parteDecimal > TOL`, alocar apos todos os inteiros.
- A fracao tambem pode abrir nova carreta se nao couber no restante.

Alocacao:
- Enquanto houver paletes a alocar:
  - Se a capacidade livre for menor/igual a tolerancia, abrir nova carreta.
  - `qtdAlocar = min(qtdPaletesRestantes, capacidadeLivre)`.
  - Criar item da carreta.
  - Calcular:
    - `qtdUnidade = qtdAlocada * undPorPalete`.
    - `qtdCaixa = qtdAlocada * cxPorPalete`.
  - Atualizar saldos arredondando para 6 casas.

## Estrutura Tecnica Prevista

Classes:
- `ParticionarPedidosAction.java`: entry point da acao. Valida, captura input, orquestra e retorna mensagem.
- `ParticionamentoService.java`: algoritmo de duas passadas e split entre carretas.
- `PedidoRepository.java`: consultas de validacao e leitura dos itens.
- `OEGenerator.java`: geracao das OEs via API/SDK Sankhya.
- `ItemPedido.java`: model do item original.
- `ItemCarreta.java`: model do item alocado em uma carreta.
- `Carreta.java`: model da carreta.
- `NumericUtils.java`: conversao numerica segura e arredondamento.

## SQL Base Previsto

Validacao do pedido:
```sql
SELECT
  cab.NUNOTA,
  cab.CODTIPOPER,
  top.AD_PERMITEPART,
  cab.AD_PARTICIONADO
FROM TGFCAB cab
JOIN TGFTOP top ON top.CODTIPOPER = cab.CODTIPOPER
WHERE cab.NUNOTA = :nuNota
```

Leitura dos itens:
```sql
SELECT
  ite.CODPROD,
  pro.DESCRPROD,
  ite.QTDNEG,
  pro.AD_UNDPALETE,
  pro.AD_CXPALETE,
  CASE
    WHEN pro.AD_UNDPALETE > 0 THEN ite.QTDNEG / pro.AD_UNDPALETE
    ELSE 0
  END AS QTD_PALETE_DEMANDA
FROM TGFITE ite
JOIN TGFPRO pro ON pro.CODPROD = ite.CODPROD
WHERE ite.NUNOTA = :nuNota
ORDER BY ite.SEQUENCIA
```

Marcacao do pedido original:
```sql
UPDATE TGFCAB
SET AD_PARTICIONADO = 'S'
WHERE NUNOTA = :nuNota
```

Observacao: insercao direta em `TGFCAB/TGFITE` nao e recomendada. Geracao das OEs deve usar API/SDK Sankhya para respeitar regras nativas, triggers e validacoes.

## Mensagens e Validacoes

- TOP nao permite particionamento:
  - `"A TOP deste pedido nao permite particionamento."`
- Pedido ja particionado:
  - `"Este pedido ja foi particionado anteriormente."`
- Capacidade invalida:
  - `"Capacidade informada invalida. Informe um valor maior que zero."`
- Nenhum item valido:
  - `"Nenhum item valido encontrado no pedido."`
- Erro de geracao de OE:
  - `"Erro ao gerar pedido de OE para carreta N: [detalhe]."`
- Sucesso:
  - `"Particionamento concluido com sucesso! N pedido(s) de OE gerado(s). Notas geradas: ..."`

## Cenarios de Teste

1. Simples sem split:
   - PROD-A 10 pal, 100 und/pal, 10 cx/pal.
   - PROD-B 8 pal, 60 und/pal, 6 cx/pal.
   - Capacidade 28.
   - Resultado: 1 carreta com 18 paletes.

2. Split entre carretas:
   - PROD-A 20 pal.
   - PROD-B 15 pal.
   - Capacidade 28.
   - Resultado: carreta 1 com 20 + 8; carreta 2 com 7.

3. Fracoes:
   - PROD-A 10,75.
   - PROD-B 4,20.
   - PROD-C 8,00.
   - Resultado: inteiros 22 na carreta 1; fracoes 0,95 tambem na carreta 1; total 22,95.

4. Fracoes abrem nova carreta:
   - PROD-A 27,8.
   - PROD-B 0,5.
   - Capacidade 28.
   - Resultado: PROD-A 27 + 0,8 na carreta 1; PROD-B 0,5 na carreta 2.

5. Capacidade customizada:
   - Dados do cenario 2 com capacidade 16.
   - Resultado esperado: 3 carretas.

6. Validacoes:
   - TOP sem flag.
   - Pedido ja particionado.
   - Capacidade zero.
   - Item sem qtd palete.
   - Fracao infima `0,0000005`.

## Pendencias Criticas

- Confirmar nomes reais dos campos de produto para:
  - Unidades por palete.
  - Caixas por palete.
- Confirmar `CODTIPOPER` da TOP de OE.
- Confirmar forma concreta de criacao de pedido via API/SDK disponivel no ambiente Sankhya da Skala.
- Confirmar se campos `AD_QTDPALETES` e `AD_QTDCAIXA` existem em `TGFITE` para as OEs.
- Confirmar quais campos de cabecalho devem ser copiados do pedido original para a OE.
- Confirmar politica de rollback/cancelamento de OEs no Sankhya.
- Validar algoritmo contra a planilha `Gerador_de_Demanda.xlsm`, se disponivel.

## Riscos

- Campos de palete nao mapeados no produto.
- TOP de OE nao configurada.
- Execucao duplicada gerando OEs redundantes.
- Aumento de escopo durante homologacao.

## Proximo Passo Natural

Antes de codificar a integracao real, separar o trabalho em duas camadas:
1. Implementar e testar o algoritmo puro em Java com os 6 cenarios, sem dependencia do Sankhya.
2. Depois plugar repository/action/OEGenerator usando os artefatos e APIs reais do ambiente Sankhya.
