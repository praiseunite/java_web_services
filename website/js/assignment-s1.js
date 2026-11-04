/* Local self-reported log; this page runs no equipment service. */
document.addEventListener('DOMContentLoaded', initWorkshopOneLog);
function initWorkshopOneLog() {
    const checks = [...document.querySelectorAll('[data-ws1-check]')];
    const notes = document.getElementById('ws1Notes');
    const summary = document.getElementById('ws1ChecklistSummary');
    const progress = document.getElementById('ws1Progress');
    const status = document.getElementById('ws1SaveStatus');
    const download = document.getElementById('ws1ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_workshop_s1_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.ws1Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.ws1Check, check.checked]))}));
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
        const log = ['Aptech — S1 workshop learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-workshop_s1-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
