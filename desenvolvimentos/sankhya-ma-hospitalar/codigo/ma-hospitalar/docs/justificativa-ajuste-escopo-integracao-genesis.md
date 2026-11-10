# Justificativa Técnica para Ajuste de Escopo

## Integração Sankhya × Genesis — Base Instalada

| Informação | Conteúdo |
|---|---|
| Cliente | MA Hospitalar |
| Assunto | Realinhamento do escopo da integração Sankhya × Genesis |
| Finalidade | Subsidiar decisão gerencial, técnica e contratual sobre a continuidade do projeto |
| Público | MA Hospitalar, gestão do projeto, equipes funcionais e equipes técnicas envolvidas |
| Classificação | Documento de análise e justificativa de ajuste de escopo |
| Situação | Para validação e formalização pelas partes |

> Este documento não atribui responsabilidade individual e não apresenta estimativas comerciais. Seu objetivo é demonstrar, com rastreabilidade, a diferença entre a premissa formal originalmente documentada e o processo operacional posteriormente esclarecido.

## 1. Objetivo

Este documento tem como objetivo justificar tecnicamente a necessidade de revisão do escopo da integração entre o Sankhya e a plataforma Genesis.

A análise confronta cinco fontes:

1. o escopo funcional originalmente aprovado;
2. o IntPDD produzido a partir desse escopo;
3. os esclarecimentos registrados na reunião de alinhamento técnico;
4. a integração Protheus atualmente funcional, usada como referência operacional;
5. a implementação Sankhya desenvolvida conforme a premissa original.

O ponto central da análise é determinar se a necessidade atual pode ser atendida apenas pela troca do endpoint de estoque pelo endpoint de itens-materiais ou se representa uma ampliação funcional que exige nova arquitetura, novas estruturas de dados e nova estratégia de transição.

## 2. Conclusão executiva

A implementação Sankhya atual está, em sua essência, aderente à premissa formal inicialmente documentada: identificar movimentos de entrada e saída de estoque controlados por série, consolidá-los em uma VIEW, apresentá-los em dashboard e enviá-los à Genesis pelo recurso de estoque.

O cenário esclarecido posteriormente é materialmente diferente. Para substituir o Protheus, o Sankhya deverá assumir a manutenção da Base Instalada na Genesis, o que exige:

- sincronização de clientes;
- criação obrigatória de um setor padrão por cliente;
- sincronização de materiais ou grupos tecnológicos;
- sincronização de fabricantes;
- sincronização de modelos;
- criação e atualização de itens-materiais, que representam os equipamentos individualizados por série;
- armazenamento dos identificadores retornados pela Genesis;
- absorção e conciliação da base legada;
- distinção entre registros de origem Genesis e registros de origem Sankhya;
- continuidade da identidade do equipamento durante a transição Protheus → Sankhya;
- prevenção de duplicidades durante inclusões, atualizações e reprocessamentos.

Essas responsabilidades não estão definidas como fluxos de integração no escopo original nem no IntPDD. Clientes, parceiros, produtos, grupos e marcas aparecem nesses documentos como dados correlacionados ao movimento de estoque, não como cadastros independentes que precisam ser consultados, incluídos, atualizados e relacionados por IDs internos da Genesis.

Portanto, o atendimento do cenário atual deve ser tratado como **ampliação funcional e técnica de escopo**, e não como simples correção do desenvolvimento existente ou substituição isolada de endpoint.

## 3. Fontes e hierarquia das evidências

### 3.1 Fontes analisadas

| Código | Fonte | Papel na análise |
|---|---|---|
| ESC-2764 | `2764 - Definição de Escopo de Customização - API Genesis.pdf` | Linha de base funcional originalmente documentada |
| INTPDD-001 | `ZY-MAH-2026-GENESIS-INTPDD-001.pdf` | Tradução técnica da linha de base original |
| REU-ALIN | `[MA Hospitalar] Alinhamento Técnico Genesis - Transcrição` | Registro dos esclarecimentos posteriores sobre o processo real |
| PRO-GEN | `Códigos de integração Protheus para Genesis (1).zip` | Evidência executável do fluxo operacional anterior |
| SNK-GEN | `MA_Hospitalar_Genesis_Java8_Completo_20260820-133505.zip` | Evidência da implementação Sankhya construída a partir do escopo e do IntPDD |

