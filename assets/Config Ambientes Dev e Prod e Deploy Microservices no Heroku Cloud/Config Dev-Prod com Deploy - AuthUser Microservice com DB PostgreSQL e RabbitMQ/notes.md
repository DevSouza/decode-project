### Links

[Heroku Database Doc](https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-jdbc_database_url)

### Materiais Complementares

[04-guia-deploy-authuser.pdf](./04-guia-deploy-authuser.pdf)

### Commandos

1. Conforme estou utilizando monorepo, após eu criar o aplicativo com o comando:
    ```cmd
        heroku create -a ds-authuser-prod --remote heroku-prod
    ```
2. preciso rodar o seguinte comando para configurar o path do MS que eu criei
    ```cmd
        heroku config:set -a ds-authuser-prod APP_BASE=EAD/authuser
    ```
3. E Adicionar os seguintes buildpack
    ```cmd
        heroku buildpacks:add -a ds-authuser-prod heroku/java
    ```
    ```cmd
        heroku buildpacks:add -a ds-authuser-prod https://github.com/lstoll/heroku-buildpack-monorepo -i 1
    ```
4. E finalizar com o push.
    ```cmd
        git push heroku-prod master
    ```
    >>> Caso fique dando erro ao tentar dar push utilize a flag ```-f``` no final do comando.

# Environments

```
    APP_BASE => EAD/authuser
    APP_DOMAIN_NAME => ds-authuser-prod.herokuapp.com
    CLOUDAMQP_APIKEY => 3ac62909-9ca2-4aa5-8d71-f2a883700d07
    CLOUDAMQP_URL => amqps://aofuoeik:kBKFjJOiyquQYW4XmmdI1oLnPu2iAfxH@moose.rmq.cloudamqp.com/aofuoeik
    CONFIG_SERVER_URL => https://cs123456:cs123456@ds-configserver-prod.herokuapp.com
    DATABASE_URL => postgres://uyenqrgtzhgqfy:5ca2689e1e5b1dbc4c155043be44314f72b7056832ea5c157253b8cc83b45923@ec2-52-71-23-11.compute-1.amazonaws.com:5432/dkgrnqoc83cda
```