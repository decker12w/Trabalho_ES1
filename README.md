## Como rodar o projeto

Para rodar o projeto Spring Boot, é necessário ter o **Maven** instalado no seu computador, pois ele é a ferramenta utilizada para gerenciar as dependências e compilar o projeto. Se o Maven não estiver instalado, você pode instalar seguindo a documentação oficial.

Após a instalação do Maven, siga os passos abaixo para rodar o projeto:

1. **Abrir o terminal**:

   - Navegue até o diretório raiz do projeto, onde o arquivo `pom.xml` está localizado.

2. **Rodar o projeto**:
   - No terminal, execute o seguinte comando:
     ```
     mvn spring-boot:run
     ```
   - Esse comando irá compilar o projeto, baixar as dependências e iniciar a aplicação Spring Boot. Após isso, a aplicação estará rodando, geralmente na porta `8080` (a menos que configurado de outra forma).

---

### Como rodar os testes

Existem três formas principais de rodar os testes da aplicação:

1. **Usando Maven no terminal**:

   - Se você tem o Maven instalado, abra o terminal no diretório raiz do projeto e execute o comando:
     ```
     mvn test
     ```
   - Esse comando vai compilar o projeto e executar todos os testes unitários configurados no projeto, fornecendo um relatório com os resultados dos testes.

2. **Usando a interface da sua IDE**:

   - Caso você esteja utilizando uma IDE como **VSCode**, **Eclipse** ou **IntelliJ**, você pode rodar os testes diretamente pela interface gráfica. Essas IDEs geralmente têm a opção de executar os testes com Maven já configurada. Você pode clicar com o botão direito no projeto ou na pasta de testes e selecionar a opção de rodar os testes.

3. **Usando GitHub Actions**:
   - Caso prefira rodar os testes automaticamente ao fazer alterações no código e realizar **push** ou **pull request** no GitHub, você pode configurar **GitHub Actions** para rodar os testes automaticamente. Isso facilita o processo de integração contínua, garantindo que os testes sejam sempre executados nas atualizações do código.

......
