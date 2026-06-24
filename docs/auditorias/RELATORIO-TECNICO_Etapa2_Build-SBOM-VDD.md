# Relatório Técnico — Etapa 2 da GC: SBOM, VDD e Script de Build

> **Projeto:** BikeToGo · **IC:** registro técnico do GC · **Versão:** v1.0 ·
> **Norma base:** IEEE Std 828-2012 (PGCS / DOC-04) · **Data:** 2026-06-24 ·
> **Responsável (GC):** Davi Augusto Ferreira de Carvalho ·
> **Baseline:** `v0.3-rc2` (M3 — LB-Desenvolvimento)

---

## 1. Objetivo e escopo

A Etapa 2 do Plano de Gerência de Configuração entrega três artefatos sobre o
projeto da Etapa 1, mais este relatório. Os artefatos descrevem e produzem o
**artefato de software real** do repositório — o aplicativo **Java/Swing** em
`src/backend/java/` (pacote `pooTrabalhoFinal`):

| # | Artefato | Arquivo | IC |
|---|----------|---------|----|
| 1 | **Script de Build** | `config/build.sh` | CFG-01 |
| 2 | **SBOM** (Software Bill of Materials) | `docs/auditorias/sbom.json` | docs/auditorias |
| 3 | **VDD** (Version Description Document) | `docs/auditorias/VDD.md` | docs/auditorias |

> **Decisão de stack.** A Etapa 1 deixou o repositório com código Java real e,
> em paralelo, um `package.json` Node **sem código-fonte** (`src/server.js`
> inexistente). O SBOM/VDD da `v0.3-rc1` inventariava a stack Node fictícia.
> Como esta etapa exige um **build real** com análise de determinismo e
> reprodutibilidade, os artefatos passam a ter como alvo o **único artefato
> construível**: o app Java. A troca é uma mudança de ARQ-01 e **requer RDM/CCB**
> (PGCS §3) — ver VDD §6.

---

## 2. Resumo dos artefatos

### 2.1 Script de Build — `config/build.sh`

Compila os 20 fontes `.java` e empacota um **JAR executável**
`build/biketogo-0.3.0.jar` (saída em `build/`, que é *gitignored* — o binário é
liberado via BLD-01/Releases, não versionado). Características:

- **Toolchain auto-resolvida e robusta:** deriva o JDK de `JAVA_HOME` (ou de
  `java -XshowSettings`) e normaliza o caminho com `cygpath`, contornando o
  *shim* do Windows (`javapath`) que não expõe o utilitário `jar`.
- **Bytecode fixo:** `javac --release 21 -encoding UTF-8`.
- **Empacotamento determinístico:** entradas adicionadas em ordem estável e com
  timestamp fixo (ver §3).
- **Auto-verificação:** `./config/build.sh --verify` faz dois builds
  independentes e compara o SHA-256, falhando se divergirem.
- **Saída auditável:** imprime e grava o SHA-256 do artefato em
  `build/SHA256SUMS-build.txt`.

### 2.2 SBOM — `docs/auditorias/sbom.json`

Formato **CycloneDX 1.5**. A varredura com **Trivy 0.71.1** sobre o código e
sobre o JAR confirmou **zero dependências de terceiros** (não há lockfile nem
bibliotecas embarcadas). O SBOM, curado a partir desse resultado, registra:

- **Componente principal:** `biketogo-backend@0.3.0` (`type: application`), com o
  **SHA-256 do JAR** embutido em `hashes`.
- **Única dependência:** a **plataforma Java SE 21** (Oracle JDK 21.0.11),
  declarada como componente `type: platform` e ligada ao app no grafo
  `dependencies`. É a dependência de build **e** de runtime.
- **`vulnerabilities: []`** — superfície de terceiros nula nesta baseline.

### 2.3 VDD — `docs/auditorias/VDD.md`

Documento de descrição de versão (IEEE 828-2012): identifica a baseline
`v0.3-rc2`, o artefato e seu hash, a matriz de rastreabilidade
commits↔ICs, o resumo do SBOM, os problemas conhecidos/limitações e a pendência
de RDM. Substitui a v0.1 (que descrevia a stack Node).

---

## 3. O build é determinístico? **Sim.**

Um build é determinístico quando a **mesma entrada** (fontes + toolchain +
parâmetros) produz **sempre o mesmo artefato byte-a-byte**. Sem cuidados, um JAR
**não** é determinístico, porque o utilitário `jar` grava a hora corrente em cada
entrada do ZIP. As fontes de não-determinismo foram neutralizadas assim:

