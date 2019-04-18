# JAEbot
JAEbot é o nome do User Agent do coletor desenvolvido na matéria Recuperação de Informação, no CEFET-MG, com a finalidade de estudar e aprender a arquitetura de um coletor simples para Web.

## Funcionamento do coletor
1. URL's sementes são adicionadas no escalonador 
1. O primeiro servidor acessível e que não tem fila vazia é escolhido para ter uma URL coletada.
1. Para cada URL coletada é extraído todos os links validos.
1. Novas URL's são adicionadas no final da fila de seu respectivo servidor (é criado um servidor para cada domínio novo)
1. Passos 2-4 são repetidos até que se atinja um limite de 500 páginas.

## Caracteristicas/Especificação
* Baixa apenas páginas publicas
* Obedece aos protocolos de exclusão de robôs
* Coleta exatamente 500 paginas
* Acessa cada servidor de 30 em 30 segundos
* Coleta somente até uma profundidade 4

## URLs sementes utilizadas
Foram disponibilizadas as seguintes URL’s sementes:
* https://edition.cnn.com/
* https://www.openhub.net/
* https://uai.com.br/
* https://www.msn.com/
* https://www.clarin.com/
* https://www.lanacion.com.ar/

## Datas das coletas
As coletas foram realizadas no período de 18/03 a 22/04

## Desenvolvedores
* Ana Paula Victoy ([AnaVictoy](https://github.com/AnaVictoy))
* Estevão ([jaguarsurf](https://github.com/jaguarsurf))
* Juliane Guedes ([JuGP](https://github.com/JuGP))

