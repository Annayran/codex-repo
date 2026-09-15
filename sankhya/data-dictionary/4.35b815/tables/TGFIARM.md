# TGFIARM — Itens da Apuração da Receita Mensal

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItensApuracaoReceitaMensal | Itens da Apuração da Receita Mensal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| NUAPURACAO | Nro. Apuração | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| QTDNEG | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPOSN | Tipo de Partilha/Anexo | S |  |  | N |
| USOPROD | Uso do Produto | S |  |  | N |
| VLRDESC | Vlr. Desconto | F |  |  | N |
| VLRTOT | Vlr. Total | F |  |  | N |
| VLRTOTRECEXT | Total da Receita Bruta Externa | F |  |  | N |
| VLRTOTRECINT | Total da Receita Bruta Interna | F |  |  | N |
| VLRUNIT | Vlr. Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensApuracaoReceitaMensal / [TGFIARM](TGFIARM.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItensApuracaoReceitaMensal / [TGFIARM](TGFIARM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| NotasApuracaoReceitaMensal / [TGFNARM](TGFNARM.md) | ItensApuracaoReceitaMensal / [TGFIARM](TGFIARM.md) | NUAPURACAO->NUAPURACAO; NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
