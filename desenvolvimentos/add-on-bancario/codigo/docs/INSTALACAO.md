# Instalação em homologação

> Ainda não foi produzido pacote instalável neste checkout. A adequação e os testes de código estão descritos em [ADEQUACAO_LOCAL.md](ADEQUACAO_LOCAL.md). Fornecer a identidade autorizada, validar o SDK/runtime alvo e seguir as etapas abaixo antes de ativar contas. Testes locais não autorizam habilitar os perfis bancários.

## Pré-requisitos

Sankhya linha 4.36, Oracle ou SQL Server, saída HTTPS para os hosts bancários oficiais e permissão de instalação do add-on. A compatibilidade de patches/Cloud deve ser comprovada antes de habilitar o produto. O build local usa JDK 17 com bytecode Java 8.

AppKey e licença são as originais do projeto. O pacote gerado localmente não é publicado automaticamente no portal. Nunca compartilhar esses valores ou credenciais bancárias em chamados, fixtures ou logs.

## Cofre de credenciais

O adaptador implementado resolve uma `javax.crypto.SecretKey` AES no recurso gerenciado `java:comp/env/zycb/EncryptionKey`. O operador de infraestrutura/Cloud deve disponibilizar o recurso suportado pelo ambiente, com controle de acesso e política de backup/rotação. O add-on não cria uma chave em parâmetro TSIPAR, não grava senha em texto claro e não lê arquivos do servidor.

Esse binding não foi comprovado em Sankhya Cloud. Se o provedor não disponibilizar esse recurso, será necessário implementar um `KeyProvider` para o cofre oficialmente suportado antes de ativar. O diagnóstico e a gravação de credenciais falham de forma fechada até lá. Não guardar a chave em texto claro na própria base como contorno.

A chave deve permanecer estável entre reinícios e nós. Troca de chave exige recifrar o conteúdo com a anterior disponível; não substituir o recurso sem procedimento de migração. AES-GCM usa IV aleatório e vincula o conteúdo ao ID da configuração.

## Sequência

1. Validar o `.exts` e instalar em ambiente de homologação, com backups e jobs desligados.
2. Confirmar execução de V1/V2 para o SGBD correspondente e descrições do dicionário. Não reaplicar manualmente versões antigas nem executar limpeza de objetos demonstrativos do banco.
3. Conceder ao SUP acesso à central. Restringir as consultas técnicas Dynaform a administradores técnicos; acessos operacionais por empresa são administrados na central.
4. Conferir empresa e código bancário da conta interna: 341 para Itaú e 33 para Santander. Definir usuário técnico ativo com permissões reais de baixa e mensageria.
5. Conferir TOP, lançamento, modelo nativo do boleto, SMTP e numeração disponível. Garantir que outro motor não registre a mesma conta/título.
6. Gravar configuração com origem SANKHYA (padrão para novas contas), usando o certificado da empresa no Console NF-e. Se o banco exigir outro certificado, selecionar e salvar UPLOAD e informar P12/PFX e senha pela interface. Gravar Client ID e client secret nas duas origens. Não há fallback automático e credenciais nunca são devolvidas na consulta. Consulte CERTIFICADOS.md.
7. Testar autenticação e consulta. Configuração ou credenciais alteradas invalidam o diagnóstico anterior. O diagnóstico expira em 24 horas.
8. Concluir a matriz de homologação, homologar os perfis na release e instalar essa release. Aprovação da conta pelo administrador não substitui o manifesto do conector.
9. Em janela sem inclusões financeiras em andamento, definir `ZYCBHABIL=S` e ativar. O corte de NUFIN é capturado uma única vez.
10. Conferir um pequeno conjunto de boletos e recebimentos e ampliar gradualmente.

## Contingência

Pausar novas emissões mantém consultas e coleta. A coleta pode ser suspensa separadamente. Parada geral usa `ZYCBHABIL=N`, preservando todas as tabelas. Não reenviar títulos incertos por CNAB nem trocar o controle para outro motor antes de consultar o banco.

Atualização de software não desfaz boleto registrado ou recebimento. Não desinstalar tabelas com fila ou cobranças históricas. Reemissão requer cancelamento confirmado e gera outra identidade, preservando o histórico.
