/**
 * Aptech Web Services Academy - Main Application Logic
 * Supports Desktop, Tablet & Mobile Off-Canvas Drawer
 */

document.addEventListener('DOMContentLoaded', () => {
    initMobileMenu();
    initThemeToggle();
    initCopyButtons();
    initCodeTabs();
    initAccordion();
    initQuizzes();
    initNavigation();
    initSearch();
});

// 1. Mobile Menu Off-Canvas Drawer
function initMobileMenu() {
    const menuToggle = document.getElementById('menuToggle');
    const closeSidebar = document.getElementById('closeSidebar');
    const sidebar = document.getElementById('sidebar');
    const backdrop = document.getElementById('sidebarBackdrop');

    if (!sidebar) return;

    const openDrawer = () => {
        sidebar.classList.add('open');
        backdrop?.classList.add('active');
        document.body.style.overflow = 'hidden'; // Prevent background scrolling
    };

    const closeDrawer = () => {
        sidebar.classList.remove('open');
        backdrop?.classList.remove('active');
        document.body.style.overflow = '';
    };

    menuToggle?.addEventListener('click', openDrawer);
    closeSidebar?.addEventListener('click', closeDrawer);
    backdrop?.addEventListener('click', closeDrawer);

    // Auto-close drawer when clicking any link inside the sidebar on mobile
    sidebar.querySelectorAll('.menu-item').forEach(link => {
        link.addEventListener('click', () => {
            if (window.innerWidth <= 992) {
                closeDrawer();
            }
        });
    });

    // Close on Escape key
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && sidebar.classList.contains('open')) {
            closeDrawer();
        }
    });
}

// 2. Theme Toggle (Dark / Light)
function initThemeToggle() {
    const toggleBtn = document.getElementById('themeToggle');
    if (!toggleBtn) return;

    toggleBtn.addEventListener('click', () => {
        document.body.classList.toggle('theme-light');
        const isLight = document.body.classList.contains('theme-light');
        toggleBtn.textContent = isLight ? '☀️' : '🌙';
        try { localStorage.setItem('aptech_theme', isLight ? 'light' : 'dark'); } catch { /* Theme still works without storage. */ }
    });

    let savedTheme;
    try { savedTheme = localStorage.getItem('aptech_theme'); } catch { /* Use the default theme. */ }
    if (savedTheme === 'light') {
        document.body.classList.add('theme-light');
        toggleBtn.textContent = '☀️';
    }
}

// 3. Copy Code Buttons
function initCopyButtons() {
    document.querySelectorAll('.btn-copy').forEach(btn => {
        btn.addEventListener('click', async () => {
            let textToCopy = '';

            if (btn.dataset.copy) {
                textToCopy = btn.dataset.copy;
            } else if (btn.dataset.copyTarget) {
                const targetElem = document.getElementById(btn.dataset.copyTarget);
                if (targetElem) {
                    textToCopy = targetElem.textContent;
                }
            }

            if (!textToCopy) return;

            try {
                await navigator.clipboard.writeText(textToCopy);
                const originalText = btn.textContent;
                btn.textContent = '✓ Copied!';
                btn.style.background = '#10b981';
                btn.style.color = '#ffffff';

                setTimeout(() => {
                    btn.textContent = originalText;
                    btn.style.background = '';
                    btn.style.color = '';
                }, 2000);
            } catch (err) {
                const originalText = btn.textContent;
                btn.textContent = 'Select text to copy';
                btn.classList.add('copy-failed');
                setTimeout(() => { btn.textContent = originalText; btn.classList.remove('copy-failed'); }, 2500);
            }
        });
    });
}

// 4. Tab Switching
function initCodeTabs() {
    document.querySelectorAll('.code-tab-container').forEach(container => {
        const buttons = container.querySelectorAll('.tab-btn');
        const contents = container.querySelectorAll('.tab-content');

        buttons.forEach(btn => {
            btn.addEventListener('click', () => {
                const targetTabId = btn.getAttribute('data-tab');

                buttons.forEach(b => b.classList.remove('active'));
                contents.forEach(c => c.classList.remove('active'));

                btn.classList.add('active');
                const targetContent = container.querySelector('#' + targetTabId);
                if (targetContent) {
                    targetContent.classList.add('active');
                }
            });
        });
    });
}

