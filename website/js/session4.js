/* Browser teaching aids only; no Java or database requests happen here. */
document.addEventListener('DOMContentLoaded',()=>{initSessionFourTrace();initSessionFourLog();});
function initSessionFourTrace() {
    const selector=document.getElementById('s4TraceRequest');
    const next=document.getElementById('s4TraceNext'), reset=document.getElementById('s4TraceReset');
    const list=document.getElementById('s4TraceSteps'), status=document.getElementById('s4TraceStatus');
    if(!selector||!next||!reset||!list||!status)return;
    const journeys={
        save:['The client POSTs a title JSON to /api/books.','Jersey reads JSON into Input and calls BookResource.create.','BookStore constructs and validates Book, then opens an EntityManager and begins a transaction.','Hibernate inserts the entity; H2 assigns its id. The transaction successfully commits.','BookStore closes the context and returns the saved entity.','The resource returns 201, JSON id/title and Location; a later GET reads the stored row.'],
        lookup:['The caller reads the SOAP agreement and supplies the actual saved book id.','A SOAP POST reaches /library; Metro identifies getBookTitle and unmarshals bookId.','LibraryServiceImpl checks the positive id and calls the same BookStore used by REST.','BookStore opens a fresh context and Hibernate reads the stored row from H2.','The implementation returns its title after the store closes the context.','Metro marshals the title into SOAP; a raw caller reads XML, or a typed proxy returns a String.'],
        reject:['The client POSTs a blank title as valid JSON.','Jersey reads the body and calls the resource method.','BookStore constructs Book; its setter detects blank text.','IllegalArgumentException stops creation before opening the insert transaction.','The resource catch builds a 400 response with the validation reason.','No row was inserted; compare the collection before/after to prove it.']
    };
    let step=0;
    const restart=()=>{step=0;const item=document.createElement('li');item.textContent='Predict first, then reveal a step.';list.replaceChildren(item);status.textContent='0 of 6 steps revealed.';next.disabled=false;};
    next.addEventListener('click',()=>{const steps=journeys[selector.value];if(!steps||step>=steps.length)return;if(!step)list.replaceChildren();const item=document.createElement('li');item.textContent=steps[step++];list.appendChild(item);next.disabled=step===steps.length;status.textContent=step+' of 6 steps revealed.'+(next.disabled?' Now explain it using real source and test evidence.':'');});
    selector.addEventListener('change',restart);reset.addEventListener('click',restart);restart();
}
function initSessionFourLog() {
    const checks = [...document.querySelectorAll('[data-s4-check]')];
    const notes = document.getElementById('s4Notes');
    const summary = document.getElementById('s4ChecklistSummary');
    const progress = document.getElementById('s4Progress');
    const status = document.getElementById('s4SaveStatus');
    const download = document.getElementById('s4ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session4_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s4Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s4Check, check.checked]))}));
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
        const log = ['Aptech — Session 4 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session4-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
