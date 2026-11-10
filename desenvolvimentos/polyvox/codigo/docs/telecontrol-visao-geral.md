# Telecontrol - Visao Geral do Ecossistema

## Objetivo

Este documento desenha como o Telecontrol funciona hoje no projeto `PolyvoxTelecontrol`, considerando o pacote Java consolidado em:

```txt
C:\zy-tools\workspace\Polyvox\PolyvoxTelecontrol
```

A visao abaixo foi montada a partir do codigo-fonte atual e do contexto registrado em `/.ai/context`. Onde o codigo nao permite confirmar valores finais, a informacao foi marcada como **A confirmar**.

## Resumo Executivo

O Telecontrol e uma integracao Java executada dentro do Sankhya. O JAR expoe `Actions`, `Schedulers` e eventos que leem dados do banco Sankhya/Oracle, montam JSONs, chamam a API Telecontrol via HTTP e gravam retorno/erros em tabelas customizadas.

Principais caracteristicas atuais:

- Entrada pelo Sankhya via `AcaoRotinaJava`, `ScheduledAction` e eventos.
- Configuracao central em `AD_TCLCFG`, referenciada pelo parametro global `IDCONFIGTLC`.
- Cliente HTTP central em `br.zaya.telecontrol.shared.infrastructure.http.Api`.
- Log tecnico central em `AD_TCLLGT`.
- Dados locais de integracao em tabelas `AD_TCL*`.
- Pacotes novos por feature, mantendo bridges legadas para nao quebrar FQCNs cadastrados no Sankhya.

## Arquitetura Atual

```mermaid
flowchart TB
    U["Usuario / Job Sankhya"] --> A["Actions / Schedulers / Eventos"]
    A --> F["Feature Actions<br/>produto, peca, serie, preco,<br/>pedidos, faturamento, OS"]
    F --> S["Application / Services"]
    S --> M["Mappers / Parsers JSON"]
    S --> R["Repositorios / Jape / NativeSql"]
    R --> DB["Oracle Sankhya<br/>AD_TCL*, TGF*, TGFPAR, TSI*"]
    S --> C["ConfigTelecontrol<br/>AD_TCLCFG + IDCONFIGTLC"]
    S --> H["Api.java<br/>GET / POST / PUT / DELETE"]
    H --> T["API Telecontrol"]
    H --> L["LogUtil"]
    S --> L
    L --> LG["AD_TCLLGT"]
```

## Camadas

```mermaid
flowchart LR
    subgraph Entrada["Entrada Sankhya"]
        AC["AcaoRotinaJava"]
        SC["ScheduledAction"]
        EV["Eventos"]
    end

    subgraph Aplicacao["Aplicacao"]
        APP["Use cases / Services por feature"]
        LEG["Services legados mantidos"]
    end

    subgraph Dominio["Dominio / DTO"]
        MOD["Models"]
        DTO["DTOs especificos<br/>ex.: SeriePendente"]
    end

    subgraph Infra["Infraestrutura"]
        PAR["Parsers / Mappers JSON"]
        DAO["JapeFactory / EntityFacade / NativeSql"]
        HTTP["Api.java / Autenticacao"]
        LOG["LogUtil"]
    end

    subgraph Externos["Externos"]
        ORA["Oracle / Sankhya DB"]
        API["API Telecontrol"]
    end

    Entrada --> Aplicacao
    Aplicacao --> Dominio
    Aplicacao --> Infra
    Infra --> ORA
    Infra --> API
    Infra --> LOG
```

## Modelo EAR / Componentes

```mermaid
flowchart TB
    subgraph "Sankhya ERP"
        UI["Telas / Botoes / Agendadores"]
        JAR["TelecontrolPolyvox.jar"]
        PARAM["Parametro IDCONFIGTLC"]
    end

    subgraph "TelecontrolPolyvox.jar"
        ACT["Actions e Schedulers"]
        FEAT["Pacotes por feature"]
        BRG["Bridges legadas<br/>br.com.zaya.*, ProdutoSerie.*, actions/*"]
        CORE["Shared Core<br/>Config, Api, Auth, Log, Utils"]
    end

    subgraph "Oracle"
        CFG["AD_TCLCFG"]
        LOG["AD_TCLLGT"]
        FILAS["AD_TCLPRO / AD_TCLPCA / AD_TCLPED / AD_TCLLOGSER / AD_TCLLTB"]
        ERP["TGFPRO / TGFCAB / TGFITE / TGFVAR / TGFPAR"]
    end

    subgraph "Telecontrol API"
        CAD["Cadastros"]
        PED["Pedidos"]
        FAT["Faturamento"]
        SER["Numero de Serie"]
        OS["Ordem de Servico"]
    end

    UI --> ACT
    PARAM --> CFG
    ACT --> FEAT
    BRG --> FEAT
    FEAT --> CORE
    CORE --> CFG
    CORE --> LOG
    FEAT --> FILAS
    FEAT --> ERP
    CORE --> CAD
    CORE --> PED
    CORE --> FAT
    CORE --> SER
    CORE --> OS
```

