# TCSELC — Empresas Licenciadas

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmpresaLicenciada | Empresa Licenciada | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CNPJCPF | CNPJ/CPF | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| RAZAOSOCIAL | Razão Social | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contrato / [TCSCON](TCSCON.md) | EmpresaLicenciada / [TCSELC](TCSELC.md) | NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
