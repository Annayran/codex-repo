# Changelog

## V1.5 — 2026-10-06

- Dashboard V1.4 normaliza rotulos fragmentados pelo SAP, como `S ITE`, `M UNICIPIO` e `M ES/ANO INICIO`.
- Pedido 5500629122 passa a extrair SITE MGCEN21, municipio BELO HORIZONTE e periodo 01/2026 a 12/2026.
- O XML enviado a acao 68 foi validado no Java com um item e SITE preenchido; nenhuma flexibilizacao da regra obrigatoria foi necessaria.

## V1.4 — 2026-10-02

- Dashboard V1.3 passa a aceitar anexos SAP que listam referencias adicionais ausentes do pedido atual.
- O enriquecimento do anexo continua obrigatorio para cada item real do pedido; apenas blocos extras sao ignorados.
- Pedidos 5500623466, 5500626029 e 5500630348 passaram no navegador, com 2, 1 e 1 itens respectivamente.
- Nenhum erro JavaScript `Unexpected token` ocorreu com o pacote fonte atualizado.

## V1.3 — 2026-09-04

- Corrigida falha da rotina Java quando o parser XML do Sankhya nao reconhece os atributos JAXP 1.5 `accessExternalDTD` e `accessExternalSchema`.
- Os atributos passam a ser aplicados quando suportados; processamento seguro, rejeicao de DOCTYPE, desativacao de entidades externas e bloqueio pelo `EntityResolver` permanecem ativos.
- Dashboard V1.2 e metadados V1.1 foram preservados, pois o erro ocorre exclusivamente no backend.

## V1.2 — 2026-09-04

- Botao Salvar pedido recebido habilitado apos leitura e conferencia do PDF.
- Integracao com a Rotina Java codigo 68 por `ActionButtonsSP.executeJava`, usando parametro `XMLPEDIDO`.
- Removida dependencia de SankhyaJX/CDN; sessao mantida somente no cookie same-origin.
- Adicionados confirmacao, bloqueio de clique concorrente, timeout, limites de resposta e mensagens funcionais.
- E2E confirma payload, ausencia de sessao na URL, sucesso, PDF real, mobile e arquivo invalido.
- Acao observada com controle de acesso desligado; ativacao permanece obrigatoria antes de producao.

## Backend de homologacao — 2026-09-04

- Gerado JAR Java 8 da Rotina Java de recebimento, sem dependencias Sankhya embarcadas.
- Build registra hashes da biblioteca de extensoes e das dependencias compile-only.
- Adicionada validacao obrigatoria de `SankhyaW-extensions.jar` ao Gradle.
- Documentado cadastro da acao, transacao automatica, controle de acesso e roteiro de rollback/duplicidade.
- O botao interno permanecia desabilitado nessa etapa e foi integrado na V1.2 apos a confirmacao da acao 68.

## Correcao exportInfo — 2026-09-04

- Corrigido erro do importador legado: `Informe o elemento 'exportInfo'`.
- Gerador passa a obter o bloco de um export nativo da mesma PortoMag 4.35b811.
- Valores de conexao ficam somente no ZIP local em `out/`, ignorado pelo Git; XML fonte e documentacao continuam sanitizados.
- Empacotador valida ordem, versao, charset e estrutura obrigatoria antes de liberar os artefatos.

## Metadados V1.0 revisados — 2026-09-04

- Registrada de forma sanitizada a estrutura do export nativo CENTPARAM 4.36b126.
- Detalhe alterado para chave composta IDPED + SEQEXT e listado antes do mestre.
- Relacao alterada para mestre ID -> detalhe IDPED, com remocao vinculada conforme a evidencia.
- Removido o ID artificial do detalhe e a constraint unica redundante.
- O exportInfo ainda estava ausente nessa revisao e foi corrigido na etapa seguinte.

## V1.1 — 2026-09-04

- Corrigida a estrutura de publicacao apos erro 500 relatado no Sankhya.
- Distribuicao plana conforme a referencia: index.jsp com CSS/parser/controlador incorporados, mais pdf.js, worker e licenca.
- Removidos iframe, subpastas e arquivos `.mjs` do ZIP de implantacao.
- EL do JSP limitada ao BASE_FOLDER; interpolacoes JavaScript eliminadas do fonte incorporado.
- Causa exata do erro 500 nao confirmada por falta de log do servidor; novo pacote exige teste em homologacao.

## V1.0 — 2026-09-04

- Projeto novo para leitura do pedido Claro em dashboard HTML5.
- Modelo de recebimento mestre/detalhe ZPM; nenhum objeto legado alterado.
- Parser de colunas, conferencia de totais e anexo por item.
- Contrato XML com validacao Java 8, protecao contra XXE e campos desconhecidos.
- Persistencia Jape preparada para transacao automatica do botao nativo.
- Metadados, constraints, roteiro de implantacao e rollback.
- Ligacao HTML5 -> botao nativo e homologacao no Sankhya pendentes; geracao comercial fora desta etapa.
- Entrada JSP adaptada a referencia fornecida: taglib Sankhya, snk:load e EL habilitada; interface direta sem iframe adicional. Geracao do JSP a partir da view evita divergencia entre preview e pacote.
