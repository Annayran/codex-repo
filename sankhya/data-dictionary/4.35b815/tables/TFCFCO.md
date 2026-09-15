# TFCFCO — Funções currículo

- Módulo técnico: [TFC](../modules/TFC.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FuncaoCurriculo | Funções Desempenhadas | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFUNCAO | Função | I |  |  | N |
| NUCURRICULO | Curriculo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Curriculo / [TFCBCO](TFCBCO.md) | FuncaoCurriculo / [TFCFCO](TFCFCO.md) | NUCURRICULO->NUCURRICULO | I |
| FuncaoCurriculo / [TFCFCO](TFCFCO.md) | Funcao /  | CODFUNCAO->CODFUNCAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
