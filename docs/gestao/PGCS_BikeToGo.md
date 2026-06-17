---
ic: DOC-04
titulo: Plano de Gerência de Configuração de Software (PGCS)
projeto: BikeToGo
versao: v1.0
norma: IEEE Std 828-2012
status: aprovado
responsavel: Gerente de Configuração (GC)
localizacao: docs/gestao/
disciplina: Gerência de Configuração de Software — INF/UFG
atualizado_em: 2026-06-17
---

# Plano de Gerência de Configuração de Software — Projeto BikeToGo

> **IC:** DOC-04 · **Versão:** v1.0 · **Norma base:** IEEE Std 828-2012
>
> Versionamento deste documento: `vX.Y` no front-matter acima — `X` (major)
> muda após aprovação em CCB; `Y` é revisão menor. Alterações seguem o fluxo de
> RDM da Seção 4.1.2 (ver Seção 5). Histórico preservado no Git.

**Instituição:** Ministério da Educação · Universidade Federal de Goiás · Instituto de Informática
**Disciplina:** Gerência de Configuração de Software

**Discentes:**

| Nome | Matrícula |
|------|-----------|
| Claudio Cristiano Louza Filho | 202305528 |
| Davi Augusto Ferreira de Carvalho | 202403066 |
| Murillo Gordo de Andrade | 201602506 |
| Rodrigo Luiz Ferreira Ramos | 202403091 |

---

## 1. Introdução

### 1.1 Objetivos

Este documento estabelece o Plano de Gerência de Configuração de Software (PGCS)
do projeto BikeToGo, definindo políticas, papéis, procedimentos, ferramentas e
cronograma para identificar, controlar, registrar e auditar a evolução dos itens
de configuração ao longo de todo o ciclo de vida do produto. O plano segue a
norma IEEE Std 828-2012 — *Standard for Configuration Management in Systems and
Software Engineering* — e tem como objetivos específicos:

- Garantir a integridade, a rastreabilidade e a reprodutibilidade de todos os
  artefatos relevantes do projeto (documentação, código, design, testes e
  configuração de ambiente).
- Estabelecer um processo formal e auditável de controle de mudanças, evitando
  alterações não autorizadas e perda de informação.
- Definir linhas de base (baselines) consistentes ao longo das fases do projeto.
- Apoiar a equipe na adesão a práticas de versionamento e integração contínua,
  reduzindo retrabalho e conflitos durante o desenvolvimento.
- Suportar a conformidade do produto com requisitos legais e contratuais — em
  particular a LGPD — preservando evidências de como cada decisão foi tomada e
  implementada.

### 1.2 Escopo

Este PGCS aplica-se a todos os artefatos produzidos pela equipe do BikeToGo desde
a fase de Iniciação até a fase de Encerramento, abrangendo as seguintes
categorias de itens:

- **Documentos de engenharia:** requisitos, histórias de usuário, critérios de
  aceitação, arquitetura, modelagem de dados e protótipos.
- **Código-fonte:** front-end, back-end, scripts de banco de dados e arquivos de
  infraestrutura/CI.
- **Artefatos de teste:** planos, casos, scripts automatizados e evidências de
  execução.
- **Configuração de ambiente:** arquivos de build, parâmetros de execução,
  pipelines de CI/CD e definição de imagens Docker.

Estão **fora do escopo** deste plano: a gestão de configuração de sistemas de
terceiros que venham a ser integrados, bem como a gestão de configuração de
infraestrutura de hospedagem em produção após o encerramento da disciplina, que
deverá ser objeto de plano específico em uma eventual continuidade do projeto.

### 1.3 Domínio de Aplicação

O BikeToGo é uma plataforma mobile do domínio de marketplace de serviços e
produtos automotivos, voltada especificamente para veículos menores (bicicletas,
motocicletas e similares). A plataforma conecta clientes finais a oficinas e
borracharias locais, oferecendo cadastro de lojas e produtos, agendamento de
serviços, compra de itens e acompanhamento de status de manutenção. Por se tratar
de um domínio que envolve dados pessoais de clientes e prestadores (cadastro,
endereço, dados de veículo e pagamento), aplica-se integralmente a Lei Geral de
Proteção de Dados (LGPD), o que reforça a necessidade de uma gerência de
configuração rigorosa, capaz de evidenciar como cada requisito de privacidade foi
tratado nas diferentes versões do produto.

