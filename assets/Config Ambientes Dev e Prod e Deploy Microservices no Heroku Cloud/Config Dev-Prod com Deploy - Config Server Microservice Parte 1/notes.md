### Links

[Key Generator](https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx)

### Materiais Complementares

[02-guia-deploy-configserver-parte1.pdf](./02-guia-deploy-configserver-parte1.pdf)

### Commandos

1. Conforme estou utilizando monorepo, após eu criar o aplicativo com o comando:
    ```cmd
        heroku create -a ds-configserver-prod --remote heroku-prod
    ```
2. preciso rodar o seguinte comando para configurar o path do MS que eu criei
    ```cmd
        heroku config:set -a ds-configserver-prod APP_BASE=EAD/config-server
    ```
3. E Adicionar os seguintes buildpack
    ```cmd
        heroku buildpacks:add -a ds-configserver-prod heroku/java
    ```
    ```cmd
        heroku buildpacks:add -a ds-configserver-prod https://github.com/lstoll/heroku-buildpack-monorepo -i 1
    ```
4. E finalizar com o push.
    ```cmd
        git push heroku-prod master
    ```
    >>> Caso fique dando erro ao tentar dar push utilize a flag ```-f``` no final do comando.

### Configurações extras
Para que o config server funcione corretamente sera necessario criar uma variavel de ambiente chamada ```LANG``` com o seguinte valor ```en_US.UTF-8```

### Environments

```
    APP_BASE => EAD/config-server
    APP_DOMAIN_NAME => ds-configserver-prod.herokuapp.com
    CONFIG_SERVER_PASSWORD => cs123456
    CONFIG_SERVER_USERNAME => cs123456
    GIT_PASSWORD => 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
    GIT_USERNAME => devsouza
    LANG => en_US.UTF-8
    EUREKA_URL => https://sr123456:sr123456@ds-serviceregistry-prod.herokuapp.com/eureka
```