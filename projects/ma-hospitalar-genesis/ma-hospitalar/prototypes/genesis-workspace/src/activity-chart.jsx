import React,{useState} from 'react';
import {Activity,AlertTriangle,CalendarDays} from 'lucide-react';

export function periodBounds(rows,days){
 const dates=rows.map(r=>r.date).filter(d=>/^\d{4}-\d{2}-\d{2}$/.test(d||'')).sort();
 const end=dates.at(-1)||'';
 if(!end)return {start:'',end:'',min:''};
 const startDate=new Date(end+'T00:00:00Z');startDate.setUTCDate(startDate.getUTCDate()-days+1);
 return {start:[dates[0],startDate.toISOString().slice(0,10)].sort().at(-1),end,min:dates[0]};
}
export function filterDays(rows,start,end){return rows.filter(r=>r.date>=start&&r.date<=end);}
const label=date=>date?date.slice(8,10)+'/'+date.slice(5,7)+'/'+date.slice(0,4):'—';

export function ActivityChart({source}){
 const rows=source?.rows||[],available=periodBounds(rows,90);
 const [preset,setPreset]=useState('7'),[custom,setCustom]=useState(null);
 const range=preset==='custom'?(custom||periodBounds(rows,7)):periodBounds(rows,Number(preset));
 const invalid=!range.start||!range.end||range.start>range.end||range.start<available.min||range.end>available.end;
 const days=invalid?[]:filterDays(rows,range.start,range.end),max=Math.max(1,...days.map(d=>Number(d.success)+Number(d.errors)));
 const success=days.reduce((sum,d)=>sum+Number(d.success||0),0),errors=days.reduce((sum,d)=>sum+Number(d.errors||0),0);
 const changeDate=(key,value)=>{setCustom({...range,[key]:value});setPreset('custom');};
 return <section className="activity-chart"><div className="section-title"><div><h2>Movimentação da integração</h2><p>Respostas HTTP por dia</p></div><span className="chip"><Activity size={14}/>Histórico real</span></div>
 {!source||source.error?<div className="empty"><AlertTriangle/><p>Histórico diário indisponível.</p></div>:!available.end?<div className="empty"><CalendarDays/><p>Nenhuma data disponível para consulta.</p></div>:<>
 <div className="chart-period" aria-label="Período do gráfico"><label>Período<select value={preset} onChange={e=>{setPreset(e.target.value);if(e.target.value==='custom')setCustom(range);}}><option value="7">Últimos 7 dias</option><option value="30">Últimos 30 dias</option><option value="90">Últimos 90 dias</option><option value="custom">Personalizado</option></select></label><label>De<input aria-label="Data inicial do gráfico" type="date" value={range.start} min={available.min} max={available.end} onChange={e=>changeDate('start',e.target.value)}/></label><label>Até<input aria-label="Data final do gráfico" type="date" value={range.end} min={available.min} max={available.end} onChange={e=>changeDate('end',e.target.value)}/></label></div>
 {invalid?<p className="notice" role="alert">Selecione um intervalo válido entre {label(available.min)} e {label(available.end)}. A data inicial não pode ser posterior à final.</p>:<>
 <div className="chart-legend"><span><i className="green-dot"/>HTTP 2xx</span><span><i className="amber-dot"/>HTTP ≥ 400</span><span className="period-summary" role="status">{label(range.start)} a {label(range.end)} · {success} respostas 2xx · {errors} erros HTTP</span></div>
 <div key={range.start+'|'+range.end} className={'bar-chart period-chart'+(days.length>30?' is-dense':'')} role="img" aria-label={'Respostas HTTP de '+label(range.start)+' a '+label(range.end)}>{days.map((d,i)=><div className="chart-column" key={d.date} title={label(d.date)+': '+d.success+' respostas 2xx; '+d.errors+' erros HTTP'}><div className="bar-track"><div className="bar-confirmed" style={{height:Number(d.success)/max*100+'%'}}/><div className="bar-review" style={{height:Number(d.errors)/max*100+'%'}}/></div><span>{days.length<=10||i===0||i===days.length-1||i%Math.ceil(days.length/7)===0?d.label:'\u00a0'}</span></div>)}</div>
 {success+errors===0&&<p role="status">Nenhuma resposta nas categorias exibidas para este período.</p>}</>}
 <p className="chart-footnote">Disponível de {label(available.min)} a {label(available.end)}. O período altera somente este gráfico. Sucesso HTTP não confirma identidade ou gravação local.</p>
 </>}
 </section>;
}
