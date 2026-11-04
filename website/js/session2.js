/* Browser learning tools. Actual Java execution happens in the separate project. */
document.addEventListener('DOMContentLoaded', () => {
    initSessionTwoTrace();
    initSessionTwoLog();
});

function initSessionTwoTrace() {
    const selector = document.getElementById('s2TraceRequest');
    const next = document.getElementById('s2TraceNext');
    const reset = document.getElementById('s2TraceReset');
    const list = document.getElementById('s2TraceSteps');
    const status = document.getElementById('s2TraceStatus');
    if (!selector || !next || !reset || !list || !status) return;
    const scenarios = {
        one: ['The client sends GET /api/courses/1 and accepts JSON.', 'Jersey matches the /api/ base, /courses class path, /{id} method path and @GET.', 'Jersey converts path text 1 to int and calls get(1).', 'Your method asks STORE.find(1), finds the Course and builds a 200 response.', 'Jackson serialises the Course. The client receives 200 and JSON with id 1.'],
        search: ['The client sends GET /api/courses?search=java.', 'Jersey matches the /courses class and the @GET method with no extra method path.', 'Jersey reads the search query value and calls list("java").', 'Your store collects titles containing java, ignoring case. In the fresh data, one course matches.', 'Jackson serialises the List<Course> as an array. The client receives 200 with one Course object in that array.'],
        create: ['The client sends POST /api/courses with Content-Type application/json and a valid title.', 'Jersey matches @POST and @Consumes(application/json), and Jackson converts the body to NewCourse.', 'Jersey calls create with the input object and UriInfo. Your method validates and trims the title.', 'STORE.add creates the Course with id 3 in this fresh-run example. Your method builds 201 with the created object and Location.', 'Jackson serialises the returned Course. The client receives 201 and can GET the new resource at its actual Location.'],
        blank: ['The client sends POST /api/courses with a JSON title containing only spaces.', 'Jersey matches the method/media type. Jackson creates a NewCourse object.', 'Your create method finds that the supplied title is blank.', 'Your method returns 400 with ApiError before calling STORE.add. The store and next id are unchanged.', 'Jackson serialises the error object. The client receives 400 and A non-blank title is required.'],
        missing: ['The client sends GET /api/courses/999.', 'Jersey matches the GET path and converts 999 into an int argument.', 'Your get method accepts the positive id, then asks STORE.find(999).', 'The store returns null. Your method builds 404 with Course not found.', 'Jackson serialises your error object. The client receives the 404 status and JSON error.'],
        format: ['The client sends POST /api/courses declaring Content-Type text/plain.', 'Jersey considers the POST route and checks its @Consumes(application/json).', 'The declared request-body format does not match the method’s accepted format.', 'Jersey generates 415 Unsupported Media Type before invoking create; no course is added.', 'The client receives 415. The framework-generated response body may differ from your ApiError bodies.']
    };
    let revealed = 0;
    const restart = () => {
        revealed = 0;
        const item = document.createElement('li');
        item.textContent = 'Predict first, then reveal a step.';
        list.replaceChildren(item);
        next.disabled = false;
        status.textContent = '0 of 5 steps revealed.';
    };
    next.addEventListener('click', () => {
        const steps = scenarios[selector.value];
        if (revealed === steps.length) return;
        if (!revealed) list.replaceChildren();
        const item = document.createElement('li');
        item.textContent = steps[revealed++];
        list.appendChild(item);
        next.disabled = revealed === steps.length;
        status.textContent = `${revealed} of ${steps.length} steps revealed.${next.disabled ? ' Explain which steps Jersey performs and which your method performs.' : ''}`;
    });
    selector.addEventListener('change', restart);
    reset.addEventListener('click', restart);
    restart();
}

function initSessionTwoLog() {
    const checks = [...document.querySelectorAll('[data-s2-check]')];
    const notes = document.getElementById('s2Notes');
    const summary = document.getElementById('s2ChecklistSummary');
    const progress = document.getElementById('s2Progress');
    const status = document.getElementById('s2SaveStatus');
    const download = document.getElementById('s2ExportNotes');
    if (!checks.length || !notes || !summary || !progress || !status || !download) return;
    const key = 'aptech_session2_learning_v1';
    let canSave = true;
    try {
        const saved = JSON.parse(localStorage.getItem(key) || '{}');
        if (saved && typeof saved === 'object') {
            checks.forEach(check => { check.checked = saved.checks?.[check.dataset.s2Check] === true; });
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
            localStorage.setItem(key, JSON.stringify({notes: notes.value, checks: Object.fromEntries(checks.map(check => [check.dataset.s2Check, check.checked]))}));
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
        const log = ['Aptech — Session 2 learning log', 'Milestones are self-reported; instructor review is required.', '', ...checks.map(check => `${check.checked ? '[x]' : '[ ]'} ${check.nextElementSibling.textContent}`), '', 'My notes:', notes.value].join('\n');
        const url = URL.createObjectURL(new Blob([log], {type:'text/plain;charset=utf-8'}));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aptech-session2-learning-log.txt';
        document.body.appendChild(link);
        link.click();
        link.remove();
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    });
    render();
    status.textContent = canSave ? 'Progress stays in this browser. Download a separate copy when needed.' : 'Local saving is unavailable. Download your log before closing the page.';
}