### 1.4 Termos-chave e Definições

| Termo / Sigla | Definição |
|---------------|-----------|
| GCS | Gerência de Configuração de Software – disciplina que controla a evolução dos artefatos do projeto. |
| IC | Item de Configuração – artefato (código, documento, modelo) submetido ao controle formal de GCS. |
| LB (Baseline) | Linha de base; conjunto de ICs formalmente revisados e aprovados que serve de referência para o desenvolvimento. |
| RDM | Requisição de Mudança – pedido formal para alteração de um IC controlado. |
| CCB | Configuration Control Board – Comitê de Controle de Mudanças responsável por avaliar e aprovar RDMs. |
| VCS | Version Control System – sistema de controle de versão (no projeto: Git/GitHub). |
| EAP | Estrutura Analítica do Projeto – decomposição hierárquica do trabalho do projeto. |
| LGPD | Lei Geral de Proteção de Dados Pessoais (Lei nº 13.709/2018). |
| MR / MVP | Merge Request / Minimum Viable Product – integração de código revisada e versão mínima funcional do produto. |
| Tag / Release | Marcação imutável no repositório que congela o estado do código em um marco do projeto. |

### 1.5 Referências

- IEEE Std 828-2012 — *IEEE Standard for Configuration Management in Systems and Software Engineering*.
- Lei nº 13.709/2018 (Lei Geral de Proteção de Dados Pessoais — LGPD).
- Guia PMBOK®, 6ª edição — referência complementar para definição de marcos e auditorias.

---

## 2. Responsabilidades e Papéis

A Gerência de Configuração no BikeToGo é uma responsabilidade compartilhada por
toda a equipe, mas coordenada por um Gerente de Configuração (GC). Outras
responsabilidades são a do Desenvolvedor e do Comitê de Controle de Mudanças.

### 2.1 Matriz de Responsabilidades

| Papel | Responsável (sugerido) | Responsabilidades |
|-------|------------------------|-------------------|
| Gerente de Configuração (GC) | Davi Augusto Ferreira de Carvalho | Elaborar, manter e divulgar o PGCS; definir padrões de identificação e versionamento; configurar e administrar o repositório; coordenar auditorias; aprovar liberação de baselines. |
| Designer UX/UI | Claudio Cristiano Louza Filho | Versionar protótipos, design system e assets gráficos como ICs; manter histórico de versões dos protótipos de alta fidelidade. |
| Desenvolvedor (Front/Back) | Murillo Gordo de Andrade | Versionar código no repositório seguindo padrão de branches e commits; abrir Merge Requests; resolver não-conformidades de configuração apontadas em auditoria. |
| Comitê de Controle de Mudanças (CCB) | Rodrigo Luiz Ferreira Ramos | Avaliar impacto, custo e risco de cada RDM; aprovar, rejeitar ou postergar mudanças; registrar decisões na ata de CCB. |

### 2.2 Comitê de Controle de Mudanças (CCB)

O CCB é a instância responsável por avaliar e autorizar mudanças em qualquer IC já
incorporado a uma linha de base. Sua composição mínima é Gerente de Configuração,
Gerente de Projeto e Analista de Requisitos; para mudanças com impacto técnico
significativo, um Desenvolvedor sênior do módulo afetado é convocado. O CCB se
reúne em duas situações:

- **Reunião ordinária:** a cada quinze dias, alinhada ao ciclo de entregas do
  projeto, para tratar todas as RDMs em aberto.
- **Reunião extraordinária:** quando uma RDM classificada como crítica (bloqueio
  de baseline, risco de prazo ou risco de conformidade com a LGPD) precisar ser
  tratada fora do ciclo regular.

As decisões do CCB são registradas em ata padronizada e anexadas ao IC DOC-04
(este PGCS) como histórico de mudanças do projeto.

---

## 3. Identificação dos Itens de Configuração

### 3.1 Critérios de Entrada de um Item no Controle de Configuração

Nem todo artefato produzido pela equipe é considerado IC. Para ser submetido ao
processo formal de GCS descrito neste plano, um artefato precisa atender, pelo
menos, aos critérios abaixo, todos validados pelo Gerente de Configuração antes do
registro no inventário de ICs:

