const esbuild=require('esbuild'),fs=require('fs');
esbuild.buildSync({entryPoints:['src/readonly.jsx'],bundle:true,minify:true,outfile:'dist/readonly.js',supported:{'template-literal':false},define:{'process.env.NODE_ENV':'"production"'}});
const js=fs.readFileSync('dist/readonly.js','utf8').replace(/<\/script/gi,'<\\/script');
const css=fs.readFileSync('dist/readonly.css','utf8');
if(js.includes('${')||css.includes('${'))throw Error('JSP EL collision in bundle; refuse packaging.');
const template=fs.readFileSync('sankhya-readonly.jsp','utf8');
fs.mkdirSync('dist/sankhya-readonly',{recursive:true});
fs.writeFileSync('dist/sankhya-readonly/index.jsp',template.replace('/*__APP_CSS__*/',()=>css).replace('/*__APP_JS__*/',()=>js));
console.log('Read-only JSP generated. Requires compilation and permissions validation in Sankhya.');
