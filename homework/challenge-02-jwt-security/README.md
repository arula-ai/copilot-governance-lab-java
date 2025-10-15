# Challenge 2: JWT Security & Session Management

**Difficulty:** 5/5  
**Estimated Time:** 3-4 hours  
**Points:** 20 (+ 5 bonus)

---

## Scenario

You inherit a Spring Boot-based banking API. Authentication uses JWTs, but the implementation contains critical flaws that enable account takeover, token replay, and permission escalation.

**Business Requirements:**
- Support login from multiple devices with session tracking
- Provide refresh tokens for long-lived sessions
- Allow users to revoke sessions per device
- Detect suspicious token reuse events

**Security Requirements:**
- Prevent algorithm confusion and unsigned JWT acceptance
- Store tokens securely (HttpOnly cookies preferred)
- Rotate refresh tokens on every use
- Reject stolen/expired tokens immediately
- Maintain audit logs for authentication events

---

## Vulnerable Code

### `JwtAuthController.java`

```java
@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {

    private final JwtTokenService tokenService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        if (!Objects.equals(user.getPassword(), request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        String token = tokenService.generateToken(user, "none"); // VULNERABILITY: algorithm none allowed
        String refresh = tokenService.generateRefreshToken(user);

        Cookie cookie = new Cookie("access_token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(false); // VULNERABILITY: accessible to scripts
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponse(token, refresh, user));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> body) {
        String refresh = body.get("refreshToken");
        String username = tokenService.extractUsername(refresh); // VULNERABILITY: no signature validation
        User user = userRepository.findByUsername(username).orElseThrow();
        String newAccessToken = tokenService.generateToken(user, "HS256");
        return ResponseEntity.ok(Map.of("token", newAccessToken, "refreshToken", refresh)); // VULNERABILITY: refresh token reused
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // VULNERABILITY: no server-side invalidation
        return ResponseEntity.ok().build();
    }
}
```

### `JwtTokenService.java`

```java
@Service
public class JwtTokenService {

    private final String secret = "hard-coded-secret"; // VULNERABILITY
    private final Map<String, String> refreshTokens = new ConcurrentHashMap<>();

    public String generateToken(User user, String algorithm) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(60 * 60 * 24)))
                .signWith(SignatureAlgorithm.forName(algorithm), secret.getBytes())
                .compact();
    }

    public String generateRefreshToken(User user) {
        String refresh = Base64.getEncoder().encodeToString((user.getUsername() + ":" + UUID.randomUUID()).getBytes());
        refreshTokens.put(refresh, user.getUsername());
        return refresh;
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
```

---

## Attack Scenarios
- Forge tokens using `alg"none"` or weak symmetric keys
- Replay refresh tokens to mint unlimited access tokens
- Steal access token via JavaScript (cookie not HttpOnly)
- Use leaked refresh token from one device to hijack another
- Replay tokens after logout (no server-side blacklist)

---

## Tasks
1. Enforce strong signing algorithms and rotate secrets via configuration.
2. Store access tokens in HttpOnly/SameSite cookies; return minimal JSON payloads.
3. Rotate refresh tokens on every use and maintain revocation lists.
4. Implement server-side session tracking with device metadata.
5. Add structured audit logging for authentication/refresh events.
6. Write tests covering token replay, invalid signature, and logout.

---

## Success Criteria
- [ ] Login flow uses secure password verification (BCrypt, etc.)
- [ ] Access cookies set with `HttpOnly`, `Secure`, and `SameSite=Strict`
- [ ] Refresh tokens rotated and revoked on logout
- [ ] MockMvc/Unit tests cover misuse scenarios
- [ ] Governance documentation updated with mitigations

---

## Bonus (Up to +5 pts)
- Detect refresh token reuse and revoke all sessions for the user
- Integrate device fingerprinting with risk scoring
- Expose admin endpoint summarizing active sessions with pagination
