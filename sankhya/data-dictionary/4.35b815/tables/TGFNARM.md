# TGFNARM — Notas da Apuração da Receita Mensal

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NotasApuracaoReceitaMensal | Notas da Apuração da Receita Mensal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| DIGITADO | Digitado | S |  |  | N |
| DTNEG | Dt. Negociação | D |  |  | N |
| NUAPURACAO | Nro. Apuração | I |  |  | N |
| NUMNOTA | Nro. Nota | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| SERIENOTA | Série da nota | S |  |  | N |
| STATUS | Status | I |  |  | N |
| TIPMOV | Tipo de Movimento | S |  |  | N |
| VLRTOTRECEXT | Total da Receita Bruta Externa | F |  |  | N |
| VLRTOTRECINT | Total da Receita Bruta Interna | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | ApuracaoReceitaMensal /  | NUAPURACAO->NUAPURACAO | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | ItensApuracaoReceitaMensal / [TGFIARM](TGFIARM.md) | NUAPURACAO->NUAPURACAO; NUNOTA->NUNOTA | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
