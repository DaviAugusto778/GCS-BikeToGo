# VDD — Version Description Document — BikeToGo

> **Documento de Descrição de Versão** · **Versão do VDD:** v0.1 ·
> **Norma base:** IEEE Std 828-2012 · **Data:** 2026-06-18 ·
> **Responsável (GC):** Davi Augusto Ferreira de Carvalho
>
> Documento de governança que descreve uma versão liberada do produto,
> consolidando identificação, modificações, composição (SBOM) e limitações.

---

## 1. Identificação da baseline

| Campo | Valor |
|-------|-------|
| Identificador único da baseline | `v0.3-rc1` |
| Marco | M3 — LB-Desenvolvimento |
| Commit-base (HEAD) | `f10878a` |
| Branch | `main` |
| Data de geração | 2026-06-18 |
| Tag associada | `v0.3-rc1` (tag anotada; assinatura GPG dispensada nesta simulação) |
| SBOM associado | `docs/auditorias/sbom.json` |
| Relatório de auditoria | `docs/auditorias/RAC-01_Auditoria_Configuracao_v0.3-rc1.md` |

---

## 2. Inventário de modificações

### 2.1 Matriz de rastreabilidade (commits ↔ RDMs ↔ ICs)

| Commit | Data | Descrição | RDM / Issue | ICs afetados |
|--------|------|-----------|-------------|--------------|
| `f10878a` | 2026-06-17 | Estruturação inicial e upload de arquivos passados | — (sem RDM; pré-baseline) | DOC-04, CFG-01, COD-02, README |
| `67882a6` | 2026-06-17 | Inicialização do repositório específico para a disciplina | — | Estrutura do repositório |

> Observação: o `package.json` (COD-02) foi adicionado para esta atividade e
> **ainda não está commitado/tagueado**. Ao incluí-lo, registre o commit aqui e
> a RDM correspondente (a adoção da stack Node é uma mudança que demanda
> ARQ-01/RDM).

### 2.2 Novas funcionalidades

Derivado das mensagens de commit (Seção 2.1): até esta baseline não há
funcionalidade de aplicação entregue. Os commits cobrem apenas a estruturação do
repositório de Gerência de Configuração (DOC-04, CFG-01, estrutura de ICs).

### 2.3 Correções de bugs

Nenhuma correção de bug registrada até esta baseline.

---

## 3. Composição do software (SBOM)

Resumo extraído de `docs/auditorias/sbom.json`, gerado por **Trivy** a partir de
`src/backend/package-lock.json`.

| Métrica | Valor |
|---------|-------|
| Formato | CycloneDX JSON |
| Ferramenta / versão | Trivy 0.71.1 (DB v2, 2026-06-18) |
| Pacotes resolvidos (npm audit) | 509 |
| Componentes totais no SBOM | 492 |
| Dependências diretas (prod) | 7 — `express`, `pg`, `jsonwebtoken`, `bcryptjs`, `dotenv`, `axios`, `lodash` |
| Dependências de desenvolvimento | 3 — `jest`, `eslint`, `nodemon` |
| Dependências transitivas | demais (resolvidas no lockfile) |

---

## 4. Problemas conhecidos e limitações

> Para vulnerabilidades, basear-se no relatório do Trivy e justificar por que
> permanecem nesta versão.

| Item | Tipo | Descrição | Por que permanece nesta versão |
|------|------|-----------|--------------------------------|
| 21 CVEs em `axios@0.21.4` (10 HIGH, 10 MEDIUM, 1 LOW) | Vulnerabilidade | SSRF, prototype pollution, vazamento de credenciais de proxy, DoS, RCE | Correção exige upgrade *major* para `0.32.0`/`1.16.0` (quebra compatibilidade); avaliação e adoção dependem de **RDM/CCB**. Exposição é teórica nesta baseline (sem código de aplicação consumindo a lib ainda) |
| 3 CVEs em `jsonwebtoken@8.5.1` (1 HIGH, 2 MEDIUM) | Vulnerabilidade | Uso de chave legada, algoritmo inseguro por padrão, recuperação de chave insegura | Correção exige upgrade *major* para `9.0.0` (quebra compatibilidade); requer **RDM/CCB**. Remediação planejada antes do M4/`v1.0-Release` |
| Stack indefinida | Limitação | COD-02 mistura Java (Swing) e Node (`package.json`) | Conciliação depende de ARQ-01 / RDM |
| CI placeholder | Limitação | Lint/build/teste não executam de fato | Stack ainda não escolhida formalmente |
| Cobertura de ICs | Limitação | Maioria dos ICs ausente (`.gitkeep`) | Repositório em fase de esqueleto de GC |

---

## 5. Integridade — Hashes SHA-256

O registro canônico de integridade dos artefatos desta baseline está em
[`SHA256SUMS.txt`](./SHA256SUMS.txt), gerado como passo final (após o conteúdo
dos documentos estar congelado). Isso evita o problema de auto-referência (o
hash de um arquivo não pode constar dentro dele mesmo).
