# Relatório de Auditoria de Configuração — BikeToGo

> **IC:** registro de auditoria do GC (PGCS §4.1.4) · **Versão:** v0.1 ·
> **Norma base:** IEEE Std 828-2012 · **Tipo:** Auditoria Física de Configuração (PCA)
> · **Data:** 2026-06-18 · **Responsável (GC):** Davi Augusto Ferreira de Carvalho

---

## 1. Identificação da baseline auditada

| Campo | Valor |
|-------|-------|
| Marco | M3 — LB-Desenvolvimento |
| Identificador único | `v0.3-rc1` (release candidate) |
| Commit-base (HEAD) | `f10878a` |
| Branch de origem | `main` |
| Data da auditoria | 2026-06-18 |
| Inventário SBOM associado | `docs/auditorias/sbom.json` |
| Ferramenta de inventário | Trivy 0.71.1 (DB de vulnerabilidades v2, atualizada em 2026-06-18; formato CycloneDX JSON, com `--include-dev-deps`) |

---

## 2. Objetivo

Verificar, por **Auditoria Física (PCA)**, se os Itens de Configuração (ICs)
declarados no índice mestre (`README.md`) estão fisicamente presentes no
repositório, identificados conforme o padrão e na versão esperada para a
baseline `v0.3-rc1`; e registrar a **composição de software** (SBOM) gerada por
ferramenta automatizada, controlando proveniência e integridade do produto.

---

## 3. Auditoria física dos ICs (PCA)

Legenda: ✅ presente · ⚠️ parcial/placeholder · ❌ ausente

| IC | Item de Configuração | Localização esperada | Estado | Observação |
|----|----------------------|----------------------|--------|------------|
| DOC-01 | Plano de Abertura | `docs/gestao/` | ❌ | Não versionado |
| DOC-02 | Análise do Projeto | `docs/gestao/` | ❌ | Não versionado |
| DOC-03 | EAP | `docs/gestao/` | ❌ | Não versionado |
| DOC-04 | PGCS | `docs/gestao/` | ✅ | `PGCS_BikeToGo.md` v1.0 |
| REQ-01 | Histórias de usuário | `docs/requisitos/` | ❌ | Apenas `.gitkeep` |
| REQ-02 | Especificação de requisitos | `docs/requisitos/` | ❌ | Apenas `.gitkeep` |
| DSG-01 | Protótipo (Figma) | `design/prototipo/` | ❌ | Apenas `.gitkeep` |
| DSG-02 | Design System | `design/system/` | ❌ | Apenas `.gitkeep` |
| ARQ-01 | Arquitetura (C4/UML) | `docs/arquitetura/` | ❌ | Stack ainda não baseline |
| COD-01 | Front-end | `src/frontend/` | ❌ | Apenas `.gitkeep` |
| COD-02 | Back-end | `src/backend/` | ⚠️ | Código Java (Swing) + `package.json` (Node) — **incoerência de stack a resolver via ARQ-01/RDM** |
| BD-01 | Migrations | `db/migrations/` | ❌ | Apenas `.gitkeep` |
| TST-01 | Planos de teste | `tests/planos/` | ❌ | Apenas `.gitkeep` |
| TST-02 | Testes automatizados | `tests/automatizados/` | ❌ | Apenas `.gitkeep` |
| CFG-01 | Configuração de ambiente | `config/` | ✅ | `.env.example` + `ci.yml` (pipeline placeholder) |
| BLD-01 | Builds liberados | GitHub Releases | ❌ | Nenhuma tag publicada |

---

## 4. Composição do software (resultado do SBOM)

Inventário gerado por Trivy a partir de `src/backend/package-lock.json`, formato
**CycloneDX JSON**, em `docs/auditorias/sbom.json`.

