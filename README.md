# BikeToGo

Plataforma mobile — marketplace de serviços e produtos automotivos para veículos
menores (bicicletas, motocicletas e similares), conectando clientes a oficinas e
borracharias locais.

Este repositório é gerido segundo o **Plano de Gerência de Configuração de
Software (PGCS)**, baseado na **IEEE Std 828-2012**. Antes de contribuir, leia o PGCS em [`docs/gestao/`](./docs/gestao/).

## Índice de Itens de Configuração (IC)

| Código | Item de Configuração | Localização |
|--------|----------------------|-------------|
| DOC-01 | Plano de Abertura do Projeto | `docs/gestao/` |
| DOC-02 | Análise do Projeto | `docs/gestao/` |
| DOC-03 | EAP — Estrutura Analítica do Projeto | `docs/gestao/` |
| DOC-04 | Plano de Gerência de Configuração (PGCS) | `docs/gestao/` |
| REQ-01 | Histórias de Usuário e Critérios de Aceitação | `docs/requisitos/` |
| REQ-02 | Especificação de requisitos funcionais e não-funcionais | `docs/requisitos/` |
| DSG-01 | Protótipo de alta fidelidade (Figma) | `design/prototipo/` |
| DSG-02 | Design System / Guia de estilo | `design/system/` |
| ARQ-01 | Documento de arquitetura e diagramas (C4/UML) | `docs/arquitetura/` |
| COD-01 | Código-fonte do front-end | `src/frontend/` |
| COD-02 | Código-fonte do back-end | `src/backend/` |
| BD-01  | Scripts de banco de dados (DDL/migrations) | `db/migrations/` |
| TST-01 | Planos e casos de teste | `tests/planos/` |
| TST-02 | Scripts de testes automatizados e evidências | `tests/automatizados/` |
| CFG-01 | Arquivos de configuração de ambiente | `config/` |
| BLD-01 | Builds liberados (releases / tags) | GitHub Releases |

## Papéis

| Papel | Responsável |
|-------|-------------|
| Gerente de Configuração (GC) | Davi Augusto Ferreira de Carvalho |
| Designer UX/UI | Claudio Cristiano Louza Filho |
| Desenvolvedor (Front/Back) | Murillo Gordo de Andrade |
| Comitê de Controle de Mudanças (CCB) | Rodrigo Luiz Ferreira Ramos |

## Baselines

| Marco | Linha de Base | Tag |
|-------|---------------|-----|
| M1 | LB-Requisitos | `v0.1-LB-Requisitos` |
| M2 | LB-Projeto/Arquitetura | `v0.2-LB-Projeto` |
| M3 | LB-Desenvolvimento | `v0.3-LB-Desenvolvimento` |
| M4 | LB-Testes/Release | `v1.0-Release` |

## Como contribuir

Branch dedicada → commit (Conventional Commits, referenciando IC/RDM) → Pull
Request com code review → merge em `main` (protegida). Mudanças em ICs com
baseline aprovada exigem **RDM** (issue com etiqueta `change-request`). Detalhes no PGCS.
