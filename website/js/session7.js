/* Browser teaching aids only; no Java or database requests happen here. */
document.addEventListener('DOMContentLoaded',()=>{initSessionSevenTrace();initSessionSevenLog();});
function initSessionSevenTrace() {
    const selector=document.getElementById('s7TraceRequest');
    const next=document.getElementById('s7TraceNext'), reset=document.getElementById('s7TraceReset');
    const list=document.getElementById('s7TraceSteps'), status=document.getElementById('s7TraceStatus');
    if(!selector||!next||!reset||!list||!status)return;
    const journeys={
    "save": [
        "The client POSTs application/json to /api/products.",
        "Jersey chooses create and Jackson maps the body to ProductInput.",
        "ProductRules accepts the required text and whole-naira price.",
        "ProductStore chooses the next id, stores normalized immutable Product data and increments the counter.",
        "The resource returns 201 with JSON product data and its absolute Location.",
        "The client GETs that Location and confirms the stored values."
    ],
    "lookup": [
        "The client PUTs complete JSON to an existing /api/products/{id}.",
        "Jersey supplies the path id and Jackson maps the request body.",
        "The resource checks the positive id and all ProductRules.",
        "The store finds the key and replaces its value with a new Product preserving the id.",
        "The resource returns 200 with the replacement JSON.",
        "A following GET and filter check prove the final values and unchanged count."
    ],
    "reject": [
        "The client PUTs well-formed JSON containing priceNgn -1.",
        "Jackson can map the negative integer into ProductInput.",
        "ProductRules detects that the price is below the accepted range.",
        "The resource returns 400 JSON error before calling the store replacement.",
        "The prior Product and collection count remain unchanged.",
        "The client GETs the target, compares every prior value and explains the rejection."
    ],
    "delete": [
        "The client DELETEs an existing positive item path, with no body.",
        "Jersey selects delete and supplies the converted path id.",
        "The resource checks the positive id and asks ProductStore to remove its entry.",
        "The store returns true because the entry existed.",
        "The resource returns 204 with no response body.",
        "GET and repeated DELETE return 404; the intended state remains item absent."
    ]
};
    let step=0;
    const restart=()=>{step=0;const item=document.createElement('li');item.textContent='Predict first, then reveal a step.';list.replaceChildren(item);status.textContent='0 of 6 steps revealed.';next.disabled=false;};
    next.addEventListener('click',()=>{const steps=journeys[selector.value];if(!steps||step>=steps.length)return;if(!step)list.replaceChildren();const item=document.createElement('li');item.textContent=steps[step++];list.appendChild(item);next.disabled=step===steps.length;status.textContent=step+' of 6 steps revealed.'+(next.disabled?' Now explain it using real source and test evidence.':'');});
    selector.addEventListener('change',restart);reset.addEventListener('click',restart);restart();
}
function initSessionSevenLog() {
    const checks = [...document.querySelectorAll('[data-s7-check]')];
    const notes = document.getElementById('s7Notes');
    const summary = document.getElementById('s7ChecklistSummary');
    const progress = document.getElementById('s7Progress');
    const status = document.getElementById('s7SaveStatus');
    const download = document.getElementById('s7ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session7_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s7Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s7Check, check.checked]))}));
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
        const log = ['Aptech — Session 7 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session7-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