### 3.2 Hierarquia utilizada

- **Escopo nº 2764:** estabelece o que foi formalmente solicitado e delimitado.
- **IntPDD:** demonstra como a equipe técnica traduziu o escopo em arquitetura e contrato de integração.
- **Reunião de alinhamento:** registra a necessidade operacional esclarecida após a implementação inicial.
- **Código Protheus:** comprova quais entidades, dependências e operações eram necessárias no processo funcional anterior.
- **Código Sankhya:** comprova o alcance efetivamente implementado e permite verificar sua aderência à premissa original.

O código Protheus é usado como evidência do processo vigente, e não como especificação que deva ser copiada literalmente. Regras técnicas antigas, fragilidades e particularidades do Protheus devem ser reavaliadas na solução Sankhya.

## 4. Processo originalmente descrito no escopo

### 4.1 Objeto principal da integração

A seção 4 do ESC-2764 define como processo a desenvolver a integração dos **movimentos de estoque controlados por série** do Sankhya com a Genesis.

A seção 5 reforça que a Genesis consumiria movimentos de entrada e saída decorrentes de operações como:

- implantação de saldo de estoque;
- ajuste de saldo de estoque;
- compra;
- remessa;
- retorno.

Na mesma seção, o documento delimita que, naquele primeiro momento, a integração seria exclusivamente destinada aos movimentos de estoque. Também informa que outros processos exigiriam novo levantamento e nova definição de customização.

### 4.2 Sequência operacional prevista

O processo originalmente descrito pode ser representado da seguinte forma:

```text
Lançamento de nota ou movimento no Sankhya
                    │
                    ▼
      Verificação da TOP parametrizada
                    │
                    ▼
       Produto controlado por série
                    │
                    ▼
 VIEW única com empresa, parceiro, produto,
     nota, item, série, estoque e local
             ┌──────┴──────┐
             ▼             ▼
         Dashboard     Ação agendada
                           │
                           ▼
               API Genesis — Estoques
```

### 4.3 Consulta e dashboard

A seção 6.1 determina a construção de um dashboard de entradas e saídas de estoque. A consulta foi didaticamente separada em parceiros, produtos e cabeçalho/itens, mas o próprio documento estabelece que a implementação deveria resultar em uma única QUERY ou VIEW.

Assim:

- os dados de parceiro serviriam para contextualizar o movimento;
- os dados de produto e grupo serviriam para identificar o item movimentado;
- a marca seria um atributo do produto;
- a série individualizaria a linha do movimento;
- nenhum desses blocos foi definido como rotina cadastral autônoma na Genesis.

### 4.4 Ação agendada

A seção 6.2 prevê uma ação agendada responsável por obter os movimentos de entrada e saída associados às TOPs configuradas e integrá-los à Genesis.

A regra de negócio da seção 11 mantém o mesmo gatilho: todo movimento de entrada ou saída com TOP configurada deveria aparecer no dashboard e ser integrado.

A seção 16 repete como premissa que o objeto da integração seria o movimento de estoque e que processos adicionais não faziam parte da proposta.

### 4.5 O que o escopo não descreve

O ESC-2764 não define:

- consulta, criação ou atualização de clientes na Genesis;
- criação de setor padrão obrigatório;
- consulta, criação ou atualização do cadastro Genesis de materiais;
- consulta, criação ou atualização de fabricantes;
- consulta, criação ou atualização de modelos;
- criação ou atualização do equipamento em `/itens-materiais/`;
- sequência de dependências entre esses cadastros;
- armazenamento, no Sankhya, dos IDs internos retornados pela Genesis;
- extração e importação da Base Instalada legada;
- regra de origem Genesis/Sankhya;
- tratamento de equipamentos existentes na Genesis sem nota correspondente no Sankhya;
- transição operacional do Protheus para o Sankhya;
- atualização dos códigos externos legados;
- reconciliação ou cutover entre os dois ERPs.

