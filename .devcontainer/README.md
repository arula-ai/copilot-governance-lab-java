# DevContainer Configuration

This directory contains the DevContainer configuration for the Copilot Governance Lab (Java edition).

## What is a DevContainer?

A Development Container (DevContainer) is a Docker container configured as a full-featured development environment. It includes all the tools, runtimes, and extensions needed for this lab.

## Benefits

- **Zero Setup Time**: Everything pre-configured and ready to use  
- **Consistency**: Everyone uses the exact same environment  
- **No Version Conflicts**: JDK, Maven, and Spring tooling pre-installed  
- **Pre-loaded Extensions**: Java tooling, Copilot, Spring helpers configured  
- **Works with GitHub Codespaces**: Instant cloud-based development  

## Prerequisites

- **Docker Desktop** installed and running
- **VS Code** with the "Dev Containers" extension
  - Install: `code --install-extension ms-vscode-remote.remote-containers`
- **GitHub Copilot** subscription (for full experience)

## How to Use

### Option 1: Local with VS Code

1. Open this folder in VS Code
2. When prompted, click "Reopen in Container"
3. Or use Command Palette: `Dev Containers: Reopen in Container`
4. Wait for container to build (~2-3 minutes first time)
5. Start coding!

### Option 2: GitHub Codespaces

1. Click "Code" → "Codespaces" → "Create codespace on main"
2. Wait for environment to initialize (~2 minutes)
3. Start coding in the browser!

### Option 3: Command Line

```bash
# Build and open in container
code --folder-uri vscode-remote://dev-container+$(pwd | sed 's|/|%2F|g')/workspaces/copilot-governance-lab-java
```

## What's Included

### Base Image
- **JDK 17**
- **Apache Maven 3.9+**
- **Git** pre-configured
- **GitHub CLI** (`gh`)

### Spring Boot Tooling
- Maven dependency cache prepped via `mvn dependency:go-offline`
- Java compilation tools
- Spring Boot VS Code extensions

### VS Code Extensions
- **GitHub Copilot**
- **GitHub Copilot Chat**
- **Java Extension Pack** (language support, refactoring, formatting)
- **Spring Boot Tools**

### Pre-configured Settings
- Format on save enabled
- Google-style formatting profile for Java
- Copilot enabled for Java/HTML/YAML
- Auto-save on focus change

## Ports Forwarded

- **8080**: Spring Boot development server (`mvn spring-boot:run`)

## First Steps After Container Starts

The container automatically runs:
```bash
mvn -B dependency:go-offline
```

Recommended quickstart commands:
```bash
# Start dev server
mvn spring-boot:run

# Run tests
mvn test

# Verify build + coverage
mvn verify

# Governance checks
./scripts/run-all-checks.sh
```

## Customization

### Adding Extensions

Edit `.devcontainer/devcontainer.json`:
```json
"customizations": {
  "vscode": {
    "extensions": [
      "your-extension-id"
    ]
  }
}
```

### Adding System Packages

Add a `Dockerfile`:
```dockerfile
FROM mcr.microsoft.com/devcontainers/java:1-17-bullseye

RUN apt-get update && apt-get install -y \
    your-package-here
```

Then update `devcontainer.json`:
```json
"build": {
  "dockerfile": "Dockerfile"
}
```

## Troubleshooting

### Container won't start
```bash
# Rebuild container without cache
Command Palette → "Dev Containers: Rebuild Container Without Cache"
```

### Port 8080 not forwarding
- Check Docker Desktop is running
- Verify port 8080 isn't in use locally
- Try manually forwarding: Ports panel → Add Port

### Extensions not loading
- Ensure VS Code is up to date
- Check Docker has enough resources (4GB RAM minimum)
- Try rebuilding container

### Maven download failures
```bash
# Inside container terminal
mvn -U dependency:go-offline
```

### Git not configured
```bash
# Inside container terminal
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

## Performance Tips

### Speed up builds
- Use `.dockerignore` to exclude unnecessary files
- Cache Maven dependencies via `dependency:go-offline`
- Disable tests during rebuilds (`mvn -DskipTests package`)

### Reduce resource usage
- Limit Docker Desktop resources in settings
- Stop container when not in use
- Use local setup for quick checks

## Comparison: DevContainer vs Local Setup

| Aspect | DevContainer | Local Setup |
|--------|--------------|-------------|
| Setup Time | 30 seconds | 5 minutes |
| Consistency | 100% identical | Varies by machine |
| Disk Space | ~2GB | ~1GB |
| RAM Usage | ~1GB extra | Normal |
| Portability | Works anywhere | Machine-specific |
| GitHub Codespaces | Yes | No |
| Offline Work | Requires Docker | Yes |

## Additional Resources
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [VS Code Java Guide](https://code.visualstudio.com/docs/java/java-tutorial)
- [Dev Containers Documentation](https://containers.dev/)