## Requisicoes Mapeadas

Os endpoints finais sao montados com `AD_TCLCFG.URL + campo EP_*`. Os caminhos reais configurados no banco devem ser confirmados no ambiente.

| Fluxo | Origem local | Endpoint configurado | Metodo | Resultado local |
|---|---|---|---|---|
| Produto | `AD_TCLPRO` | `EP_PRODUTOS` | `POST` quando nao tem `COD_TELECONTROL`; `PUT /referencia/{REFERENCIA}` quando tem | Atualiza `COD_TELECONTROL` e `DHINTEG` |
| Peca | `AD_TCLPCA` | `EP_PECAS` | `POST` quando nao tem `COD_TELECONTROL`; `PUT /referencia/{REFERENCIA}` quando tem | Atualiza `COD_TELECONTROL` e `DHINTEG` |
| Posto | `AD_TCLPST` | `EP_POSTOS` | `POST` ou `PUT` conforme codigo Telecontrol local | Atualiza integracao local |
| Lista basica - envio local | `AD_TCLLTB` | `EP_LISTABASICA` | `POST`, `PUT` ou `DELETE por produto/peca` | Envia composicao local para a Telecontrol |
| Lista basica - busca API | `AD_TCLPRO.REFERENCIA` + API Telecontrol | `EP_LISTABASICA/referenciaProduto/{referencia}` | `GET` | Persiste retorno em `AD_TCLLTB`, resolvendo produto por `AD_TCLPRO.REFERENCIA` e peca por `AD_TCLPCA.REFERENCIA` |
| Lista basica - delete produto | `AD_TCLPRO.REFERENCIA` | `EP_LISTABASICA/referenciaProduto/{referencia}` | `DELETE` | Solicita exclusao da lista basica do produto na API Telecontrol |
| Familia | API Telecontrol | `EP_FAMILIA` | `GET` | Grava/atualiza `AD_TCLFAM` |
| Linha | API Telecontrol | `EP_LINHA` | `GET` | Grava/atualiza `AD_TCLLIN` |
| Pedidos nao integrados | API Telecontrol | `EP_PEDIDOSNAOINTEG` | `GET` paginado | Grava/atualiza `AD_TCLPED` com JSON do pedido |
| Pedido exportado | `AD_TCLPED` | `EP_PEDIDOS` | `PUT` | Marca `AD_TCLPED.EXPORTADO = 'S'` |
| Faturamento | `AD_TCLPED` + notas `TGFCAB/TGFITE/TGFVAR` | `EP_FATURAMENTO` | `POST` | Marca `AD_TCLPED.FATURADO = 'S'` |
| Serie com revenda | `AD_TCLSER` view + `AD_TCLLOGSER` + `TGFPRO` | `EP_SERIE` | `POST` | Sincroniza a view para `AD_TCLLOGSER` sem duplicar e marca `AD_TCLLOGSER.PROCESSADO = 'S'` em sucesso ou duplicidade conhecida |
| Preco | `AD_TCLPRO` / `AD_TCLPCA` + `SNK_PRECO` | `EP_PRECOS` | `POST` quando preco local nulo; `PUT /siglaTabela/{VEN/GAR}/pecaReferencia/{REFERENCIA}` quando ja existe | Atualiza `PRECOVEN` e/ou `PRECOGAR` |
| Ordem de servico | API Telecontrol | `EP_OS` ou `/os/osvisaogeral` | `GET` | Integra dados de OS e apoia documento/pedido |

## Fluxo das Requisicoes HTTP

