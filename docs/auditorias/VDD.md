# VDD — Version Description Document — BikeToGo

> **Documento de Descrição de Versão** · **Versão do VDD:** v0.2 ·
> **Norma base:** IEEE Std 828-2012 · **Data:** 2026-06-24 ·
> **Responsável (GC):** Davi Augusto Ferreira de Carvalho
>
> Documento de governança que descreve uma versão liberada do produto,
> consolidando identificação, modificações, composição (SBOM) e limitações.
>
> ⚠️ **Supersessão:** esta v0.2 substitui a v0.1 (baseline `v0.3-rc1`), que
> descrevia uma stack **Node fictícia** (sem código-fonte no repositório). A
> composição agora reflete o **artefato real e construível**: o app **Java/Swing**
> em `src/backend/java/`. A troca de narrativa de stack é formalmente uma mudança
> de ARQ-01 e **demanda RDM/CCB** (ver §6) — registrada aqui na branch
> `feat/atividade-sbom-vdd`.

---

## 1. Identificação da baseline

| Campo | Valor |
|-------|-------|
| Identificador único da baseline | `v0.3-rc2` |
| Marco | M3 — LB-Desenvolvimento |
| Branch | `feat/atividade-sbom-vdd` |
| Data de geração | 2026-06-24 |
| Artefato liberado (BLD-01) | `build/biketogo-0.3.0.jar` (JAR executável) |
| Versão do artefato (SemVer) | `0.3.0` |
| Toolchain de referência | **Oracle JDK 21.0.11** (`--release 21`) |
| SHA-256 do artefato | `1aae71ca3e23ab50b7f5e1d341eac33a5d5f1853794c62f036e011f8212ada28` |
| Script de build | `config/build.sh` (CFG-01) |
| SBOM associado | `docs/auditorias/sbom.json` (CycloneDX 1.5) |
| Relatório técnico | `docs/auditorias/RELATORIO-TECNICO_Etapa2_Build-SBOM-VDD.md` |

---

## 2. Inventário de modificações

### 2.1 Matriz de rastreabilidade (commits ↔ RDMs ↔ ICs)

| Commit | Data | Descrição | RDM / Issue | ICs afetados |
|--------|------|-----------|-------------|--------------|
| `1445ae0` | 2026-06-18 | SBOM, VDD e auditoria física da baseline `v0.3-rc1` (stack Node) | — (pré-baseline) | docs/auditorias |
| `f10878a` | 2026-06-17 | Estruturação inicial e upload de arquivos passados (inclui app Java) | — | DOC-04, CFG-01, COD-02, README |
| `67882a6` | 2026-06-17 | Inicialização do repositório da disciplina | — | Estrutura do repositório |
| _(pendente)_ | 2026-06-24 | Etapa 2: build script + SBOM/VDD do artefato Java | **RDM a abrir** | CFG-01, COD-02, docs/auditorias |

### 2.2 Novas funcionalidades

Nenhuma funcionalidade de aplicação nova nesta baseline. A entrega da Etapa 2 é
de **infraestrutura de Gerência de Configuração**: um processo de build
reprodutível (`config/build.sh`) que empacota o código Java pré-existente
(COD-02) em um artefato versionado e auditável (BLD-01), além do SBOM e deste VDD.

### 2.3 Correções de bugs

Nenhuma correção de bug de aplicação. Corrigida, porém, uma **não-conformidade de
GC**: o SBOM/VDD da `v0.3-rc1` inventariava dependências Node (`axios`, `pg`,
`jsonwebtoken`…) inexistentes no código. Agora a composição corresponde ao
artefato realmente construído.

---

## 3. Composição do software (SBOM)

Resumo de `docs/auditorias/sbom.json` (CycloneDX 1.5). A varredura com **Trivy
0.71.1** sobre `src/backend/java` e sobre o JAR **não encontrou pacote de
terceiros** — o app usa exclusivamente a biblioteca-padrão do JDK (`java.*`,
`javax.swing.*`).

| Métrica | Valor |
|---------|-------|
| Formato | CycloneDX 1.5 (JSON) |
| Componente principal | `biketogo-backend@0.3.0` (type: application) |
| Dependências de terceiros (runtime) | **0** |
| Dependência de plataforma | Oracle JDK 21.0.11 (Java SE 21, LTS) |
| Fontes compiladas | 20 arquivos `.java` (pacote `pooTrabalhoFinal`) |
| Conteúdo do JAR | 36 `.class` + 4 recursos (`.png`/`.jpg`) + manifesto |
| Tamanho do artefato | 175.569 bytes |

---

## 4. Problemas conhecidos e limitações

| Item | Tipo | Descrição | Por que permanece nesta versão |
|------|------|-----------|--------------------------------|
| Hash sensível ao JDK | Reprodutibilidade | O SHA-256 muda se o JDK (vendor/versão) mudar — o manifesto grava `Created-By`. Ex.: GraalVM 21.0.10 → `2aad0bee…1791` | Mitigado fixando a toolchain (Oracle JDK 21.0.11) e provisionando-a igual no CI. Ver relatório §Reprodutibilidade |
| GUI exige display | Limitação | É um app Swing desktop; não roda *headless* (CI valida só o build, não a execução) | Fora do escopo desta baseline; teste funcional fica para TST-01/TST-02 |
| `package.json` Node órfão | Limitação | `src/backend/package.json` permanece no repo sem código que o use | Remoção/decisão final depende de ARQ-01/RDM |
| Cobertura de ICs | Limitação | Maioria dos ICs ainda ausente (`.gitkeep`) | Repositório em fase de esqueleto de GC |
| Sem assinatura GPG | Limitação | Tag/baseline não assinada nesta simulação | Dispensada no contexto avaliativo |

---

## 5. Integridade — Hashes SHA-256

O registro canônico de integridade dos artefatos desta baseline está em
[`SHA256SUMS.txt`](./SHA256SUMS.txt), gerado como passo final (após o conteúdo
dos documentos estar congelado). O hash do **artefato** (`build/biketogo-0.3.0.jar`)
é emitido pelo próprio `config/build.sh` em `build/SHA256SUMS-build.txt` e
reproduzido na §1 acima.

---

## 6. Controle de mudança (RDM) — pendência

A migração da composição declarada de **Node → Java** altera a arquitetura
registrada (ARQ-01) e a composição de uma baseline já tagueada (`v0.3-rc1`).
Conforme o PGCS §3, isto exige **RDM** (issue com etiqueta `change-request`):

1. Abrir RDM descrevendo a motivação (incoerência apontada na RAC-01 §5.2) e os
   ICs impactados (incoerência de stack — RAC-01 §5, item 2; ARQ-01, COD-02, CFG-01, docs/auditorias).
2. CCB avalia e decide; se aprovada, este conjunto (build + SBOM + VDD) é
   promovido a `v0.3-rc2` e segue para `v0.3-LB-Desenvolvimento`.
