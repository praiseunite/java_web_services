/**
 * Aptech Web Services Academy - Interactive Lab Simulators
 * 1. XML DOM & SAX Inspector
 * 2. SOAP Request / Response Simulator
 */

document.addEventListener('DOMContentLoaded', () => {
    initXmlSimulator();
    initSoapSimulator();
});

// ==========================================
// 1. XML & DOM / SAX PARSER SIMULATOR
// ==========================================
function initXmlSimulator() {
    const xmlInput = document.getElementById('xmlInputArea');
    const btnDom = document.getElementById('btnRunDom');
    const btnSax = document.getElementById('btnRunSax');
    const btnReset = document.getElementById('btnResetXml');
    const outputDisplay = document.getElementById('domOutputDisplay');

    if (!xmlInput || !outputDisplay) return;

    const defaultXml = `<students>
    <student id="101" course="ADSE">
        <name>Amina Bello</name>
        <email>amina@aptech.edu</email>
    </student>
    <student id="102" course="CPISM">
        <name>Chidi Okafor</name>
        <email>chidi@aptech.edu</email>
    </student>
</students>`;

    // Reset button
    btnReset?.addEventListener('click', () => {
        xmlInput.value = defaultXml;
        outputDisplay.textContent = '// XML reset to default. Click "Parse with DOM" or "Stream with SAX".';
        outputDisplay.style.color = '#a7f3d0';
    });

    // Parse with DOM
    btnDom?.addEventListener('click', () => {
        const rawXml = xmlInput.value.trim();
        if (!rawXml) {
            outputDisplay.textContent = '❌ Please enter some XML first!';
            outputDisplay.style.color = '#f43f5e';
            return;
        }

        try {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(rawXml, 'application/xml');

            const parseError = xmlDoc.querySelector('parsererror');
            if (parseError) {
                outputDisplay.textContent = '❌ XML SYNTAX ERROR:\n' + parseError.textContent;
                outputDisplay.style.color = '#f43f5e';
                return;
            }

            // Successfully parsed into in-memory DOM Tree!
            let result = '✓ [DOM SUCCESS] XML successfully loaded into RAM Tree!\n';
            result += '==================================================\n';
            result += 'Root Node: <' + xmlDoc.documentElement.nodeName + '>\n';
            result += 'Child Elements Count: ' + xmlDoc.documentElement.children.length + '\n\n';

            // Traverse children
            const traverseNode = (node, depth = 0) => {
                const indent = '  '.repeat(depth);
                if (node.nodeType === Node.ELEMENT_NODE) {
                    let attrs = '';
                    if (node.attributes.length > 0) {
                        attrs = ' [' + Array.from(node.attributes).map(a => `${a.name}="${a.value}"`).join(', ') + ']';
                    }
                    result += `${indent}📁 Element: <${node.nodeName}>${attrs}\n`;

                    // Check if node only has direct text
                    if (node.children.length === 0 && node.textContent.trim()) {
                        result += `${indent}   └─ 📝 Text Value: "${node.textContent.trim()}"\n`;
                    } else {
                        Array.from(node.children).forEach(child => traverseNode(child, depth + 1));
                    }
                }
            };

            Array.from(xmlDoc.documentElement.children).forEach(child => traverseNode(child, 1));

            outputDisplay.textContent = result;
            outputDisplay.style.color = '#38bdf8';

        } catch (err) {
            outputDisplay.textContent = 'Parsing Exception: ' + err.message;
            outputDisplay.style.color = '#f43f5e';
        }
    });

    // Stream with SAX Simulator
    btnSax?.addEventListener('click', () => {
        const rawXml = xmlInput.value.trim();
        if (!rawXml) return;

        outputDisplay.textContent = '⚡ [SAX EVENT STREAM RUNNING...]\n==================================================\n';
        outputDisplay.style.color = '#fbbf24';

        let events = [];
        events.push('1. [EVENT: startDocument()] -> Stream opened (0 bytes in RAM)');

        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(rawXml, 'application/xml');

        if (xmlDoc.querySelector('parsererror')) {
            outputDisplay.textContent = '❌ Invalid XML. SAX cannot stream malformed documents.';
            outputDisplay.style.color = '#f43f5e';
            return;
        }

        const buildSaxEvents = (node) => {
            if (node.nodeType === Node.ELEMENT_NODE) {
                let attrs = '';
                if (node.attributes.length > 0) {
                    attrs = ' (Attributes: ' + Array.from(node.attributes).map(a => `${a.name}="${a.value}"`).join(', ') + ')';
                }
                events.push(`2. [EVENT: startElement] <${node.nodeName}>${attrs}`);

                if (node.children.length === 0 && node.textContent.trim()) {
                    events.push(`3. [EVENT: characters] "${node.textContent.trim()}"`);
                } else {
                    Array.from(node.children).forEach(buildSaxEvents);
                }

                events.push(`4. [EVENT: endElement] </${node.nodeName}>`);
            }
        };

        buildSaxEvents(xmlDoc.documentElement);
        events.push('5. [EVENT: endDocument()] -> Stream completed. Total memory retained: 0 KB!');

        outputDisplay.textContent = events.join('\n');
    });
}