## 5. Como o IntPDD traduziu o escopo original

O INTPDD-001 preserva a mesma premissa funcional do ESC-2764.

### 5.1 Arquitetura definida

Nas páginas 2 a 4, o documento define uma arquitetura composta por:

- VIEW de consolidação de movimentos;
- dashboard de estoque por série;
- tabela de controle de integração;
- ação agendada Java;
- cliente HTTP;
- DTO de estoque;
- repositório de movimentos.

### 5.2 Contrato adotado

Nas páginas 7 e 8, o mapeamento técnico é explicitamente descrito como **VIEW → EstoqueJson**.

Na página 10, o contrato selecionado é:

- recurso: Estoques;
- método: POST;
- caminho: `/api/v1/estoques/`;
- corpo: `EstoqueJson`.

Nas páginas 11 a 13, tanto o cliente HTTP quanto a ação agendada são exemplificados enviando `EstoqueGenesisDTO` para o endpoint de estoques.

### 5.3 Pendências reconhecidas, mas não resolvidas

O IntPDD registra dúvidas sobre IDs de categoria, fabricante e fornecedor, bem como sobre o uso do código externo. Entretanto, essas dúvidas aparecem como campos a confirmar no payload de estoque, não como uma árvore cadastral obrigatória a ser criada antes do equipamento.

O documento também não define fluxo de importação da Base Instalada, transição de origem, criação de setor ou sincronização completa dos cadastros.

Conclui-se que o IntPDD não ampliou autonomamente o escopo: ele materializou tecnicamente a premissa formal disponível naquele momento.

## 6. Processo efetivamente necessário após o esclarecimento

### 6.1 Sankhya como sucessor do Protheus

Na reunião, em 13:17, foi esclarecido que o Protheus deixará de ser utilizado e que a integração passará a ser executada pelo Sankhya.

Essa mudança significa que o Sankhya não poderá apenas informar eventos de estoque. Ele precisará assumir as responsabilidades cadastrais necessárias para que a Genesis continue reconhecendo clientes, equipamentos e seus relacionamentos após o desligamento do Protheus.

### 6.2 Árvore obrigatória da Base Instalada

Entre 16:46 e 20:48, a reunião esclarece a estrutura conceitual da Genesis. Entre 34:29 e 36:10, a árvore necessária é consolidada tecnicamente:

```text
Cliente
   └── Setor obrigatório

Material ou grupo tecnológico
   └── Modelo
          ├── Fabricante
          └── Item-material ou equipamento individual
                   └── Número de série
```

Em 18:36, também foi esclarecido que a MA Hospitalar não utiliza uma setorização detalhada, mas que a Genesis exige ao menos um setor. A regra operacional indicada é criar um setor padrão, normalmente repetindo o nome do cliente.

### 6.3 Item-material não é movimento de estoque

Na Genesis, o item-material representa o equipamento individual, identificado por número de série e relacionado a cliente, setor, material, modelo e fabricante.

O movimento de estoque é um evento ou informação correlacionada. Ele não substitui o cadastro do equipamento nem resolve as dependências necessárias à sua existência na Base Instalada.

Por essa razão, a mudança de `/estoques/` para `/itens-materiais/` altera o objeto funcional da integração:

| Endpoint original | Objeto representado |
|---|---|
| `/estoques/` | Produto, serviço, peça ou posição/movimento relacionado a estoque |
| `/itens-materiais/` | Equipamento individual da Base Instalada, vinculado à árvore cadastral |

### 6.4 Equipamentos sem nota no Sankhya

Entre 29:24 e 33:18, foi explicado que existem equipamentos pertencentes ao cliente ou adquiridos de terceiros que já constam na Genesis, mas que não possuem movimento de nota correspondente no ERP.

Esses equipamentos podem permanecer como estoque de terceiro em poder de terceiro até que ocorra uma remessa para conserto ou outra movimentação no Sankhya.

Essa condição invalida a premissa de que toda a Base Instalada poderia ser reconstruída apenas a partir de `TGFCAB`, `TGFITE`, `TGFESE`, `TGFSER` e `TGFEST`. Quando não existe nota, a VIEW orientada a movimentos não possui fato de origem suficiente para gerar o registro.

