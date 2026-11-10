# Capturando JAR

## Atualizacao do modulo Font Life

Entrega atual: `dist/FileViewer-atualizado-v2.jar`, classe `br.com.sankhya.bhz.central.AtualizarLocal`.
Build: `./build-atualizar-local.ps1`. Testes: `./test-atualizar-local.ps1`.
Regras, configuracao de AD_ENTRACK e protecao de DTALTER, validacao e rollback:
[roteiro de implantacao](docs/implantacao-atualizar-local.md).

## Extrator original

Acao Java para baixar o conteudo binario do campo `ARQUIVO` de um registro selecionado no Sankhya. Classe preservada do codigo fornecido: `br.com.zaya.actions.CapturandoJar`.

## Uso

Cadastre `dist/capturando-jar.jar` como modulo Java e configure a acao com a classe acima na tela que possui o campo `ARQUIVO`. Selecione um registro, execute a acao e clique em **Baixar JAR**. O arquivo sera disponibilizado com o nome `FileViewer.jar`.

Esta acao apenas disponibiliza o binario para download; nao atualiza datas nem registros no banco.

## Build

Execute `./build.ps1 -SankhyaLib 'caminho/das/bibliotecas'` usando JDK 9 ou superior. O script gera bytecode Java 8. As bibliotecas Sankhya sao fornecidas pelo servidor e nao sao incorporadas ao artefato.

Compilacao local com bibliotecas Sankhya 4.35b99. A execucao e o download precisam ser validados no ambiente Font Life, cuja versao nao foi informada.
