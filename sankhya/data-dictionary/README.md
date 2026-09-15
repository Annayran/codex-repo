# Mapas de dicionário Sankhya

Cada versão possui um mapa isolado. Nunca use o mapa de uma versão como prova
de compatibilidade com outra.

## Versões disponíveis

- [4.35b815](4.35b815/README.md)

## Uso

1. Confirme a versão do ambiente alvo.
2. Abra o `README.md` da versão correspondente.
3. Pesquise a tabela ou instância.
4. Abra a página específica em `tables/`.
5. Confirme no ambiente tudo que envolver regra funcional, constraint física,
   objeto adicional ou DML.

Os mapas são referências sanitizadas e não devem ser importados no Sankhya.

## Gerar uma nova versão

O gerador reutilizável fica em `tools/generate-mgedd-map.ps1`. Execute-o somente
após validar o archive, extrair o XML para uma área intermediária autorizada e
definir uma pasta de saída vazia correspondente à versão Sankhya.

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass `
  -File .\tools\generate-mgedd-map.ps1 `
  -SourceXml <arquivo.xml> `
  -OutputDir <diretorio-da-versao> `
  -SourceArchive <arquivo.rar> `
  -SourceHash <sha256>
```

O gerador não copia usuário, domínio nem conteúdo integral dos scripts. A opção
`-Force` exige avaliação prévia, pois permite sobrescrever arquivos do mapa.