| Métrica | Valor |
|---------|-------|
| Formato do inventário | CycloneDX JSON |
| Ferramenta / versão (metadados) | Trivy 0.71.1 (DB v2, 2026-06-18) |
| Total de pacotes resolvidos (npm audit) | 509 |
| Total de componentes no SBOM | 492 |
| Dependências diretas de produção | 7 (`express`, `pg`, `jsonwebtoken`, `bcryptjs`, `dotenv`, `axios`, `lodash`) |
| Dependências diretas de desenvolvimento | 3 (`jest`, `eslint`, `nodemon`) |
| Dependências transitivas | demais (resolvidas no `package-lock.json`) |

### 4.1 Vulnerabilidades conhecidas (Trivy)

Total: **24** — CRITICAL: 0 · HIGH: 11 · MEDIUM: 12 · LOW: 1. Concentradas em
duas dependências diretas de produção:

| Componente | Versão instalada | Qtde CVEs | Severidades | Versão corrigida | Situação nesta baseline |
|------------|------------------|-----------|-------------|------------------|--------------------------|
| `axios` | 0.21.4 | 21 | 10 HIGH · 10 MEDIUM · 1 LOW | `0.32.0` / `1.16.0` | Mantida — correção é upgrade *major* (quebra compat.); requer RDM |
| `jsonwebtoken` | 8.5.1 | 3 | 1 HIGH · 2 MEDIUM | `9.0.0` | Mantida — correção é upgrade *major* (quebra compat.); requer RDM |

**CVEs HIGH (11):**
`axios` — CVE-2025-27152, CVE-2026-25639, CVE-2026-42033, CVE-2026-42035,
CVE-2026-42043, CVE-2026-44486, CVE-2026-44487, CVE-2026-44492, CVE-2026-44495,
CVE-2026-44496; `jsonwebtoken` — CVE-2022-23539.

**CVEs MEDIUM (12):**
`axios` — CVE-2023-45857, CVE-2025-62718, CVE-2026-40175, CVE-2026-42034,
CVE-2026-42036, CVE-2026-42038, CVE-2026-42039, CVE-2026-42041, CVE-2026-42042,
CVE-2026-44490; `jsonwebtoken` — CVE-2022-23540, CVE-2022-23541.

**CVE LOW (1):** `axios` — CVE-2026-42040.

---

## 5. Não-conformidades identificadas

1. **Cobertura incompleta de ICs:** a maioria dos ICs declarados no README está
   ausente (apenas `.gitkeep`). A baseline `v0.3-rc1` cobre, de fato, apenas
   DOC-04, CFG-01, README e o COD-02 parcial.
2. **Incoerência de stack em COD-02:** coexistem código Java (Swing, desktop) e
   manifesto Node (`package.json`) sem decisão de arquitetura registrada em
   ARQ-01. Requer **RDM** para conciliação.
3. **Pipeline de CI não funcional:** passos de lint/build/teste em `ci.yml` são
   placeholders (`echo "TODO..."`) e não validam o produto.
4. **Ausência de baselines anteriores:** não há tags `v0.1`/`v0.2`, quebrando a
   cadeia de aprovação que antecede uma release.

---

## 6. Resultado da auditoria

**Status:** APROVADA COM RESSALVAS (sujeita à homologação do CCB)

A auditoria física confirma que os ICs presentes estão corretamente
identificados e localizados, e que o inventário SBOM (`sbom.json`) está íntegro e
corresponde ao `package-lock.json` da baseline. Contudo, a baseline `v0.3-rc1`
**não** está apta a uma liberação `v1.0-Release` no estado
atual, dadas as não-conformidades da seção 5. O inventário SBOM
(`docs/auditorias/sbom.json`) está íntegro e serve de referência de composição
para a versão. As não-conformidades acima devem originar **RDMs** (etiqueta
`change-request`) para tratamento conforme o fluxo do PGCS §3.

---

## 7. Hashes de integridade (SHA-256)

O registro canônico de integridade dos artefatos desta baseline está em
[`SHA256SUMS.txt`](./SHA256SUMS.txt), gerado como passo final (após o conteúdo
dos documentos estar congelado).
