# PortoMag — Importador de pedidos PDF V1.5

Projeto novo do Item 34 / OS 7263: recebimento documental pelo dashboard HTML5, com registro mestre e detalhe. Nao altera AD_PEDREC nem reaproveita codigo do componente antigo.

## Entrega atual

- Botao Importar PDF e drag-and-drop; leitura local por PDF.js 5.6.205 sem CDN/OCR.
- Parser por posicao de colunas para o layout observado do pedido Claro 5500623466.
- Conferencia de cabeçalho, itens, impostos impressos, site, municipio e blocos do anexo.
- Contrato XML fechado e rotina Java MVC para validacao no servidor e persistencia Jape das entidades proprias.
- Metadados propostos ZPM_PEDREC (17 campos) e ZPM_PEDITE (26 campos). O mestre referencia o detalhe, cuja chave e composta por IDPED + SEQEXT, conforme a topologia do export nativo recebido; unicidade do mestre e FK ficam no deploy separado.
- Testes locais de parser, modelo Java e navegador. Dados do PDF usado em teste ficam em build/ e nao entram na distribuicao.

## Integracao de gravacao

O botao **Salvar pedido recebido** envia o XML para a Rotina Java de codigo 68 por `ActionButtonsSP.executeJava`, contrato interno comprovado em componentes do mesmo ambiente e autorizado para a PortoMag 4.35b811. A chamada usa mesma origem, nao coloca a sessao na URL e nao depende de CDN. A acao, rollback, concorrencia e permissoes ainda precisam de homologacao no servidor.

Nao ha geracao de pedido/nota, OCR, aprovacao automatica, cadastro de produtos/cliente, armazenamento do PDF no servidor ou gestao completa de contratos. Um recebimento com composicao divergente e salvo pela rotina com situacao DIVERGENTE, sempre sujeito a conferencia. A API de gravacao ainda nao esta publicada.

## Estrutura

- `web/model`: parser e serializacao do contrato.
- `web/controller`, `web/view`: fontes de operacao e conferencia responsiva, incorporadas ao JSP pelo empacotador.
- `src/main/java/.../model`: validacao e repositorio de recebimento.
- `src/main/java/.../controller`: orquestracao.
- `src/main/java/.../sankhya`: adaptador AcaoRotinaJava.
- `metadata`: XML e dicionario da estrutura proposta.
- `database`: deploy, consultas de validacao e rollback.
- `docs`: implantacao, fontes e evidencias dos testes.
- `tools`: geradores reproduziveis de metadados e pacote HTML5.

## Preparar e testar

Importar no IntelliJ como projeto Gradle. Usar JDK 21 para executar o Gradle Wrapper 9.0.0 canonico e JDK 8 para compilar o modulo; nao houve atualizacao de runtime do cliente. Informar a biblioteca Sankhya homologada explicitamente.

```powershell
# Na pasta ImportadorPedidos, com runtimes disponiveis no PATH:
python tools/build_metadata.py --export-info-from "<export nativo 4.35b811 do mesmo ambiente>"
node tests/parser.test.mjs "<pedido de referencia.pdf>" "<pdfjs-dist/legacy/build/pdf.mjs>"
.\gradlew.bat build --offline --no-daemon -Pjava8Home="<JDK 8>" -PsankhyaExtensionsJar="<SankhyaW-extensions.jar do ambiente>" -PsankhyaDependenciesDir="<bibliotecas homologadas>"
python tools/package.py
```

Enquanto o Gradle deste computador estiver bloqueado pelo erro de loopback documentado, o empacotador alternativo reproduzivel pode ser usado para homologacao:

```powershell
python tools/build_jar.py --java8-home "<JDK 8>" --extensions-jar "<SankhyaW-extensions.jar do ambiente>" --dependencies-dir "<bibliotecas homologadas>"
```

O teste de modelo Java requer o XML gerado pelo teste do parser em build/test-input.xml; ele nao e distribuido. O build deve gerar `build/libs/ZAYA-PORTOMAGPDF-V1.3.jar`. Consulte `docs/VALIDACAO.md` para o resultado real desta entrega, inclusive limitacoes do build local.

O gerador usa por padrao `Downloads/Metadados_AD_PEDREC.zip` como origem do `exportInfo`. Ele valida versao, charset e estrutura, copia somente esse bloco para o ZIP em `out/` e nao grava seus valores no XML fonte, schema ou documentacao. A pasta `out/` permanece ignorada pelo Git.

Para preparar as bibliotecas web em uma nova maquina, copiar SOMENTE os arquivos `legacy/build/pdf.mjs`, `legacy/build/pdf.worker.mjs` e `LICENSE` da distribuicao pdfjs-dist **5.6.205** aprovada para `web/vendor/`; conferir os SHA-256 em `vendor-lock.json`. O empacotador recusa bibliotecas diferentes. Origem e licenca em `docs/FONTES.md`. Nao obter codigo de branch movel nem trocar versao silenciosamente.

Para conferir a interface localmente, servir apenas a pasta web por HTTP local e abrir `/view/dashboard.html`. `index.jsp` e o ponto de entrada do pacote Sankhya. Em producao, validar HTTPS, MIME .mjs e suporte de worker/navegador.

A entrada `index.jsp` segue a referencia JSP enviada em 04/09: diretivas UTF-8/EL, taglibs Sankhya/JSTL, `<snk:load />` e interface no proprio documento. O empacotador incorpora CSS, parser e controlador no JSP. A distribuicao fica plana e publica apenas `index.jsp`, `pdf.js`, `pdf.worker.js` e a licenca. `${BASE_FOLDER}` localiza a biblioteca. Nao foram copiadas consultas, regras ou identidade visual da MA Hospitalar.

## Implantacao e referencias

Ler `docs/IMPLANTACAO.md`, `docs/CADASTRO_ACAO.md`, `docs/DICIONARIO.md`, `docs/FONTES.md`, `../docs/zaya/metadata-legado-sankhya.md` e `database/rollback.md`. Pacotes sao **candidatos de homologacao**, nao exports validados no servidor. O desenho da aba mestre/detalhe e os IDs de botoes/tela devem ser configurados na versao real.

A entrega consolidada e gerada por `python tools/package_delivery.py` em `out/PORTOMAG-IMPORTADOR-V1.5-HOMOLOGACAO.zip`. Ela inclui dashboard V1.4, metadados V1.1, backend V1.3, banco, documentacao e manifesto; nao inclui PDF nem XML de cliente.
