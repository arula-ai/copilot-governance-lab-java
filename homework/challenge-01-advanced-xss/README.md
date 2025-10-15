# Challenge 1: Advanced XSS & Content Security Policy

**Difficulty:** 4/5  
**Estimated Time:** 2-3 hours  
**Points:** 20 (+ 5 bonus)

---

## Scenario

You're building a Spring Boot powered publishing platform where users craft rich HTML stories with embedded media. The platform embraces creativity but must prevent sophisticated XSS attacks that bypass naive sanitization.

**Business Requirements:**
- Users can submit HTML-formatted content with headings, lists, and images
- Inline styles allowed for typography tweaks
- SVG icons can be pasted directly into the editor
- Markdown-to-HTML converter supplies the initial markup
- Live preview shows exactly what will be published

**Security Requirements:**
- Prevent stored, reflected, and DOM-based XSS vectors
- Enforce strict Content Security Policy including nonces/hashes
- Sanitize user-generated HTML and SVG safely
- Block CSS exfiltration techniques
- Provide governance evidence documenting residual risk

---

## Vulnerable Code

### `RichPostController.java`

```java
package com.github.copilot.governancelab.homework;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RichPostController {

    @GetMapping("/homework/xss/editor")
    public String editor(Model model, HttpSession session) {
        model.addAttribute("content", session.getAttribute("draftContent"));
        model.addAttribute("customCss", session.getAttribute("draftCss"));
        model.addAttribute("svg", session.getAttribute("draftSvg"));
        return "homework/rich-editor";
    }

    @PostMapping("/homework/xss/preview")
    public String preview(@ModelAttribute RichPostForm form, Model model, HttpSession session) {
        session.setAttribute("draftContent", form.getHtml());
        session.setAttribute("draftCss", form.getCustomCss());
        session.setAttribute("draftSvg", form.getSvg());
        model.addAttribute("html", form.getHtml());
        model.addAttribute("css", form.getCustomCss());
        model.addAttribute("svg", form.getSvg());
        model.addAttribute("externalScript", form.getExternalScript());
        return "homework/rich-preview";
    }
}
```

### `rich-preview.html`

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Preview</title>
    <!-- VULNERABILITY: Inline CSS from user input -->
    <style th:utext="${css}"></style>
    <!-- VULNERABILITY: External script without CSP -->
    <script th:if="${externalScript != null}" th:src="${externalScript}"></script>
</head>
<body>
<h1>Live Preview</h1>
<!-- VULNERABILITY: Unescaped HTML -->
<div th:utext="${html}"></div>
<!-- VULNERABILITY: Raw SVG injection -->
<div th:utext="${svg}"></div>

<script>
    // VULNERABILITY: Dynamic innerHTML assignment
    const preview = document.querySelector('.live-preview');
    if (preview) {
        preview.innerHTML = /*[[${html}]]*/ '';
    }
</script>
</body>
</html>
```

### `RichPostForm.java`

```java
public class RichPostForm {
    private String html;
    private String customCss;
    private String svg;
    private String externalScript;
    // getters/setters omitted for brevity
}
```

---

## Attack Scenarios
- Inject `<script>` payloads that bypass naive sanitization
- Use CSS `background-image:url('javascript:...')` to execute scripts
- Embed malicious SVGs with `<script>` tags or animated payloads
- Abuse external script URL field to load attacker-controlled content
- Exploit DOM-based XSS via the inline `innerHTML` assignment

---

## Tasks
1. Replace unsafe rendering with a sanitation pipeline (OWASP Java HTML Sanitizer or similar).
2. Enforce CSP headers (nonce or hash-based) via a Spring filter.
3. Remove or validate external script URLs with allowlists.
4. Convert SVG handling to a safe subset or transform to image rendering.
5. Add security-focused tests verifying payloads are neutralized.
6. Document changes and residual risks in governance artifacts.

---

## Success Criteria
- [ ] Jacoco coverage ≥ 70% for new sanitizer/filter code
- [ ] MockMvc tests prove XSS payloads are blocked
- [ ] CSP headers present with nonce/hash usage
- [ ] No unescaped `th:utext` for user fields
- [ ] Governance documentation updated (`VULNERABILITIES.md`, `FIXES.md`)

---

## Bonus (Up to +5 pts)
- Implement Trusted Types style nonce pipeline for inline scripts
- Add real-time sanitation preview using a REST endpoint + tests
- Provide a threat model summarizing mitigations and remaining risks