| Fonte de não-determinismo | Tratamento em `config/build.sh` |
|---------------------------|----------------------------------|
| Timestamps das entradas do JAR | `jar --date=2026-06-24T00:00:00Z` (via `SOURCE_DATE_EPOCH=1782259200`) — congela a data de todas as entradas |
| Ordem das entradas no JAR | Lista de arquivos via `find … \| LC_ALL=C sort` — ordenação estável e independente do SO |
| Ordem de compilação | Fontes passadas ao `javac` na mesma ordenação `LC_ALL=C` |
| Encoding de origem | `-encoding UTF-8` explícito (não depende do *locale*) |
| Versão de bytecode | `--release 21` fixa a versão da classe (65) e a API-alvo |
| Timestamps dentro do `.class` | Inexistentes — o formato `.class` não grava data; o `javac` é determinístico para um mesmo JDK |

**Evidência empírica:** a flag `--verify` executa dois builds limpos e
independentes. Resultado observado (Oracle JDK 21.0.11):

```
✓ REPRODUTÍVEL: os dois builds são byte-a-byte idênticos
  (1aae71ca3e23ab50b7f5e1d341eac33a5d5f1853794c62f036e011f8212ada28).
```

---

## 4. Versão em SemVer? **Sim — `0.3.0`.**

O artefato adota **Semantic Versioning 2.0.0** no formato `MAJOR.MINOR.PATCH`:

| Campo | Valor | Significado |
|-------|-------|-------------|
| MAJOR | `0` | Fase inicial/instável (pré-1.0): a API/funcionalidade ainda pode mudar de forma incompatível |
| MINOR | `3` | Alinhado ao marco **M3 — LB-Desenvolvimento** |
| PATCH | `0` | Sem correções pontuais sobre esta linha ainda |

- O nome do artefato carrega a versão: **`biketogo-0.3.0.jar`**.
- Mapeamento com as **baselines do PGCS**: a release candidate `v0.3-rc2`
  estabiliza-se em `v0.3-LB-Desenvolvimento`; a primeira versão estável de
  produção será `1.0.0` (tag assinada `v1.0-Release`).
- Regras de evolução: correção sem mudança de comportamento → incrementa PATCH;
  nova funcionalidade retrocompatível → MINOR; quebra de compatibilidade → MAJOR.

A versão é fonte única em `config/build.sh` (`APP_VERSION=0.3.0`) e replicada no
SBOM (`component.version`) e no VDD — garantindo consistência entre os ICs.

---

## 5. Análise de reprodutibilidade

**Reprodutibilidade** vai além do determinismo local: exige que **terceiros**, em
**outra máquina/momento**, regenerem o **mesmo artefato byte-a-byte** a partir do
mesmo fonte. O resultado é **reprodutível sob uma condição explícita: a toolchain
fixada**.

### 5.1 Experimento controlado

Mantendo fonte, script e `SOURCE_DATE_EPOCH` constantes e variando **apenas o
JDK**:

| Toolchain | SHA-256 do JAR | Reprodutível em 2 builds? |
|-----------|----------------|---------------------------|
| **Oracle JDK 21.0.11** (referência da baseline) | `1aae71ca…ada28` | ✓ idêntico |
| GraalVM 21.0.10 | `2aad0bee…1791` | ✓ idêntico (consigo mesmo) |

**Causa raiz da divergência entre JDKs:** o manifesto do JAR grava o atributo
`Created-By`, que reflete a versão do JDK:

```
Created-By: 21.0.11 (Oracle Corporation)     ← Oracle 21.0.11
Created-By: 21.0.10 (Oracle Corporation)     ← GraalVM 21.0.10
```

Essa única linha (e potenciais diferenças sutis de bytecode entre compiladores)
altera o hash. **Conclusão:** o build é determinístico e reprodutível **desde que
o JDK (vendor + versão) seja idêntico**. Por isso o JDK é declarado como
dependência fixa no **SBOM** e identificado no **VDD**.

### 5.2 Garantia de reprodutibilidade no CI

Para que a reprodutibilidade seja verificável de forma independente, o pipeline
(`.github/workflows/ci.yml`) foi conectado para:

1. Provisionar a **mesma toolchain** via `actions/setup-java` (JDK 21 fixado).
2. Executar `config/build.sh --verify` — falha se o build não for determinístico.

Assim, qualquer revisor reproduz o artefato apenas rodando o script com o JDK
declarado.

### 5.3 Limitações conhecidas

- A reprodutibilidade depende de **fixar o JDK**; trocar de vendor/versão muda o
  hash (esperado e documentado).
- O artefato é um app **Swing desktop**: o build é validável em CI, mas a
  **execução** exige *display* gráfico (fora do escopo desta baseline).

---

## 6. Conclusão

Os três artefatos foram entregues e são **coerentes com o código real** do
projeto. O build é **determinístico** (verificado por `--verify`, byte-a-byte),
adota **SemVer** (`0.3.0`) e é **reprodutível sob toolchain fixada** — condição
documentada no SBOM (dependência de plataforma) e demonstrada empiricamente pela
sensibilidade ao JDK. A migração da composição de Node para Java corrige a
não-conformidade da RAC-01 §5 (item 2) e **fica pendente de RDM/CCB** para promoção da
baseline `v0.3-rc2` (PGCS §3).
