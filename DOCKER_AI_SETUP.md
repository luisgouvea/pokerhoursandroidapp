# Docker AI Agents Setup

Ambiente Docker isolado para rodar agentes de IA (opencode e Claude Code) com acesso restrito ao projeto.

## Por que Docker?

O agente roda dentro de um container Linux. Ele enxerga **apenas** a pasta do projeto — nada do resto do Mac é acessível. Isso evita que o agente leia ou modifique arquivos fora do escopo do projeto.

## Estrutura de pastas

```
~/sannux/
  templates/
    opencode/          → Dockerfile, compose.yml, .env do opencode
    claude-code/       → Dockerfile, compose.yml, .env, entrypoint.sh do Claude Code

~/sannux-data/
  opencode/
    agent-home/        → sessões, tokens e config persistentes do opencode
  claude-code/
    agent-home/        → sessões, tokens e config persistentes do Claude Code
```

## Como funciona

```
Seu Mac
  └── Docker container (isolamento de filesystem)
        ├── /workspace  →  pasta do projeto (bind mount do Mac)
        └── /home/agent →  ~/sannux-data/.../agent-home (bind mount do Mac)
```

O container só monta essas duas pastas. Qualquer outra pasta do Mac (`~/Downloads`, `~/Documents`, etc.) é invisível para o agente.

## Comandos do dia a dia

As funções abaixo estão no `~/.zshrc`:

```bash
# Abrir opencode em um projeto
opencode ~/Projetos/Android/pokerhoursandroidapp/pokerhoursandroidapp/

# Abrir Claude Code em um projeto
claudecode ~/Projetos/Android/pokerhoursandroidapp/pokerhoursandroidapp/
```

## Retomar conversa anterior (dentro do container)

```bash
claude -c   # retoma a conversa mais recente do diretório atual
claude -r   # abre picker para escolher qual conversa retomar
```

## Trabalho paralelo com worktrees

Múltiplas instâncias Docker montam os mesmos arquivos. Para evitar conflito, use git worktrees:

```bash
# Criar worktrees
git worktree add ../pokerapp-feature-a feature-a
git worktree add ../pokerapp-feature-b feature-b

# Abrir cada um em um container separado
claudecode ~/Projetos/Android/pokerhoursandroidapp/pokerapp-feature-a
claudecode ~/Projetos/Android/pokerhoursandroidapp/pokerapp-feature-b
```

Cada container edita sua própria pasta. No final, merge normalmente via git.

## Memória dos agentes

- Claude Code nativo (fora do Docker) → salva memória em `~/.claude/`
- Claude Code no Docker → salva memória em `~/sannux-data/claude-code/agent-home/`
- Múltiplas instâncias Docker compartilham o mesmo `agent-home` → memória unificada entre worktrees

## Comandos úteis de administração

```bash
# Ver containers em execução
docker ps

# Entrar em um container que já está rodando
docker exec -it <container_id> bash

# Rebuild da imagem do opencode
cd ~/sannux/templates/opencode && docker compose build --no-cache

# Rebuild da imagem do Claude Code
cd ~/sannux/templates/claude-code && docker compose build --no-cache

# Login opencode (se precisar reautenticar)
cd ~/sannux/templates/opencode && docker compose run --rm agent auth login

# Login Claude Code (se precisar reautenticar)
cd ~/sannux/templates/claude-code && docker compose run --rm agent login

# Shell interativo dentro do container (para debug)
cd ~/sannux/templates/claude-code && docker compose run --rm --entrypoint bash agent
```

## Limitações do Docker

| Limitação | Detalhe |
|---|---|
| Clipboard | Não funciona Ctrl+V de imagens ou texto do Mac |
| IDE | Sem integração com VS Code ou JetBrains |
| Arquivos externos | Agente não acessa nada fora do workspace |
| Conflito de arquivos | Duas instâncias no mesmo path podem sobrescrever uma à outra — usar worktrees |

## Detalhes técnicos das imagens

| Item | opencode | Claude Code |
|---|---|---|
| Base | `debian:trixie-slim` | `debian:trixie-slim` |
| Node | 22 | 24 |
| Instalação | `npm install -g opencode-ai@latest` | `curl claude.ai/install.sh` + `entrypoint.sh` |
| Entrypoint | `opencode` | `/entrypoint.sh` → `claude` |
| Imagem Docker | `sannux/opencode:latest` | `sannux/claude-code:latest` |
| UID/GID | 501/20 (alinhado com o Mac) | 501/20 (alinhado com o Mac) |
