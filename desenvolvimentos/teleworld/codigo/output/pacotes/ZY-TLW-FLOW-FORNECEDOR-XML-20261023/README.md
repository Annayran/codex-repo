# Teleworld — Flow de Fornecedor via XML

Pacote isolado do desenvolvimento responsável por registrar importações XML na
fila `AD_ZFLOWFORN`, iniciar o Flow de fornecedor e preencher o `CODPARC` na
fila e na `TGFIXN` quando o parceiro for localizado na `TGFPAR`.

## Entradas Sankhya

- Evento programável:
  `br.com.zaya.teleworld.fornecedorxml.sankhya.events.FornecedorXmlEvent`
- Ação agendada:
  `br.com.zaya.teleworld.fornecedorxml.sankhya.schedulers.FlowFornecedorScheduler`

## Preferências

- `AD_STXMLFLOW`: status elegíveis da `TGFIXN`.
- `AD_PRNFLOWFOR`: código do processo de Flow.
- `AD_USUFLOWFOR`: usuário técnico utilizado para iniciar o processo.

## Estrutura

- `src/`: código-fonte e testes.
- `database/metadata/`: metadado da tabela adicional.
- `database/deploy/`: criação do índice único após a importação do metadado.
- `database/validation/`: consulta de validação.
- `database/rollback/`: rollback exclusivo do índice adicional.
- `docs/`: documentação funcional/técnica do processo.
- `build/libs/`: JAR gerado pela validação deste pacote.

## Compilação

Requer Java 8 e as bibliotecas do SDK Sankhya disponíveis externamente:

```powershell
.\gradlew.bat clean test jar -PsankhyaLibsDir=C:/caminho/Dependencias
```

As bibliotecas proprietárias do SDK não são distribuídas neste ZIP.

## Validação desta entrega

- O JAR dedicado contém somente o pacote
  `br.com.zaya.teleworld.fornecedorxml` extraído do artefato validado
  `ZAYA-TLW-V2.5.jar`.
- Bytecode confirmado como Java 8 (`major version 52`).
- O conteúdo do JAR foi inspecionado para impedir a inclusão das classes do
  `AD_REVISADO` e dos demais módulos Teleworld.
- A recompilação pelo Gradle Wrapper não foi concluída neste ambiente porque o
  Gradle não conseguiu estabelecer sua conexão local de execução. Os testes
  permanecem incluídos para execução no ambiente de desenvolvimento.

## Observação de implantação

O metadado deve ser importado antes do cadastro do evento e da ação agendada.
Scripts de banco devem ser executados apenas com plano de implantação e rollback
aprovados para o ambiente de destino.