| Critério | Descrição |
|----------|-----------|
| Relevância para o produto | O artefato é necessário para construir, entender, operar ou manter o BikeToGo. |
| Compartilhamento entre papéis | O artefato é consumido ou alterado por mais de um membro da equipe. |
| Necessidade de rastreabilidade | O artefato pode ser referenciado em mudanças futuras, auditorias ou retrabalho. |
| Impacto em baseline | Sua alteração afeta um marco já liberado ou pode comprometer entregas planejadas. |
| Conformidade legal/contratual | Artefatos exigidos pela LGPD, pela disciplina ou por stakeholders externos. |
| Aprovação do GC | Todo IC novo precisa ser registrado pelo Gerente de Configuração no inventário de ICs antes de receber código `CATEGORIA-NN`. |

### 3.2 Padrão de Nomenclatura

Cada IC recebe um código único no formato `CATEGORIA-NN`, onde `CATEGORIA`
identifica o tipo do artefato (DOC, REQ, DSG, ARQ, COD, BD, TST, CFG, BLD) e `NN`
é um número sequencial de dois dígitos atribuído pelo GC no momento do registro. O
versionamento dos ICs segue dois mecanismos complementares:

- **Documentos:** versão semântica no próprio cabeçalho do arquivo no formato
  `vX.Y` (X = versão major, alterada após aprovação em CCB; Y = revisão menor).
- **Código-fonte e configuração:** versionamento via Git, com tags assinadas para
  cada release alinhada a uma baseline (por exemplo, `v0.1-LB-Requisitos`,
  `v0.2-LB-Projeto`, `v1.0-Release`).

### 3.3 Inventário Inicial de Itens de Configuração

A tabela a seguir apresenta o conjunto inicial de ICs do projeto BikeToGo. Novos
ICs podem ser incluídos ao longo do projeto, sempre por solicitação ao GC e
mediante atendimento aos critérios de entrada definidos em 3.1.

| Código | Item de Configuração | Tipo | Localização | Responsável |
|--------|----------------------|------|-------------|-------------|
| DOC-01 | Plano de Abertura do Projeto | Documento de gestão | `docs/gestao/` | GC |
| DOC-02 | Análise do Projeto | Documento de gestão | `docs/gestao/` | GC |
| DOC-03 | EAP — Estrutura Analítica do Projeto | Documento de gestão | `docs/gestao/` | GC |
| DOC-04 | Plano de Gerência de Configuração (este documento) | Documento de gestão | `docs/gestao/` | GC |
| REQ-01 | Documento de Histórias de Usuário e Critérios de Aceitação | Documento de requisitos | `docs/requisitos/` | GC |
| REQ-02 | Especificação de requisitos funcionais e não-funcionais | Documento de requisitos | `docs/requisitos/` | GC |
| DSG-01 | Protótipo de alta fidelidade (Figma) | Artefato de design | `design/prototipo/` | Designer UX/UI |
| DSG-02 | Design System / Guia de estilo | Artefato de design | `design/system/` | Designer UX/UI |
| ARQ-01 | Documento de arquitetura e diagramas (C4 / UML) | Documento técnico | `docs/arquitetura/` | GC + Devs |
| COD-01 | Código-fonte do front-end | Código-fonte | `src/frontend/` | Desenvolvedor |
| COD-02 | Código-fonte do back-end | Código-fonte | `src/backend/` | Desenvolvedor |
| BD-01 | Scripts de banco de dados (DDL / migrations) | Código-fonte | `db/migrations/` | Desenvolvedor |
| TST-01 | Planos e casos de teste | Documento de testes | `tests/planos/` | CCB |
| TST-02 | Scripts de testes automatizados e evidências | Código + relatório | `tests/automatizados/` | CCB |
| CFG-01 | Arquivos de configuração de ambiente (.env.example, docker-compose, CI/CD) | Configuração | `config/` | CCB |
| BLD-01 | Builds liberados (releases / tags) | Pacote de entrega | GitHub Releases | CCB |

### 3.4 Estrutura Padronizada do Repositório

Todo o material controlado pelo PGCS reside em um repositório Git central (GitHub)
com a seguinte estrutura de diretórios. Essa árvore é **obrigatória**: arquivos
fora dela são considerados rascunho e não estão sob controle de configuração.

