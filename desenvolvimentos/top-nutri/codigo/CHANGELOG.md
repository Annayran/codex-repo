# Changelog

## Não publicado

### V1.9

- Pré-validação ampliada para uma simulação cadastral somente leitura, sem
  criação de versão, alteração de composição ou publicação de processo.
- Visão por produto de como ficará o PA no processo `1`, separando Macro,
  PI/Batida e embalagens preservadas da versão anterior.
- Visão da composição futura do PI no processo `6`, com quantidade original
  dos micros no PA e quantidade proporcional normalizada para 1 kg.
- Consulta de códigos, descrições, unidades, categorias, PI derivado, processos
  e etiqueta/sacaria antes de habilitar a confirmação produtiva.
- Pendências cadastrais da simulação passam a bloquear a confirmação e são
  apresentadas junto às pendências estruturais do TXT.

### V1.8

- Separação produtiva de macronutrientes no PA e micronutrientes no PI.
- PI resolvido pelo prefixo `200` do código do PA e versionado no processo
  produtivo `6`; PAs continuam versionados no processo `1`.
- Quantidade do PI no PA calculada pela soma exata dos micros do TXT.
- Composição do PI normalizada proporcionalmente para total exato de 1 kg com
  precisão `DECIMAL128` e compensação decimal no último item.
- Etiqueta e sacaria preservadas da versão anterior e reinseridas como `1 UN`
  quando não estiverem presentes na nova versão, sem duplicação.
- Importação bloqueada para componentes do TXT sem categoria Macro ou Micro e
  para PAs sem etiqueta e sacaria identificáveis na versão anterior.
- Processos PA e Micro validados antes da publicação e publicados na ordem
  Micro → PA dentro da mesma execução transacional.
- Interface diferencia a conferência estrutural do TXT da validação cadastral
  executada somente na confirmação produtiva.

### V1.7

- Compatibilidade de `atualizarFormulasProducao` com os overloads nativos de
  três parâmetros e quatro parâmetros (`Boolean` ou `boolean`).
- O overload adicional é chamado com `false`, mantendo o comportamento do
  wrapper existente na dependência 4.23.
- Diagnóstico de incompatibilidade passa a informar as assinaturas públicas
  realmente encontradas no runtime da Manufatura.

### V1.6

- Mapeamento do controle adicional de estoque da MP a partir de
  `TGFPRO.TIPCONTEST` para `TPRLMP.TIPOCONTROLEMP`.
- Matérias-primas controladas por lote usam o valor técnico `I` (Automático) e
  deixam `CONTROLEMP` vazio, conforme a validação nativa da Manufatura.
- Componentes existentes na versão copiada são atualizados sem recriação, para
  preservar locais, controles e demais configurações operacionais.
- Componentes novos recebem `CODVOL` do produto e os defaults mínimos da tela
  padrão; controles por lista sem valor determinável são bloqueados antes da
  persistência.

### V1.5

- Execução produtiva conectada ao versionamento nativo da Manufatura, sem DML
  direto nas tabelas padrão.
- Localização do processo padrão, criação de nova versão, substituição integral
  das composições presentes no TXT, validação nativa e deploy do processo.
- `TGFPRO.AD_CATEGORIA` convertido para `TPRLMP.AD_TIPOMP` durante a inclusão
  via entidade Jape `ListaMateriaisAtividade`.
- Verificação reflexiva das assinaturas internas para falhar antes da alteração
  quando o runtime 4.36b109 não for compatível com o contrato esperado.
- Interface bloqueada após sucesso para reduzir reenvio acidental na mesma tela.

### V1.4

- Aplicação do logo vetorial oficial e das cores institucionais azul, turquesa
  e cinza, com os pesos 400, 600 e 700 da Poppins empacotados localmente.
- Logo incluído no ZIP do componente HTML5, sem dependência externa em runtime.
- Cabeçalho externo do modal alinhado à identidade Topnutri, com título funcional,
  acabamento do contêiner e tratamento visual do botão de fechamento.
- Cor branca do fechamento reforçada para ícones de fonte, pseudo-elementos e
  SVGs usados por diferentes renderizações do modal Sankhya.
- Logo carregado pelo endpoint do componente HTML5 com `NU_GDG` e fallback
  textual; fechamento reposicionado à direita do cabeçalho externo.
- Separadores tipográficos do resumo representados por escapes Unicode para não
  depender da codificação aplicada ao script inline pelo servidor JSP.
- Botão de confirmação habilitado somente depois de uma conferência válida.
- Clique em confirmar volta a enviar arquivo e SHA-256 para revalidação pela
  ação Java.

### V1.3

- Tolerância simétrica de massa em 5% da quantidade do PA; somente diferenças
  acima do limite bloqueiam o arquivo e os valores não são ajustados.
- Detecção estrita de UTF-8, suporte a BOM e fallback ISO-8859-1.
- Interface simplificada, com nomenclatura orientada ao usuário e menos dados
  técnicos, inspirada na linguagem visual do site oficial da Topnutri.
- Colunas de cadastro ainda não integradas foram removidas da visualização.

### V1.2

- Conferência expansível por PA, busca e filtro de inconsistências na interface.
- Diagnóstico decimal de massa com PA, esperado, soma e diferença exata.
- JSON com escapes Unicode para proteger acentos no transporte da ação.
- Assinatura textual TOP Nutri e proposta de paleta verde-oliva.
- Campos de cadastro não consultados indicados explicitamente e confirmação
  produtiva desabilitada; nenhuma gravação habilitada.

### V1.1

- Mapeamento explícito de AD_CATEGORIA para AD_TIPOMP no núcleo Java.
- Testes de múltiplos PAs distintos, fechamento do último bloco e rejeição do
  arquivo quando um bloco posterior apresenta inconsistência.
- Conversão do PI para 1 kg e integração da rotina existente permanecem pendentes.

### Build

- Validada a geração do JAR V1.0 pelo Gradle com testes automatizados.
- Documentado contorno local para falha de socket Unix do JDK no Windows,
  sem alteração de Java, Gradle ou dependências do projeto.

### Adicionado

- Estrutura inicial Java 8/Gradle conforme o padrão Zaya/Sankhya.
- Parser ISO-8859-1 para os registros `0` e `1` do Optimal.
- Validação estrutural e fechamento exato de massa.
- Cálculo SHA-256 do arquivo de importação.
- Planejamento da composição de PA e PI conforme `AD_CATEGORIA`.
- Tratamento inicial de categoria nula como componente direto do PA, substituído
  na V1.8 pela exigência de classificação explícita Macro ou Micro.
- Testes unitários do núcleo independente do ERP.
- Componente HTML5 de upload, pré-validação e confirmação do importador.
- Ações Java para pré-validar e revalidar arquivo/hash antes da execução.
- Empacotamento Gradle do componente `TPN_html5Component.zip`.
- Decisão de substituição integral somente com itens do TXT e sem arredondamento.