// 5. Accordion Toggle
function initAccordion() {
    document.querySelectorAll('.accordion-header').forEach(header => {
        header.addEventListener('click', () => {
            const item = header.parentElement;
            const wasActive = item.classList.contains('active');

            // Close siblings
            item.parentElement.querySelectorAll('.accordion-item').forEach(sibling => {
                sibling.classList.remove('active');
            });

            if (!wasActive) {
                item.classList.add('active');
            }
        });
    });
}

// 6. Interactive Quizzes
function initQuizzes() {
    document.querySelectorAll('.quiz-container').forEach(container => {
        const options = container.querySelectorAll('.quiz-opt');
        const feedback = container.querySelector('.quiz-feedback');

        options.forEach(opt => {
            opt.addEventListener('click', () => {
                // Reset states
                options.forEach(o => {
                    o.classList.remove('correct', 'wrong');
                    o.disabled = true;
                });

                const isCorrect = opt.dataset.correct === 'true';
                if (isCorrect) {
                    opt.classList.add('correct');
                    if (feedback) {
                        feedback.textContent = 'Correct. ' + (container.dataset.explanation || 'Review the related explanation to understand why.');
                        feedback.style.color = document.body.classList.contains('theme-light') ? '#047857' : '#34d399';
                    }
                } else {
                    opt.classList.add('wrong');
                    // Highlight the right one
                    const correctOpt = container.querySelector('[data-correct="true"]');
                    if (correctOpt) correctOpt.classList.add('correct');
                    if (feedback) {
                        feedback.textContent = 'Review this answer. ' + (container.dataset.explanation || 'The highlighted option is correct. Review the related explanation.');
                        feedback.style.color = document.body.classList.contains('theme-light') ? '#be123c' : '#fb7185';
                    }
                }
            });
        });
        container.querySelector('.quiz-retry')?.addEventListener('click', () => {
            options.forEach(opt => { opt.disabled = false; opt.classList.remove('correct', 'wrong'); });
            if (feedback) feedback.textContent = '';
            options[0]?.focus();
        });
    });
}

// 7. Navigation and Scrollspy
function initNavigation() {
    const pills = document.querySelectorAll('.nav-pill');
    pills.forEach(pill => {
        pill.addEventListener('click', () => {
            const targetId = pill.dataset.target;
            const targetElement = document.getElementById(targetId);
            if (targetElement) {
                targetElement.scrollIntoView({ behavior: 'smooth' });
                pills.forEach(p => p.classList.remove('active'));
                pill.classList.add('active');
            }
        });
    });

    // Sidebar active state on scroll
    const sections = document.querySelectorAll('.content-block, .hero-section, .sub-block');
    const menuLinks = document.querySelectorAll('.sidebar .menu-item');

    const updateActiveNavigation = () => {
        let currentId = '';
        sections.forEach(sec => {
            if (!sec.getClientRects().length) return;
            const top = sec.getBoundingClientRect().top + window.scrollY - 135;
            if (window.scrollY >= top) {
                currentId = sec.getAttribute('id');
            }
        });

        menuLinks.forEach(link => {
            link.classList.remove('active');
            if (link.getAttribute('href') === '#' + currentId) {
                link.classList.add('active');
            }
        });
        const current = document.getElementById(currentId);
        const majorId = current?.closest('.content-block, .hero-section')?.id;
        const pillId = majorId === 'welcome' || majorId === 'prerequisites' ? 'session1' : majorId;
        pills.forEach(pill => pill.classList.toggle('active', pill.dataset.target === pillId));
    };
    window.addEventListener('scroll', updateActiveNavigation, { passive: true });
    window.addEventListener('hashchange', updateActiveNavigation);
    updateActiveNavigation();
}

// 8. Search Bar Filter
function initSearch() {
    const searchInput = document.getElementById('conceptSearch');
    if (!searchInput) return;

    searchInput.addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase().trim();
        if (!query) {
            document.querySelectorAll('.sub-block').forEach(b => b.style.display = '');
            return;
        }

        document.querySelectorAll('.sub-block').forEach(block => {
            const text = block.textContent.toLowerCase();
            if (text.includes(query)) {
                block.style.display = '';
            } else {
                block.style.display = 'none';
            }
        });
    });
}