### 6.5 Origem Genesis e origem Sankhya

A reunião estabelece dois estados operacionais:

- **Origem Genesis:** equipamento legado existente na Genesis sem movimento correspondente no Sankhya.
- **Origem Sankhya:** equipamento novo ou equipamento legado que passou a ter movimentação e controle efetivo no Sankhya.

O fluxo de transição esperado é:

```text
Equipamento legado na Genesis
            │
            ▼
Espelho e vínculo no Sankhya sem implantação de saldo
            │
            ▼
Primeira movimentação identificada no Sankhya
            │
            ▼
Atualização do mesmo item-material na Genesis
            │
            ▼
Origem operacional passa a ser SANKHYA
```

A atualização deve preservar a identidade do equipamento. Criar um novo item-material quando já existe um registro correspondente produziria duplicidade e perda de histórico.

### 6.6 Códigos externos e IDs Genesis

Entre 42:41 e 45:48, foi solicitada uma extração da base contendo os códigos externos. A reunião reconhece que esses códigos são necessários para ligar o legado ao novo ERP e que poderão ser substituídos por referências Sankhya depois da conciliação.

Para que essa substituição seja segura, a solução precisa manter:

- ID interno Genesis;
- código externo legado;
- código externo atual;
- chave correspondente no Sankhya;
- entidade a que o vínculo pertence;
- situação da conciliação;
- histórico mínimo de atualização.

Essas informações não existem na modelagem original de movimentos.

## 7. Evidência do processo funcional no Protheus

O projeto Protheus contém rotinas separadas para cada parte da árvore cadastral.

| Arquivo | Entidade/endpoint | Evidência funcional |
|---|---|---|
| `GENM010 - Clientes.prw` | `clientes/` | Consulta por código externo, inclui ou atualiza e armazena o ID Genesis no cliente |
| `GENM060 - Setor Cliente.prw` | `setores/` | Cria/atualiza setor vinculado ao ID Genesis do cliente |
| `genm030 - Grupo de produto.prw` | `materiais/` | Sincroniza o grupo tecnológico e armazena o ID do material |
| `genm040 - Marcas.fabricantes.prw` | `empresas/` | Sincroniza marca como fabricante/fornecedor |
| `GENM020 - Produtos.prw` | `modelos/` | Sincroniza modelo vinculado ao fabricante e ao material |
| `genm050 - Base Instalada.prw` | `itens-materiais/` | Envia equipamento com cliente, setor, modelo, material e série |
| `GENFWS - Acesso API.prw` | Infraestrutura comum | Implementa GET por código externo, POST e PUT por recurso |
| `GENM070 - Peças, serviços e preços.prw` | `estoques/` | Trata separadamente peças, serviços e preços |

### 7.1 Dependências comprovadas pelo payload Protheus

O `genm050` monta o item-material com referências como:

- `idCliente`;
- `idSetor`;
- `idModelo`;
- `idMaterial`;
- número de série;
- datas de aquisição e instalação;
- situação;
- garantia;
- marca;
- código externo.

Isso comprova que o item-material não pode ser enviado corretamente antes que os cadastros predecessores existam e seus IDs tenham sido resolvidos.

### 7.2 Separação entre Base Instalada e estoque comercial

O Protheus mantém duas rotinas distintas:

- `genm050`, voltada à Base Instalada e a itens-materiais;
- `GENM070`, voltada a peças, serviços e preços no recurso de estoques.

Essa separação reforça que o endpoint de estoque adotado pelo escopo original não equivale ao cadastro de equipamentos da Base Instalada.

## 8. Evidência da implementação Sankhya atual

### 8.1 Elementos aderentes ao escopo original

O projeto Sankhya implementa os componentes previstos no IntPDD:

- `AD_VW_GENESIS_ESTOQUE`;
- `AD_GENMOVEST` para controle de movimento;
- `MovimentoEstoque`;
- `EstoqueGenesisDTO`;
- `PayloadBuilder`;
- `MovimentoEstoqueRepository`;
- `GenesisHttpClient`;
- `IntegracaoGenesisScheduleAction`;
- dashboard baseado na VIEW e na tabela de controle.

