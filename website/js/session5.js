/* Browser teaching aids only; no Java or database requests happen here. */
document.addEventListener('DOMContentLoaded',()=>{initSessionFiveTrace();initSessionFiveLog();});
function initSessionFiveTrace() {
    const selector=document.getElementById('s5TraceRequest');
    const next=document.getElementById('s5TraceNext'), reset=document.getElementById('s5TraceReset');
    const list=document.getElementById('s5TraceSteps'), status=document.getElementById('s5TraceStatus');
    if(!selector||!next||!reset||!list||!status)return;
    const journeys={
    "save": [
        "Create a Student with fictional values in Java memory.",
        "Check its application rules before export.",
        "Prepare a JAXBContext and create a Marshaller.",
        "JAXB reads the annotated properties and uses the agreed root, attribute and child names/order.",
        "JAXB escapes special characters and writes XML; the excluded property is omitted.",
        "Inspect the actual output file and compare its parsed values with your prediction."
    ],
    "lookup": [
        "Choose the matching XML input and explicit acceptance XSD.",
        "Configure the input parser and attach the Schema to the Unmarshaller.",
        "JAXB checks/matches XML and constructs a new mapped Student.",
        "The annotated properties receive decoded values; omitted password remains null.",
        "Check the application rules before using the object.",
        "Read getters and compare values; the result is a new object, not the original reference."
    ],
    "reject": [
        "Start with well-formed XML whose course child is missing.",
        "The validated reader attaches student_schema.xsd and stops on validation problems.",
        "The parser reaches gpa where the sequence requires course.",
        "Schema validation reports the missing/incorrectly placed element.",
        "The reader catches the rejection, prints its useful clue and exits with code 2.",
        "No ACCEPTED result is printed; repair the input and prove the corrected file passes."
    ]
};
    let step=0;
    const restart=()=>{step=0;const item=document.createElement('li');item.textContent='Predict first, then reveal a step.';list.replaceChildren(item);status.textContent='0 of 6 steps revealed.';next.disabled=false;};
    next.addEventListener('click',()=>{const steps=journeys[selector.value];if(!steps||step>=steps.length)return;if(!step)list.replaceChildren();const item=document.createElement('li');item.textContent=steps[step++];list.appendChild(item);next.disabled=step===steps.length;status.textContent=step+' of 6 steps revealed.'+(next.disabled?' Now explain it using real source and test evidence.':'');});
    selector.addEventListener('change',restart);reset.addEventListener('click',restart);restart();
}
function initSessionFiveLog() {
    const checks = [...document.querySelectorAll('[data-s5-check]')];
    const notes = document.getElementById('s5Notes');
    const summary = document.getElementById('s5ChecklistSummary');
    const progress = document.getElementById('s5Progress');
    const status = document.getElementById('s5SaveStatus');
    const download = document.getElementById('s5ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session5_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s5Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s5Check, check.checked]))}));
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
        const log = ['Aptech — Session 5 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session5-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
