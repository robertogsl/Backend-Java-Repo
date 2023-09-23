# Backend-Java-Repo

Esse é um repositório público feito para meu teste técnico em um processo seletivo.

Para o desenvolvimento deste projeto utilizei o Java na versão 11 e Spring Boot 2.7 e uma tentativa de integração com docker e banco de dados H2.

Caso queria testar o funcionamento no docker execute o comando <br>
<b>docker run -p 8080:8080 spring-boot-docker-demo</b> <br>
Porém, nem todos os endpoints estarão funcionais ao usar este método, por este motivo recomendo executar o projeto pelo IntelliJ <br>

## Twillio

Para o desenvolvimento do desafio fiz integração com o twillio para notificações SMS por este motivo quando for rodar localmente será necessário algumas alterações:
### Passo 1 

Será necessário de uma conta criada no Twillio e utilizar as sua credenciais <br>
![image](https://github.com/robertogsl/Backend-Java-Repo/assets/61751830/3f0ab4df-12c1-44ae-a7f9-e59faed62a12) <br>
na application.properties do projeto como no exemplo <br>
![image](https://github.com/robertogsl/Backend-Java-Repo/assets/61751830/2dc754e6-4153-4824-bdc5-6cf23d5b3125)

### Passo 2
Também será necessário cadastrar os telefones para qual for enivar o SMS na plataforma do Twillio, até procurei uma forma de criar isso via API mas era pago. <br>
![image](https://github.com/robertogsl/Backend-Java-Repo/assets/61751830/1e46fc36-f149-412b-bd0b-4518cda5c76b) <br>

## Endpoints

### Cliente
http://localhost:8080/cliente/registrar POST - Endpoint para realizar o registro do cliente no banco de dados <br>
![image](https://github.com/robertogsl/Backend-Java-Repo/assets/61751830/ee307760-8d94-4980-8d16-90981d52b76c) <br>
Note que o CPF deverá ser um válido e deverá manter o padrão de número de telefone para integração com o Twillio <br>
<br>
http://localhost:8080/cliente/{cpf} GET - Endpoint que irá retornar o cliente pelo seu CPF <br>
<br>

### Empresa
http://localhost:8080/empresa/registrar POST -  Endpoint para realizar o registro da empresa no banco de dados <br>
![image](https://github.com/robertogsl/Backend-Java-Repo/assets/61751830/ccf32202-ff29-40c8-9e0d-5f09ccdf4735) <br>
O CNPJ também deverá ser válido! <br>
<br>
http://localhost:8080/empresa/{cnpj}/depositar/{valor}/{cpf} POST - Endpoint para um Cliente realizar um depósito para a Empresa <br>
![image](https://github.com/robertogsl/Backend-Java-Repo/assets/61751830/4ca6ea8e-9e78-4dd7-8741-3c5a3bb291c3) <br>
<br>
http://localhost:8080/empresa/{cnpj}/depositar/{valor}/{cpf} POST - Endpoint para um Cliente realizar um saque na Empresa desejada <br>
<br>
http://localhost:8080/empresa/{cnpj} GET - Endpoint que irá retornar uma empresa pelo seu CNPJ