O cliente HTTP define `/api/v1/estoques/` como destino de envio. O repositório busca pendências em `AD_VW_GENESIS_ESTOQUE`, e o builder transforma cada movimento em `EstoqueGenesisDTO`.

Essa composição é coerente com o ESC-2764 e com o INTPDD-001.

### 8.2 Elementos ausentes para o cenário atual

Não foram identificados no projeto:

- DTOs específicos de cliente, setor, material, fabricante, modelo e item-material;
- métodos GET/POST/PUT para a árvore cadastral;
- repositórios de mapeamento de IDs Genesis;
- persistência do código externo legado e atual por entidade;
- importador ou staging da Base Instalada;
- regra de origem Genesis/Sankhya;
- reconciliação de séries legadas;
- ordem de dependência entre os endpoints;
- atualização do item-material existente por ID;
- fluxo de cutover Protheus → Sankhya.

### 8.3 Pontos técnicos que também precisam ser revistos

- A chave de idempotência atual identifica um movimento, não a identidade estável do equipamento.
- A VIEW parte de notas e movimentos e, portanto, não representa equipamentos sem documento Sankhya.
- A geração de chave primária por `MAX(ID)+1` não é segura em execução concorrente.
- O limite máximo de tentativas está modelado, mas não é efetivamente aplicado no processamento.
- Há divergência entre a criação de garantia em `TGFSER` e sua leitura em `TGFITE` pela VIEW.
- URL e token foram fixados temporariamente no código; esses valores devem ser removidos, protegidos e rotacionados, sem reproduzi-los neste documento.

Esses pontos não alteram a conclusão principal: o projeto atual implementa um fluxo de movimentos, enquanto o cenário real exige sincronização de entidades e estado da Base Instalada.

## 9. Matriz consolidada de divergências

### 9.1 Legenda

- **Previsto:** definido de forma suficiente para implementação.
- **Parcial:** citado como atributo ou dependência, sem fluxo completo.
- **Ausente:** não definido na fonte.
- **Contraditório:** a fonte orienta comportamento diferente do necessário.
- **Comprovado:** existe evidência funcional no código ou na reunião.

| Requisito | ESC-2764 | INTPDD-001 | Reunião | Protheus | Sankhya atual | Conclusão |
|---|---|---|---|---|---|---|
| Enviar movimentos de estoque seriado | Previsto | Previsto | Reorientado | Parcial | Implementado | Aderente à premissa original, insuficiente para o novo fluxo |
| Sincronizar clientes | Parcial: dados no movimento | Ausente como endpoint | Necessário | Comprovado em `GENM010` | Ausente | Ampliação de escopo |
| Criar setor padrão | Ausente | Ausente | Necessário e obrigatório | Comprovado em `GENM060` | Ausente | Ampliação de escopo |
| Sincronizar materiais | Parcial: grupo no payload | Ausente como endpoint | Necessário | Comprovado em `genm030` | Ausente | Ampliação de escopo |
| Sincronizar fabricantes | Parcial: marca | Campo de ID a confirmar | Necessário | Comprovado em `genm040` | Ausente | Ampliação de escopo |
| Sincronizar modelos | Ausente como fluxo | Ausente como endpoint | Necessário | Comprovado em `GENM020` | Ausente | Ampliação de escopo |
| Criar/atualizar item-material | Ausente | Contraditório: usa EstoqueJson | Fluxo principal confirmado | Comprovado em `genm050` | Ausente | Mudança do objeto principal da integração |
| Consultar por código externo | Não definido | Parcialmente discutido | Necessário | Comprovado em `GENFWS` | Apenas GET genérico de autenticação/listagem | Novo requisito de sincronização |
| Persistir IDs Genesis | Ausente | Ausente como modelo por entidade | Necessário | Comprovado nos cadastros Protheus | Ausente | Novo modelo de dados |
| Importar Base Instalada | Ausente | Ausente | Necessário | Base existente comprovada | Ausente | Nova frente de migração |
| Espelhar legado sem saldo | Ausente | Ausente | Necessário | Não aplicável ao ERP novo | Ausente | Nova regra de transição |
| Origem Genesis/Sankhya | Ausente | Ausente | Confirmada | Implícita no legado | Ausente | Nova regra de negócio |
| Evitar duplicidade do equipamento | Parcial: movimento idempotente | Previsto apenas por movimento | Necessário | ID por cadastro | Chave por nota/item/série | Idempotência precisa mudar de movimento para entidade |
| Dashboard operacional | Previsto | Previsto | Continua necessário | Não é a referência principal | Protótipo implementado | Deve ser ampliado para origem, mapas e divergências |
| Cutover Protheus → Sankhya | Ausente | Ausente | Confirmado | Sistema de origem atual | Ausente | Nova atividade de transição |
| Segurança de credenciais | Documento contém acessos | Recomenda cofre | Não detalhado | Parâmetros externos | Credencial fixa temporária | Exige saneamento antes da continuidade |

