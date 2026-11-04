/* Local teaching aids; no XML conversion or HTTP happens in this browser. */
document.addEventListener('DOMContentLoaded', () => { initSessionEightTrace(); initSessionEightLog(); });
function initSessionEightTrace() {
    const selector = document.getElementById('s8TraceRequest');
    const next = document.getElementById('s8TraceNext');
    const reset = document.getElementById('s8TraceReset');
    const list = document.getElementById('s8TraceSteps');
    const status = document.getElementById('s8TraceStatus');
    if (!selector || !next || !reset || !list || !status) return;
    const journeys = {
    "accept": [
        "The CLI checks the XML input argument and a new output folder.",
        "The parser reads XML; the attached XSD checks root, child order and price.",
        "ProductRules checks trimmed text and price and creates normalized input.",
        "JAXB writes schema-valid XML, reads it again and confirms all three values.",
        "Jackson writes the explicit name/priceNgn/category Map as JSON with no id.",
        "The program publishes three local files and exits 0; no HTTP was sent."
    ],
    "schema": [
        "The XML has name and category but no required price_ngn child.",
        "The XML parser can read its well-formed tags.",
        "The attached XSD rejects the missing child or unexpected child order.",
        "The Unmarshaller rejects before returning accepted application data.",
        "The CLI prints a rejection clue and exits 2 before publishing an output folder.",
        "Repair the XML in a personal file and retest into a new output folder."
    ],
    "rules": [
        "The XML name contains only ordinary spaces; all children and price are present.",
        "The parser and chosen XSD accept the structure and XML types.",
        "ProductRules trims the name and detects the empty text.",
        "The rule throws before normalized XML or JSON is prepared.",
        "The CLI prints the name clue and exits 2; no new output folder exists.",
        "Earlier accepted files remain earlier evidence, not this failed conversion."
    ],
    "http": [
        "Inspect product.json from a successful accepted conversion; it has no id.",
        "The client POSTs those JSON bytes with Content-Type: application/json.",
        "Jersey selects create and Jackson maps the body into ProductInput.",
        "The server checks its own rules, assigns an id and stores the Product.",
        "The resource returns 201 JSON with its absolute Location.",
        "GET that actual Location to prove the stored values; local conversion alone did not prove this."
    ]
};
    let step = 0;
    const restart = () => {
        step = 0;
        const item = document.createElement('li');
        item.textContent = 'Predict first, then reveal a step.';
        list.replaceChildren(item);
        status.textContent = '0 of 6 steps revealed.';
        next.disabled = false;
    };
    next.addEventListener('click', () => {
        const steps = journeys[selector.value];
        if (!steps || step >= steps.length) return;
        if (!step) list.replaceChildren();
        const item = document.createElement('li');
        item.textContent = steps[step++];
        list.appendChild(item);
        next.disabled = step === steps.length;
        status.textContent = step + ' of 6 steps revealed.' + (next.disabled ? ' Explain it using actual code and evidence.' : '');
    });
    selector.addEventListener('change', restart);
    reset.addEventListener('click', restart);
    restart();
}
function initSessionEightLog() {
    const checks = [...document.querySelectorAll('[data-s8-check]')];
    const notes = document.getElementById('s8Notes');
    const summary = document.getElementById('s8ChecklistSummary');
    const progress = document.getElementById('s8Progress');
    const status = document.getElementById('s8SaveStatus');
    const download = document.getElementById('s8ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session8_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s8Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s8Check, check.checked]))}));
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
        const log = ['Aptech — Session 8 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session8-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
