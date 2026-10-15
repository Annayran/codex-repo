# Parâmetros do formulário de intercâmbio SOU Unimed

O modelo utiliza Oracle e é compatível com JasperReports/iReport 4.0.1. A consulta não depende de requisição de pessoal, currículo ou contrato.

## Parâmetros

Todos são recebidos como `java.lang.String`, conforme o envio do Relatório Formatado Sankhya.

| Parâmetro | Conteúdo |
|---|---|
| `P_CODEMP` | Código da empresa em `TSIEMP` |
| `P_CODFUNC` | Código do funcionário em `VFPFUN` |
| `P_RESPONSAVEL_SOLICITANTE` | Nome de quem está solicitando o atendimento |
| `P_FINALIDADE` | `A`, `P`, `D`, `M` ou `R` |
| `P_NOVA_FUNCAO` | Nova função, quando houver mudança de risco |
| `P_NOVO_SETOR` | Novo setor, quando houver mudança de risco |
| `P_DATA_PROVAVEL_ADMISSAO` | Data informada para exame admissional |
| `P_CIDADE_ATENDIMENTO` | Cidade onde ocorrerá o atendimento |

## Finalidades aceitas

| Código | Finalidade |
|---|---|
| `A` | Admissional |
| `P` | Periódico |
| `D` | Demissional |
| `M` | Mudança de risco |
| `R` | Retorno ao trabalho |

A finalidade selecionada é convertida em `X` no campo correspondente do formulário.

## Fontes atuais

- `VFPFUN`: nome e identificação do funcionário.
- `TSIEMP`: razão social e CNPJ da empresa.
- `TSICID`: cidade da empresa, utilizada como alternativa quando a cidade de atendimento não for informada.
- `DUAL`: garante uma linha para que o modelo seja exibido mesmo quando os códigos não forem encontrados.

## Campos pendentes

Os campos abaixo permanecem em branco até a identificação da entidade nativa do funcionário no módulo Pessoal:

- data de nascimento;
- CPF;
- cargo;
- setor;
- nome da mãe;
- sexo;
- celular;
- e-mail.

O logotipo está incorporado ao JRXML; não é necessário cadastrar arquivo de imagem separado.