## 10. Por que não basta trocar o endpoint

### 10.1 Dependência referencial

O endpoint de itens-materiais exige referências que não são resolvidas pelo payload de estoque. Antes de enviar o equipamento, a integração precisa saber os IDs Genesis de cliente, setor, material e modelo, além do fabricante associado ao modelo.

### 10.2 Ciclo de vida diferente

O desenvolvimento atual cria registros de estoque a partir de movimentos. O novo fluxo precisa:

1. localizar a entidade pelo mapa ou código externo;
2. consultar a Genesis quando o vínculo ainda não estiver disponível;
3. criar a entidade se ela não existir;
4. armazenar o ID retornado;
5. atualizar por ID quando o cadastro já existir;
6. respeitar a ordem das dependências;
7. reprocessar sem duplicar.

### 10.3 Identidade do equipamento

A chave atual, formada por empresa, nota, sequência, produto e série, identifica um evento de movimento. Um mesmo equipamento pode participar de várias notas ao longo do tempo. Se cada nota for tratada como nova identidade, o resultado poderá ser a duplicação do item-material.

A nova solução precisa manter uma identidade estável por equipamento e uma identidade separada para cada evento de sincronização.

### 10.4 Ausência de nota no legado

Equipamentos existentes apenas na Genesis não aparecem na fonte de dados atual. Logo, nenhuma alteração isolada no `PayloadBuilder` é suficiente para integrá-los. É necessária uma carga ou espelho independente das tabelas de movimento.

### 10.5 Transição entre ERPs

O Protheus mantém IDs internos Genesis em seus cadastros. Se esses vínculos não forem extraídos e preservados na passagem para o Sankhya, a nova integração poderá criar novamente clientes, materiais, modelos ou equipamentos já existentes.

## 11. Riscos de continuar sem formalizar o ajuste

- Duplicação de equipamentos e cadastros na Genesis.
- Perda do relacionamento entre o registro legado e o novo registro Sankhya.
- Falha no envio do item-material por ausência de IDs obrigatórios.
- Base Instalada incompleta por exclusão de equipamentos sem nota.
- Atualização do cliente ou equipamento errado em casos de código externo ambíguo.
- Divergência entre dashboard, estoque Sankhya e Base Instalada Genesis.
- Operação simultânea ou lacuna operacional durante o desligamento do Protheus.
- Impossibilidade de definir critérios objetivos de homologação.
- Exposição operacional causada por credenciais mantidas em código ou documentos.
- Replanejamento informal sem atualização correspondente de prazo, responsabilidades e aceite.

## 12. Escopo revisado recomendado

### 12.1 Itens a incluir na versão revisada

#### Sincronização cadastral

- Cliente.
- Setor padrão obrigatório.
- Material ou grupo tecnológico.
- Fabricante.
- Modelo.
- Item-material/equipamento por série.

#### Contrato de integração

- GET por código externo quando aplicável.
- POST para inclusão.
- PUT para atualização por ID Genesis.
- DTO e validações específicas por endpoint.
- Política de campos obrigatórios e opcionais.
- Tratamento diferenciado de erro funcional, autenticação e indisponibilidade.

#### Modelo de vínculo

