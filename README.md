# Zaya Engineering

## Repositório canônico

Este repositório deve usar como `origin` privado:
`https://github.com/Annayran/codex-repo.git`.

O endereço remoto documenta a origem canônica, mas não autoriza `push`,
publicação ou exposição de conteúdo. Essas ações exigem autorização explícita,
e nenhum segredo pode ser versionado.

Biblioteca corporativa de engenharia da Zaya.IT. Seu propósito é manter regras,
skills, recomendações, checklists e templates reutilizáveis para desenvolvimento,
revisão e publicação de soluções.

A arquitetura usa um kernel global enxuto, a skill como roteador e esta
biblioteca como fonte de recuperação seletiva. Consulte
`docs/context-architecture.md`.

## Público-alvo

Engenharia de software, sustentação, plataforma, revisão técnica e pessoas responsáveis por projetos Java, banco de dados, HTML5/JavaScript e customizações Sankhya.

## Como utilizar

1. Leia `AGENTS.md` antes de alterar os padrões.
2. Classifique a tarefa e carregue no máximo duas referências iniciais conforme
   `.agents/skills/zaya-sankhya-development/references/library-routing.md`.
3. Use os templates e checklists no repositório do projeto, sem copiar dados confidenciais.
4. Trate itens marcados como “Pendente de validação pela equipe” como não aprovados.
5. Registre desvios e justificativas na Issue, OS ou Merge Request.

Para consultar tabelas, campos, instâncias e relações da versão Sankhya
`4.35b815`, use primeiro `scripts/Get-ZayaTable.ps1`,
`Get-ZayaField.ps1`, `Get-ZayaRelation.ps1` ou
`Search-ZayaDictionary.ps1`. Abra a página da tabela somente quando necessário.

Ao abrir um projeto Zaya, a skill `zaya-sankhya-development` deve identificar o
modelo do projeto, ler as instruções locais e manter o perfil técnico
`docs/zaya/project-profile.md` no próprio repositório. O perfil não substitui a
documentação funcional nem autoriza mudanças.

## Documentos obrigatórios

- `AGENTS.md` para governança deste repositório.
- `standards/git-workflow.md` para fluxo de mudanças.
- `templates/merge-request/Default.md` para revisão.
- `standards/project-structure.md`, `standards/java.md` e `standards/gradle.md` para projetos Java/Sankhya.
- `standards/mvc.md` para a arquitetura padrão de novos projetos.
- `sankhya/addon-studio-metadata.md` para Add-on Studio e metadados.
- `.agents/skills/zaya-sankhya-development` para descoberta e validação.
- Checklist aplicável à tecnologia e ao processo Sankhya.
- Plano de deploy, validação e rollback quando houver banco ou publicação.
- `sankhya/data-dictionary/<versão>/AGENTS.md` ao consultar o mapa de entidades.

## Recomendações

Os demais documentos são recomendações iniciais até aprovação formal. Cada documento deve identificar claramente o que é obrigatório, recomendado ou pendente.

Para iniciar uma solução Java/Sankhya, copie o conteúdo de
`templates/repository/java-sankhya`, substitua os identificadores indicados e
remova somente os componentes comprovadamente não aplicáveis.

## CLI

O wrapper `scripts/zaya.ps1` oferece:

    zaya.ps1 project <projeto>
    zaya.ps1 profile <projeto>
    zaya.ps1 table TGFCAB
    zaya.ps1 field NUNOTA
    zaya.ps1 relation TGFCAB TGFITE
    zaya.ps1 pattern jape
    zaya.ps1 error ORA-00904

## Skills e testes

- `scripts/Install-ZayaSkills.ps1`: instala somente arquivos diferentes.
- `scripts/Sync-ZayaSkills.ps1`: sincroniza origem canônica para a instalação.
- `scripts/Test-ZayaSkillSync.ps1`: compara hashes.
- `tests/`: testes Pester de discovery, freshness, dicionário, sync e routing.
- `scripts/Write-ZayaTelemetry.ps1`: telemetria local opcional e sem conteúdo.
- `scripts/Get-ZayaProjectProfile.ps1`: ponto de entrada estável para descoberta e freshness; delega à implementação canônica da skill.

## Como propor alterações

Abra uma Issue ou OS, descreva motivação, impacto, alternativas e compatibilidade. Trabalhe em branch temporária, atualize `CHANGELOG.md`, abra Draft Merge Request, obtenha revisão obrigatória e só então solicite aprovação. Nenhuma alteração de padrão deve incluir código, nomes, credenciais ou dados específicos de clientes.
