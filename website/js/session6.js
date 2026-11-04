/* Browser teaching aids only; no Java or database requests happen here. */
document.addEventListener('DOMContentLoaded',()=>{initSessionSixTrace();initSessionSixLog();});
function initSessionSixTrace() {
    const selector=document.getElementById('s6TraceRequest');
    const next=document.getElementById('s6TraceNext'), reset=document.getElementById('s6TraceReset');
    const list=document.getElementById('s6TraceSteps'), status=document.getElementById('s6TraceStatus');
    if(!selector||!next||!reset||!list||!status)return;
    const journeys={
    "save": [
        "The client sends GET /api/greeting?name=Ada to the running JDK HTTP server.",
        "Jersey matches /greeting and @GET in GreetingResource.",
        "Jersey supplies the name query value; your method trims and validates it.",
        "GreetingLogic returns the welcome sentence as ordinary Java text.",
        "Response.ok chooses 200 and Jersey writes the text/plain body.",
        "The client receives the response; compare it with your actual curl.exe output."
    ],
    "lookup": [
        "The client sends GET /api/products/2 in full mode.",
        "Jersey matches /products/{id} and converts the path segment to int 2.",
        "ProductResource verifies the positive id and calls ProductCatalog.findById.",
        "The fixed in-memory list contains product 2, so the lookup returns that object.",
        "Response.ok chooses 200; Jackson writes the product getters as JSON.",
        "The client reads USB Mouse and priceNgn 6500; verify with the real service."
    ],
    "reject": [
        "The client sends GET /api/products/999 in full mode.",
        "Jersey matches the existing resource route and converts the path id.",
        "The numeric id is positive, so your method calls findById(999).",
        "No product matches; ProductCatalog returns null.",
        "Your resource selects 404 and Map error data; Jackson serializes the map.",
        "The client receives the JSON error. A valid route can still report an absent item."
    ]
};
    let step=0;
    const restart=()=>{step=0;const item=document.createElement('li');item.textContent='Predict first, then reveal a step.';list.replaceChildren(item);status.textContent='0 of 6 steps revealed.';next.disabled=false;};
    next.addEventListener('click',()=>{const steps=journeys[selector.value];if(!steps||step>=steps.length)return;if(!step)list.replaceChildren();const item=document.createElement('li');item.textContent=steps[step++];list.appendChild(item);next.disabled=step===steps.length;status.textContent=step+' of 6 steps revealed.'+(next.disabled?' Now explain it using real source and test evidence.':'');});
    selector.addEventListener('change',restart);reset.addEventListener('click',restart);restart();
}
function initSessionSixLog() {
    const checks = [...document.querySelectorAll('[data-s6-check]')];
    const notes = document.getElementById('s6Notes');
    const summary = document.getElementById('s6ChecklistSummary');
    const progress = document.getElementById('s6Progress');
    const status = document.getElementById('s6SaveStatus');
    const download = document.getElementById('s6ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session6_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s6Check] === true; });
            if (typeof saved.notes === 'string') notes.value = saved.notes;
        }
    } catch { canSave = false; }
    const render = () => {
        const count = checks.filter(check => check.checked).length;
        progress.textContent = `${count} of ${checks.length} learning milestones checked. These are self-reported, not automatic grades.`;
        const list = document.createElement('ul');
        checks.forEach(check => {
            const item = document.createElement('li');
            const symbol = document.createElement('span');
            symbol.textContent = check.checked ? '✓' : '○';
            symbol.setAttribute('aria-hidden', 'true');
            const link = document.createElement('a');
            link.href = '#' + check.closest('.sub-block').id;
            link.textContent = (check.checked ? 'Checked: ' : 'To practise: ') + check.nextElementSibling.textContent;
            item.append(symbol, link);
            list.appendChild(item);
        });
        summary.replaceChildren(list);
    };
    const save = () => {
        try {
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s6Check, check.checked]))}));
            canSave = true;
            status.textContent = 'Saved in this browser only. Download a copy to submit or keep elsewhere.';
        } catch {
            canSave = false;
            status.textContent = 'Local saving is unavailable. Download this log before closing the page.';
        }
    };
    checks.forEach(check => check.addEventListener('change', () => { render(); save(); }));
    let saveTimer;
    notes.addEventListener('input', () => { clearTimeout(saveTimer); saveTimer = setTimeout(save, 350); });
    window.addEventListener('pagehide', save);
    download.addEventListener('click', () => {
        save();
        const log = ['Aptech — Session 6 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session6-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