- Entidade.
- Chave Sankhya.
- ID Genesis.
- Código externo legado.
- Código externo atual.
- Origem do registro.
- Hash e situação de sincronização.
- Datas de criação, atualização e conciliação.

#### Base legada

- Recepção da extração Genesis.
- Carga em staging.
- Validação de referências e duplicidades.
- Conciliação com parceiros, grupos, produtos e séries Sankhya.
- Espelho da Base Instalada sem criação de saldo fiscal.
- Relatório de registros conciliados, pendentes e ambíguos.

#### Orquestração

- Fila ou outbox por entidade.
- Resolução da árvore na ordem correta.
- Controle de concorrência.
- Idempotência por entidade e equipamento.
- Retentativas e reprocessamento controlado.
- Monitoramento e auditoria.

#### Operação e gestão

- Dashboard ampliado.
- Origem Genesis/Sankhya.
- Situação de conciliação.
- IDs e códigos externos.
- Erros e reprocessamentos.
- Indicadores de fila e divergência.

#### Transição

- Congelamento dos jobs Protheus.
- Extração diferencial final.
- Reconciliação pré-cutover.
- Ativação controlada da integração Sankhya.
- Plano de retorno operacional.
- Monitoramento assistido após a virada.

### 12.2 Itens explicitamente fora da versão revisada

Salvo nova decisão formal, devem permanecer fora desta primeira versão:

- estoque comercial de peças;
- serviços;
- preços de custo e venda;
- regras equivalentes ao `GENM070`;
- outros processos Genesis não relacionados à Base Instalada;
- implantação fiscal de saldo para representar o legado;
- alterações funcionais não descritas neste realinhamento.

Essa exclusão é importante para impedir que a análise da Base Instalada seja interpretada como reprodução integral de todas as integrações existentes no Protheus.

## 13. Dependências externas para o novo escopo

O desenvolvimento e a homologação dependem dos seguintes insumos:

1. extração Genesis prometida na reunião, contendo IDs, códigos externos e relacionamentos;
2. contrato atualizado dos endpoints de clientes, setores, materiais, empresas/fabricantes, modelos e itens-materiais;
3. exemplos válidos de request e response por operação;
4. definição dos campos obrigatórios e valores de domínio;
5. acesso ao ambiente de homologação, com credenciais fornecidas por canal seguro;
6. lista definitiva de TOPs participantes;
7. regra de correspondência entre grupo/produto/marca Sankhya e material/modelo/fabricante Genesis;
8. definição dos responsáveis pela validação dos registros ambíguos;
9. plano de congelamento e desligamento dos jobs Protheus;
10. rotação dos segredos expostos nos artefatos anteriores.

## 14. Critérios de aceite recomendados

O escopo revisado deverá estabelecer, no mínimo, os seguintes critérios:

- cliente novo é criado ou localizado sem duplicidade;
- cliente existente é atualizado pelo mesmo ID Genesis;
- setor padrão é criado e vinculado ao cliente correto;
- material, fabricante e modelo são resolvidos antes do item-material;
- item-material novo é criado com todas as referências obrigatórias;
- item-material legado é atualizado, e não recriado;
- equipamento sem nota aparece no espelho com origem `GENESIS` e sem saldo fiscal artificial;
- primeira movimentação Sankhya altera a origem operacional sem quebrar o vínculo legado;
- reexecuções com o mesmo conteúdo não duplicam entidades;
- registros ambíguos são bloqueados para tratamento, sem criação automática;
- erros são registrados por entidade e podem ser reprocessados;
- dashboard representa origem, conciliação e resultado da integração;
- nenhum token ou senha é exposto em código, log, dashboard ou documentação;
- o cutover comprova contagens e vínculos antes da desativação definitiva do Protheus.

## 15. Formalização recomendada

Antes da continuidade do desenvolvimento, recomenda-se:

