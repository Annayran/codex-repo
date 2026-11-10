# Certificado digital e autenticação mTLS

Novas configurações usam `SANKHYA`: o add-on resolve o CNPJ da empresa e acessa `AssinadorNFe`, como a integração Sicoob. A chave privada é entregue diretamente ao gerenciador TLS em memória; não é exportada nem gravada no cofre do add-on. Não há dependência de arquivos no servidor. Certificado válido, cadeia disponível, chave correspondente, CNPJ da empresa e uso para autenticação de cliente são verificados. As validações TLS do servidor permanecem padrão.

A alternativa `UPLOAD` precisa ser selecionada e salva explicitamente. Somente nela se informa P12/PFX e senha, protegidos com AES-GCM pelo cofre gerenciado. Não existe fallback automático. Configurações anteriores sem o novo campo continuam em UPLOAD: a atualização não altera a identidade TLS das contas existentes. O campo está no JSON de configuração; não exige mudança de schema.

Client ID e client secret continuam obrigatórios nas duas origens e são protegidos no cofre. O formulário não devolve segredos. Na origem SANKHYA, uploads são rejeitados e materiais antigos de upload não são carregados para uso. Uma nova gravação das credenciais nessa origem substitui o conteúdo antigo do cofre sem certificado ou senha.

O processador usa a origem da configuração atual, inclusive para cobranças cujo snapshot seja anterior à troca. Antes de cada chamada autenticada, resolve novamente o certificado nativo. O fingerprint integra a chave do cache OAuth: uma renovação demanda novo token. Diagnósticos também incluem o fingerprint, portanto uma renovação invalida a aprovação anterior para ativação. Chamadas em andamento mantêm o mesmo material entre OAuth e operação bancária. A origem só muda ao salvar a configuração, pausando novas emissões e exigindo novo diagnóstico. Recebimentos continuam conforme parametrizados.

## Homologação necessária

O certificado do Console NF-e só poderá ser usado se estiver autorizado pelo banco para a conta, aplicação, ambiente e contrato. Um e-CNPJ fiscal não assegura essa autorização. Se o banco emitir ou exigir outro certificado, selecione UPLOAD e cadastre o material homologado. O diagnóstico obtém token e consulta sem emitir boleto.

Validar no ambiente Sankhya de destino o acesso a AssinadorNFe e a renovação de seu cache, disponibilidade da chave em Cloud, autorização dos dois bancos, cadeia de confiança e comportamento de expiração/revogação. A detecção ocorre quando o Sankhya entrega o novo certificado; o add-on não altera o cache interno do ERP. Revogação e vínculo bancário dependem da infraestrutura e homologação do banco. Os perfis bancários continuam bloqueados até a homologação real.
