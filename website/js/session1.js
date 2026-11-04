/* Teaching tools only: no Java compilation or server requests happen here. */
document.addEventListener('DOMContentLoaded', () => {
    initSessionOneTrace();
    initSessionOneLearningLog();
});

function initSessionOneTrace() {
    const selector = document.getElementById('s1TraceRequest');
    const next = document.getElementById('s1TraceNext');
    const reset = document.getElementById('s1TraceReset');
    const list = document.getElementById('s1TraceSteps');
    const status = document.getElementById('s1TraceStatus');
    if (!selector || !next || !reset || !list || !status) return;

    const scenarios = {
        greeting: ['The client sends GET /greeting to 127.0.0.1:8081.', 'The HTTP server receives the request and calls the registered handler.', 'The handler reads method GET and path /greeting. The method is allowed.', 'The /greeting branch matches. It chooses status 200 and Welcome to Aptech!', 'The handler sends the headers and UTF-8 body. The client receives 200 and the greeting.'],
        missing: ['The client sends GET /missing to 127.0.0.1:8081.', 'The HTTP server receives the request and calls the registered handler.', 'The handler reads method GET and path /missing. The method is allowed.', 'The known path does not match. The final else chooses status 404 and Resource not found.', 'The handler sends the headers and UTF-8 body. The client receives 404 and the explanation.'],
        post: ['The client sends POST /greeting to 127.0.0.1:8081.', 'The HTTP server receives the request and calls the registered handler.', 'The handler reads method POST. The first test, not GET, is true.', 'The first branch chooses status 405, Use GET for this service., and Allow: GET. Later branches are skipped.', 'The handler sends the headers and UTF-8 body. The client receives 405 and the explanation.']
    };
    let revealed = 0;
    const restart = () => {
        revealed = 0;
        list.replaceChildren();
        const placeholder = document.createElement('li');
        placeholder.textContent = 'Predict the result, then press “Next step.”';
        list.appendChild(placeholder);
        status.textContent = '0 of 5 steps revealed.';
        next.disabled = false;
    };
    next.addEventListener('click', () => {
        const steps = scenarios[selector.value];
        if (revealed >= steps.length) return;
        if (!revealed) list.replaceChildren();
        const item = document.createElement('li');
        item.textContent = steps[revealed];
        list.appendChild(item);
        revealed += 1;
        status.textContent = `${revealed} of ${steps.length} steps revealed.${revealed === steps.length ? ' Now explain the journey in your own words.' : ''}`;
        next.disabled = revealed === steps.length;
    });
    selector.addEventListener('change', restart);
    reset.addEventListener('click', restart);
    restart();
}

function initSessionOneLearningLog() {
    const checks = [...document.querySelectorAll('[data-s1-check]')];
    const progress = document.getElementById('s1Progress');
    const summary = document.getElementById('s1ChecklistSummary');
    const notes = document.getElementById('s1Notes');
    const saveStatus = document.getElementById('s1SaveStatus');
    const download = document.getElementById('s1ExportNotes');
    if (!checks.length || !progress || !summary || !notes || !saveStatus || !download) return;
    const storageKey = 'aptech_session1_learning_v1';
    let storageAvailable = true;
    try {
        const saved = JSON.parse(localStorage.getItem(storageKey) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s1Check] === true; });
            if (typeof saved.notes === 'string') notes.value = saved.notes;
        }
    } catch { storageAvailable = false; }

    const renderSummary = () => {
        const completed = checks.filter(check => check.checked).length;
        progress.textContent = `${completed} of ${checks.length} learning milestones checked. These are self-reported, not automatic grades.`;
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
        const state = { notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s1Check, check.checked])) };
        try {
            localStorage.setItem(storageKey, JSON.stringify(state));
            storageAvailable = true;
            saveStatus.textContent = 'Saved in this browser only. Download a copy to submit or keep elsewhere.';
        } catch {
            storageAvailable = false;
            saveStatus.textContent = 'This browser cannot save progress locally. Download your learning log before closing the page.';
        }
    };
    checks.forEach(check => check.addEventListener('change', () => { renderSummary(); save(); }));
    let saveTimer;
    notes.addEventListener('input', () => { clearTimeout(saveTimer); saveTimer = setTimeout(save, 350); });
    window.addEventListener('pagehide', save);
    download.addEventListener('click', () => {
        save();
        const log = ['Aptech — Session 1 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const blob = new Blob([log], { type: 'text/plain;charset=utf-8' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session1-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    renderSummary();
    saveStatus.textContent = storageAvailable ? 'Progress stays in this browser. Use Download to keep a separate copy.' : 'Local saving is unavailable. Download your learning log before closing the page.';
}
