### Materiais Complementares

[05-guia-deploy-course.pdf](./05-guia-deploy-course.pdf)


### Commandos

1. Conforme estou utilizando monorepo, após eu criar o aplicativo com o comando:
    ```cmd
        heroku create -a ds-course-prod --remote heroku-prod
    ```
2. preciso rodar o seguinte comando para configurar o path do MS que eu criei
    ```cmd
        heroku config:set -a ds-course-prod APP_BASE=EAD/course
    ```
3. E Adicionar os seguintes buildpack
    ```cmd
        heroku buildpacks:add -a ds-course-prod heroku/java
    ```
4. Configurar Monorepo.
    ```cmd
        heroku buildpacks:add -a ds-course-prod https://github.com/lstoll/heroku-buildpack-monorepo -i 1
    ```
5. Compartilhar add ons CloudAMQP do authuser com course
    ```cmd
        heroku addons:attach ds-authuser-prod::CLOUDAMQP --app ds-course-prod
    ```
6. E finalizar com o push.
    ```cmd
        git push heroku-prod master
    ```
    >>> Caso fique dando erro ao tentar dar push utilize a flag ```-f``` no final do comando.

### Environments

```
    APP_BASE => EAD/course
    APP_DOMAIN_NAME => ds-course-prod.herokuapp.com
    CLOUDAMQP_APIKEY => 3ac62909-9ca2-4aa5-8d71-f2a883700d07
    CLOUDAMQP_URL => amqps://aofuoeik:kBKFjJOiyquQYW4XmmdI1oLnPu2iAfxH@moose.rmq.cloudamqp.com/aofuoeik
    CONFIG_SERVER_URL => https://cs123456:cs123456@ds-configserver-prod.herokuapp.com
    DATABASE_URL => postgres://uxyxzvyrjnxxhx:9c982ec23b0472c9ff15fad19898f4633b6c5f9ec897926f4d63572935212d1c@ec2-23-23-151-191.compute-1.amazonaws.com:5432/dchdt55e0seuhb
```