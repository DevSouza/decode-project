### Materiais Complementares

[06-guia-deploy-notification.pdf](./06-guia-deploy-notification.pdf)

### Commandos

1. Conforme estou utilizando monorepo, após eu criar o aplicativo com o comando:
    ```cmd
        heroku create -a ds-notification-prod --remote heroku-prod
    ```
2. preciso rodar o seguinte comando para configurar o path do MS que eu criei
    ```cmd
        heroku config:set -a ds-notification-prod APP_BASE=EAD/notification
    ```
3. E Adicionar os seguintes buildpack
    ```cmd
        heroku buildpacks:add -a ds-notification-prod heroku/java
    ```
4. Configurar Monorepo.
    ```cmd
        heroku buildpacks:add -a ds-notification-prod https://github.com/lstoll/heroku-buildpack-monorepo -i 1
    ```
5. Compartilhar add ons CloudAMQP do authuser com course
    ```cmd
        heroku addons:attach ds-authuser-prod::CLOUDAMQP --app ds-notification-prod
    ```
6. E finalizar com o push.
    ```cmd
        git push heroku-prod master
    ```
    >>> Caso fique dando erro ao tentar dar push utilize a flag ```-f``` no final do comando.

### Environments

```
    APP_BASE => EAD/notification
    APP_DOMAIN_NAME => ds-notification-prod.herokuapp.com
    CLOUDAMQP_APIKEY => 3ac62909-9ca2-4aa5-8d71-f2a883700d07
    CLOUDAMQP_URL => amqps://aofuoeik:kBKFjJOiyquQYW4XmmdI1oLnPu2iAfxH@moose.rmq.cloudamqp.com/aofuoeik
    CONFIG_SERVER_URL => https://cs123456:cs123456@ds-configserver-prod.herokuapp.com
    DATABASE_URL => postgres://jsombnprqdfquw:42a7f80627d38615b574bd8ea069ad1e54f2705c19882642e03455aea6f0ce74@ec2-18-204-142-254.compute-1.amazonaws.com:5432/d5bpg291s459nc
```