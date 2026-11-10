# Entrega - Flow do parceiro por AD_REVISADO

Pacote de repasse técnico do desenvolvimento que inicia o Flow de revisão do
parceiro quando o campo `TGFPAR.AD_REVISADO` é alterado para um status elegível.

## Comportamento

- Entidade/tabela: Parceiro (`TGFPAR`).
- Campo observado: `AD_REVISADO`.
- Gatilhos usados pelo evento: `beforeUpdate` e `afterUpdate`.
- Status que marcam o início: `P` ou `N`, desde que o valor tenha sido alterado.
- Código do processo configurado no fonte: `13`.
- Dado enviado ao formulário `AD_PARCPEND`: `CODPARC`.
- O início é marcado na transação para evitar disparos em atualizações que não
  tenham efetivamente alterado `AD_REVISADO`.
- Uma falha ao iniciar o Flow é registrada no log e não impede a gravação do
  parceiro.

## Classes entregues

- `StartProcessHelperEvent`: evento Java principal para a alteração do parceiro.
- `StartProcessHelperSupport`: montagem e início do processo Sankhya Flow.
- `StartProcessHelperButton`: botão auxiliar para teste manual com o parceiro
  selecionado.

FQCN do evento:

```text
br.com.zaya.flow.actions.StartProcessHelperEvent
```

FQCN do botão de teste:

```text
br.com.zaya.flow.actions.StartProcessHelperButton
```

## Cadastro no Sankhya

1. Publicar o JAR no módulo Java correspondente.
2. Cadastrar `StartProcessHelperEvent` como Evento Programável Java da instância
   de Parceiros/TGFPAR.
3. Habilitar `beforeUpdate` e `afterUpdate` no mesmo cadastro do evento.
4. Confirmar que o processo de código `13` está publicado e que o usuário possui
   permissão para iniciá-lo.
5. Para teste manual opcional, cadastrar `StartProcessHelperButton` como ação de
   rotina Java na tela de Parceiros.
6. Não manter simultaneamente o evento legado `StartProcess`, para evitar
   inicialização duplicada.

## Build

- Java: 8.
- Build: Gradle Wrapper.
- Comando: `gradlew.bat clean build`.
- Artefato validado incluído: `artefato/ZAYA-TLW-V2.5.jar`.
- As bibliotecas proprietárias Sankhya são externas/`compileOnly` e não foram
  incluídas. Ajustar `baseLibPath` no `build.gradle` para o diretório local das
  dependências do SDK.

O arquivo `StartProcessHelperSupport` é compartilhado pelo projeto original e
também contém suporte ao processo de fornecedor por XML. O evento entregue aqui
utiliza exclusivamente o método `iniciarProcessoParceiro`.

## Homologação sugerida

1. Alterar `AD_REVISADO` de um valor diferente para `P` ou `N`.
2. Confirmar os logs com o marcador `[Teleword][StartProcessHelperEvent]`.
3. Confirmar a criação da instância do processo `13` com o `CODPARC` correto.
4. Salvar outro campo sem alterar `AD_REVISADO` e confirmar que nenhum novo Flow
   foi iniciado.
5. Repetir a gravação com o mesmo valor e confirmar ausência de duplicidade.

## Segurança e conteúdo

O pacote não contém credenciais, tokens, caches Gradle, metadados de IDE,
dependências proprietárias nem o repositório Git original.

