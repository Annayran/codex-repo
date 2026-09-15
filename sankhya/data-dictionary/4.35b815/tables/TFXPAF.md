# TFXPAF — FOX-TFXPAF

- Módulo técnico: [TFX](../modules/TFX.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParametrosPdv | Parametros Pdv | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CHAVE | Chave | S |  |  | N |
| CODEMPRESA | Cód.Empresa | I |  |  | N |
| CODPERFIL | Cód.Perfil | I |  |  | N |
| CODUSUARIO | Cód.Usuário | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DT | Data | D |  |  | N |
| GRUPO | Grupo | S |  |  | N |
| INTEIRO | Inteiro | I |  |  | N |
| LOGICO | Lógico | S |  |  | N |
| NUMERICO | Numero | F |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| TEXTO | Texto | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ParametrosPdv / [TFXPAF](TFXPAF.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMPRESA->CODEMP | I |
| ParametrosPdv / [TFXPAF](TFXPAF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUARIO->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
