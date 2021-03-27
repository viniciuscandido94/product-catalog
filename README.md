# Compasso API Rest - Catalogo Produtos

Para executar o projeto você terá que:
* Clonar o projeto
* Instalar o docker e docker-compose
* Rodar o bat "run.sh" dentro da raiz do projeto  

Vão ser criados 3 containers:
 
* 1º CompassoRestApi
* 2º CompassoDataBase 
* 3º CompassoPGAdmin

A partir disso a aplicação estará ON, basta rodar o comando "docker ps" e verá os containers com suas respectivas portas.
* Obs: A propriedade 'volumes' do container 'CompassoDataBase' no docker-compose está comentada, pode deixar assim ou configurá-lo.

Na raiz do projeto tem uma collection do postman (Compasso_API_Products_Catalog.postman_collection.json) pronta, apenas importar e configurar a URL BASE.

Api documentada com swagger, basta acessar pelo navegador o endpoint: '{baseURL}/swagger-ui.html'.
