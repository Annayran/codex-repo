# Sankhya Support POC

POC própria da Zaya.IT para coleta contextual de ocorrências de estoque insuficiente nos Portais do Sankhya.

## Estado
Base documental e contrato de dados. Ainda não há botão instalável, API executável, banco provisionado ou integração com IA. Nenhum cliente foi selecionado. Exemplos são fictícios.

## Primeiro fluxo
1. Cliente seleciona um documento no Portal e aciona Reportar problema de estoque.
2. Informa operação, mensagem e horário da ocorrência, quando conhecidos.
3. Confirma o envio; coletor valida acesso e executa somente consultas previamente aprovadas.
4. Receptor autentica o ambiente e persiste o pacote antes de retornar protocolo.
5. Equipe consulta fatos, fontes, lacunas e contexto para investigar a ocorrência.

O estado coletado depois da falha não comprova o estado no instante da ocorrência. Uma mensagem de estoque insuficiente não comprova a causa raiz.

## Estrutura
- sankhya-extension/: adaptador e coletor, implementação pendente.
- support-service/: receptor externo proposto em Quarkus, implementação pendente.
- contracts/: contrato JSON Schema e exemplo fictício.
- docs/collection-contract.md: fontes, semântica e limites.
- docs/validation-plan.md: critérios de homologação.
- docs/zaya/: arquitetura, decisões, pendências e perfil manual.

## Próximo passo
Escolher homologação, confirmar versão do ERP/JDK/SDK, Portal e operação exatos e obter um caso resolvido. Mapear consultas autorizadas e regra de disponibilidade antes de implementar a extensão. Java 8 é a referência inicial corporativa para a extensão, sujeita à compatibilidade comprovada. O runtime do serviço externo é independente e será definido com sua versão Quarkus.

Repositório: https://github.com/Annayran/sankhya-support-poc. Entregas por pull request. Build e dependências aguardam definição; commits e publicação exigem autorização explícita.