```mermaid
sequenceDiagram
    participant Acao as Action/Scheduler
    participant Serv as Service/UseCase
    participant Cfg as AD_TCLCFG
    participant Api as Api.java
    participant Auth as Autenticacao
    participant Tcl as API Telecontrol
    participant Log as AD_TCLLGT

    Acao->>Serv: executar fluxo
    Serv->>Cfg: carregar URL, token, env e endpoint
    Serv->>Serv: buscar dados locais e montar JSON
    Serv->>Api: GET/POST/PUT/DELETE
    Api->>Auth: Login()
    Auth->>Cfg: ler token/env
    Api->>Tcl: requisicao HTTP com headers
    Tcl-->>Api: status + body
    Api->>Log: registrar METHOD, URL, REQUEST, RESPONSE
    alt Sucesso 200/201/202/204
        Api-->>Serv: response body
        Serv->>Serv: atualizar flags/codigos locais
    else Erro HTTP
        Api->>Log: registrar erro e body retornado
        Api-->>Serv: exception com resumo tecnico
    end
```

## Fluxo de Serie

```mermaid
flowchart TB
    SC["PostSerieSc / PostSerieBt<br/>ou bridge ProdutoSerie"] --> SVC["EnviaSerieService"]
    SVC --> CFG["ConfigTelecontrol<br/>URL + EP_SERIE"]
    SVC --> SYNC["SerieRepository.sincronizarViewParaLog"]
    SYNC --> VIEW["AD_TCLSER<br/>view de origem"]
    SYNC --> LOGSER["AD_TCLLOGSER<br/>insere apenas novos registros"]
    SVC --> REP["SerieRepository.buscarPendentes"]
    REP --> Q["AD_TCLLOGSER<br/>PROCESSADO = N"]
    REP --> P["TGFPRO<br/>referencia por CODPROD"]
    SVC --> DTO["SeriePendente"]
    DTO --> MAP["ParseSerie.toJson"]
    MAP --> PAY["JSON NumeroSerieRevenda<br/>serie, referenciaProduto,<br/>revenda, datas"]
    PAY --> API["POST API Telecontrol"]
    API --> OK{"Sucesso ou<br/>duplicidade conhecida?"}
    OK -- "Sim" --> UPD["AD_TCLLOGSER.PROCESSADO = S"]
    OK -- "Nao" --> LOG["AD_TCLLGT com erro"]
```

Observacoes importantes:

- `AD_TCLSER` e a view de origem das series.
- `AD_TCLLOGSER` e a fila/controle de processamento.
- `AD_STP_INC_AD_TCLLOGSER` nao e mais necessaria para o envio Java atual; o insert na `AD_TCLLOGSER` e feito pelo `SerieRepository`.
- A referencia enviada no payload prioriza `TGFPRO.REFERENCIA`; `AD_TCLLOGSER.REFERENCIA` e fallback.

## Fluxo de Pedidos e Faturamento

```mermaid
flowchart TB
    API1["GET EP_PEDIDOSNAOINTEG<br/>por data e pagina"] --> BP["BuscaPedidos"]
    BP --> PED["AD_TCLPED<br/>PEDIDO, JSON, TIPOPEDIDO"]
    BP --> OS["BuscaOrdemServico<br/>quando item tem OS"]

    PED --> CTRL["PedidoControl.gravaPedido"]
    CTRL --> PARC["Parceiro por CNPJ"]
    CTRL --> PECAS["AD_TCLPCA por referencia"]
    CTRL --> NOTA["Cria nota Sankhya<br/>TGFCAB/TGFITE"]
    CTRL --> PED2["AD_TCLPED.NUNOTA"]

    PED2 --> FAT["EnvioFaturamento"]
    FAT --> VAR["TGFVAR vincula pedido/origem e NF"]
    FAT --> NF["TGFCAB/TGFITE nota liberada e NFe aprovada"]
    FAT --> API2["POST EP_FATURAMENTO"]
    API2 --> PED3["AD_TCLPED.FATURADO = S"]

    PED2 --> EXP["EnvioPedidoExportado"]
    EXP --> API3["PUT EP_PEDIDOS"]
    API3 --> PED4["AD_TCLPED.EXPORTADO = S"]
```

## Fluxo de Cadastros

