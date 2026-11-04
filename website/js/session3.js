/* Teaching aids only. Real Java calls use the separate Maven project. */
document.addEventListener('DOMContentLoaded', () => {
    initSessionThreeTrace();
    initSessionThreeLog();
});

function initSessionThreeTrace() {
    const selector = document.getElementById('s3TraceRequest');
    const next = document.getElementById('s3TraceNext');
    const reset = document.getElementById('s3TraceReset');
    const list = document.getElementById('s3TraceSteps');
    const status = document.getElementById('s3TraceStatus');
    if (!selector || !next || !reset || !list || !status) return;
    const scenarios = {
        fees: [
            'The caller prepares calculateCourseFees with ADSE and 2 using the contract namespace and input names.',
            'HTTP POST carries the SOAP 1.1 Envelope and Body to /greeting with the matching headers.',
            'Metro identifies the operation and unmarshals courseName to String and numberOfSemesters to int.',
            'GreetingServiceImpl validates 1–8, looks up ADSE at 850 USD and returns 850 × 2 = 1700.',
            'Metro marshals 1700 into totalFees inside calculateCourseFeesResponse and returns HTTP 200 with SOAP XML.',
            'The raw client reads the status/body; a typed proxy instead unmarshals totalFees and returns the Java int.'
        ],
        fault: [
            'The caller sends calculateCourseFees with ADSE and 0 in a correctly shaped SOAP request.',
            'HTTP POST delivers the request. Zero is representable as an XML int, so this is not an XML syntax error.',
            'Metro invokes GreetingServiceImpl.calculateCourseFees("ADSE", 0).',
            'The implementation rejects the count and throws CourseFault with Semesters must be between 1 and 8. No total is returned.',
            'Metro maps the declared fault to a SOAP Fault with application detail; this implementation returns HTTP 500.',
            'The raw sender prints the fault body; the compatible typed client maps it to CourseFault for its catch block.'
        ],
        generated: [
            'Before the call, wsimport has read WSDL/XSD and generated compatible client-side types. It did not copy the implementation.',
            'The handwritten client obtains the generated port proxy and calls getCourseInfo("CPISM").',
            'Metro’s client runtime marshals the argument and POSTs a SOAP operation to the server endpoint.',
            'The server runtime invokes getCourseInfo. Your implementation creates CourseInfo("CPISM", 500, "USD").',
            'Server-side binding marshals code, feePerSemester and currency into the schema-shaped SOAP response.',
            'Client-side binding unmarshals a client CourseInfo object. The handwritten client reads its getters and prints the result.'
        ]
    };
    let revealed = 0;
    const restart = () => {
        revealed = 0;
        const item = document.createElement('li');
        item.textContent = 'Predict first, then reveal a step.';
        list.replaceChildren(item);
        next.disabled = false;
        status.textContent = '0 of 6 steps revealed.';
    };
    next.addEventListener('click', () => {
        const steps = scenarios[selector.value];
        if (!steps || revealed >= steps.length) return;
        if (!revealed) list.replaceChildren();
        const item = document.createElement('li');
        item.textContent = steps[revealed++];
        list.appendChild(item);
        next.disabled = revealed === steps.length;
        status.textContent = `${revealed} of ${steps.length} steps revealed.${next.disabled ? ' Explain the role of each program in this call.' : ''}`;
    });
    selector.addEventListener('change', restart);
    reset.addEventListener('click', restart);
    restart();
}

function initSessionThreeLog() {
    const checks = [...document.querySelectorAll('[data-s3-check]')];
    const notes = document.getElementById('s3Notes');
    const summary = document.getElementById('s3ChecklistSummary');
    const progress = document.getElementById('s3Progress');
    const status = document.getElementById('s3SaveStatus');
    const download = document.getElementById('s3ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session3_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s3Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s3Check, check.checked]))}));
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
        const log = ['Aptech — Session 3 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session3-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
