### Comandos 

Caso queira mudar de branch ou tenha removido o remote repository do heroku e queira adicionar basta rodar o seguinte comando.
```cmd
    heroku git:remote -a ds-authuser-prod --remote authuser-prod
```

e para dar o push dessa nova branch para o heroku

```cmd
    git push authuser-prod master -f
```