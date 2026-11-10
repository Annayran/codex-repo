# Creative Momento Negociacao

Projeto de personalizacao Sankhya para o fluxo **Momento da Negociacao / Cancelamento de Proposta**.

O objetivo do projeto e oferecer uma tela operacional em HTML5/JSP para analisar a negociacao selecionada, localizar documentos relacionados, validar itens com saldo ou ja entregues e acionar rotina Java de cancelamento em ambiente homologado.

## Objetivo funcional

O fluxo atende o processo de cancelamento parcial ou bloqueio de cancelamento de itens vinculados a uma negociacao.

A tela permite:

- abrir uma negociacao pelo `NUNEGOCIACAO` da linha selecionada;
- localizar Pedido(s) de Venda relacionados pela `AD_VGLDOC`;
- localizar Nota de Movimentacao Interna por `TGFCAB.AD_NUNEGOCIACAO`;
- selecionar o documento ativo (`NUNOTA`);
- listar os itens do documento pela `TGFITE`;
- validar saldo por `QTDNEG` e `QTDENTREGUE`;
- identificar item ja cancelado por `TGFACT.AD_ITEMCANCELADO = 'S'`;
- informar motivo de cancelamento;
- chamar Action Java real pelo JSP em modo operacional;
- gravar acompanhamento em `TGFACT`/`AcompanhamentoNota`.

## Regra de negocio principal

A regra atual considera:

- se `TGFITE.QTDENTREGUE = TGFITE.QTDNEG`, o item esta totalmente entregue e nao pode ser cancelado;
- se `TGFITE.QTDENTREGUE <> TGFITE.QTDNEG`, o item possui saldo e pode receber motivo de cancelamento;
- se ja existe `TGFACT.AD_ITEMCANCELADO = 'S'` para o mesmo `NUNOTA + SEQUENCIA`, o item ja foi tratado e nao deve ser cancelado novamente;
- ao cancelar, a rotina mantem/atualiza `TGFITE.PENDENTE = 'S'`;
- o controle de auditoria e recancelamento fica em `TGFACT.AD_ITEMCANCELADO = 'S'`.

## Estrutura do repositorio

```text
.
|-- AGENTS.md
|-- .ai/
|   |-- context/
|   |-- sankhya/
|   |-- prompts/
|   `-- scripts/
|-- momento-negociacao/
|   |-- src/main/java/br/com/zaya/like/Actions/
|   |-- prototipos/modal-momento-negociacao/
|   |-- outputs/
|   |-- build.gradle
|   `-- settings.gradle
|-- prototipos/
|-- simulacoes/
|-- outputs/
|-- build.gradle
`-- settings.gradle
```

## Modulo principal

O modulo principal fica em:

```text
momento-negociacao
```

Principais pacotes Java:

```text
br.com.zaya.like.Actions
br.com.zaya.like.Actions.services
br.com.zaya.like.Actions.dao
br.com.zaya.like.Actions.dto
```

Actions existentes:

- `MomentoNegociacaoAction`
- `CancelarDocumentoNegociacaoAction`
- `CancelarSuprimentoNegociacaoAction`
- `CancelarItemPedidoVendaNegociacaoAction`

Action principal para cancelamento pelo modal:

```text
br.com.zaya.like.Actions.CancelarDocumentoNegociacaoAction
```

## JSP / HTML5

Arquivo principal do componente HTML5:

```text
momento-negociacao/prototipos/modal-momento-negociacao/html5-component/modalMomentoNegociacao.jsp
```

Script do botao Sankhya que abre o modal:

```text
momento-negociacao/prototipos/modal-momento-negociacao/botao-acao/momentoNegociacaoModalButton.js
```

O JSP recebe parametros como:

- `nunegociacao`
- `nuGdg`
- `modo`
- `codAcaoJavaMomento`
- `codAcaoJavaCancelarDocumento`

Quando `modo=OPERACIONAL`, o JSP chama a rotina Java por `ActionButtonsSP.executeJava`.

## Parametros enviados para a Action Java

A Action `CancelarDocumentoNegociacaoAction` recebe dinamicamente pelo JSP:

```text
P_NUNOTA_DOCUMENTO
P_TIPO_DOCUMENTO
P_MOTIVO
P_SEQUENCIA
```

Observacao:

- `P_SEQUENCIA` e enviado somente quando o cancelamento for item a item;
- sem `P_SEQUENCIA`, a Action trata cancelamento em lote dos itens com saldo do documento selecionado.

## Botoes do modal

- **Cancelar todos com saldo**: abre motivo e cancela todos os itens com saldo do documento selecionado.
- **Cancelar item**: abre motivo e cancela somente a sequencia selecionada.
- **Notificar areas**: previsto para rotina de aviso operacional.
- **Registrar pendencia**: previsto para registro de pendencia operacional.
- **Solicitar aprovacao**: previsto para aprovacao gerencial/administrativa.
- **Limpar simulacao**: restaura o estado visual do modal, sem chamar Java.

Os IDs de Actions podem ser configurados pela engrenagem no topo do modal e salvos em `localStorage`.

## Artefatos de homologacao

JAR Java:

```text
momento-negociacao/build/libs/CreativeLike.jar
```

ZIP HTML5:

```text
momento-negociacao/outputs/modal-momento-negociacao-html5.zip
```

O JAR deve ser compilado em Java 8, pois o ambiente Sankhya observado aceita `major version: 52`.

## Build

Para compilar o modulo:

```powershell
cd .\momento-negociacao
.\gradlew.bat clean build
```

Validar bytecode Java 8:

```powershell
javap -verbose -classpath "build\libs\CreativeLike.jar" br.com.zaya.like.Actions.CancelarDocumentoNegociacaoAction | Select-String "major version"
```

Resultado esperado:

```text
major version: 52
```

Gerar ZIP do componente HTML5:

```powershell
Compress-Archive -Path "momento-negociacao\prototipos\modal-momento-negociacao\html5-component\modalMomentoNegociacao.jsp" -DestinationPath "momento-negociacao\outputs\modal-momento-negociacao-html5.zip" -Force
```

## Cuidados Sankhya

Pontos importantes ja validados durante homologacao:

- nao usar `contexto.confirmarSimNao(...)` em Action chamada pelo JSP via `ActionButtonsSP.executeJava`;
- nao abrir `JapeSession.open()` ou `execWithTX(...)` dentro da Action chamada pelo JSP, para evitar transacao concorrente;
- nao compilar o JAR em Java 11;
- nao depender de fonte externa para icones dentro do iframe HTML5;
- nao limitar Pedido de Venda apenas por `CODTIPOPER = 1001`; a busca atual considera `TGFCAB.TIPMOV = 'P'`.

## Contexto para manutencao

Antes de continuar o desenvolvimento, leia:

```text
AGENTS.md
.ai/context/checkpoint.md
.ai/context/current-sprint.md
.ai/context/decisions.md
.ai/context/known-issues.md
.ai/context/business-rules.md
.ai/sankhya/
```

Esses arquivos guardam o estado atual do projeto, decisoes tecnicas, riscos conhecidos e guias especificos para desenvolvimento Sankhya.

## Status atual

O projeto esta em fase de homologacao.

O foco atual e validar:

1. abertura do modal pelo botao Sankhya;
2. consulta real dos documentos relacionados;
3. selecao de `NUNOTA`;
4. cancelamento item a item;
5. cancelamento em lote dos itens com saldo;
6. gravacao em `TGFACT`;
7. reabertura da negociacao exibindo itens ja cancelados e motivo.
