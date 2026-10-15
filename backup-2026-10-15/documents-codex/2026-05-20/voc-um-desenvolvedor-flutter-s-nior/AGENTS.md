# AGENTS.md

Regras para futuras execuções do Codex no AF Health Lab:

- Manter arquitetura por feature.
- Não colocar lógica de Firebase em widgets.
- Sempre rodar `flutter analyze` após mudanças grandes.
- Preservar identidade visual AF Health Lab.
- Código em inglês.
- UI em PT-BR.
- Evitar dependências desnecessárias.
- Preferir widgets reutilizáveis.
- Usar Riverpod para estado.
- Usar GoRouter para rotas.
- Manter design dark premium.
- Não substituir gráficos nativos por imagens.
- Não criar `google-services.json`, `GoogleService-Info.plist` ou `firebase_options.dart` manualmente.
- Usar fallback/mock apenas para visualização segura do MVP quando o Firestore estiver vazio.