```mermaid
flowchart LR
    subgraph Local["Sankhya / Oracle"]
        PRO["AD_TCLPRO"]
        PCA["AD_TCLPCA"]
        PST["AD_TCLPST"]
        LTB["AD_TCLLTB"]
        FAM["AD_TCLFAM"]
        LIN["AD_TCLLIN"]
        TGFPRO["TGFPRO"]
    end

    subgraph Services["Servicos"]
        SPRO["EnvioProduto"]
        SPCA["EnvioPeca"]
        SPST["EnvioPosto"]
        SLTB["EnvioListaBasica / SincronizaListaBasica / BuscaListaBasica"]
        SFAM["BuscaFamilias"]
        SLIN["BuscaLinhas"]
        BCAD["BuscaCadastrosTelecontrolService"]
    end

    subgraph ApiT["API Telecontrol"]
        EPRO["EP_PRODUTOS"]
        EPCA["EP_PECAS"]
        EPST["EP_POSTOS"]
        ELTB["EP_LISTABASICA"]
        EFAM["EP_FAMILIA"]
        ELIN["EP_LINHA"]
    end

    PRO --> SPRO --> EPRO
    PCA --> SPCA --> EPCA
    PST --> SPST --> EPST
    LTB --> SLTB --> ELTB
    ELTB --> SLTB --> LTB
    EFAM --> SFAM --> FAM
    ELIN --> SLIN --> LIN
    EPRO --> BCAD --> PRO
    EPCA --> BCAD --> PCA
    TGFPRO --> BCAD
```

## Modelo ER Simplificado

```mermaid
erDiagram
    AD_TCLCFG {
        number ID "via parametro IDCONFIGTLC"
        string URL
        string TOKEN
        string ENV
        string EP_PRODUTOS
        string EP_PECAS
        string EP_POSTOS
        string EP_LISTABASICA
        string EP_PEDIDOSNAOINTEG
        string EP_PEDIDOS
        string EP_FATURAMENTO
        string EP_SERIE
        string EP_PRECOS
        string EP_OS
    }

    AD_TCLLGT {
        number ID
        datetime DHALTER
        string SYNC
        string URL
        string REGISTRO
        string METHOD
        clob REQUEST
        string ERROR
        clob LOG
        clob RESPONSE
    }

    AD_TCLPRO {
        number CODPROD
        string REFERENCIA
        number COD_TELECONTROL
        datetime DTALTER
        datetime DHINTEG
        number PRECOVEN
        number PRECOGAR
    }

    AD_TCLPCA {
        number CODPROD
        string REFERENCIA
        number COD_TELECONTROL
        datetime DTALTER
        datetime DHINTEG
        number PRECOVEN
        number PRECOGAR
    }

    AD_TCLPED {
        number PEDIDO
        number NUNOTA
        string TIPOPEDIDO
        clob JSON
        string EXPORTADO
        string FATURADO
    }

    AD_TCLLOGSER {
        number ID
        number CODPROD
        string SERIE
        string REFERENCIA
        date DATAVENDA
        date DATAFABRICACAO
        datetime DTALTER
        string PROCESSADO
        string REVENDA
    }

    AD_TCLSER {
        number CODPROD
        string SERIE
        string REFERENCIA
        date DATAVENDA
        date DATAFABRICACAO
        string REVENDA
    }

    TGFPRO {
        number CODPROD
        string REFERENCIA
        string DESCRPROD
    }

    TGFCAB {
        number NUNOTA
        number NUMNOTA
        number CODPARC
        string STATUSNOTA
        string STATUSNFE
    }

    TGFITE {
        number NUNOTA
        number SEQUENCIA
        number CODPROD
        number QTDNEG
        number VLRUNIT
        number AD_TCLPEDITE
    }

    TGFVAR {
        number NUNOTA
        number NUNOTAORIG
        number SEQUENCIA
        number SEQUENCIAORIG
        number QTDATENDIDA
    }

    TGFPAR {
        number CODPARC
        string CGC_CPF
    }

    AD_TCLPRO ||--o| TGFPRO : "CODPROD"
    AD_TCLPCA ||--o| TGFPRO : "CODPROD"
    AD_TCLSER }o--|| TGFPRO : "CODPROD"
    AD_TCLSER ||--o{ AD_TCLLOGSER : "CODPROD/SERIE/REFERENCIA/REVENDA"
    AD_TCLLOGSER }o--|| TGFPRO : "CODPROD"
    AD_TCLPED ||--o| TGFCAB : "NUNOTA"
    TGFCAB ||--o{ TGFITE : "NUNOTA"
    TGFVAR }o--|| TGFCAB : "NUNOTA/NUNOTAORIG"
    TGFCAB }o--|| TGFPAR : "CODPARC"
```

