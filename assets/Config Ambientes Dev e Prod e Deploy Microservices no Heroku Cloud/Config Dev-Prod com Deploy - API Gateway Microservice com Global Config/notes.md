### Materiais Complementares

[07-guia-deploy-gateway.pdf](./07-guia-deploy-gateway.pdf)

### Commandos

1. Conforme estou utilizando monorepo, após eu criar o aplicativo com o comando:
    ```cmd
        heroku create -a ds-apigateway-prod --remote heroku-prod
    ```
2. preciso rodar o seguinte comando para configurar o path do MS que eu criei
    ```cmd
        heroku config:set -a ds-apigateway-prod APP_BASE=EAD/api-gateway
    ```
3. E Adicionar os seguintes buildpack
    ```cmd
        heroku buildpacks:add -a ds-apigateway-prod heroku/java
    ```
4. Configurar Monorepo.
    ```cmd
        heroku buildpacks:add -a ds-apigateway-prod https://github.com/lstoll/heroku-buildpack-monorepo -i 1
    ```
5. E finalizar com o push.
    ```cmd
        git push heroku-prod master
    ```
    >>> Caso fique dando erro ao tentar dar push utilize a flag ```-f``` no final do comando.


# Environments

```
    APP_BASE => EAD/api-gateway
    APP_DOMAIN_NAME => ds-apigateway-prod.herokuapp.com
    CONFIG_SERVER_URL => https://cs123456:cs123456@ds-configserver-prod.herokuapp.com
```