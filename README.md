# ☕ CafeteriaWeb

Aplicação web de cafeteria personalizada, com frontend em Angular 19 e backend em Java Spring Boot.
O sistema permite que o cliente monte sua própria bebida com seleção de ingredientes e identifica 
automaticamente se ela corresponde a uma bebida clássica como Mocha, Latte ou Cappuccino.

## 📌 Visão Geral

- Cliente monta bebidas com até **3 ingredientes base** e **2 adicionais**
- Ingredientes extras são automaticamente **desabilitados** após o limite
- Sistema identifica **bebidas clássicas** com base na combinação
- Cadastro, login, edição de perfil e gerenciamento completo de pedidos
- Interface intuitiva com **passo a passo de uso**
- Backend desacoplado com arquitetura limpa e migrations versionadas

## 🧠 Conceitos Técnicos Aplicados

- 🔐 Autenticação JWT (JSON Web Token)
- 🔧 Arquitetura RESTful com DTOs, Assemblers, Disassemblers
- 🔄 Redirecionamento inteligente no fluxo de autenticação
- 🗃️ Relacionamento many-to-many com PostgreSQL e JPA
- 🚦 Regras de negócio com desabilitação dinâmica de seleção
- 🧠 Identificação de bebidas clássicas com `Set` e `List` (ordem e duplicidade irrelevantes)
- 🧪 Scripts SQL e dados de teste
- ⚙️ Separação clara por ambiente (dev, prod, docker)

## 🚀 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker
- Hospedagem: **Render (via Docker Container)**

### Frontend
- Angular 19 (standalone API)
- Angular Reactive Forms
- Lazy loading com roteamento
- Consumo de API RESTful
- Hospedagem: **Render (via Git Deploy)**
