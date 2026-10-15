# Estrutura mínima de projeto

## Obrigatória

Uma solução deve manter juntos os componentes que compartilham o mesmo ciclo de publicação. Java, JSP, HTML5, JavaScript, CSS, SQL e documentação que formam uma única entrega devem permanecer no mesmo repositório.

Estrutura de referência:

```text
projeto/
├── README.md
├── AGENTS.md
├── CHANGELOG.md
├── build.gradle ou pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   └── webapp/
│   └── test/
├── database/
│   ├── deploy/
│   ├── validation/
│   └── rollback/
├── docs/
└── .gitlab/
    └── merge_request_templates/
```

Componentes não utilizados não precisam ser criados. Um projeto exclusivamente Java não precisa criar `webapp`; um projeto sem mudança de banco não precisa criar scripts vazios.

### Pacotes Java de soluções de clientes

Soluções específicas de clientes devem utilizar o prefixo:

```text
br.com.zaya.<cliente>.<solucao>
```

Os segmentos `<cliente>` e `<solucao>` devem ser escritos em minúsculas, sem
acentos, espaços ou hífens. Um nome composto deve ser concatenado, por exemplo
`apontamentoproducao`. A correspondência entre o nome comercial e o segmento
adotado deve ser registrada no `README.md` do projeto.

Novos projetos usam MVC conforme `standards/mvc.md`. Estrutura de referência:

```text
br.com.zaya.<cliente>.<solucao>/
├── controller/
├── model/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
├── view/
├── sankhya/
│   ├── actions/
│   ├── events/
│   ├── schedulers/
│   └── services/
└── shared/
    ├── config/
    ├── logging/
    └── utils/
```

Somente os pacotes efetivamente utilizados devem ser criados. `view` pode estar
no módulo `vc` em Add-on Studio. A divisão não autoriza classes sem
responsabilidade concreta nem regra funcional no controller ou na view.

Bibliotecas compartilhadas e versionadas independentemente devem utilizar
`br.com.zaya.platform.<componente>`. Ferramentas internas podem utilizar
`br.com.zaya.engineering.<ferramenta>`.

### Recursos associados a classes

SQL, templates e outros recursos carregados pelo classpath devem ficar em
`src/main/resources`, reproduzindo o caminho do pacote da classe consumidora.
Recursos não devem permanecer em `src/main/java` apenas para ficarem próximos
da classe.

### Compatibilidade em migrações

Não altere diretamente FQCNs utilizados por ações, eventos, agendamentos,
reflexão, XML, manifestos ou cadastros externos. Quando a migração for
necessária, mantenha temporariamente um adaptador no FQCN antigo, valide o novo
FQCN em homologação e remova o adaptador somente em mudança posterior aprovada.

## Recomendada

Separar código-fonte de artefatos gerados e impedir versionamento de `build`, `out`, `bin`, logs e credenciais.

Utilizar `templates/repository/java-sankhya` como ponto de partida para novas
soluções Java/Sankhya.

## Pendente de validação pela equipe

Política para módulos multi-projeto e critérios para remoção definitiva de
adaptadores de FQCN legado.