```text
bike-to-go/
├── docs/
│   ├── gestao/          ← DOC-01..04 (plano de abertura, análise, EAP, PGCS)
│   ├── requisitos/      ← REQ-01, REQ-02 (histórias de usuário e especificações)
│   ├── arquitetura/     ← ARQ-01 (diagramas C4/UML, decisões de arquitetura)
│   └── auditorias/      ← atas e relatórios de auditoria do GC
├── design/
│   ├── prototipo/       ← DSG-01 (links versionados do Figma + exports)
│   └── system/          ← DSG-02 (design system, paleta, tipografia)
├── src/
│   ├── frontend/        ← COD-01 (app mobile)
│   └── backend/         ← COD-02 (API e regras de negócio)
├── db/
│   └── migrations/      ← BD-01 (scripts de schema, seeds e migrations)
├── tests/
│   ├── planos/          ← TST-01 (plano e casos de teste)
│   └── automatizados/   ← TST-02 (scripts e relatórios de execução)
├── config/              ← CFG-01 (docker-compose, .env.example, CI/CD)
└── README.md            ← índice mestre apontando para todos os ICs
```

Regras complementares aplicadas à estrutura acima:

- Toda criação ou movimentação de diretório do primeiro nível exige aprovação do GC.
- Pull Requests precisam de ao menos uma revisão (code review) antes do merge em `main`.
- A branch `main` é protegida e só pode receber merges aprovados; releases são
  marcadas com tags assinadas.
- Arquivos pessoais, segredos e credenciais são proibidos no repositório — devem
  ser geridos por variáveis de ambiente referenciadas em `.env.example`.

---

## 4. Atividades, Cronograma e Recursos

### 4.1 Atividades de Gerência de Configuração

As atividades de GCS são organizadas em torno dos quatro processos previstos pela
IEEE 828-2012:

#### 4.1.1 Identificação de Configuração

- Manter atualizado o inventário de ICs (Tabela 3.3), incluindo novos itens
  conforme critérios de entrada.
- Definir a nomenclatura, o esquema de versionamento e a estrutura do repositório
  (Seção 3).
- Registrar as relações de rastreabilidade entre requisitos, código e testes.

#### 4.1.2 Controle de Configuração

Toda mudança em IC pertencente a uma baseline aprovada segue o fluxo de RDM:

1. Solicitante abre uma issue no GitHub com a etiqueta `change-request`,
   descrevendo motivação, ICs impactados e proposta de solução.
2. Gerente de Configuração faz triagem (formal — campos obrigatórios preenchidos)
   e classifica como baixa, média ou crítica.
3. CCB avalia impacto técnico, custo, prazo e risco (incluindo LGPD) e decide:
   Aprovada, Rejeitada ou Postergada.
4. Se aprovada, a mudança é implementada em branch dedicada, revisada via Pull
   Request e mesclada em `main`; uma nova versão do(s) IC(s) afetado(s) é emitida.
5. A RDM é fechada com referência ao commit/Pull Request que a implementou,
   garantindo rastreabilidade.

#### 4.1.3 Contabilização do Status (Status Accounting)

- Quadro Kanban no GitHub Projects mantém visão em tempo real do estado de cada
  RDM e de cada IC.
- Ao final de cada quinzena, o GC publica um Relatório de Status de Configuração
  contendo: ICs criados/modificados, RDMs abertas/fechadas, baselines liberadas e
  não-conformidades pendentes.

#### 4.1.4 Auditorias de Configuração

- **Auditoria Funcional (FCA):** verifica se a baseline atende ao que foi
  especificado e validado com os stakeholders.
- **Auditoria Física (PCA):** verifica se todos os ICs declarados estão
  fisicamente presentes no repositório, identificados conforme padrão e com versão
  correta.
- Resultados das auditorias são registrados em `docs/auditorias/` e geram, quando
  necessário, RDMs para correção.

### 4.2 Cronograma — Marcos e Linhas de Base

O cronograma de configuração é diretamente derivado do cronograma de fases do
Plano de Abertura. Cada fim de fase corresponde à liberação de uma baseline,
formalmente aprovada pelo CCB.

| Marco | Linha de Base | Início | Término | Conteúdo congelado |
|-------|---------------|--------|---------|--------------------|
| M1 | LB-Requisitos | 07/05/2026 | 28/05/2026 | Histórias de usuário, critérios de aceitação, escopo aprovado. |
| M2 | LB-Projeto/Arquitetura | 28/05/2026 | 10/06/2026 | Arquitetura, modelo de dados, protótipo de alta fidelidade, padrões de codificação. |
| M3 | LB-Desenvolvimento | 10/06/2026 | 24/06/2026 | Código integrado das funcionalidades de cadastro, pedido e acompanhamento; banco de dados; CI/CD. |
| M4 | LB-Testes/Release | 24/06/2026 | 01/07/2026 | Build de release, relatórios de teste, documentação final, tag v1.0. |

