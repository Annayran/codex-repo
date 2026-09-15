# TGFSAT — Tabela de cadastro SAT

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CadastroSat | Cadastro de Sat | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ASSINATURA | Assinatura | C |  |  | N |
| CNPJHOMOLOG | CNPJ Software House | S |  |  | N |
| CODATIVACAO | Cod. Ativação | S |  |  | N |
| CODEQUIP | Cod. Máquina | I |  |  | N |
| CODSAT | Cod. Sat | I |  |  | N |
| COMPARTILHADO | Compartilhado | S |  |  | N |
| HISTORICO | Histórico | C |  |  | S |
| HOMOLOGA | Homologação | S |  |  | N |
| IPWEBCONNECTION | IP de compartilhamento | S |  |  | N |
| MODELO | Modelo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CadastroSat / [TGFSAT](TGFSAT.md) | CadastroEquipamentoFiscal / [TGFEQF](TGFEQF.md) | CODEQUIP->CODEQUIP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
