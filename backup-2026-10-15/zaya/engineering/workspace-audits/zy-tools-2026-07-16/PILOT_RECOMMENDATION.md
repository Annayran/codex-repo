# Recomendação de projeto piloto

## Comparação

| Critério | Polyvox/Apontamento Produção | Lidon/CriacaoNotaEst | Creative Like/PersonalizacoesCompraLike |
|---|---|---|---|
| Git configurado | Sim; main e origin | Parcial; master sem origin | Sim; main e origin |
| Build identificável | Gradle | Gradle | Não detectado no relatório |
| Demanda real | Apontamento de produção | Criação de nota | Personalizações de compra |
| Dois ou mais componentes | Java, HTML5 e SQL | Java e HTML5 | Java, HTML5, JS, CSS e SQL |
| Risco controlável | Médio/alto; 5 entradas locais | Médio/alto; 13 entradas e sem origin | Médio; limpo, mas build desconhecido |
| Banco | SQL presente | Não indicado | SQL presente |
| Adequação ao piloto | Alta após baseline da working tree | Média/baixa | Média; falha no critério de build identificável |

## Recomendação única

Recomenda-se `C:\dev\zy-tools\workspace\Polyvox\Apontamento Produção` como piloto, com destino proposto `C:\dev\zaya\clients\polyvox\apontamento-producao`.

Ele é o único dos três candidatos que combina Git com origin, Gradle identificável e uma solução com Java, HTML5 e SQL. Isso permite validar preservação do repositório, solução multiartefato, README, AGENTS.md, scripts de banco e template de Merge Request sem escolher a plataforma Skala, que aparenta maior criticidade e complexidade.

## Condições obrigatórias

- Explicar e preservar as cinco entradas existentes no Git status.
- Confirmar owner e criticidade com a equipe Polyvox.
- Verificar se os SQLs têm deploy, validação e rollback.
- Fazer backup restaurável e registrar hashes antes de qualquer migração.
- Confirmar Java 8 e executar o build no caminho original e no destino.
- Validar integrações Sankhya, artefato gerado e procedimento de retorno.
- Não incluir outros projetos Polyvox no piloto.

## Critério de sucesso

Histórico Git íntegro, working tree preservada, build equivalente, documentação padrão aprovada, banco com ciclo completo, smoke test aceito e rollback demonstrado.