### 4.3 Recursos — Ferramentas

As ferramentas a seguir compõem o ambiente de Gerência de Configuração do
BikeToGo. Foram escolhidas por serem gratuitas, amplamente conhecidas e de baixa
curva de aprendizado para a equipe.

| Ferramenta | Finalidade | Observações de uso no projeto |
|------------|------------|-------------------------------|
| Git + GitHub | Controle de versão e repositório central | Repositório privado da equipe; branches por feature; `main` protegida; uso de tags para releases (v0.1, v0.2…). |
| GitHub Issues | Registro de RDMs e bugs | Toda RDM é aberta como issue com etiqueta `change-request`; bugs com etiqueta `bug`; cada issue referencia o(s) IC(s) afetado(s). |
| GitHub Projects | Acompanhamento de status (Status Accounting) | Quadro Kanban com colunas Backlog, Em análise (CCB), Aprovada, Em desenvolvimento, Em revisão, Concluída. |
| GitHub Actions | Pipeline de integração contínua | Build automático, execução de testes e verificação de lint em cada Pull Request. |
| Google Drive | Repositório de documentos finalizados | Cópia em PDF de documentos aprovados (gestão, requisitos) versionada em pasta dedicada por marco. |
| Figma | Versionamento de protótipo e design | Histórico nativo do Figma; cada baseline de design é exportada como link permanente registrado no PGCS. |
| Docker / docker-compose | Configuração de ambiente | Garante que dev, QA e build usem o mesmo ambiente; arquivos versionados como IC CFG-01. |

### 4.4 Recursos — Treinamentos

Para garantir a aderência ao plano, são previstos os seguintes treinamentos
internos, conduzidos pelo Gerente de Configuração nos primeiros dias da fase de
Planejamento:

| Treinamento | Público-alvo | Conteúdo mínimo |
|-------------|--------------|-----------------|
| Fluxo Git do projeto | Toda a equipe | Padrão de branches, mensagens de commit, abertura e revisão de Pull Requests, política de merge na `main`. |
| Uso do PGCS e processo de RDM | Toda a equipe | Como identificar um IC, como abrir uma RDM, papel do CCB, ciclo de aprovação/rejeição de mudanças. |
| Auditorias de configuração | Toda a equipe | Check-lists de auditoria funcional e física, registro de não-conformidades e plano de ação. |
| Boas práticas de versionamento de design | Desenvolvedor | Organização de páginas no Figma, nomeação de versões, exportação de assets para o repositório. |

---

## 5. Manutenção do Plano

Este PGCS é, ele próprio, um Item de Configuração (DOC-04). Qualquer mudança em seu
conteúdo segue o mesmo processo formal de RDM descrito na Seção 4.1.2. O documento
será revisado obrigatoriamente:

- Ao final de cada fase do projeto, em conjunto com a auditoria correspondente.
- Sempre que houver alteração relevante no escopo, na equipe ou no conjunto de
  ferramentas de apoio.
- Sempre que uma auditoria identificar não-conformidade recorrente que demande
  ajuste de processo.

As versões anteriores são preservadas no histórico do Git e referenciadas pelo
número de versão semântico (`vX.Y`) registrado no front-matter deste documento.

## 6. Conclusão

O Plano de Gerência de Configuração apresentado adapta as práticas da IEEE
828-2012 à realidade do projeto BikeToGo: uma equipe enxuta, com cronograma curto
e orçamento restrito, mas com necessidade real de rastreabilidade, controle de
mudanças e conformidade legal. A formalização dos itens de configuração, a
definição de um CCB leve, o uso intensivo do Git e a integração do cronograma de
baselines com o cronograma do projeto criam a base necessária para que a equipe
entregue um produto consistente, auditável e preparado para evoluções futuras.

---

## Histórico de versões

| Versão | Data | Descrição | Aprovação |
|--------|------|-----------|-----------|
| v1.0 | 2026-06-17 | Conversão do documento de apresentação para Markdown versionado no repositório; correções de digitação. | CCB |
