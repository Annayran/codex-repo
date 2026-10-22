const {chromium}=require('playwright'),fs=require('fs'),assert=require('assert');
const js=fs.readFileSync('dist/readonly.js','utf8'),css=fs.readFileSync('dist/readonly.css','utf8'),jsp=fs.readFileSync('dist/sankhya-readonly/index.jsp','utf8');
const queries=[...jsp.matchAll(/<snk:query[^>]*>([\s\S]*?)<\/snk:query>/g)].map(m=>m[1]);
assert.equal(queries.length,15);assert(queries.every(sql=>/^\s*SELECT\b/i.test(sql)&&! /\b(INSERT|UPDATE|DELETE|MERGE|ALTER|DROP|TRUNCATE|EXECUTE)\b/i.test(sql)));
assert(!/\$\{r\.(TOKENGENESIS|URLGENESIS)\}/i.test(jsp));
assert(queries.filter(sql=>sql.includes('TOKENGENESIS')).every(sql=>sql.includes('CASE WHEN TRIM(TOKENGENESIS) IS NULL')));
assert(!js.includes('${'));assert(!jsp.includes('/*__APP_JS__*/'));
const esc=s=>String(s??'').replaceAll('&','&amp;').replaceAll('<','&lt;').replaceAll('>','&gt;').replaceAll('"','&quot;');
function html({missing=false,error=false,configurationMissing=false,configurationFailure=false}={}){
 const sources={
  summary:[{total:117,sent:113,errors:3,waiting:1}],
  movements:Array.from({length:17},(_,i)=>({id:i+1,note:100+i,context:'ITEM|'+i,product:i===16?'201':'202',serial:i===16?'QA-SERIE-001':'SIM-'+i,status:i%2?'ENVIADO':'REVISAO',entity:'ITEM_MATERIAL',updated:'20/10/2026',message:'Mensagem consultada'})),
  equipment:[{id:'101',product:'201',name:'Monitor de testes',serial:'QA-SERIE-001',client:'Cliente de teste',client_id:'50',model_id:'60',status:'Ativo',conciliation:'CONCILIADO',remote_id:'301'},{id:'102',product:'201',name:'<img src=x onerror="window.injected=true">',serial:'QA-SERIE-001',client:'Outro cliente',client_id:'51',model_id:'60',status:'Ativo',conciliation:'CONCILIADO',remote_id:'302'}],
  daily:Array.from({length:90},(_,i)=>{const d=new Date(Date.UTC(2026,9,21));d.setUTCDate(d.getUTCDate()-89+i);const date=d.toISOString().slice(0,10);return {date,label:date.slice(8,10)+'/'+date.slice(5,7),success:10+i*3,errors:i%3};}),
  clients:[{id:'client-1',name:'Cliente de teste',remote_id:'50',local_code:'10',status:'Ativo',conciliation:'CONCILIADO'}],
  models:[{id:'model-1',name:'Modelo QA',remote_id:'60',local_code:'201',status:'Ativo',conciliation:'CONCILIADO'}],
  sectors:[],materials:[],manufacturers:[],
  domains:[{id:'dom-1',kind:'PROPRIEDADE',name:'Próprio',local_code:'P',remote_id:'4',status:'Ativo',conciliation:'CONCILIADO'}],
  configuration:[{id:'CENTRAL',name:'Central de parametros',status:'Cadastrada',origin:'AD_CENTPARAM / NUPAR=1',purpose:'Agrupa configuracoes'}],
  stock:[{id:'201',name:'Produto QA',external_code:'QA-201',status:'ENVIADO'}],
  cancellations:[],history:[{id:'req-qa',method:'GET',http:'200',identity_state:'VALIDADA',persistence:'PENDENTE',endpoint:'/materiais/1',movement:'ITEM|16',updated:'20/10/2026',request_body:'{}',response_body:'{"id":1}',detail:'Aplicação local QA'}],
  scope:[{id:'2',kind:'EMPRESA',status:'NUPAR=1'}]
 };
 if(configurationMissing)sources.configuration=[];
 const data=missing?'':'<div id="genesis-bootstrap" hidden data-mode="sankhya-readonly">'+Object.entries(sources).map(([key,rows])=>'<section data-source="'+key+'">'+((key==='stock'&&error)||(key==='configuration'&&configurationFailure)?'<span data-error>Consulta indisponivel</span>':rows.map(row=>'<div data-row>'+Object.entries(row).map(([k,v])=>'<span data-field="'+k+'">'+esc(v)+'</span>').join('')+'</div>').join(''))+'</section>').join('')+'</div>';
 return '<html><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><style>'+css+'</style></head><body>'+data+'<div id="root"></div><script>'+js+'</script></body></html>';
}
(async()=>{
 fs.mkdirSync('qa/readonly',{recursive:true});const b=await chromium.launch({channel:'msedge',headless:true});
 try{
  const p=await b.newPage({viewport:{width:1440,height:1000}});const errors=[],external=[];
  p.on('pageerror',e=>errors.push(e.message));p.on('request',r=>{if(!r.url().startsWith('http://readonly.test/'))external.push(r.url());});
  await p.route('http://readonly.test/**',route=>route.fulfill({contentType:'text/html',body:html({error:route.request().url().includes('error'),missing:route.request().url().endsWith('/missing'),configurationMissing:route.request().url().includes('config-empty'),configurationFailure:route.request().url().includes('config-failure')})}));
  await p.goto('http://readonly.test/');await p.evaluate(()=>{localStorage.setItem('ma-demo-rows','[{"name":"DEMO-LEAK"}]');});await p.reload();
  await p.waitForTimeout(800);assert.equal(await p.getByText('DEMO-LEAK').count(),0);
  assert.equal(await p.locator('.metric strong').first().textContent(),'117');
  assert.equal(await p.locator('.bar-chart .chart-column').count(),7);
  await p.locator('.chart-period select').selectOption('30');assert.equal(await p.locator('.bar-chart .chart-column').count(),30);
  await p.locator('.chart-period select').selectOption('90');assert.equal(await p.locator('.bar-chart .chart-column').count(),90);
  await p.getByLabel('Data inicial do gráfico',{exact:true}).fill('2026-10-01');
  await p.getByLabel('Data final do gráfico',{exact:true}).fill('2026-10-05');
  assert.equal(await p.locator('.bar-chart .chart-column').count(),5);
  assert.equal(await p.locator('.metric strong').first().textContent(),'117');
  await p.getByLabel('Data inicial do gráfico',{exact:true}).fill('2026-10-20');
  assert(await p.getByText(/Selecione um intervalo válido/).isVisible());
  assert.equal(await p.locator('.bar-chart .chart-column').count(),0);
  await p.locator('.chart-period select').selectOption('7');
  await p.screenshot({path:'qa/readonly/chart-period-dark.png',fullPage:true});
  await p.screenshot({path:'qa/readonly/desktop-dark.png',fullPage:true});
  await p.getByRole('button',{name:'Envios e pendências',exact:true}).click();
  await p.getByRole('button',{name:'Próxima página',exact:true}).click();assert.equal(await p.locator('tbody tr').count(),2);
  await p.getByRole('textbox',{name:'Pesquisar no recorte'}).fill('ITEM|16');assert.equal(await p.locator('tbody tr').count(),1);
  await p.getByRole('button',{name:'Detalhes do registro 17'}).click();assert(await p.locator('dialog').evaluate(e=>e.open));
  await p.getByText('Detalhe tecnico original',{exact:true}).click();
  assert(await p.getByText('Mensagem consultada',{exact:true}).isVisible());await p.keyboard.press('Escape');
  await p.getByRole('button',{name:'Base instalada',exact:true}).click();
  assert.equal(await p.locator('img').count(),0);assert.equal(await p.evaluate(()=>window.injected),undefined);
  await p.getByRole('textbox',{name:'Pesquisar no recorte'}).fill('QA-SERIE-001');assert.equal(await p.locator('tbody tr').count(),2);
  await p.getByRole('button',{name:'Detalhes do registro 101'}).click();
  await p.getByRole('tab',{name:'Vínculos',exact:true}).click();assert.equal(await p.locator('.linked-record').count(),2);
  await p.getByRole('tab',{name:/Histórico/}).click();assert(await p.getByRole('heading',{name:'GET /materiais/1'}).isVisible());
  await p.getByText('Ver evidência da requisição',{exact:true}).click();
  await p.getByText('Aplicação local QA',{exact:true}).waitFor({state:'visible',timeout:5000});
  await p.getByRole('tab',{name:'JSON',exact:true}).click();assert((await p.locator('dialog pre').textContent()).includes('QA-SERIE-001'));await p.keyboard.press('Escape');
  await p.getByRole('button',{name:'Base instalada',exact:true}).click();await p.getByRole('tab',{name:/Clientes/}).click();assert(await p.getByText('Cliente de teste',{exact:true}).isVisible());
  assert.equal(await p.getByRole('tablist',{name:'Cadastros da base instalada'}).getByRole('tab').count(),7);
  assert.equal(await p.getByRole('tab',{name:'Geral',exact:true}).count(),0);
  await p.getByRole('tab',{name:/Domínios/}).click();assert(await p.getByText('PROPRIEDADE',{exact:true}).isVisible());
  await p.getByRole('tab',{name:/Empresas/}).click();assert(await p.getByRole('columnheader',{name:'Fornecedor',exact:true}).isVisible());
  await p.getByRole('tab',{name:/Modelos/}).click();assert(await p.getByText('Modelo QA',{exact:true}).isVisible());
  await p.getByRole('button',{name:'Conciliação e diagnóstico',exact:true}).click();await p.getByRole('textbox',{name:'Pesquisar pendências'}).fill('QA-SERIE-001');assert.equal(await p.getByRole('button',{name:'Analisar caso'}).count(),1);
  await p.getByRole('button',{name:'Analisar caso'}).click();await p.waitForTimeout(300);await p.screenshot({path:'qa/readonly/case-detail.png',fullPage:true});await p.keyboard.press('Escape');
  await p.getByRole('button',{name:'Requisições',exact:true}).click();
  await p.getByRole('button',{name:'Detalhes do registro req-qa'}).click();
  assert(await p.getByRole('heading',{name:'Identidade validada; gravacao ainda nao confirmada',exact:true}).isVisible());
  assert(await p.getByRole('heading',{name:'Como tratar',exact:true}).isVisible());
  await p.screenshot({path:'qa/readonly/diagnosis-dark.png',fullPage:true});
  await p.setViewportSize({width:390,height:844});
  assert(await p.evaluate(()=>document.documentElement.scrollWidth<=innerWidth));
  await p.screenshot({path:'qa/readonly/diagnosis-mobile.png',fullPage:true});
  await p.keyboard.press('Escape');await p.setViewportSize({width:1440,height:1000});
  await p.getByRole('button',{name:'Configurações',exact:true}).click();
  assert(await p.getByText('Cadastrada',{exact:true}).isVisible());
  assert.equal(await p.getByRole('tab',{name:'Execuções',exact:true}).count(),0);
  assert(!/AD_CENTPARAM|NUPAR|Repository/.test(await p.locator('body').innerText()));
  await p.getByRole('tab',{name:'Empresas e locais',exact:true}).click();
  assert(await p.getByText(/Nenhum registro de local retornado/).isVisible());
  await p.getByRole('tab',{name:'Regras de processamento',exact:true}).click();
  assert(await p.getByRole('rowheader',{name:'Saldo disponível',exact:true}).isVisible());
  await p.screenshot({path:'qa/readonly/settings-rules-dark.png',fullPage:true});
  assert(!/\bAD_|\bTGF|\bHSP_|NUPAR|Repository/.test(await p.locator('body').innerText()));
  assert.equal(await p.getByRole('button',{name:/Salvar|Confirmar vínculo|Reprocessar|Restaurar/}).count(),0);
  assert.equal(await p.locator('input[type="checkbox"]').count(),0);
  const stored=await p.evaluate(()=>Object.entries(localStorage).filter(([k])=>!k.startsWith('ma-demo-')));
  assert(stored.every(([k])=>k==='ma-genesis-readonly-theme'));
  await p.getByRole('button',{name:'Ativar tema claro'}).click();await p.waitForTimeout(200);
  await p.setViewportSize({width:390,height:844});await p.getByRole('button',{name:'Abrir navegação'}).click();await p.getByRole('button',{name:'Visão geral',exact:true}).click();
  await p.waitForTimeout(800);assert(await p.evaluate(()=>document.documentElement.scrollWidth<=innerWidth));await p.screenshot({path:'qa/readonly/mobile-light.png',fullPage:true});
  await p.locator('.chart-period select').selectOption('90');
  assert.equal(await p.locator('.bar-chart .chart-column').count(),90);
  await p.waitForTimeout(900);
  await p.screenshot({path:'qa/readonly/chart-period-mobile-light.png',fullPage:true});
  assert(await p.evaluate(()=>document.documentElement.scrollWidth<=innerWidth),JSON.stringify(await p.evaluate(()=>[...document.querySelectorAll('main *')].map(e=>({tag:e.tagName,cls:e.className,right:e.getBoundingClientRect().right,width:e.getBoundingClientRect().width})).filter(e=>e.right>innerWidth))));
  await p.emulateMedia({reducedMotion:'reduce'});
  assert.equal(await p.locator('.period-chart').evaluate(e=>getComputedStyle(e).animationName),'none');
  await p.getByRole('button',{name:'Abrir navegação'}).click();await p.getByRole('button',{name:'Base instalada',exact:true}).click();
  await p.getByLabel('Número de série',{exact:true}).fill('QA-SERIE-001');assert.equal(await p.locator('tbody tr').count(),2);
  assert(await p.evaluate(()=>document.documentElement.scrollWidth<=innerWidth));await p.screenshot({path:'qa/readonly/installed-mobile-light.png',fullPage:true});
  await p.getByRole('button',{name:'Abrir navegação'}).click();await p.getByRole('button',{name:'Configurações',exact:true}).click();
  assert(await p.evaluate(()=>document.documentElement.scrollWidth<=innerWidth));await p.screenshot({path:'qa/readonly/settings-mobile-light.png',fullPage:true});
  await p.setViewportSize({width:1440,height:1000});
  await p.goto('http://readonly.test/config-empty');await p.getByRole('button',{name:'Configurações',exact:true}).click();
  assert(await p.getByText(/Configuração nº 1 não cadastrada/).isVisible());
  await p.goto('http://readonly.test/config-failure');await p.getByRole('button',{name:'Configurações',exact:true}).click();
  assert(await p.getByText(/Consulta indisponível. Confira estrutura e permissão/).isVisible());
  await p.goto('http://readonly.test/error');await p.getByRole('alert').waitFor();assert(await p.getByText('Algumas consultas não foram carregadas').isVisible());
  await p.goto('http://readonly.test/missing');assert(await p.getByText('Dados do Sankhya não carregados').isVisible());assert.equal(await p.locator('tbody tr').count(),0);
  assert.deepEqual(errors,[]);assert.deepEqual(external,[]);
  console.log('PASS: fifteen SELECT-only queries, 7/30/90/custom date ranges, invalid dates, reduced motion, customer-facing configuration labels, no execution tab, installed-base tabs, bootstrap escaping/XSS, filtering/pagination, mobile/themes, missing sources, zero external requests. JSP/Oracle compilation not validated locally.');
 }finally{await b.close();}
})().catch(e=>{console.error(e);process.exitCode=1;});
