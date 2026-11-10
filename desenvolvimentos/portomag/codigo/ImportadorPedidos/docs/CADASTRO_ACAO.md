# Cadastro da acao de recebimento — homologacao

## Artefato

- JAR: `out/ZAYA-PORTOMAGPDF-V1.3-HOMOLOGACAO.jar`.
- Classe: `br.com.zaya.portomag.importador.sankhya.ReceberPedidoAction`.
- Entidade: `ZPM_PEDREC`.
- Parametro: `XMLPEDIDO`, texto obrigatorio, limite aplicado no servidor de 250.000 caracteres.
- Transacao: controle manual desabilitado; a acao depende da transacao automatica do contexto.
- Controle de acesso: habilitado.

O JAR nao contem Jape, modelcore nem a biblioteca de extensoes. Essas APIs devem ser fornecidas pelo Sankhya.

## Compatibilidade antes da publicacao

O JAR local foi compilado com Java 8, a biblioteca `SankhyaW-extensions.jar` baixada em 17/08/2026 e Jape/modelcore 4.23b244 apenas como referencia de compilacao. A V1.3 tolera a ausencia dos atributos JAXP 1.5 `accessExternalDTD` e `accessExternalSchema` no parser XML antigo do servidor, mantendo obrigatorios o bloqueio de DOCTYPE, entidades externas e resolucao externa. Antes de producao, substituir Jape/modelcore pelas bibliotecas homologadas da PortoMag 4.35b811 e gerar novamente. Nao misturar bibliotecas de servidores diferentes.

## Implantacao

1. Confirmar backup restauravel e ambiente de homologacao.
2. Conferir as duas entidades e a relacao mestre/detalhe no Dicionario de Dados.
3. Solicitar ao DBA a execucao de `database/deploy.sql`; depois executar as consultas somente leitura de `database/validacao.sql`.
4. Publicar o JAR pelo procedimento de modulos Java adotado no ambiente.
5. Na aba Acoes da instancia ZPM_PEDREC, usar a Rotina Java codigo 68, descricao `[ZAYA.IT] - Grava PDF`, apontando para a classe acima.
6. Alterar `Controla Acesso` de `Nao` para `Sim` antes de producao e manter controle manual de transacao desabilitado.
7. Criar o parametro `XMLPEDIDO` como texto longo obrigatorio. Confirmar que o dialogo nao trunca o XML.
8. Criar ou configurar um componente nativo Tabela ligado a ZPM_PEDREC e disponibilizar nele a acao aos perfis autorizados.

O XML da acao nao foi incorporado ao metadado porque nenhum export fornecido comprova a estrutura de uma acao Java nessa versao. O cadastro manual evita inventar atributos incompativeis.

## Teste funcional

1. No dashboard HTML5, ler o PDF e baixar a leitura XML.
2. Executar a acao com esse XML em homologacao.
3. Confirmar um registro em ZPM_PEDREC e dois registros em ZPM_PEDITE.
4. Conferir `IDPED`, sequencias, total, situacao, usuario e data da importacao.
5. Repetir o mesmo XML: a segunda execucao deve ser bloqueada pela `CHAVEIMP`.
6. Provocar erro em um item: nenhum mestre ou detalhe deve permanecer.
7. Testar com usuario autorizado e sem acesso.

## Botao dentro do HTML5 — V1.2

A imagem fornecida em 04/09/2026 confirmou a acao 68 na instancia ZPM_PEDREC. Componentes locais do mesmo ambiente comprovaram o contrato interno `ActionButtonsSP.executeJava` e o formato de parametro textual. O dashboard V1.2 usa diretamente esse contrato, sem copiar SankhyaJX e sem CDN.

A chamada envia `XMLPEDIDO` em `javaCall.params.param`, usa `actionID=68`, exige confirmacao consciente e trata timeout como resultado incerto. A sessao permanece no cookie same-origin; `JSESSIONID` nao e lido nem enviado em query string. Como o servico e interno, repetir a homologacao sempre que a versao Sankhya mudar.
