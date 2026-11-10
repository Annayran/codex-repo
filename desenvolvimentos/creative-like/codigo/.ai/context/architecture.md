# Arquitetura do Projeto

## Visao Geral

Projeto Java/Gradle de customizacoes Sankhya para o fluxo **Momento da Negociacao / Cancelamento de Proposta**.

Confirmado pelo codigo:

- modulo Java `momento-negociacao` com Actions Sankhya (`AcaoRotinaJava`);
- camada `services` para regras/orquestracao;
- camada `dao` para acesso a dados Sankhya via JAPE/NativeSql;
- DTOs para documentos, itens e resultados;
- componente JSP/HTML5 importavel no Sankhya;
- script JavaScript que abre o modal HTML5;
- JAR e ZIP HTML5 como artefatos de homologacao.

O fluxo atual esta em homologacao e integra JSP/HTML5 com Action Java real via `ActionButtonsSP.executeJava`.

## Camadas

### Action Sankhya

Pacote:

```text
br.com.zaya.like.Actions
```

Responsabilidades:

- receber linhas selecionadas e parametros;
- validar `NUNEGOCIACAO`;
- chamar service;
- retornar mensagem ao usuario por `ContextoAcao`.

Actions atuais:

- `MomentoNegociacaoAction`
- `CancelarDocumentoNegociacaoAction`
- `CancelarSuprimentoNegociacaoAction`
- `CancelarItemPedidoVendaNegociacaoAction`

### Service

Pacote:

```text
br.com.zaya.like.Actions.services
```

Responsabilidades:

- orquestrar regra de cancelamento;
- diagnosticar documentos e itens;
- bloquear item entregue ou ja cancelado;
- montar mensagem de resultado.

### DAO

Pacote:

```text
br.com.zaya.like.Actions.dao
```

Responsabilidades:

- consultar `AD_VGLDOC`, `TGFCAB`, `TGFITE`, `TGFACT` e tabelas de acompanhamento;
- atualizar `TGFITE.PENDENTE`;
- criar `AcompanhamentoNota`/`TGFACT`;
- fechar recursos de banco.

### DTO

Pacote:

```text
br.com.zaya.like.Actions.dto
```

Responsabilidades:

- transportar dados de negociacao, documentos, nota interna, itens e resultado.

### JSP/HTML5

Arquivo:

```text
momento-negociacao/prototipos/modal-momento-negociacao/html5-component/modalMomentoNegociacao.jsp
```

Responsabilidades:

- exibir painel operacional;
- consultar dados reais de Pedido de Venda e itens;
- selecionar documento ativo;
- exibir regra, saldo, bloqueios e cancelamentos anteriores;
- abrir motivo de cancelamento;
- chamar Action Java configurada quando `modo=OPERACIONAL`.

### Botao JavaScript

Arquivo:

```text
momento-negociacao/prototipos/modal-momento-negociacao/botao-acao/momentoNegociacaoModalButton.js
```

Responsabilidades:

- abrir `/mge/html5component.mge`;
- passar `entryPoint`, `nuGdg`, `nunegociacao`, IDs de Actions e `modo`.

## Modulos Principais

```text
momento-negociacao/
  src/main/java/br/com/zaya/like/Actions/
    dao/
    dto/
    services/
  prototipos/modal-momento-negociacao/
    html5-component/
    botao-acao/
  outputs/
```

Tambem existem pastas legadas/espelhadas na raiz (`src`, `prototipos`, `outputs`, `simulacoes`). A confirmar se ainda sao usadas ou apenas copia anterior.

## Fluxo Principal

```text
Usuario seleciona negociacao no Sankhya
  -> Action JavaScript abre modal HTML5
    -> JSP recebe NUNEGOCIACAO
      -> consulta documentos e itens
      -> usuario seleciona documento/item e informa motivo
      -> JSP chama Action 304 via ActionButtonsSP.executeJava
        -> CancelarDocumentoNegociacaoAction
          -> MomentoNegociacaoService
            -> MomentoNegociacaoDAO
              -> TGFITE / TGFACT
          -> retorno ao JSP
      -> JSP atualiza visual/log
```

## Integracoes

- Sankhya Action Button Java/JavaScript.
- Componente HTML5 (`/mge/html5component.mge`).
- `ActionButtonsSP.executeJava`.
- APIs Sankhya/JAPE: `ContextoAcao`, `Registro`, `NativeSql`, `JapeFactory`, `DynamicVO`, `EntityFacadeFactory`.
- Banco Oracle/Sankhya, inferido pelo uso de `NVL`, `ROW_NUMBER()` e tabelas `TGF*`.

## Pontos de Atencao

- O JAR deve ser Java 8 (`major version: 52`).
- O ZIP HTML5 deve conter `modalMomentoNegociacao.jsp` na raiz.
- Nao usar `confirmarSimNao` em Action chamada pelo JSP.
- Nao abrir transacao concorrente dentro da Action chamada pelo JSP.
- A regra atual mantem `TGFITE.PENDENTE = 'S'` e usa `TGFACT.AD_ITEMCANCELADO = 'S'` como trava.

## A Confirmar

- Versao exata do Sankhya e banco.
- Se pastas legadas da raiz ainda sao usadas.
- Se botoes utilitarios terao backend proprio.