// ==========================================
// 2. SOAP REQUEST & RESPONSE SIMULATOR
// ==========================================
function initSoapSimulator() {
    const opSelect = document.getElementById('soapOperationSelect');
    const nameInput = document.getElementById('soapParamName');
    const courseInput = document.getElementById('soapParamCourse');
    const semestersInput = document.getElementById('soapParamSemesters');
    const button = document.getElementById('btnSendSoap');
    const requestDisplay = document.getElementById('soapReqDisplay');
    const responseDisplay = document.getElementById('soapResDisplay');
    if (!opSelect || !button || !nameInput || !courseInput || !semestersInput || !requestDisplay || !responseDisplay) return;
    const escapeXml = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;').replaceAll('"', '&quot;').replaceAll("'", '&apos;');
    const envelope = body => '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:s="urn:aptech:session3">\n'
        + '  <soap:Body>\n' + body + '\n  </soap:Body>\n</soap:Envelope>';
    const fault = (reason, declared = true) => '    <soap:Fault>\n'
        + '      <faultcode>soap:Server</faultcode>\n'
        + '      <faultstring>' + escapeXml(reason) + '</faultstring>\n'
        + (declared ? '      <detail><s:CourseFault><reason>' + escapeXml(reason) + '</reason></s:CourseFault></detail>\n' : '')
        + '    </soap:Fault>';
    const updateInputs = () => {
        document.getElementById('paramRow1')?.classList.toggle('hidden', opSelect.value !== 'sayHello');
        document.getElementById('paramRow2')?.classList.toggle('hidden', opSelect.value === 'sayHello');
        document.getElementById('paramRow3')?.classList.toggle('hidden', opSelect.value !== 'calculateCourseFees');
    };
    opSelect.addEventListener('change', updateInputs);
    updateInputs();
    button.addEventListener('click', () => {
        const operation = opSelect.value;
        let argumentsXml = '';
        let resultXml = '';
        let reason = '';
        let declared = true;
        if (operation === 'sayHello') {
            const rawName = nameInput.value;
            const cleanName = /^\s*$/.test(rawName) ? 'Student' : rawName.replace(/^[\x00-\x20]+|[\x00-\x20]+$/g, '');
            argumentsXml = '<name>' + escapeXml(rawName) + '</name>';
            if (cleanName.length > 60) reason = 'Name must be at most 60 characters.';
            else resultXml = '<message>Hello, ' + escapeXml(cleanName) + '! Welcome to Aptech Jakarta Web Services.</message>';
        } else {
            const course = courseInput.value;
            const rate = {ADSE: 850, DISM: 650, CPISM: 500}[course];
            argumentsXml = '<courseName>' + escapeXml(course) + '</courseName>';
            if (operation === 'calculateCourseFees') {
                const rawCount = semestersInput.value;
                const count = Number(rawCount);
                argumentsXml += '<numberOfSemesters>' + escapeXml(rawCount) + '</numberOfSemesters>';
                if (!/^[+-]?\d+$/.test(rawCount) || !Number.isInteger(count) || count < -2147483648 || count > 2147483647) {
                    reason = 'The semester input must be representable as an XML int. This illustrates a runtime input failure; actual runtime wording can differ.';
                    declared = false;
                } else if (count < 1 || count > 8) reason = 'Semesters must be between 1 and 8.';
                else if (!rate) reason = 'Choose ADSE, DISM or CPISM.';
                else resultXml = '<totalFees>' + rate * count + '</totalFees>';
            } else if (!rate) reason = 'Choose ADSE, DISM or CPISM.';
            else resultXml = '<course><code>' + escapeXml(course) + '</code><feePerSemester>' + rate + '</feePerSemester><currency>USD</currency></course>';
        }
        const requestBody = '    <s:' + operation + '>' + argumentsXml + '</s:' + operation + '>';
        const responseBody = reason ? fault(reason, declared)
            : '    <s:' + operation + 'Response>' + resultXml + '</s:' + operation + 'Response>';
        requestDisplay.textContent = 'Local demonstration — not sent over the network\n\nPOST /greeting HTTP/1.1\nHost: 127.0.0.1:8095\nContent-Type: text/xml; charset=utf-8\nSOAPAction: ""\n\n' + envelope(requestBody);
        responseDisplay.textContent = 'Illustrative response — verify real behaviour in the Java lab\n\nHTTP/1.1 ' + (reason ? '500 Internal Server Error' : '200 OK')
            + '\nContent-Type: text/xml; charset=utf-8\n\n' + envelope(responseBody);
    });
}
