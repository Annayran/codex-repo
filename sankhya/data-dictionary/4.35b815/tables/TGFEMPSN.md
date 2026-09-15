# TGFEMPSN — Partilha Simples Nacional Empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PartilhaSimplesNacXEmp | Partilha simples nacional por empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | CODEMP | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| DTREF | Dt. Referência | H |  |  | N |
| NUPARTILHA | Nro. Único Partilha/Anexo | I |  |  | N |
| TIPOSN | Tipo de Partilha/Anexo | I |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | PartilhaSimplesNacXEmp / [TGFEMPSN](TGFEMPSN.md) | CODEMP->CODEMP | I |
| PartilhaSimplesNacXEmp / [TGFEMPSN](TGFEMPSN.md) | PartilhaSimplesNacional / [TGFPSN](TGFPSN.md) | NUPARTILHA->NUPARTILHA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
