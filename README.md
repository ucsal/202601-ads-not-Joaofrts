# Refatoracao Arquitetural - Olimpadas

## Visao geral da refatoracao
Este projeto foi refatorado com foco em arquitetura, organizacao e manutencao, preservando o comportamento funcional existente (cadastro de participante, prova, questao, aplicacao de prova, calculo de nota e listagem de tentativas).

A principal mudanca foi sair de um modelo centralizado em uma classe "god class" (`App`) para uma arquitetura em camadas com responsabilidades separadas.

## Problemas encontrados
- Acoplamento alto: `App` concentrava estado, regras, fluxo de menu e persistencia em memoria.
- Baixa coesao: uma unica classe fazia bootstrap, UI, validacao, negocio e acesso a dados.
- Dificuldade de extensao: qualquer mudanca exigia editar `App` diretamente.
- Violacao de DIP: regras de negocio dependiam de listas concretas, nao de abstracoes.
- Testes fracos: havia apenas teste placeholder sem cobertura de comportamento relevante.

## Decisoes arquiteturais tomadas
- Separacao em camadas e pacotes:
  - `repository` e `repository.memory`: contratos e implementacoes in-memory.
  - `service`: casos de uso e regras de aplicacao.
  - `ui`: interacao de console e controle de menu.
  - `seed`: carga inicial de dados.
  - `bootstrap`: composicao de dependencias.
- `App` passou a ser somente ponto de entrada e compatibilidade para `calcularNota`.
- Persistencia em memoria foi mantida, mas encapsulada por interfaces.
- Fluxo funcional do menu foi preservado.

## Principios SOLID aplicados
- **S (SRP)**
  - `App` agora apenas inicializa e delega.
  - `MenuController` controla somente o fluxo de UI.
  - Cada service trata um conjunto coeso de regras.
  - Repositorios tratam apenas armazenamento/consulta.
- **O (OCP)**
  - Novas estrategias de repositorio podem ser adicionadas sem alterar services (ex.: arquivo, banco).
  - UI pode evoluir para outra interface sem alterar regras de negocio.
- **L (LSP)**
  - Implementacoes in-memory substituem contratos de repositorio sem quebrar comportamento esperado.
- **I (ISP)**
  - Interfaces pequenas e especificas (`UserIO`, repositorios por agregado).
- **D (DIP)**
  - Services dependem de interfaces de repositorio, nao de `ArrayList` diretamente.
  - `MenuController` depende de `UserIO` e services, nao de detalhes concretos de armazenamento.

## Antes vs Depois
### Antes
- `App` com mais de 300 linhas.
- Estado global estatico com listas e sequenciadores de ID.
- Regras, I/O e persistencia misturados.

### Depois
- `App` minimo.
- Regras distribuidas por services especializados.
- Repositorios abstraidos por contratos.
- UI isolada em `MenuController` + `UserIO`.
- Bootstrap explicito em `ApplicationBootstrap`.
- Seed encapsulado em `DataSeeder`.

## Estrutura principal
- `src/main/java/br/com/ucsal/olimpiadas/App.java`
- `src/main/java/br/com/ucsal/olimpiadas/bootstrap/ApplicationBootstrap.java`
- `src/main/java/br/com/ucsal/olimpiadas/repository/*`
- `src/main/java/br/com/ucsal/olimpiadas/repository/memory/*`
- `src/main/java/br/com/ucsal/olimpiadas/service/*`
- `src/main/java/br/com/ucsal/olimpiadas/ui/*`
- `src/main/java/br/com/ucsal/olimpiadas/seed/DataSeeder.java`

## Como executar o projeto
### Executar testes
```powershell
./mvnw.cmd test
```

### Executar aplicacao
```powershell
./mvnw.cmd -q exec:java -Dexec.mainClass=br.com.ucsal.olimpiadas.App
```

> Observacao: caso o plugin `exec-maven-plugin` nao esteja configurado no seu ambiente atual, rode direto pela IDE com a classe `App`.

## Possiveis melhorias futuras
- Criar testes de caracterizacao para o fluxo completo de menu com entrada simulada.
- Adicionar validacoes de dominio mais explicitas (ex.: enunciado obrigatorio, alternativas nao vazias).
- Introduzir DTOs para fronteira de UI caso o projeto cresca.
- Adicionar relatorios por participante/prova em services dedicados.
- Incluir persistencia em arquivo ou banco reutilizando interfaces de repositorio.

