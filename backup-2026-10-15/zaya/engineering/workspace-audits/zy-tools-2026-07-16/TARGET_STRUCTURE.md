# Estrutura futura recomendada

```text
C:\dev\
├── tools\
│   ├── jdks\
│   ├── gradle\
│   ├── maven\
│   ├── sankhya-sdk\
│   ├── app-servers\
│   ├── database-clients\
│   └── local-utilities\
└── zaya\
    ├── clients\
    │   ├── atttelecontrol\
    │   │   └── atttelecontrol\
    │   ├── creative-like\
    │   │   ├── personalizacoes-compra-like\
    │   │   └── momento-negociacao\
    │   ├── crm-bonus\
    │   │   └── tennisplace\
    │   ├── lidon\
    │   │   ├── criacao-nota-est\
    │   │   └── lidon-lancamentos\
    │   ├── ma-hospitalar\
    │   │   └── genesis\
    │   ├── polyvox\
    │   │   ├── apontamento-producao\
    │   │   ├── importador-pedido\
    │   │   ├── koncili\
    │   │   └── polyvox-telecontrol\
    │   ├── portomag\
    │   │   └── crm-leitura-pdf\
    │   ├── skala\
    │   │   └── skala-solution\
    │   ├── teleword\
    │   │   └── flow-parceiros\
    │   └── unknown-client\
    │       ├── flow-contratos-servicos\
    │       └── tennisplace-unclassified\
    ├── platform\
    │   ├── skala-om-platform\
    │   └── shared-libraries\
    │       └── barramento-regras-lidon\
    ├── engineering\
    │   ├── standards\
    │   ├── templates\
    │   │   ├── repository\
    │   │   ├── agents\
    │   │   └── merge-request\
    │   ├── database\
    │   │   ├── deploy-template\
    │   │   ├── validation-template\
    │   │   └── rollback-template\
    │   └── workspace-audits\
    ├── sandbox\
    │   ├── spikes\
    │   └── disposable-tests\
    └── archive\
        ├── legacy\
        │   └── legacy-duplicates-original\
        ├── duplicates\
        │   ├── lidon\
        │   ├── polyvox\
        │   ├── skala\
        │   └── tennisplace\
        └── zip-backups\
            ├── creative-like\
            ├── lidon\
            ├── polyvox\
            ├── portomag\
            └── tennisplace\
```

## Princípios

- Um repositório por solução publicável de cliente; Java, JSP, HTML5, JavaScript, SQL e documentação permanecem juntos quando têm o mesmo ciclo de release.
- Nenhum monorepositório reunindo clientes.
- Componentes só entram em `platform` após evidência de reutilização e versionamento independente.
- Ferramentas e SDKs ficam fora de `zaya`, em `C:\dev\tools`.
- Itens incertos permanecem na origem até decisão humana; o desenho não autoriza movimentação.
- ZIPs permanecem fechados e só podem ser arquivados depois de checksum e backup.

## Decisões ainda condicionais

`BarramentoRegrasLidon` só deve ir para `platform` se houver consumidores independentes do cliente Lidon. Caso contrário, deve permanecer em `clients\lidon`. A plataforma Skala precisa de validação de ownership: se for específica do cliente, deve ir para `clients\skala`; se for produto reutilizado, permanece em `platform`.