# MA Hospitalar — Preço Dinâmico e Integração Genesis

Customizacao Java 8/Oracle para formar o preco de venda pela margem do produto,
persistir a memoria de calculo e controlar a margem minima consolidada do pedido.

O mesmo projeto e o mesmo `MAHospitalar.jar` também contêm a integração da Base
Instalada Genesis. Os módulos são independentes: publicar o JAR não ativa a
integração. ATENCAO: a configuracao atual de homologacao usa integracao ativa,
envio real e lote 1. Desative a agenda antes da publicacao e valide o ambiente.

## Fluxo Genesis no Sankhya (V2.1)

1. `IntegracaoGenesisScheduleAction.onTime` executa sob transacao gerenciada.
2. `ConfigGenesisRepository` valida a central `NUPAR=1`, TOPs e token protegido.
3. `CandidatoGenesisRepository` consulta `AD_VW_GENESIS_ESTOQUE`: somente M1,
   com serie, pendente/reprocessar e tentativas abaixo do limite. Deduplica por
   nota e serie; o controle usa a chave empresa|nota|sequencia|produto|serie.
4. `IntegracaoGenesisRunner` garante cliente e setor, consulta o modelo local
   por codigo externo e reutiliza o ID remoto quando encontrado. Se ausente,
   garante material, fabricante (CNPJ obrigatorio) e modelo.
5. `GenesisSyncService` consulta antes de criar: POST se ausente, PUT quando
   divergente. O item-material recebe IDs Genesis de cliente, setor e modelo,
   serie, datas epoch, valores e contexto do movimento.
6. Retornos atualizam a base instalada e `AD_GENMOVEST`, incluindo HTTP, hash,
   tentativas e status. A acao `AcaoReprocessarGenesis` registra usuario,
   data e zera tentativas. Nao executa rollback manual na transacao gerenciada.

### Arquitetura

```text
Agenda -> Runner -> Repositorios Sankhya -> Factory de payload
               -> SyncService -> Cliente HTTP -> Genesis
               -> Base instalada + controle AD_GENMOVEST
Dashboard HTML -> consolidacao de movimentos -> ordenacao/paginacao/erros
```

`webapp/genesis` apresenta consolidacao, filtros, ordenacao, paginacao e erros.
Regras criticas de elegibilidade permanecem no Java/SQL. Historico nao deve ser
excluido sem backup, homologacao e aprovacao; nunca apagar controles de envio
para forcar um novo POST.

### Configuracao efetiva e pendencias

Java 8, Oracle, modulo Java legado Sankhya. HML ativa, simulacao false,
timeout de conexao 15 s, leitura 30 s, limite 5 tentativas, lote 1, epoch MS.
O token vem de `AD_CENTPARAM.TOKENGENESIS` e vai no cabecalho `token`.
URL e flags de HML ainda estao fixas no repositorio de configuracao: externalizar
antes de producao, sem alterar contratos existentes.

Bloqueio comprovado: a API de clientes aceita filtros `nome`/`codigoExterno`,
nao `cnpj`. A V2.1 ainda tenta documento e pode falhar no POST por CNPJ existente.
Busca por nome seguida de validacao exata e unica de CPF/CNPJ e o fluxo proposto,
NAO implementado. IDs importados nao podem ser considerados validos entre bases
HML/PRD. Conectividade nao significa homologacao ponta a ponta aprovada.

### Publicacao e aceite

Desativar agenda, guardar JAR anterior e backup restauravel, validar metadados
e view na versao destino, publicar JAR/dashboard e testar com lote 1.
Confirmar cliente por documento, setor pelo cliente, modelo pelo produto e
item pela serie; repetir o movimento e provar ausencia de duplicidade.
Rollback operacional: desativar agenda e republicar artefato anterior;
rollback de SQL deve seguir os scripts especificos e o backup homologado.
Chamadas HTTP nao sao revertidas pelo rollback do banco: reconciliar IDs antes
de repetir operacoes apos falhas parciais.

## Artefatos

- `sql/00_create_objects.sql`: tabelas AD, campos adicionais, indices e validacoes.
- `sql/01_price_engine.sql`: package de calculo e procedure `STP_MAH_PRECO_DINAMICO`.
- `sql/02_sankhya_parameters.sql`: checklist/comandos nativos mantidos comentados.
- `sql/03_homologacao_memoria.sql`: 16 cenarios de referencia da planilha.
- `src/br/com/zaya/mahospitalar/preco`: calculadora, auditoria e evento de liberacao.
- `metadata/`: dicionario de campos e definicao das telas adicionais.
- `docs/implantacao.md`: ordem de instalacao, parametrizacao e rollback.
- `sql/genesis/`: schema, views, template de carga pontual e validações Genesis.
- `src/br/com/zaya/mahospitalar/genesis`: cliente HTTP, outbox, conciliação, agenda e ação manual.
- `webapp/genesis/`: dashboard operacional.
- `docs/genesis/`: arquitetura, configuração e implantação da integração enxuta.
- `docs/genesis/implantacao-consolidada.md`: objetos, campos, finalidade e ordem de deploy.
- `docs/genesis/repasse-tecnico-integracao-genesis.md`: guia interno para testes, homologação, sustentação e evolução.
- `docs/genesis/intpdd-integracao-genesis-as-built.md`: IntPDD do desenho efetivamente implementado e seus gates.

## Build

```bash
./gradlew clean test metadataPackage
```

O pacote de entrega e gerado em
`build/metadata-package/MAHospitalar-PrecoDinamico.zip`.

Para gerar a entrega Genesis, contendo o mesmo JAR:

```bash
./gradlew clean genesisPackage
```

O resultado fica em `build/genesis-package/MAHospitalar-Genesis.zip`. Smoke tests
reais são desabilitados por padrão e só executam com `MAH_GENESIS_SMOKE=S`.