1. validar este entendimento com a MA Hospitalar, Genesis e equipes responsáveis pela implantação Sankhya;
2. formalizar o diagrama do processo futuro e a responsabilidade de cada sistema;
3. revisar o documento de escopo, incluindo expressamente os novos fluxos;
4. emitir nova versão do IntPDD com contratos, dados, sequência e tratamento de erros atualizados;
5. redefinir critérios de aceite e cenários de homologação;
6. registrar dependências externas e responsáveis por cada insumo;
7. revisar cronograma e esforço somente após a validação da extração e do contrato da API;
8. aprovar o plano de cutover antes de retomar chamadas produtivas.

## 16. Conclusão

O escopo original descreveu de forma consistente um processo de integração de movimentos de estoque controlados por série. O IntPDD e o projeto Sankhya seguiram essa premissa ao construir VIEW, dashboard, controle de movimentos, ação agendada, DTO de estoque e POST para `/api/v1/estoques/`.

O alinhamento posterior demonstrou que a necessidade operacional é mais abrangente: o Sankhya substituirá o Protheus como origem da Base Instalada e deverá sustentar toda a árvore cadastral exigida para criar e atualizar o equipamento individual na Genesis.

Essa mudança introduz entidades, relacionamentos, persistência de IDs, migração de legado, regra de origem, conciliação, idempotência por equipamento e atividades de cutover que não estavam previstas na linha de base original.

Por isso, recomenda-se a formalização de um **ajuste ou aditivo de escopo** antes da retomada da implementação. Essa formalização é necessária para alinhar entendimento, responsabilidades, critérios de aceite e condições de transição, preservando tanto o trabalho já realizado quanto a continuidade operacional da Base Instalada.

## Apêndice A — Rastreabilidade resumida

| Evidência | Referência | Interpretação |
|---|---|---|
| Integração exclusivamente de movimentos | ESC-2764, seções 5 e 16 | Delimita a linha de base original |
| Dashboard e ação alimentados pela mesma VIEW | ESC-2764, seções 6.1 e 6.2 | Confirma arquitetura orientada a movimentos |
| POST de `EstoqueJson` | INTPDD-001, páginas 7 a 13 | Confirma a tradução técnica do escopo |
| Sankhya substituirá o Protheus | REU-ALIN, 13:17 | Introduz responsabilidade de continuidade cadastral |
| Setor é obrigatório | REU-ALIN, 18:36 | Introduz entidade não prevista no escopo |
| Há equipamento sem nota no ERP | REU-ALIN, 29:24–33:18 | Exige fonte de dados diferente da VIEW de movimentos |
| Árvore de cadastro completa | REU-ALIN, 34:29–36:10 | Exige múltiplos endpoints e persistência de IDs |
| Troca de estoque por itens-materiais | REU-ALIN, 38:36–42:41 | Altera o objeto central da integração |
| Extração e códigos externos | REU-ALIN, 42:41–45:48 | Exige migração e conciliação de identidade |
| Jobs independentes por entidade | PRO-GEN, `GENM010` a `GENM060` | Comprova a necessidade operacional anterior |
| GET, POST e PUT | PRO-GEN, `GENFWS` | Comprova o ciclo de sincronização cadastral |
| Sankhya envia somente estoque | SNK-GEN, cliente, DTO, builder e repository | Demonstra aderência à premissa original e lacuna frente ao cenário novo |

## Apêndice B — Glossário

| Termo | Definição utilizada neste documento |
|---|---|
| Base Instalada | Conjunto de equipamentos individualizados e relacionados a cliente, setor, material, modelo e fabricante |
| Item-material | Registro Genesis que representa o equipamento individual, normalmente identificado por série |
| Material | Grupo ou natureza tecnológica do equipamento na árvore Genesis |
| Modelo | Especificação do equipamento associada ao material e ao fabricante |
| Código externo | Referência de um registro no sistema externo, usada para localização e conciliação |
| ID Genesis | Identificador interno atribuído pela Genesis a uma entidade |
| Origem Genesis | Registro legado ainda não sustentado por movimento ou estado nativo correspondente no Sankhya |
| Origem Sankhya | Registro cujo estado e movimentações passaram a ser comandados pelo Sankhya |
| Espelho sem saldo | Representação referencial do legado no Sankhya sem criação artificial de posição fiscal de estoque |
| Cutover | Sequência controlada de congelamento, carga final, ativação e desligamento entre os sistemas |
