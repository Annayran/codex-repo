# Changelog

## Add-on 1.0.0 — template incorporado, não publicado

- Serviço autenticado `ZayaBoletoSP.gerarPdf`, com permissão explícita de emissão, transação Required e retorno PDF em Base64.
- Interfaces EJB e provider gerados pelo plugin oficial; build de model/vc e seis grupos de testes passaram em JDK 8.
- XMLs bancários vazios removidos; não há mudanças de banco nesta entrega.
- Empacotamento tentou validar a chave real e foi bloqueado por sua ausência; instalação/homologação não executadas.

- Estrutura oficial fornecida pelo solicitante, com Gradle 8.2 e plugin Add-on Studio `1+` preservados.
- Núcleo e adaptador BoletoHelper incluídos como fontes do módulo `model`.
- Chave do componente e diretório do servidor externalizados; exemplos de cadastro e rotinas fora do pacote bancário.
- Identidade do componente, pacote instalável, compatibilidade com o ERP e homologação pendentes.

## Núcleo 0.1 — em desenvolvimento

- Contratos iniciais de cobrança e conectores bancários, sem implementação HTTP.
- Fontes compatíveis com Java 8, build Gradle 8.13 e Wrapper do núcleo isolado.
- Separação MVC de controller de consulta, entidades e serviços.
- Validação monetária e verificações locais sem dependências externas.
- Catálogo preliminar Santander de token, workspaces e SONDA, com ambientes explícitos e validação de segmentos de URL.
- Adaptador direto BoletoHelper, solicitação por NUFIN e controller de geração PDF; compilação conferida contra biblioteca local 4.35b99, sem execução no ERP.
- Sem publicação, instalação Sankhya ou integração bancária real.