## Pacotes Principais

```txt
br.zaya.telecontrol
  actions/                     bridges antigas ainda usadas por metadata Sankhya
  produto/actions              actions reais de produto
  produto/application          casos de uso de produto
  produto/infrastructure       mappers de produto
  peca/actions                 actions reais de peca
  peca/application             casos de uso de peca
  serie/actions                PostSerieBt / PostSerieSc
  serie/application            EnviaSerieService
  serie/domain                 SeriePendente
  serie/infrastructure         SerieRepository / ParseSerie
  preco/actions                EnvioPrecosBt
  preco/application            EnvioPrecoService
  listabasica/actions          EnvioListaBasicaBt / SincronizaListaBasicaBt / BuscaListaBasicaBt / DeleteListaBasicaProdutoBt
  listabasica/application      EnvioListaBasicaService / SincronizaListaBasicaService / BuscaListaBasicaService
  pedidos/actions              BuscaPedidosBt / BuscaPedidosSc
  pedidos/application          BuscaPedidosService / EnvioPedidoExportadoService
  faturamento/actions          EnviaFaturamentoBt / EnviaFaturamentoSc
  faturamento/application      EnvioFaturamentoService
  ordemservico/actions         Busca/Sync OS
  osintegracao/actions         Actions e schedulers de integracao de OS
  osintegracao/application     Use cases e geracao documental
  shared/config                ConfigTelecontrol
  shared/infrastructure/http   Api / Autenticacao
  shared/utils                 LogUtil / ActionMessageUtil / LockManager
  services                     legado interno preservado
  parsers                      parsers legados preservados
  models                       modelos JSON

br.com.zaya.telecontrol        bridges de compatibilidade
ProdutoSerie                   bridges de compatibilidade da rotina antiga de serie
```

## Pontos de Melhoria Arquitetural

### Prioridade Alta

- Criar repositorios por feature para reduzir SQL/Jape direto dentro dos services legados.
- Migrar chamadas diretas ao `Api` para a interface `ITelecontrolApiClient`, como ja iniciado na parte de OS.
- Padronizar retorno das actions com `ActionMessageUtil` em todos os pontos de entrada.
- Confirmar e documentar FQCNs cadastrados no Sankhya antes de remover qualquer bridge.

### Prioridade Media

- Separar claramente `domain`, `application`, `infrastructure` para produto, peca, pedidos e faturamento, como ja foi iniciado em series.
- Criar DTOs de request/response especificos por endpoint para reduzir dependencia de JSON manual.
- Centralizar regra de atualizacao de flags (`DHINTEG`, `EXPORTADO`, `FATURADO`, `PROCESSADO`) em repositorios.
- Adicionar testes unitarios para mappers e testes de integracao simulando respostas da API.

### Prioridade Baixa

- Evoluir lock em memoria para lock transacional em banco quando houver multiplos nos executando schedulers.
- Versionar scripts DDL das tabelas `AD_TCL*`.
- Criar dashboard operacional sobre `AD_TCLLGT`.

## Pontos de Atencao

- `AD_TCLSER` faz parte do fluxo atual como view de origem.
- `AD_TCLLOGSER` e a fila atual de series e guarda controle por `PROCESSADO`.
- A procedure `AD_STP_INC_AD_TCLLOGSER` pode existir no banco, mas o Java atual faz o insert na `AD_TCLLOGSER` sem depender dela.
- O envio de series marca duplicidade conhecida da API como processada para evitar reenvio infinito.
- `ApagarLOGBt` executa limpeza de `AD_TCLLGT`; usar apenas com intencao explicita.
- As bridges legadas existem por compatibilidade, nao devem virar ponto de evolucao principal.

## A Confirmar

- Valores atuais de `AD_TCLCFG.EP_*` em homologacao/producao.
- FQCNs efetivamente cadastrados nos agendadores, botoes e eventos Sankhya.
- Se `TGFPRO.REFERENCIA` sempre deve prevalecer sobre `AD_TCLLOGSER.REFERENCIA`.
- Validar em homologacao se a view `AD_TCLSER` possui todos os campos usados pelo Java: `CODPROD`, `SERIE`, `REFERENCIA`, `DATAVENDA`, `DATAFABRICACAO` e `REVENDA`.
- Periodicidade oficial de cada scheduler.
- Estrutura completa das tabelas `AD_TCL*` em producao.
