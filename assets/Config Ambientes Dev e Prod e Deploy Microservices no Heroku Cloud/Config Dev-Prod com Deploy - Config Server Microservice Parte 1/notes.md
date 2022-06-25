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
        heroku config:set -a ds-configserver-prod APP_BASE=EAD/service-registry
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