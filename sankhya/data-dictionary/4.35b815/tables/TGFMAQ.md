# TGFMAQ — Cadastro de Máquinas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 16
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroMaquina | Cadastro de Máquinas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODEQUIP | Cod. Máquina | I |  |  | N |
| CODMAQ | Cód. Máquina | I |  |  | N |
| CODNACIONALIDENT | Código CNIEE | S |  |  | N |
| FUSOHORARIO | Fuso Horário | I |  |  | N |
| INDLIVRO | Considera apuração | S |  |  | N |
| MARCA | Marca | S |  |  | N |
| MD5PAF | MD5 Paf | S |  |  | N |
| MODDOC | Modelo do Documento | S |  |  | N |
| MODELO | Modelo Equipamento | S |  |  | N |
| NROCAIXA | Número do ECF | I |  |  | N |
| NROSERIE | Número de Série | S |  |  | N |
| NUMUSUECF | Número Usuário | I |  |  | N |
| TIPDOC | Tipo do Documento | S |  |  | N |
| TIPOECF | Tipo Equipamento ECF | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroMaquina / [TGFMAQ](TGFMAQ.md) | CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | CODEQUIP->CODEQUIP | I |
| CadastroMaquina / [TGFMAQ](TGFMAQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| TributacaoCupomFiscal / [TGFTIE](TGFTIE.md) | CadastroMaquina / [TGFMAQ](TGFMAQ.md) | CODMAQ->CODMAQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
