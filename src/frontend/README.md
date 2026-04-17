# 💰 Financial Dashboard — Frontend React

Frontend do Sistema Gerenciador Financeiro, baseado no design "The Ledger".

## 📁 Estrutura do Projeto

```
src/
├── App.jsx                    # Raiz da app com React Router
├── index.js                   # Ponto de entrada do React
├── index.css                  # Estilos globais + design tokens (variáveis CSS)
├── components/
│   ├── Sidebar.jsx            # Menu lateral fixo
│   ├── Dashboard.jsx          # Página inicial com resumo financeiro
│   ├── Transactions.jsx       # Lista de transações com filtros
│   ├── Goals.jsx              # Metas de investimento
│   ├── AIAssistant.jsx        # Chat com assistente IA
│   └── NewTransactionModal.jsx # Modal para criar transação
└── services/
    └── api.js                 # Todas as chamadas ao backend Spring Boot
```

## 🚀 Como executar

### 1. Pré-requisitos
- **Node.js 18+** — [nodejs.org](https://nodejs.org)
- **npm** (já vem com o Node)

### 2. Instale as dependências
```bash
cd financial-dashboard
npm install
```

### 3. Inicie o frontend
```bash
npm start
```
O app abre automaticamente em `http://localhost:3000`.

### 4. (Opcional) Inicie o backend Spring Boot
Se o backend estiver rodando em `http://localhost:8080`, os dados reais
serão carregados automaticamente. Se não estiver rodando, o app usa
**dados mock** para que a interface possa ser visualizada normalmente.

---

## 🔌 Como funciona a integração com o backend

O arquivo `src/services/api.js` contém todas as chamadas HTTP:

```js
const BASE_URL = 'http://localhost:8080'; // porta padrão do Spring Boot

// Exemplo de uso em um componente:
import { transacaoApi } from '../services/api';

const data = await transacaoApi.getAll();
// Se o backend estiver offline, retorna null e o componente usa mockData
```

Cada módulo da API espelha um controller do backend:
| Arquivo JS         | Controller Java           | Endpoints            |
|--------------------|--------------------------|----------------------|
| `transacaoApi`     | `TransacaoController`    | `/transacao/*`       |
| `contasApi`        | `ContasController`       | `/contas/*`          |
| `metasApi`         | `MetasController`        | `/metas/*`           |
| `cartoesApi`       | `CartoesController`      | `/cartoes/*`         |
| `categoriaApi`     | `CategoriaController`    | `/categoria/*`       |
| `tipoTransacaoApi` | `TipoTransacaoController`| `/tipo-transacao/*`  |

---

## 🎨 Sistema de Design

As cores ficam em `src/index.css` como variáveis CSS. Para mudar qualquer cor,
edite apenas o bloco `:root {}`:

```css
:root {
  --accent-200: #00668c;   /* Cor principal (botões, sidebar ativa) */
  --accent-100: #71c4ef;   /* Cor secundária (destaques sutis) */
  --bg-100: #fffefb;       /* Fundo dos cards */
  --bg-200: #f5f4f1;       /* Fundo da página */
  /* ... */
}
```

---

## 📌 Páginas e Rotas

| Rota          | Componente       | Descrição                            |
|---------------|-----------------|--------------------------------------|
| `/`           | `Dashboard`     | Visão geral, saldo total, gráfico    |
| `/transacoes` | `Transactions`  | Lista de lançamentos com filtros     |
| `/metas`      | `Goals`         | Metas financeiras com progresso      |
| `/assistente` | `AIAssistant`   | Chat com IA (simulado)               |

---

## ⚠️ CORS no backend

Para o frontend (porta 3000) falar com o backend (porta 8080), adicione
esta configuração no Spring Boot:

```java
// Em qualquer @RestController ou numa classe @Configuration:
@CrossOrigin(origins = "http://localhost:3000")

// OU globalmente em uma classe de configuração:
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```

---

## 📦 Dependências principais

| Pacote           | Versão  | Por quê                                  |
|------------------|---------|------------------------------------------|
| react            | 18      | Biblioteca base                          |
| react-router-dom | 6       | Navegação entre páginas (SPA)            |
| recharts         | 2       | Gráfico de barras no Dashboard           |
| react-scripts    | 5       | Ferramenta de build (Create React App)   |
