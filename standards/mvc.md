# MVC para Java e Sankhya

## Obrigatória

MVC é o padrão para novos projetos Java/Sankhya da Zaya.IT.

- `controller`: recebe ações, eventos, serviços ou requisições, valida o contrato
  de entrada, chama o model e converte o resultado para a saída. Não contém SQL
  nem regra funcional principal.
- `model`: contém entidades, DTOs, regras, serviços e repositórios. É independente
  da apresentação e aplica transações no contexto correto.
- `view`: contém HTML5, componentes de tela e modelos de resposta. Não decide
  autorização, integridade, cálculo ou persistência.
- `sankhya`: adapta pontos de extensão da plataforma para controllers e serviços;
  ações, listeners e schedulers devem ser pequenos.
- `shared`: somente código genuinamente transversal, sem virar depósito genérico.

Dependências seguem `view -> controller -> model`. Adaptadores Sankhya podem
chamar controllers ou serviços do model. O model não depende da view.

## Tipos de projeto

### Java legado ou personalização modular

Use `src/main/java/br/com/zaya/<cliente>/<solucao>` com os pacotes MVC. Preserve
FQCNs já cadastrados; introduza adaptadores no caminho antigo quando a migração
for aprovada.

### Add-on Studio

Preserve os módulos oficiais `model`, `vc`, `datadictionary` e `dbscripts`.
Dentro de `model/src/main/java`, separe controller e model. A view permanece em
`vc`; metadados ficam em `model/src/main/resources/datadictionary`. Não aplique o
template legado sobre um Add-on Studio.

### Rotinas sem interface

`view` pode não existir. Mantenha controller e model quando houver orquestração e
regra; não crie pacotes vazios apenas para aparentar conformidade.

## Recomendada

Organize por funcionalidade dentro das camadas quando o projeto crescer, por
exemplo `model/faturamento` e `controller/faturamento`, mantendo dependências
claras e coesas.

## Compatibilidade

A adoção em projeto existente não autoriza refatoração ampla. Primeiro registre
FQCNs externos, metadados, ações, eventos, serviços e testes; depois planeje
migração, homologação e rollback.
