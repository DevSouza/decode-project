### Materiais Complementares

[01-guia-deploy-serviceregistry.pdf](./01-guia-deploy-serviceregistry.pdf)  
[Guia Monorepo.pdf](./tutorial-monorepo.pdf)  
[Como implantar um monorepo em vários aplicativos Heroku usando o GitHub Actions](https://blog.softup.co/how-to-deploy-a-monorepo-to-multiple-heroku-apps-using-github-actions/)  

### Commandos

1. Conforme estou utilizando monorepo, após eu criar o aplicativo com o comando:
    ```cmd
        heroku create -a ds-serviceregistry-prod --remote heroku-prod
    ```
2. preciso rodar o seguinte comando para configurar o path do MS que eu criei
    ```cmd
        heroku config:set -a ds-serviceregistry-prod APP_BASE=EAD/service-registry
    ```
3. E Adicionar os seguintes buildpack
    ```cmd
    heroku buildpacks:add -a ds-serviceregistry-prod heroku/java
    ```
    ```cmd
        heroku buildpacks:add -a ds-serviceregistry-prod https://github.com/lstoll/heroku-buildpack-monorepo -i 1
    ```
4. E finalizar com o push.
    ```cmd
        git push heroku-prod master
    ```
    >>> Caso fique dando erro ao tentar dar push utilize a flag ```-f``` no final do comando.